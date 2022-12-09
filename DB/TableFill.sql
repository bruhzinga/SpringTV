--fill the tables
insert into roles (name)
values ('admin');
insert into roles (name)
values ('user');

insert into users (username, password_hash, email, role_id)
values ('admin', 'admin', 'admin@mail,com', 1);
insert into users (username, password_hash, email, role_id)
values ('user', 'user', 'user@mail.com', 2);

insert into genres (name)
values ('action');
insert into genres (name)
values ('comedy');
insert into genres (name)
values ('drama');
insert into genres (name)
values ('horror');
insert into genres (name)
values ('thriller');
insert into genres (name)
values ('sci-fi');

insert into people (name, photo_id, profession)
values ('Brad Pitt', 1, 'actor');
insert into people (name, photo_id, profession)
values ('Leonardo DiCaprio', 2, 'actor');
insert into people (name, photo_id, profession)
values ('Tom Hanks', 3, 'actor');
insert into people (name, photo_id, profession)
values ('Robert De Niro', 4, 'actor');
insert into people (name, photo_id, profession)
values ('Johnny Depp', 5, 'actor');
insert into people (name, photo_id, profession)
values ('Will Smith', 6, 'actor');
insert into people (name, photo_id, profession)
values ('Denzel Washington', 7, 'actor');
insert into people (name, photo_id, profession)
values ('Tom Cruise', 8, 'actor');
--directors
insert into people (name, photo_id, profession)
values ('Christopher Nolan', 9, 'director');
insert into people (name, photo_id, profession)
values ('Steven Spielberg', 10, 'director');
insert into people (name, photo_id, profession)
values ('Martin Scorsese', 11, 'director');
insert into people (name, photo_id, profession)
values ('Quentin Tarantino', 12, 'director');

insert into IMAGES (name, IMAGE, type)
values ('brad_pitt', empty_blob(), 'actor');
insert into IMAGES (name, IMAGE, type)
values ('leonardo_di_caprio', empty_blob(), 'actor');
insert into IMAGES (name, IMAGE, type)
values ('tom_hanks', empty_blob(), 'actor');
insert into IMAGES (name, IMAGE, type)
values ('robert_de_niro', empty_blob(), 'actor');
insert into IMAGES (name, IMAGE, type)
values ('johnny_depp', empty_blob(), 'actor');
--directors
insert into IMAGES (name, IMAGE, type)
values ('christopher_nolan', empty_blob(), 'director');
insert into IMAGES (name, IMAGE, type)
values ('steven_spielberg', empty_blob(), 'director');
insert into IMAGES (name, IMAGE, type)
values ('martin_scorsese', empty_blob(), 'director');
insert into IMAGES (name, IMAGE, type)
values ('quentin_tarantino', empty_blob(), 'director');

insert into VIDEOS (name, VIDEO, type)
values ('inception_trailer', empty_blob(), 'trailer');
insert into VIDEOS (name, VIDEO, type)
values ('interstellar_trailer', empty_blob(), 'trailer');
insert into VIDEOS (name, VIDEO, type)
values ('the_dark_knight_trailer', empty_blob(), 'trailer');
insert into VIDEOS (name, VIDEO, type)
values ('the_dark_knight_rises_trailer', empty_blob(), 'trailer');
insert into VIDEOS (name, VIDEO, type)
values ('the_shawshank_redemption_trailer', empty_blob(), 'trailer');
--movies
insert into VIDEOS (name, VIDEO, type)
values ('inception', empty_blob(), 'movie');
insert into VIDEOS (name, VIDEO, type)
values ('interstellar', empty_blob(), 'movie');
insert into VIDEOS (name, VIDEO, type)
values ('the_dark_knight', empty_blob(), 'movie');
insert into VIDEOS (name, VIDEO, type)
values ('the_dark_knight_rises', empty_blob(), 'movie');

insert into IMAGES (name, IMAGE, type)
values ('inception', empty_blob(), 'movie');
insert into IMAGES (name, IMAGE, type)
values ('interstellar', empty_blob(), 'movie');
insert into IMAGES (name, IMAGE, type)
values ('the_dark_knight', empty_blob(), 'movie');
insert into IMAGES (name, IMAGE, type)
values ('the_dark_knight_rises', empty_blob(), 'movie');

call ADDNEWMOVIE('Interstellar',
                 'Set in a dystopian future where humanity is struggling to survive, the film follows a group of astronauts who travel through a wormhole near Saturn in search of a new home for mankind.',
                 2007, 11, 7, 3, 15, 2);



call ADDNEWMOVIE('test1',
                 'test1',
                 2007, 11, 7, 3, 22, 2);

call ADDNEWMOVIE('test2',
                 'test2',
                 2007, 11, 7, 3, 22, 2);

call ADDNEWMOVIE('test100',
                 'test1233',
                 2007, 1, 10, 13, 22, 5);
select *
from movies
where TITLE = 'jsadjsadj';
delete
from movies
where TITLE = 'jsadjsadj';


declare
    v_id number;
begin
    for i in 2..100000
        loop
            SPRINGTVADMIN.ADMINPACKAGE.ADDNEWMOVIE('test' || i,
                                                   'test' || i,
                                                   2022, 1, 3, 72, 1, 1, v_id);
        end loop;
end;