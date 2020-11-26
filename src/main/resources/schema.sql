
create table user (
    id VARCHAR(36) not null,
    email VARCHAR(255) not null,
    password VARCHAR(255) not null,
    investor_id VARCHAR(36) null,
    bidder_id VARCHAR(36) null,
    foreign key (investor_id) references investor(id),
    foreign key (bidder_id) references bidder(id)
)

create table investor
(
   id VARCHAR(36) not null,
   name VARCHAR(255) not null,
   description VARCHAR(1024) null,
   primary key(id)
);

create table bidder
(
   id VARCHAR(36) not null,
   name VARCHAR(255) not null,
   description VARCHAR(1024) null,
   primary key(id),
);

create table construction
(
   id VARCHAR(36) not null,
   name VARCHAR(200) not null,
   description CLOB null,

   primary key(id),
   foreign key (investor_id) references investor(id)
);

create table construction_work_branch
(
   id VARCHAR(36) not null,
   name VARCHAR(255) not null,
   primary key(id)
);

create table construction_work_branch_aggregation (
    id VARCHAR(36) not null,
    construction_id VARCHAR(36) not null,
    construction_work_id VARCHAR(36) not null,
    primary key(id),
    foreign key (construction_id_fk) references construction(id),
    foreign key (construction_work_id_fk) references construction_work_branch(id)
)

create table offer
(
   id VARCHAR(36) not null,
   description CLOB not null,
   amount DOUBLE not null,
   accepted BOOLEAN not null,
   primary key(id),
   foreign key (bidder_id) references bidder(id),
   foreign key (construction_id) references construction(id),
);

create table offer_work_branch_aggregation (
    id VARCHAR(36) not null,
    construction_id INTEGER not null,
    construction_work_id INTEGER not null,
    primary key(id),
    foreign key (construction_id_fk) references construction(id),
    foreign key (construction_work_id_fk) references construction_work_branch(id)
)



