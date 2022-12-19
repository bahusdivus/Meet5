create table meet5_user
(
    id        integer
        constraint meet5_user_pk primary key,
    user_name varchar(100) not null,
    b_date    timestamp    not null,
    reg_date  timestamp    not null,
    note      text
);

comment
on column meet5_user.b_date is 'birthdate';

comment
on column meet5_user.reg_date is 'registration timestamp';

comment
on column meet5_user.note is 'Some optional field';

create index meet5_user_name_index on meet5_user (user_name);

comment
on index meet5_user_name_index is 'Probably we will need such index for search by name';

create index meet5_user_b_date_index on meet5_user using brin (b_date);

comment
on index meet5_user_b_date_index is 'Probably we will need such index for search by range of b_date';

create sequence meet5_user_id_seq as int;

create table meet5_visit
(
    v_time     timestamp not null default now(),
    user_id    int       not null
        constraint meet5_visit_user_id_fk
            references meet5_user,
    profile_id int       not null
        constraint meet5_visit_profile_id_fk
            references meet5_user,
    metadata   json
) partition by RANGE (v_time);

comment
on column meet5_visit.v_time is 'visit time';

comment
on column meet5_visit.user_id is 'who visited';

comment
on column meet5_visit.profile_id is 'whom visited';

comment
on column meet5_visit.metadata is 'whatever we need to save along';

create index meet5_visit_v_time_index
    on meet5_visit using brin (v_time);

create table readings_p2020_12_18
    partition of meet5_visit
    for values from
(
    '2022-12-18 00:00:00+00'
)
    to
(
    '2023-12-18 00:00:00+00'
);
