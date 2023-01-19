package admin.adminsiteserver.promotion.domain;

import admin.adminsiteserver.common.domain.AggregateRoot;
import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.promotion.exception.WrongPromotionException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static admin.adminsiteserver.promotion.domain.PromotionStatus.APPROVE;
import static admin.adminsiteserver.promotion.domain.PromotionStatus.REJECT;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Promotion extends AggregateRoot {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Author author;

    @Enumerated(STRING)
    private RoleType role;

    @Enumerated(STRING)
    private PromotionStatus status;

    public Promotion(Author author, RoleType role) {
        if (!author.higherThan(role)) {
            throw new WrongPromotionException();
        }
        this.author = author;
        this.role = role;
        this.status = PromotionStatus.WAITING;
    }

    public void update(Author author, RoleType role) {
        if (!author.higherThan(role)) {
            throw new WrongPromotionException();
        }
        this.author.validate(author);
        this.role = role;
    }

    public void cancel(Author author) {
        this.author.validate(author);
        this.status = PromotionStatus.CANCEL;
    }

    public void approve() {
        this.status = APPROVE;
        registerEvent(new PromotionEvent(id, author.getMemberId(), APPROVE, role));
    }

    public void reject() {
        this.status = PromotionStatus.REJECT;
        registerEvent(new PromotionEvent(id, author.getMemberId(), REJECT, role));
    }
}
