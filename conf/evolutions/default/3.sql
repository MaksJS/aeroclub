# --- !Ups

create table transaction (
  id                        bigint not null,
  account_id                bigint,
  date                      timestamp,
  amount                    float,
  constraint pk_transaction primary key (id))
;

create sequence transaction_seq;

alter table transaction add constraint fk_transaction_account_4 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_transaction_account_4 on transaction (account_id);

# --- !Downs

drop table if exists transaction;

drop sequence if exists transaction_seq;