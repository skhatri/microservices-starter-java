#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

CREATE USER todo WITH ENCRYPTED PASSWORD 'password';
CREATE SCHEMA IF NOT EXISTS todo AUTHORIZATION todo;

create table if not exists todo.tasks(
    id varchar(64),
    description varchar(300),
    action_by varchar(100),
    created TIMESTAMP,
    status varchar(50),
    updated TIMESTAMP,
    primary key(id)
);

insert into todo.tasks(id, description, action_by, created, status)
values('1', 'Buy Groceries', 'user1', '2020-03-20 00:00:00', 'NEW');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('2', 'Watch Movie', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');


insert into todo.tasks(id, description, action_by, created, status, updated)
values('3', 'Read', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');


insert into todo.tasks(id, description, action_by, created, status, updated)
values('4', 'Dance', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('5', 'Go for Walk', 'user1', '2020-03-20 13:00:00', 'DONE', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('6', 'Gym', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('7', 'Do Yoga', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('8', 'Chat with Friends', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('9', 'Listen to Spotify', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');

insert into todo.tasks(id, description, action_by, created, status, updated)
values('10', 'Sleep until Late', 'user1', '2020-03-20 13:00:00', 'NEW', '2020-03-20 13:00:00');


EOSQL
