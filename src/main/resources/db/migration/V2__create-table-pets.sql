create table pets(
    id bigint not null auto_increment,
    type varchar(100) not null,
    name varchar(100) not null,
    breed varchar(100) not null,
    age int not null,
    color varchar(100) not null,
    weight decimal(4,2) not null,
    shelter_id bigint not null,
    is_adopted boolean not null,
    primary key(id),
    constraint fk_pets_shelter_id foreign key(shelter_id) references shelters(id)
);