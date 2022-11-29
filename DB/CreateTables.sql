create table ROLES
(
    id   number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    name varchar2(50) not null unique,
    primary key (id)
);


create table USERS
(
    id            number(10) GENERATED by default as IDENTITY (START with 1 INCREMENT by 1),
    username      varchar2(50) not null unique,
    password_hash varchar2(50) not null,
    role_id       number(10)   not null,
    email         varchar2(50) not null unique,
    constraint users_pk primary key (id),
    constraint user_fk foreign key (role_id) references roles (id) on delete cascade,
    constraint correct_email check (REGEXP_LIKE(email, '^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$'))
);
create table IMAGES
(
    id    number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    name  varchar2(50) not null,
    IMAGE BLOB         not null,
    type  varchar2(50) not null check ( type in ('movie', 'actor', 'director') ),
    primary key (id),
    constraint image_name_uq unique (name, type)
);
create table PEOPLE
(
    id         number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    name       varchar2(50) not null,
    photo_id   number(10)   not null,
    profession varchar2(50) not null check ( profession in ('actor', 'director') ),
    primary key (id),
    constraint actor_fk1 foreign key (photo_id) references images (id) on delete cascade,
    constraint actor_name_uq unique (name, profession)
);
create table GENRES
(
    id   number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    name varchar2(50) not null unique,
    primary key (id)
);
create table VIDEOS
(
    id    number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    name  varchar2(50) not null,
    VIDEO BLOB         not null,
    type  varchar2(50) not null check ( type in ('trailer', 'movie') ),
    primary key (id),
    constraint video_name_uq unique (name, type)
);


create table MOVIES
(
    id              number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    title           varchar2(50)  not null unique,
    year            number(4)     not null,
    image_id        number(10)    not null,
    trailer_id      number(10)    not null,
    video_id        number(10)    not null,
    director_id     number(10)    not null,
    genre_id        number(10)    not null,
    description     varchar2(500) not null,
    number_of_views number(10) default 0,
    primary key (id),
    CONSTRAINT movie_fk1_images FOREIGN KEY (image_id) REFERENCES images (id) on delete cascade,
    CONSTRAINT movie_fk2_videos FOREIGN KEY (trailer_id) REFERENCES videos (id) on delete cascade,
    CONSTRAINT movie_fk3_people FOREIGN KEY (director_id) REFERENCES PEOPLE (id) on delete cascade,
    CONSTRAINT movie_fk4_genres FOREIGN KEY (genre_id) REFERENCES genres (id) on delete cascade,
    Constraint movie_fk5_video FOREIGN KEY (video_id) REFERENCES videos (id) on delete cascade
);



create table MOVIE_CASTS
(
    id       number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    movie_id number(10)   not null,
    actor_id number(10)   not null,
    role     varchar2(50) not null,
    /*   character varchar2(50) not null,*/
    primary key (id),
    CONSTRAINT movie_casts_fk1 FOREIGN KEY (movie_id) REFERENCES movies (id) on delete cascade,
    CONSTRAINT movie_casts_fk2 FOREIGN KEY (actor_id) REFERENCES PEOPLE (id) on delete cascade,
    constraint movie_casts_uk unique (role, actor_id)
);



create table FAVOURITES
(
    id       number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    user_id  number(10) not null,
    movie_id number(10) not null,
    primary key (id),
    CONSTRAINT favorites_fk1 FOREIGN KEY (user_id) REFERENCES users (id) on delete cascade,
    CONSTRAINT favorites_fk2 FOREIGN KEY (movie_id) REFERENCES movies (id) on delete cascade,
    CONSTRAINT userMovie UNIQUE (user_id, movie_id)
);

create table COMMENTS
(
    id        number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    user_id   number(10)   not null,
    movie_id  number(10)   not null,
    "COMMENT" varchar2(50) not null,
    primary key (id),
    CONSTRAINT comments_fk1 FOREIGN KEY (user_id) REFERENCES users (id) on delete cascade,
    CONSTRAINT comments_fk2 FOREIGN KEY (movie_id) REFERENCES movies (id) on delete cascade
);

create table HISTORY
(
    id       number(10) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    user_id  number(10)                      not null,
    movie_id number(10)                      not null,
    time     date default (sysdate + 3 / 24) not null,
    primary key (id),
    CONSTRAINT history_fk1 FOREIGN KEY (user_id) REFERENCES users (id) on delete cascade,
    CONSTRAINT history_fk2 FOREIGN KEY (movie_id) REFERENCES movies (id) on delete cascade
);




