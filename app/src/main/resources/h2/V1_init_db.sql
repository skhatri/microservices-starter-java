create schema if not exists todo;

create table if not exists todo.tasks(
    id varchar(64),
    description varchar(300),
    action_by varchar(100),
    created TIMESTAMP WITH TIME ZONE,
    status varchar(50),
    updated TIMESTAMP WITH TIME ZONE,
    primary key(id)
);
