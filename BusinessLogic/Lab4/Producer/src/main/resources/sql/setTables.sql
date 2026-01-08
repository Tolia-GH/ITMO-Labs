create table if not exists accounts(
    id serial not null primary key,
    username varchar(32) not null,
    password varchar(256) not null,
    email varchar(256) not null,
    role TEXT not null
);

create table if not exists movies(
    id serial not null primary key,
    name varchar(256) not null,
    description text not null,
    rating float not null,
    rateCount int not null
);

create table if not exists tickets(
    id serial not null primary key,
    movie_id int references movies(id),
    amount int not null,
    price float not null
);

create table if not exists reviews(
    id serial not null primary key,
    author_id int references accounts(id),
    movie_id int references movies(id),
    content text not null
);

create table if not exists favourites(
    id serial not null primary key,
    user_id int references accounts(id),
    movie_id int references movies(id)
);

create table if not exists orders(
    id serial not null primary key,
    user_id int references accounts(id),
    ticket_id int references tickets(id),
    is_paid boolean default false not null
);