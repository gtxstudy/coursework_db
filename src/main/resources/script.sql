drop table if exists university cascade;
create table university (
	id serial primary key,
	name varchar(64) unique not null,
	city int not null references city (id)
);

-- drop table if exists faculty cascade;
-- create table faculty (
-- 	id serial primary key,
-- 	university_id int not null references university (id),
-- 	name varchar(64) not null
-- );

drop table if exists field_of_study cascade;
create table field_of_study (
	id serial primary key,
	university_id int not null references university (id),
	name varchar(64),
	year int check (year > 0)
);

drop table if exists service_user cascade;
create table service_user (
	id serial primary key,
	user_id bigint unique not null,
	state varchar(32) not null,
	created timestamp,
	active boolean not null,
	reactions_from int not null check (reactions_from >= 0),
	reactions_to int not null check (reactions_to >= 0)
);

drop table if exists city cascade;
create table city (
    id serial primary key,
    name varchar(64) unique
);

drop table if exists profile cascade;
create table profile (
	id serial primary key,
	user_id int unique not null references service_user (id),
	name varchar(64),
	age int check (age > 0),
	sex varchar(32),
	field_of_study_id int references field_of_study (id),
	country varchar(64),
	city int references city (id),
	about varchar(1024),
	goal varchar(32),
	modified timestamp
);

drop table if exists image cascade;
create table image (
	id serial primary key,
	profile_id int not null references profile (id),
	file_path varchar(256) not null,
	uploaded timestamp
);

drop table if exists reaction cascade;
create table reaction (
	id serial primary key,
	from_id int not null references service_user (id),
	to_id int not null references service_user (id),
	type varchar(32) not null,
	at timestamp
);

-------

CREATE OR REPLACE function inc_reactions() RETURNS trigger
language plpgsql
as '
begin
    update service_user
    set reactions_from = reactions_from + 1
    where id = NEW.from_id;

    update service_user
    set reactions_to = reactions_to + 1
    where id = NEW.to_id;
     return NEW;
end;
';

drop trigger if exists tr_inc_reactions on reaction;
CREATE TRIGGER tr_inc_reactions
AFTER INSERT ON reaction FOR EACH ROW
EXECUTE PROCEDURE inc_reactions();

-------

CREATE OR REPLACE function profile_set_modified() RETURNS trigger
language plpgsql
as '
begin
    NEW.modified = NOW()::TIMESTAMP;
    return NEW;
end;
';

drop trigger if exists tr_mod_profile on profile;
CREATE TRIGGER tr_mod_profile
AFTER UPDATE ON profile FOR EACH ROW
EXECUTE PROCEDURE profile_set_modified();

-------

create index if not exists profile_id_hash_index on profile using hash (id);
create index if not exists service_user_id_hash_index on service_user using hash (id);

-------

insert into city (name) values ('Санкт-Петербург'), ('Москва');

insert into university (name, city) values ('ИТМО', 1), ('СПБГУ', 1);
