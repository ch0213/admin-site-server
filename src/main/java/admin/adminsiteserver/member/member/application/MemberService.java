package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberFilePath;
import admin.adminsiteserver.member.member.domain.MemberFilePathRepository;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.AlreadyExistUserIDException;
import admin.adminsiteserver.member.member.exception.NotExistMemberException;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public MemberDto signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember(passwordEncoder);
        memberRepository.findByUserId(member.getUserId())
                .ifPresent(m -> {
                    throw new AlreadyExistUserIDException();
                });
        Member signupMember = memberRepository.save(member);

        if (signUpRequest.getImage() != null) {
            FilePathDto filePathDto = s3Uploader.upload(signUpRequest.getImage(), MEMBER_IMAGE_PATH);
            signupMember.addProfileImage(filePathDto.toFilePath(MemberFilePath.class));
            return MemberDto.of(signupMember, filePathDto);
        }
        return MemberDto.from(signupMember);
    }

    @Transactional
    public void updateMember(UpdateMemberRequest updateMemberRequest, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(NotExistMemberException::new);
        member.update(updateMemberRequest.getEmail(),
                updateMemberRequest.getName(),
                updateMemberRequest.getStudentNumber(),
                updateMemberRequest.getPhoneNumber());
    }

    @Transactional
    public void updateMemberImage(MultipartFile multipartFile, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(NotExistMemberException::new);
        s3Uploader.delete(member.getFilePath().getFileUrl());
        MemberFilePath filePath = filePathRepository.save(s3Uploader.upload(multipartFile, MEMBER_IMAGE_PATH).toFilePath(MemberFilePath.class));
        member.addProfileImage(filePath);
    }

    @Transactional
    public void deleteMember(String userId) {
        memberRepository.deleteByUserId(userId);
    }
}
