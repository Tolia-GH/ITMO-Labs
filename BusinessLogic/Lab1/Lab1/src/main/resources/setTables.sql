create table if not exists admins(
    id serial not null primary key,
    username varchar(32) not null,
    password varchar(256) not null
);

create table if not exists tickets(
    id serial not null primary key,
    amount int not null,
    price float not null
);

create table if not exists users(
    id serial not null primary key,
    username varchar(32) not null,
    password varchar(256) not null
);

create table if not exists reviews(
    id serial not null primary key,
    author_id int references users(id),
    content text not null
);

create table if not exists movies(
    id serial not null primary key,
    name varchar(256) not null,
    description text not null,
    rating float not null,
    rateCount int not null,
    review_id int references reviews(id),
    ticket_id int references tickets(id)
);

create table if not exists favourites(
    id serial not null primary key,
    user_id int references users(id),
    movie_id int references movies(id)
);

create table if not exists orders(
    id serial not null primary key,
    user_id int references users(id),
    ticket_id int references tickets(id),
    is_paid boolean default false not null
);