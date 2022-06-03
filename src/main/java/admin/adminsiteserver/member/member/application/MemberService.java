package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.member.application.dto.MemberResponse;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberFilePath;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.AlreadyExistStudentNumberException;
import admin.adminsiteserver.member.member.exception.AlreadyExistUserIDException;
import admin.adminsiteserver.member.member.exception.NotExistMemberException;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private static final String MEMBER_IMAGE_PATH = "member/";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    public MemberResponse signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember(passwordEncoder);
        checkAlreadySignUp(member);
        Member signupMember = memberRepository.save(member);

        if (signUpRequest.hasImage()) {
            FilePathDto filePathDto = s3Uploader.upload(signUpRequest.getImage(), MEMBER_IMAGE_PATH);
            signupMember.uploadProfileImage(filePathDto.toFilePath(MemberFilePath.class));
            return MemberResponse.of(signupMember, filePathDto);
        }
        return MemberResponse.from(signupMember);
    }

    private void checkAlreadySignUp(Member signUpMember) {
        Optional<Member> member = memberRepository.findByEmailOrStudentNumber(signUpMember.getEmail(), signUpMember.getStudentNumber());
        if (member.isPresent()) {
            throw new AlreadyExistUserIDException();
        }
    }

    public void updateInfo(UpdateMemberRequest updateMemberRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotExistMemberException::new);

        Optional<Member> updateMember = memberRepository.findByStudentNumberAndDeletedFalse(updateMemberRequest.getStudentNumber());
        if (updateMember.isPresent()) {
            throw new AlreadyExistStudentNumberException();
        }

        member.updateMemberInfo(updateMemberRequest.getName(),
                updateMemberRequest.getStudentNumber(),
                updateMemberRequest.getPhoneNumber());
    }

    public void updatePassword(Member loginMember, UpdatePasswordRequest request) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NotExistMemberException::new);
        member.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    public void updateImage(Member loginMember, MultipartFile image) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NotExistMemberException::new);

        MemberFilePath filePath = s3Uploader.upload(image, MEMBER_IMAGE_PATH)
                .toFilePath(MemberFilePath.class);

        member.uploadProfileImage(filePath);
    }

    public void delete(Member loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NotExistMemberException::new);
        member.delete();
    }
}
