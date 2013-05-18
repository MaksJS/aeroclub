# --- !Ups

create table transaction (
  id                        bigint auto_increment not null,
  account_id                bigint,
  date                      datetime,
  amount                    double,
  constraint pk_transaction primary key (id))
;

alter table transaction add constraint fk_transaction_account_4 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_transaction_account_4 on transaction (account_id);

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table transaction;

SET FOREIGN_KEY_CHECKS=1;