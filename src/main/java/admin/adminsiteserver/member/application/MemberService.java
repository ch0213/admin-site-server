package admin.adminsiteserver.member.application;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberEventType;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.domain.MemberEvent;
import admin.adminsiteserver.member.exception.MemberAlreadyExistException;
import admin.adminsiteserver.member.exception.StudentNumberAlreadyExistException;
import admin.adminsiteserver.member.exception.MemberNotFoundException;
import admin.adminsiteserver.member.ui.request.SignUpRequest;
import admin.adminsiteserver.member.ui.request.UpdateMemberRequest;
import admin.adminsiteserver.member.ui.request.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static final String MEMBER_IMAGE_PATH = "members/";

    private final ApplicationEventPublisher eventPublisher;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    public Long signUp(SignUpRequest request) {
        String password = passwordEncoder.encode(request.getPassword());
        Member member = request.toEntity(password);
        validateAlreadyExist(member);

        Member saveMember = memberRepository.save(member);
        saveImage(saveMember, request);
        return saveMember.getId();
    }

    public void update(Long memberId, UpdateMemberRequest request) {
        Member member = findById(memberId);
        validateStudentNumber(member, request.getStudentNumber());

        member.update(request.getName(), request.getStudentNumber(), request.getPhoneNumber());
        eventPublisher.publishEvent(new MemberEvent(this, member.getId(), MemberEventType.UPDATE));
    }

    public void updatePassword(Long memberId, UpdatePasswordRequest request) {
        Member member = findById(memberId);
        String newPassword = passwordEncoder.encode(request.getPassword());
        member.updatePassword(newPassword);
    }

    public void updateProfileImage(Long memberId, MultipartFile image) {
        Member member = findById(memberId);
        FilePath filePath = s3Uploader.upload(image, MEMBER_IMAGE_PATH);
        member.updateImage(filePath.getFileName(), filePath.getFileUrl());
    }

    public void delete(Long memberId) {
        Member member = findById(memberId);
        member.delete();
    }

    private void validateAlreadyExist(Member member) {
        String email = member.getEmail();
        String studentNumber = member.getStudentNumber();

        if (memberRepository.existsByEmailOrStudentNumberAndDeletedFalse(email, studentNumber)) {
            throw new MemberAlreadyExistException();
        }
    }

    private void validateStudentNumber(Member member, String studentNumber) {
        if (member.sameStudentNumber(studentNumber)) {
            return;
        }

        if (memberRepository.existsByStudentNumberAndDeletedFalse(studentNumber)) {
            throw new StudentNumberAlreadyExistException();
        }
    }

    private void saveImage(Member member, SignUpRequest request) {
        if (request.hasImage()) {
            FilePath filePath = s3Uploader.upload(request.getImage(), MEMBER_IMAGE_PATH);
            member.updateImage(filePath.getFileName(), filePath.getFileUrl());
        }
    }

    private Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }
}
