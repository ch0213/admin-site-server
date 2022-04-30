create table announcement
(
    id           bigint not null,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    title        varchar(255),
    primary key (id)
) engine=InnoDB;

create table announcement_comment
(
    id              bigint not null,
    created_at      datetime(6),
    modified_at     datetime(6),
    author_email    varchar(255),
    author_name     varchar(255),
    comment         varchar(255),
    announcement_id bigint,
    primary key (id)
) engine=InnoDB;

create table announcement_file_path
(
    id              bigint not null,
    file_name       varchar(255),
    file_url        varchar(255),
    announcement_id bigint,
    primary key (id)
) engine=InnoDB;

create table answer
(
    id           bigint not null,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    qna_id       bigint,
    primary key (id)
) engine=InnoDB;

create table answer_comment
(
    id           bigint not null,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    comment      varchar(255),
    answer_id    bigint,
    primary key (id)
) engine=InnoDB;

create table answer_file_path
(
    id        bigint not null,
    file_name varchar(255),
    file_url  varchar(255),
    answer_id bigint,
    primary key (id)
) engine=InnoDB;

create table calendar
(
    id         bigint not null,
    start_date date,
    title      varchar(255),
    primary key (id)
) engine=InnoDB;

create table gallery
(
    id           bigint not null,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    title        varchar(255),
    primary key (id)
) engine=InnoDB;

create table gallery_comment
(
    id           bigint not null,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    comment      varchar(255),
    gallery_id   bigint,
    primary key (id)
) engine=InnoDB;

create table gallery_file_path
(
    id         bigint not null,
    file_name  varchar(255),
    file_url   varchar(255),
    gallery_id bigint,
    primary key (id)
) engine=InnoDB;

create table level_up
(
    id          bigint not null auto_increment,
    created_at  datetime(6),
    modified_at datetime(6),
    processed   bit    not null,
    role        varchar(255),
    user_email  varchar(255),
    member_id   bigint,
    primary key (id)
) engine=InnoDB;

create table member
(
    id             bigint not null auto_increment,
    email          varchar(255),
    name           varchar(255),
    password       varchar(255),
    phone_number   varchar(255),
    role           varchar(255),
    student_number varchar(255),
    primary key (id)
) engine=InnoDB;

create table member_file_path
(
    id        bigint not null auto_increment,
    file_name varchar(255),
    file_url  varchar(255),
    member_id bigint,
    primary key (id)
) engine=InnoDB;

create table qna
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    title        varchar(255),
    primary key (id)
) engine=InnoDB;

create table question_comment
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    comment      varchar(255),
    qna_id       bigint,
    primary key (id)
) engine=InnoDB;

create table question_file_path
(
    id        bigint not null auto_increment,
    file_name varchar(255),
    file_url  varchar(255),
    qna_id    bigint,
    primary key (id)
) engine=InnoDB;

alter table announcement_comment
    add constraint foreign key (announcement_id)
        references announcement (id);

alter table announcement_file_path
    add constraint foreign key (announcement_id)
        references announcement (id);

alter table answer
    add constraint foreign key (qna_id)
        references qna (id);

alter table answer_comment
    add constraint foreign key (answer_id)
        references answer (id);

alter table answer_file_path
    add constraint foreign key (answer_id)
        references answer (id);

alter table gallery_comment
    add constraint foreign key (gallery_id)
        references gallery (id);

alter table gallery_file_path
    add constraint foreign key (gallery_id)
        references gallery (id);

alter table level_up
    add constraint foreign key (member_id)
        references member (id);

alter table member_file_path
    add constraint foreign key (member_id)
        references member (id);

alter table question_comment
    add constraint foreign key (qna_id)
        references qna (id);

alter table question_file_path
    add constraint foreign key (qna_id)
        references qna (id);
