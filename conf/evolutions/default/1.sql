# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table flight (
  id                        bigint not null,
  plane_id                  bigint,
  date                      timestamp,
  duration                  integer,
  flight_reduction          float,
  week_reduction            float,
  special_price             float,
  initiation_fee            boolean,
  constraint pk_flight primary key (id))
;

create table plane (
  id                        bigint not null,
  matriculation             varchar(255),
  price                     float,
  week_reduction            float,
  plane_type_id             bigint,
  constraint pk_plane primary key (id))
;

create table plane_type (
  id                        bigint not null,
  description               varchar(255),
  fee1                      float,
  fee2                      float,
  fee3                      float,
  fee_time1                 integer,
  fee_time2                 integer,
  fee_time3                 integer,
  constraint pk_plane_type primary key (id))
;

create table member (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  constraint uq_member_username unique (username),
  constraint pk_member primary key (id))
;

create sequence flight_seq;

create sequence plane_seq;

create sequence plane_type_seq;

create sequence member_seq;

alter table flight add constraint fk_flight_plane_1 foreign key (plane_id) references plane (id);
create index ix_flight_plane_1 on flight (plane_id);
alter table plane add constraint fk_plane_planeType_2 foreign key (plane_type_id) references plane_type (id);
create index ix_plane_planeType_2 on plane (plane_type_id);



# --- !Downs

drop table if exists flight cascade;

drop table if exists plane cascade;

drop table if exists plane_type cascade;

drop table if exists member cascade;

drop sequence if exists flight_seq;

drop sequence if exists plane_seq;

drop sequence if exists plane_type_seq;

drop sequence if exists member_seq;

