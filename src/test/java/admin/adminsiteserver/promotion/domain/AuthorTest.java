package admin.adminsiteserver.promotion.domain;

import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("작성자 단위테스트")
class AuthorTest {
    @Nested
    class 권한을_검증할_때 {
        @Test
        void 같은_작성자면_검증에_성공한다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);

            assertDoesNotThrow(() -> author.validate(author));
        }

        @Test
        void 접근권한이_같은_다른_작성자면_예외가_발생한다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(2L, "김철수", "202011111", RoleType.OFFICER);

            assertThatThrownBy(() -> author.validate(other))
                    .isInstanceOf(PermissionDeniedException.class);
        }

        @Test
        void 같은_작성자면_권한을_가진다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);

            assertThat(author.hasAuthority(author)).isTrue();
        }

        @Test
        void 다른_작성자면_접근권한이_없다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(2L, "김철수", "202011111", RoleType.OFFICER);

            assertThat(author.hasAuthority(other)).isFalse();
        }

        @Test
        void 접근권한이_낮은_작성자면_권한을_가진다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.ADMIN);
            Author other = new Author(2L, "김철수", "202011111", RoleType.OFFICER);

            assertThat(author.hasAuthority(other)).isFalse();
        }

        @Test
        void 작성자의_ID가_같으면_true를_반환한다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);

            assertThat(author.equalsId(author)).isTrue();
        }

        @Test
        void 작성자의_ID가_다르면_false를_반환한다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.MEMBER);
            Author other = new Author(2L, "김철수", "202011111", RoleType.ADMIN);

            assertThat(author.equalsId(other)).isFalse();
        }
    }

    @Nested
    class 동일성_동등성_검증 {
        @Test
        void 두_작성자가_동등하다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);

            assertThat(author).isEqualTo(other);
        }

        @Test
        void 두_작성자가_동등하지_않다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(2L, "김철수", "202011111", RoleType.ADMIN);

            assertThat(author).isNotEqualTo(other);
        }

        @Test
        void 두_작성자의_hashCode가_같다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);

            assertThat(author).hasSameHashCodeAs(other);
        }

        @Test
        void 두_작성자의_hashCode가_다르다() {
            Author author = new Author(1L, "홍길동", "202012345", RoleType.OFFICER);
            Author other = new Author(2L, "김철수", "202011111", RoleType.ADMIN);

            assertThat(author.hashCode()).doesNotHaveSameHashCodeAs(other);
        }
    }
}
