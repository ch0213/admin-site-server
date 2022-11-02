package admin.adminsiteserver.member.domain;

import admin.adminsiteserver.common.domain.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static admin.adminsiteserver.member.fixture.MemberFixture.회원1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("회원 단위 테스트")
class MemberTest {
    @DisplayName("회원 정보(이름, 학번, 연락처)를 업데이트 한다.")
    @Test
    void updateMemberInfo() {
        Member member = 회원1.toEntity();
        member.update("김짱구", "201700000", "010-1111-1111");

        assertAll(
                () -> assertThat(member.getName()).isEqualTo("김짱구"),
                () -> assertThat(member.sameStudentNumber("201700000")).isTrue(),
                () -> assertThat(member.getPhoneNumber()).isEqualTo("010-1111-1111")
        );
    }

    @DisplayName("회원의 비밀번호를 업데이트 한다.")
    @Test
    void updatePassword() {
        Member member = 회원1.toEntity();
        member.updatePassword("afterPassword");

        assertThat(member.getPassword()).isEqualTo("afterPassword");
    }

    @DisplayName("회원의 프로필 이미지를 업데이트 한다.")
    @Test
    void uploadProfileImage() {
        Member member = 회원1.toEntity();
        member.updateImage("피자.png", "https://cloudfront.com/admin2");

        assertThat(member.getFilePath())
                .isEqualTo(new MemberFilePath("피자.png", "https://cloudfront.com/admin2"));
    }

    @DisplayName("회원의 역할을 업데이트 한다.")
    @ParameterizedTest
    @EnumSource(RoleType.class)
    void updateRole(RoleType roleType) {
        Member member = 회원1.toEntity();
        member.updateRole(roleType);

        assertThat(member.getRole()).isEqualTo(roleType);
    }

    @DisplayName("Security Context에 추가할 회원의 권한 정보를 반환한다.")
    @ParameterizedTest
    @EnumSource(RoleType.class)
    void securityRoleType(RoleType roleType) {
        Member member = 회원1.toEntity();
        member.updateRole(roleType);

        assertThat(member.getRoleType()).isEqualTo(roleType.getRole());
    }

    @DisplayName("회원의 이메일이 존재할 경우 True를 반환한다.")
    @Test
    void hasEmail() {
        Member member = Member.builder()
                .email("admin@admin.com")
                .build();

        assertThat(member.hasEmail()).isTrue();
    }

    @DisplayName("회원의 이메일이 존재하지 않을 경우 False를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void hasNotEmail(String email) {
        Member member = Member.builder()
                .email(email)
                .build();

        assertThat(member.hasEmail()).isFalse();
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        Member member = 회원1.toEntity();
        member.delete();
        assertThat(member.isDeleted()).isTrue();
    }
}
