create table certificate (id bigint not null, not_after datetime(6) not null, not_before datetime(6) not null, parent_cert_serial_number varchar(255) not null, revocation_date datetime(6), revoked bit, serial_number varchar(255) not null, type integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
alter table certificate add constraint UK_d8aghiqso72xl3tbcrhvobi99 unique (serial_number)
create table authorities (id bigint not null auto_increment, role varchar(255), primary key (id)) engine=InnoDB
create table certificate (id bigint not null, not_after datetime(6) not null, not_before datetime(6) not null, parent_cert_serial_number varchar(255) not null, revocation_date datetime(6), revoked bit, serial_number varchar(255) not null, type integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, authority_id bigint not null) engine=InnoDB
create table users (user_type varchar(31) not null, id bigint not null auto_increment, email varchar(60), enabled bit, first_name varchar(30), first_time bit, last_name varchar(50), last_password_reset_date datetime(6), password varchar(80) not null, phone_number varchar(25), username varchar(45) not null, primary key (id)) engine=InnoDB
alter table certificate add constraint UK_d8aghiqso72xl3tbcrhvobi99 unique (serial_number)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
alter table user_authority add constraint FKil6f39w6fgqh4gk855pstsnmy foreign key (authority_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
create table authorities (id bigint not null auto_increment, role varchar(255), primary key (id)) engine=InnoDB
create table certificate (id bigint not null, not_after datetime(6) not null, not_before datetime(6) not null, parent_cert_serial_number varchar(255) not null, revocation_date datetime(6), revoked bit, serial_number varchar(255) not null, type integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, authority_id bigint not null) engine=InnoDB
create table users (user_type varchar(31) not null, id bigint not null auto_increment, email varchar(60), enabled bit, first_name varchar(30), first_time bit, last_name varchar(50), last_password_reset_date datetime(6), password varchar(80) not null, phone_number varchar(25), username varchar(45) not null, primary key (id)) engine=InnoDB
alter table certificate add constraint UK_d8aghiqso72xl3tbcrhvobi99 unique (serial_number)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
alter table user_authority add constraint FKil6f39w6fgqh4gk855pstsnmy foreign key (authority_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
create table authorities (id bigint not null auto_increment, role varchar(255), primary key (id)) engine=InnoDB
create table certificate (id bigint not null, not_after datetime(6) not null, not_before datetime(6) not null, parent_cert_serial_number varchar(255) not null, revocation_date datetime(6), revoked bit, serial_number varchar(255) not null, type integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, authority_id bigint not null) engine=InnoDB
create table users (user_type varchar(31) not null, id bigint not null auto_increment, email varchar(60), enabled bit, first_name varchar(30), first_time bit, last_name varchar(50), last_password_reset_date datetime(6), password varchar(80) not null, phone_number varchar(25), username varchar(45) not null, primary key (id)) engine=InnoDB
alter table certificate add constraint UK_d8aghiqso72xl3tbcrhvobi99 unique (serial_number)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
alter table user_authority add constraint FKil6f39w6fgqh4gk855pstsnmy foreign key (authority_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
create table authorities (id bigint not null auto_increment, role varchar(255), primary key (id)) engine=InnoDB
create table certificate (id bigint not null, not_after datetime(6) not null, not_before datetime(6) not null, parent_cert_serial_number varchar(255) not null, revocation_date datetime(6), revoked bit, serial_number varchar(255) not null, type integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, authority_id bigint not null) engine=InnoDB
create table users (user_type varchar(31) not null, id bigint not null auto_increment, email varchar(60), enabled bit, first_name varchar(30), first_time bit, last_name varchar(50), last_password_reset_date datetime(6), password varchar(80) not null, phone_number varchar(25), username varchar(45) not null, primary key (id)) engine=InnoDB
alter table certificate add constraint UK_d8aghiqso72xl3tbcrhvobi99 unique (serial_number)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
alter table user_authority add constraint FKil6f39w6fgqh4gk855pstsnmy foreign key (authority_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
