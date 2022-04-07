package admin.adminsiteserver.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Calendar {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private LocalDate startDate;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateStartDate(LocalDate localDate) {
        this.startDate = localDate;
    }

    public Calendar(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
    }
}
