package admin.adminsiteserver.promotion.domain;

import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import admin.adminsiteserver.promotion.exception.WrongPromotionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static admin.adminsiteserver.common.domain.RoleType.OFFICER;
import static admin.adminsiteserver.common.domain.RoleType.PRESIDENT;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static admin.adminsiteserver.promotion.domain.PromotionStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("승진 신청 단위테스트")
class PromotionTest {
    @Test
    void 승진_신청을_생성한다() {
        Promotion promotion = new Promotion(회원1.author(Author::new), OFFICER);

        assertThat(promotion.getStatus()).isEqualTo(WAITING);
    }

    @ValueSource(strings = {"OFFICER", "MEMBER"})
    @ParameterizedTest
    void 현재_직책보다_같거나_낮은_직책으로_신청할_수_없다(RoleType role) {
        assertThatThrownBy(() -> new Promotion(임원1.author(Author::new), role))
                .isInstanceOf(WrongPromotionException.class);
    }

    @Test
    void 승진_신청을_변경한다() {
        Author author = 회원1.author(Author::new);
        Promotion promotion = new Promotion(author, OFFICER);

        promotion.update(author, PRESIDENT);

        assertAll(
                () -> assertThat(promotion.getStatus()).isEqualTo(WAITING),
                () -> assertThat(promotion.getRole()).isEqualTo(PRESIDENT)
        );
    }

    @ValueSource(strings = {"OFFICER", "MEMBER"})
    @ParameterizedTest
    void 현재_직책보다_같거나_낮은_직책으로_변경할_수_없다(RoleType role) {
        Author author = 임원1.author(Author::new);
        Promotion promotion = new Promotion(author, PRESIDENT);

        assertThatThrownBy(() -> promotion.update(author, role))
                .isInstanceOf(WrongPromotionException.class);
    }

    @Test
    void 다른_사람의_승진_신청을_변경할_수_없다() {
        Author author1 = 회원1.author(Author::new);
        Author author2 = 회원2.author(Author::new);
        Promotion promotion = new Promotion(author1, OFFICER);

        assertThatThrownBy(() -> promotion.update(author2, PRESIDENT))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 직책이_높더라도_다른_사람의_승진_신청을_변경할_수_없다() {
        Author author1 = 회원1.author(Author::new);
        Author author2 = 임원1.author(Author::new);
        Promotion promotion = new Promotion(author1, OFFICER);

        assertThatThrownBy(() -> promotion.update(author2, PRESIDENT))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 승진_신청을_취소한다() {
        Author author = 회원1.author(Author::new);
        Promotion promotion = new Promotion(author, OFFICER);

        promotion.cancel(author);

        assertThat(promotion.getStatus()).isEqualTo(CANCEL);
    }

    @Test
    void 다른_사람의_승진_신청을_취소할_수_없다() {
        Author author1 = 회원1.author(Author::new);
        Author author2 = 회원2.author(Author::new);
        Promotion promotion = new Promotion(author1, OFFICER);

        assertThatThrownBy(() -> promotion.cancel(author2))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 직책이_높더라도_다른_사람의_승진_신청을_취소할_수_없다() {
        Author author1 = 회원1.author(Author::new);
        Author author2 = 임원1.author(Author::new);
        Promotion promotion = new Promotion(author1, OFFICER);

        assertThatThrownBy(() -> promotion.cancel(author2))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 승진_신청을_수락한다() {
        Author author = 회원1.author(Author::new);
        Promotion promotion = new Promotion(author, OFFICER);

        promotion.approve();

        assertThat(promotion.getStatus()).isEqualTo(APPROVE);
    }

    @Test
    void 승진_신청을_거절한다() {
        Author author = 회원1.author(Author::new);
        Promotion promotion = new Promotion(author, OFFICER);

        promotion.reject();

        assertThat(promotion.getStatus()).isEqualTo(REJECT);
    }
}
