create table shelters(
    id bigint not null auto_increment,
    name varchar(100) not null unique,
    phone varchar(14) not null unique,
    email varchar(100) not null unique,
    primary key(id)
);