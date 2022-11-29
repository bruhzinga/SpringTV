--create all views
ALTER SESSION SET CONTAINER = SpringTV;
create view USERS_VIEW as
select u.id, u.username, u.password_hash, u.email, r.name as role
from users u
         join roles r on u.role_id = r.id;
create view ROLES_VIEW as
select r.id, r.name
from roles r;

create view MOVIES_VIEW as
select m.id,
       m.title,
       m.year,
       m.description,
       m.number_of_views,
       p.name as director,
       g.name as genre
from movies m
         join images i on m.image_id = i.id
         join videos v on m.trailer_id = v.id
         join people p on m.director_id = p.id
         join genres g on m.genre_id = g.id;

create view COMMENTS_VIEW as
select c.id, c."COMMENT", M.title, U.username
from COMMENTS c
         join users u on c.user_id = u.id
         join movies m on c.movie_id = m.id;
Create view FAVOURITES_VIEW as
select f.id, m.title, u.username
from FAVOURITES f
         join users u on f.user_id = u.id
         join movies m on f.movie_id = m.id;
create or replace view HISTORY_VIEW as
select h.id, m.title, u.username, h.time
from HISTORY h
         join users u on h.user_id = u.id
         join movies m on h.movie_id = m.id;
create or replace view MOVIE_MEDIA_VIEW as
select m.id,
       i.name  as image_name,
       i.image,
       v.name  as video_name,
       v.video,
       t.NAME  as trailer_name,
       t.VIDEO as trailer_video
from movies m
         join images i on m.image_id = i.id
         join videos v on m.VIDEO_ID = v.id
         join VIDEOS t on m.trailer_id = t.id;

create view GENRES_VIEW as
select g.id, g.name
from genres g;


create view IMAGES_VIEW as
select *
from images i;

create view VIDEOS_VIEW as
select *
from videos v;

create view PEOPLE_VIEW as
select *
from people p;

create or replace view MOVIE_ACTORS_VIEW as
select mc.id,
       m.id as movie_id,
       m.TITLE,
       p.id as actor_id,
       p.name,
       I.ID as image_id
from MOVIE_CASTS mc
         join people p on mc.actor_id = p.id
         join movies m on mc.movie_id = m.id
         join IMAGES I on p.PHOTO_ID = I.ID;

