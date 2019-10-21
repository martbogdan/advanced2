create sequence hibernate_sequence start 1 increment 1;

create table cat
(
    id                bigserial not null,
    cat_belt          int4      not null,
    cat_cheast        int4      not null,
    cat_expirience    int4      not null,
    cat_head          int4      not null,
    cat_hp            int4      not null,
    cat_legs          int4      not null,
    cat_level         int4      not null,
    cat_maxexpirience int4      not null,
    cat_straight      int4      not null,
    name              varchar(255),
    user_id           int8      not null,
    primary key (id)
);
create table tarakan
(
    id          bigserial not null,
    draw        int4,
    experience  int4,
    img_id      varchar(255),
    level       int4,
    loss        int4,
    running     int4,
    step        int4,
    tarname     varchar(255),
    way_for_bot int4,
    win         int4,
    user_id     int8      not null,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table users
(
    id       bigserial not null,
    active   boolean   not null,
    fights   int4,
    loss     int4,
    password varchar(255),
    username varchar(255),
    win      int4,
    primary key (id)
);
alter table if exists cat
    add constraint FKlyjeo2tasjxv7kmbtgwe0pm1o foreign key (user_id) references users;
alter table if exists tarakan
    add constraint FKjj0625iu0d1gfl1461jcipn9v foreign key (user_id) references users;
alter table if exists user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;