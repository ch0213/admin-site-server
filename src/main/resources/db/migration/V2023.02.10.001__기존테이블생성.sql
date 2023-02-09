create table announcement (
    id             bigint not null auto_increment,
    created_at     datetime(6),
    modified_at    datetime(6),
    member_id      bigint,
    name           varchar(255),
    role_type      integer,
    student_number varchar(255),
    content        longtext,
    deleted        bit,
    title          varchar(255),
    primary key (id)
);

create table announcement_comment
(
    id              bigint not null auto_increment,
    created_at      datetime(6),
    modified_at     datetime(6),
    member_id       bigint,
    name            varchar(255),
    role_type       integer,
    student_number  varchar(255),
    content         varchar(255),
    deleted         bit    not null,
    announcement_id bigint,
    primary key (id)
);

create table announcement_file_path
(
    id              bigint not null auto_increment,
    deleted         bit    not null,
    file_name       varchar(255),
    file_url        varchar(255),
    announcement_id bigint,
    primary key (id)
);

create table answer
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    qna_id       bigint,
    primary key (id)
);

create table answer_comment
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    comment      varchar(255),
    answer_id    bigint,
    primary key (id)
);

create table answer_file_path
(
    id        bigint not null auto_increment,
    file_name varchar(255),
    file_url  varchar(255),
    answer_id bigint,
    primary key (id)
);

create table calendar
(
    id             bigint not null auto_increment,
    member_id      bigint,
    name           varchar(255),
    role_type      integer,
    student_number varchar(255),
    deleted        bit    not null,
    description    varchar(255),
    end_time       datetime(6),
    start_time     datetime(6),
    title          varchar(255),
    primary key (id)
);

create table gallery
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    content      longtext,
    title        varchar(255),
    primary key (id)
);

create table gallery_comment
(
    id           bigint not null auto_increment,
    created_at   datetime(6),
    modified_at  datetime(6),
    author_email varchar(255),
    author_name  varchar(255),
    comment      varchar(255),
    gallery_id   bigint,
    primary key (id)
);

create table gallery_file_path
(
    id         bigint not null auto_increment,
    file_name  varchar(255),
    file_url   varchar(255),
    gallery_id bigint,
    primary key (id)
);

create table member
(
    id             bigint       not null auto_increment,
    deleted        bit          not null,
    email          varchar(255),
    file_name      varchar(255),
    file_url       varchar(255),
    name           varchar(255) not null,
    password       varchar(255) not null,
    phone_number   varchar(255) not null,
    role           varchar(255),
    student_number varchar(255),
    primary key (id)
);

create table member_event_history
(
    id              bigint not null auto_increment,
    event_date_time datetime(6),
    event_type      integer,
    member_id       bigint,
    processed       bit    not null,
    primary key (id)
);

create table promotion
(
    id             bigint not null auto_increment,
    created_at     datetime(6),
    modified_at    datetime(6),
    member_id      bigint,
    name           varchar(255),
    role_type      integer,
    student_number varchar(255),
    role           varchar(255),
    status         varchar(255),
    primary key (id)
);

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
);

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
);

create table question_file_path
(
    id        bigint not null auto_increment,
    file_name varchar(255),
    file_url  varchar(255),
    qna_id    bigint,
    primary key (id)
);

alter table member
    add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email);

alter table member
    add constraint UK_9aum603gwkjo0ihn9bhygqad8 unique (student_number);
