# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table flight (
  id                        bigint auto_increment not null,
  plane_id                  bigint,
  date                      datetime,
  duration                  integer,
  flight_reduction          double,
  week_reduction            double,
  special_price             double,
  initiation_fee            tinyint(1) default 0,
  constraint pk_flight primary key (id))
;

create table plane (
  id                        bigint auto_increment not null,
  matriculation             varchar(255),
  price                     double,
  week_reduction            double,
  plane_type_id             bigint,
  constraint pk_plane primary key (id))
;

create table plane_type (
  id                        bigint auto_increment not null,
  description               varchar(255),
  fee1                      double,
  fee2                      double,
  fee3                      double,
  fee_time1                 integer,
  fee_time2                 integer,
  fee_time3                 integer,
  constraint pk_plane_type primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id))
;

alter table flight add constraint fk_flight_plane_1 foreign key (plane_id) references plane (id) on delete restrict on update restrict;
create index ix_flight_plane_1 on flight (plane_id);
alter table plane add constraint fk_plane_planeType_2 foreign key (plane_type_id) references plane_type (id) on delete restrict on update restrict;
create index ix_plane_planeType_2 on plane (plane_type_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table flight;

drop table plane;

drop table plane_type;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

