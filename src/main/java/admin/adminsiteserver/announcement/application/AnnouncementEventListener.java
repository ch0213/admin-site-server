package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.announcement.domain.Author;
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
public class AnnouncementEventListener {
    private final AnnouncementRepository announcementRepository;
    private final MemberEventHistoryRepository memberEventHistoryRepository;
    private final MemberRepository memberRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handle(MemberEvent event) {
        Member member = findMember(event);
        List<Announcement> announcements = announcementRepository.findAllByDeletedIsFalse();
        announcements.forEach(announcement -> announcement.exchange(author(member)));

        MemberEventHistory history = findMemberEventHistory(event);
        history.process();
    }

    private Author author(Member member) {
        return new Author(member.getId(), member.getName(), member.getStudentNumber(), member.getRole());
    }

    private Member findMember(MemberEvent event) {
        return memberRepository.findById(event.getMemberId())
                .orElseThrow(MemberNotFoundException::new);
    }

    private MemberEventHistory findMemberEventHistory(MemberEvent event) {
        return memberEventHistoryRepository.findByMemberId(event.getMemberId())
                .orElseThrow(MemberEventHistoryNotFoundException::new);
    }
}
