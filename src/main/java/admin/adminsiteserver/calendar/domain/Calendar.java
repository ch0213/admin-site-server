package admin.adminsiteserver.calendar.domain;

import admin.adminsiteserver.calendar.exception.CalendarPeriodException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Calendar {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Author author;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean deleted;

    public Calendar(Author author, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this(null, author, title, description, startTime, endTime);
    }

    public Calendar(Long id, Author author, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        validate(startTime, endTime);
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(String title, String description, LocalDateTime startTime, LocalDateTime endTime, Author author) {
        validate(startTime, endTime);
        this.author.validate(author);
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void delete(Author author) {
        this.author.validate(author);
        this.deleted = true;
    }

    public void exchange(Author author) {
        if (this.author.equalsId(author)) {
            this.author = author;
        }
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new CalendarPeriodException();
        }
    }
}
