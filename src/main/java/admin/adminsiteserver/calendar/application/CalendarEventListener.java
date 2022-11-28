package admin.adminsiteserver.calendar.application;

import admin.adminsiteserver.calendar.domain.Author;
import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarQueryRepository;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberEvent;
import admin.adminsiteserver.member.domain.MemberEventHistory;
import admin.adminsiteserver.member.domain.MemberEventHistoryRepository;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.exception.MemberEventHistoryNotFoundException;
import admin.adminsiteserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalendarEventListener {
    private final CalendarQueryRepository calendarQueryRepository;
    private final MemberEventHistoryRepository memberEventHistoryRepository;
    private final MemberRepository memberRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handle(MemberEvent event) {
        Member member = findMemberById(event.getMemberId());
        List<Calendar> calendars = calendarQueryRepository.findAllByAuthorId(member.getId());
        calendars.forEach(calendar -> calendar.exchange(author(member)));
        complete(event);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private MemberEventHistory findMemberEventHistoryById(Long memberId) {
        return memberEventHistoryRepository.findByMemberId(memberId)
                .orElseThrow(MemberEventHistoryNotFoundException::new);
    }

    private Author author(Member member) {
        return new Author(
                member.getId(),
                member.getName(),
                member.getStudentNumber(),
                member.getRole());
    }

    private void complete(MemberEvent event) {
        MemberEventHistory history = findMemberEventHistoryById(event.getMemberId());
        history.process();
    }
}
