create table adoptions(
    id bigint not null auto_increment,
    date datetime not null,
    owner_id bigint not null,
    pet_id bigint not null,
    motive varchar(255) not null,
    status varchar(100) not null,
    status_justification varchar(255),
    primary key(id),
    constraint fk_adoptions_owner_id foreign key(owner_id) references owners(id),
    constraint fk_adoptions_pet_id foreign key(pet_id) references pets(id)
);