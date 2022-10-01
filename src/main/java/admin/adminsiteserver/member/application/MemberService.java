package admin.adminsiteserver.member.application;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.exception.MemberAlreadyExistException;
import admin.adminsiteserver.member.exception.StudentNumberAlreadyExistException;
import admin.adminsiteserver.member.exception.MemberNotFoundException;
import admin.adminsiteserver.member.ui.request.SignUpRequest;
import admin.adminsiteserver.member.ui.request.UpdateMemberRequest;
import admin.adminsiteserver.member.ui.request.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static final String MEMBER_IMAGE_PATH = "members/";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    public Long signUp(SignUpRequest request) {
        Member member = request.toMember(passwordEncoder.encode(request.getPassword()));
        checkAlreadyExist(member);

        Member signupMember = memberRepository.save(member);
        saveImage(signupMember, request);

        return signupMember.getId();
    }

    private void checkAlreadyExist(Member member) {
        String email = member.getEmail();
        String studentNumber = member.getStudentNumber();

        if (memberRepository.existsByEmailOrStudentNumberAndDeletedFalse(email, studentNumber)) {
            throw new MemberAlreadyExistException();
        }
    }

    private void saveImage(Member member, SignUpRequest request) {
        if (request.hasImage()) {
            FilePath filePath = s3Uploader.upload(request.getImage(), MEMBER_IMAGE_PATH);
            member.updateImage(filePath.getFileName(), filePath.getFileUrl());
        }
    }

    public void update(Long id, UpdateMemberRequest request) {
        Member member = findMemberById(id);
        validateStudentNumber(member, request.getStudentNumber());
        member.update(request.getName(), request.getStudentNumber(), request.getPhoneNumber());
    }

    private void validateStudentNumber(Member member, String studentNumber) {
        if (member.sameStudentNumber(studentNumber)) {
            return;
        }

        if (memberRepository.existsByStudentNumberAndDeletedFalse(studentNumber)) {
            throw new StudentNumberAlreadyExistException();
        }
    }

    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Member member = findMemberById(id);
        member.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    public void updateImage(Long id, MultipartFile image) {
        Member member = findMemberById(id);
        FilePath filePath = s3Uploader.upload(image, MEMBER_IMAGE_PATH);
        member.updateImage(filePath.getFileName(), filePath.getFileUrl());
    }

    public void delete(Long id) {
        Member member = findMemberById(id);
        member.delete();
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }
}
