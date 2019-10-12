drop database taracat;
create database taracat character set utf8;

use taracat;

create table cat
(
    id                bigint  not null auto_increment,
    cat_belt          integer not null,
    cat_cheast        integer not null,
    cat_expirience    integer not null,
    cat_head          integer not null,
    cat_hp            integer not null,
    cat_legs          integer not null,
    cat_level         integer not null,
    cat_maxexpirience integer not null,
    cat_straight      integer not null,
    name              varchar(255),
    user_id           bigint  not null,
    primary key (id)
) engine = INNODB CHARACTER SET=utf8;
create table tarakan
(
    id          bigint not null auto_increment,
    draw        integer,
    experience  integer,
    img_id      varchar(255),
    level       integer,
    loss        integer,
    running     integer,
    step        integer,
    tarname     varchar(255),
    way_for_bot integer,
    win         integer,
    user_id     bigint not null,
    primary key (id)
) engine = INNODB CHARACTER SET=utf8;
create table user
(
    id       bigint not null auto_increment,
    active   bit    not null,
    fights   integer,
    loss     integer,
    password varchar(255),
    username varchar(255),
    win      integer,
    primary key (id)
) engine = INNODB CHARACTER SET=utf8;
create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine = INNODB CHARACTER SET=utf8;