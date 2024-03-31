-- liquibase formatted sql
-- changeset rivaldi:31-01-changelog
create table student (
    id varchar(36) not null primary key ,
    name varchar(255) not null,
    age tinyint not null
)
