package admin.adminsiteserver.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static admin.adminsiteserver.member.domain.RoleType.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 도메인 테스트")
class MemberTest {

    private Member member;

    @BeforeEach
    void init() {
        member = Member.builder()
                .email("admin@admin.com")
                .password("admin")
                .name("김철수")
                .studentNumber("201600000")
                .phoneNumber("010-0000-0000")
                .filePath(new MemberFilePath("치킨.png", "https://cloudfront.com/admin"))
                .role(MEMBER)
                .build();
    }

    @DisplayName("회원 정보(이름, 학번, 연락처)를 업데이트 한다.")
    @Test
    void updateMemberInfo() {
        member.updateMemberInfo("김짱구", "201700000", "010-1111-1111");

        Member testMember = Member.builder()
                .email("admin@admin.com")
                .password("admin")
                .name("김짱구")
                .studentNumber("201700000")
                .phoneNumber("010-1111-1111")
                .filePath(new MemberFilePath("치킨.png", "https://cloudfront.com/admin"))
                .role(MEMBER)
                .build();
        assertThat(member).isEqualTo(testMember);
    }

    @DisplayName("회원의 비밀번호를 업데이트 한다.")
    @Test
    void updatePassword() {
        member.updatePassword("afterPassword");

        assertThat(member.getPassword()).isEqualTo("afterPassword");
    }

    @DisplayName("회원의 프로필 이미지를 업데이트 한다.")
    @Test
    void uploadProfileImage() {
        member.uploadProfileImage(new MemberFilePath("피자.png", "https://cloudfront.com/admin2"));

        assertThat(member.getFilePath()).isEqualTo(new MemberFilePath("피자.png", "https://cloudfront.com/admin2"));
    }

    @DisplayName("회원의 역할을 업데이트 한다.")
    @ParameterizedTest
    @EnumSource(RoleType.class)
    void updateRole(RoleType roleType) {
        member.updateRole(roleType);

        assertThat(member.getRole()).isEqualTo(roleType);
    }

    @DisplayName("Security Context에 추가할 회원의 권한 정보를 반환한다.")
    @ParameterizedTest
    @EnumSource(RoleType.class)
    void securityRoleType(RoleType roleType) {
        member.updateRole(roleType);

        assertThat(member.securityRoleType()).isEqualTo(roleType.getRole());
    }

    @DisplayName("회원의 이메일 존재 여부를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"admin@admin.com:true", "null:false"}, nullValues = "null", delimiter = ':')
    void hasEmail(String email, boolean hasEmail) {
        Member testMember = Member.builder()
                .email(email)
                .build();

        assertThat(testMember.hasEmail()).isEqualTo(hasEmail);
    }

    @DisplayName("회원의 삭제 여부를 true로 변경한다. 회원의 삭제 여부를 반환한다.")
    @Test
    void delete_isDeleted() {
        assertThat(member.isDeleted()).isFalse();
        member.delete();
        assertThat(member.isDeleted()).isTrue();
    }
}