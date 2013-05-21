# --- !Ups

create table account (
  id                        bigint not null,
  amount                    float,
  user_id                   bigint,
  constraint pk_account primary key (id))
;

create sequence account_seq;

alter table account add constraint fk_account_user_3 foreign key (user_id) references member (id) on delete restrict on update restrict;
create index ix_account_user_3 on account (user_id);

# --- !Downs

drop table if exists account cascade;

drop sequence if exists account_seq;