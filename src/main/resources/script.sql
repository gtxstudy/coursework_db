-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile
drop type if exists sex_enum;
create type sex_enum as enum ('male', 'female', 'other');
drop type if exists goal_enum;
create type goal_enum as enum ('work', 'study', 'science', 'relationship', 'friendship');
drop type if exists reaction_type_enum;
create type reaction_type_enum as enum ('like', 'skip');

drop table if exists service_user cascade;
create table service_user (
	id serial primary key,
	created timestamp,
	active boolean not null,
	reactions_from int not null check (reactions_from >= 0),
	reactions_to int not null check (reactions_to >= 0)
);

drop table if exists profile cascade;
create table profile (
	id serial primary key,
	user_id int unique not null references service_user (id),
	name varchar(64) not null,
	age int check (age > 0),
	sex sex_enum,
	country varchar(64),
	city varchar(64),
	about text,
	goal goal_enum not null,
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
	type reaction_type_enum not null,
	at timestamp
);

drop table if exists area_of_interest cascade;
create table area_of_interest (
	id serial primary key,
	name varchar(64) not null
);

drop table if exists interest cascade;
create table interest (
	id serial primary key,
	area_id int not null references area_of_interest (id),
	name varchar(64) not null,
	created timestamp
);

drop table if exists profile_interest_relation cascade;
create table profile_interest_relation (
	profile_id int references profile (id),
	interest_id int references interest (id),
	primary key (profile_id, interest_id)
);

drop table if exists university cascade;
create table university (
	id serial primary key,
	name varchar(64) not null,
	country varchar(64),
	city varchar(64)
);

drop table if exists faculty cascade;
create table faculty (
	id serial primary key,
	university_id int not null references university (id),
	name varchar(64) not null
);

drop table if exists field_of_study cascade;
create table field_of_study (
	id serial primary key,
	faculty_id int not null references faculty (id),
	profile_id int references profile (id),
	name varchar(64) not null,
	year int check (year > 0)
);

-------

CREATE OR REPLACE function inc_reactions() RETURNS trigger
language plpgsql
as $$
begin
    update service_user
    set reactions_from = reactions_from + 1
    where id = NEW.from_id;

    update service_user
    set reactions_to = reactions_to + 1
    where id = NEW.to_id;
     return NEW;
end;
$$;

drop trigger if exists tr_inc_reactions on reaction;
CREATE TRIGGER tr_inc_reactions
AFTER INSERT ON reaction FOR EACH ROW
EXECUTE PROCEDURE inc_reactions();

-------

CREATE OR REPLACE function profile_set_modified() RETURNS trigger
language plpgsql
as $$
begin
    NEW.modified = NOW()::TIMESTAMP;
    return NEW;
end;
$$;

drop trigger if exists tr_mod_profile on profile;
CREATE TRIGGER tr_mod_profile
AFTER UPDATE ON profile FOR EACH ROW
EXECUTE PROCEDURE profile_set_modified();

-------

CREATE OR REPLACE function delete_interest_if_unused()  returns trigger
language plpgsql
as $$
declare 
refs Integer := 0;
begin
    select count(*) from profile_interest_relation
    into refs
    where interest_id = OLD.interest_id;

  if refs < 1 then
    delete from interest where id = inter_id;
  end if;
  return OLD;
end;
$$;

drop trigger if exists tr_delete_interest on profile_interest_relation;
CREATE TRIGGER tr_delete_interest
AFTER DELETE ON profile_interest_relation FOR EACH ROW
EXECUTE PROCEDURE delete_interest_if_unused();

-------

drop function if exists get_recomendations;
CREATE OR REPLACE function get_recomendations(pr_id Integer, n Integer) returns SETOF profile
as $$
  select p2 from profile as p1
  join profile_interest_relation as pr on (pr.profile_id = p1.id)
  join interest as i1 on (i1.id  = pr.interest_id)
  join profile as p2 on (p2.city = p1.city and p2.goal = p1.goal)
  join profile_interest_relation as pr2 on (pr.profile_id = p2.id)
  join interest as i2 on (i2.id = pr2.interest_id)
  where p1.id = pr_id
  group by pr2.profile_id, p1.id, pr.profile_id, pr.interest_id, i1.id, p2.id,
  pr2.interest_id, i2.id 
  order by abs(p2.age - p1.age), 
  count((
    select id from area_of_interest
    where (id = i1.area_id and id = i2.area_id)
  ))
  limit n;
$$
language sql;

-------

create unique index if not exists profile_id_hash_index on profile using hash (id);
create unique index if not exists service_user_id_hash_index on service_user using hash (id);
create unique index if not exists interest_id_hash_index on interest using hash (id);
