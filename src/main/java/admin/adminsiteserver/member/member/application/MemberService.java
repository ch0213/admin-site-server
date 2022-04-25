package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.application.dto.MemberResponse;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberFilePath;
import admin.adminsiteserver.member.member.domain.MemberFilePathRepository;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.AlreadyExistUserIDException;
import admin.adminsiteserver.member.member.exception.NotExistMemberException;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.INQUIRE_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberFilePathRepository filePathRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;
    private static final String MEMBER_IMAGE_PATH = "member/";

    @Transactional
    public MemberResponse signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember(passwordEncoder);
        checkAlreadySignUp(member);
        Member signupMember = memberRepository.save(member);

        if (signUpRequest.hasImage()) {
            FilePathDto filePathDto = s3Uploader.upload(signUpRequest.getImage(), MEMBER_IMAGE_PATH);
            signupMember.addProfileImage(filePathDto.toFilePath(MemberFilePath.class));
            return MemberResponse.of(signupMember, filePathDto);
        }
        return MemberResponse.from(signupMember);
    }

    private void checkAlreadySignUp(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {throw new AlreadyExistUserIDException();});
    }

    @Transactional
    public void updateMember(UpdateMemberRequest updateMemberRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotExistMemberException::new);
        member.updateMemberInfo(updateMemberRequest.getName(),
                updateMemberRequest.getStudentNumber(),
                updateMemberRequest.getPhoneNumber());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotExistMemberException::new);
        member.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    @Transactional
    public void updateMemberImage(MultipartFile multipartFile, String userId) {
        Member member = memberRepository.findByEmail(userId)
                .orElseThrow(NotExistMemberException::new);
        s3Uploader.delete(FilePathDto.from(member.getFilePath()));
        MemberFilePath filePath = filePathRepository.save(s3Uploader.upload(multipartFile, MEMBER_IMAGE_PATH).toFilePath(MemberFilePath.class));
        member.addProfileImage(filePath);
    }

    @Transactional
    public void deleteMember(String userId) {
        Member member = memberRepository.findByEmail(userId)
                .orElseThrow(NotExistMemberException::new);
        s3Uploader.delete(FilePathDto.from(member.getFilePath()));
        memberRepository.deleteByEmail(userId);
    }

    public MemberResponse findMyself(LoginUserInfo loginUserInfo) {
        Member member = memberRepository.findByEmail(loginUserInfo.getEmail())
                .orElseThrow(NotExistMemberException::new);
        return MemberResponse.from(member);
    }

    public CommonResponse<List<MemberResponse>> findMembers(Pageable pageable) {
        Page<MemberResponse> members = memberRepository.findAll(pageable).map(MemberResponse::from);
        return CommonResponse.of(members.getContent(), PageInfo.from(members), INQUIRE_SUCCESS.getMessage());
    }
}
