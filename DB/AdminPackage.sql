/*
--procedures
CREATE OR REPLACE PROCEDURE REGISTER_USER(user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,
                                          user_role_id Number) is
begin
    INSERT INTO users (USERNAME, PASSWORD_HASH, email, ROLE_ID)
    VALUES (user_login, (user_password), user_email, user_role_id);
EXCEPTION
    WHEN OTHERS THEN
        raise_application_error(-20001, 'Error while registering user');
end REGISTER_USER;


create or replace procedure getUserById(userId IN number, result OUT sys_refcursor) is
begin
    open result for
        select * from USERS_VIEW where ID = userId;
end getUserById;



create or replace procedure GetUserIdByUsername(InUserName IN varchar2, outID OUT number) is
begin
    select id into outID from users where username = InUserName;
exception
    when no_data_found then
        raise_application_error(-20001, 'User not found');
end GetUserIdByUsername;


create or replace procedure GetUserEncryptedPasswordByLogin(InUserName IN varchar2, password OUT varchar2) is
begin
    select password_hash into password from users where username = InUserName;
exception
    when no_data_found then
        raise_application_error(-20001, 'User not found');
end GetUserEncryptedPasswordByLogin;


create or replace procedure add_favourite(userID number, movieID number) is
begin
    insert into favourites(USER_ID, MOVIE_ID)
    values (userID, movieID);
exception
    when dup_val_on_index then
        raise_application_error(-20001, 'Movie already in favourites');
end add_favourite;

create or replace procedure delete_favourite(userID number, movieID number) is
begin
    delete
    from favourites
    where FAVOURITES.user_id = userId
      and movie_id = movieID;
exception
    when no_data_found then
        raise_application_error(-20001, 'Movie not in favourites');


end delete_favourite;

create or replace procedure add_comment(userID number, movieID number, comment_text varchar2) is
begin
    insert into comments(USER_ID, MOVIE_ID, "COMMENT")
    values (userID, movieID, comment_text);

end add_comment;


create or replace procedure getUserFavouritesByUsername(UserUsername IN varchar2, result OUT SYS_REFCURSOR) is
begin
    Open result for select * from FAVOURITES_VIEW where USERNAME = UserUsername;
end;


create or replace procedure getAllGenres(result OUT SYS_REFCURSOR) is
begin
    Open result for select * from GENRES_VIEW;
end;

create or replace procedure addGenre(genreName IN varchar2) is
begin
    insert into genres(NAME)
    values (genreName);
exception
    when dup_val_on_index then
        raise_application_error(-20001, 'Genre already exists');
end addGenre;

create or replace procedure deleteGenre(genreID IN number) is
begin
    delete
    from genres
    where genres.id = genreID;
exception
    when no_data_found then
        raise_application_error(-20001, 'Genre not found');
end deleteGenre;

create or replace procedure AddNewImage(imageName IN varchar2, image IN BLOB, ImType IN varchar2) is
begin
    insert into images(NAME, IMAGE, "TYPE")
    values (imageName, image, ImType);
end AddNewImage;

create or replace procedure AddNewVideo(videoName IN varchar2, video IN BLOB, videoType IN varchar2) is
begin
    insert into videos(NAME, VIDEO, "TYPE")
    values (videoName, video, videoType);
end AddNewVideo;

create or replace procedure AddNewMovie(movieName IN varchar2, movieDescription IN MOVIES.DESCRIPTION%TYPE,
                                        movieYear IN number,
                                        ImageID IN number, VideoID IN number, GenreID IN number, DirectorID IN number,
                                        TrailerID IN number) is
    imageType   IMAGES.TYPE%TYPE;
    videoType   VIDEOS.TYPE%TYPE;
    trailerType VIDEOS.TYPE%TYPE;
    peopleType  PEOPLE.PROFESSION%TYPE;
    genre       GENRES.NAME%TYPE;
begin
    select (select "TYPE" from images where id = ImageID) into imageType from dual;
    if imageType is null then
        raise_application_error(-20001, 'Image not found');
    end if;
    select (select "TYPE" from videos where id = VideoID) into videoType from dual;
    if videoType is null then
        raise_application_error(-20001, 'Video not found');
    end if;
    select (select "TYPE" from videos where id = TrailerID) into trailerType from dual;
    if trailerType is null then
        raise_application_error(-20001, 'Trailer not found');
    end if;
    select (select profession from people where id = DirectorID) into peopleType from dual;
    if peopleType is null then
        raise_application_error(-20001, 'Director not found');
    end if;

    select (select name from genres where id = GenreID) into genre from dual;
    if genre is null then
        raise_application_error(-20001, 'Genre not found');
    end if;


    if imageType != 'movie' then
        raise_application_error(-20001, 'Image is not an movie image');
    end if;
    if videoType != 'movie' then
        raise_application_error(-20001, 'Video is not an movie video');
    end if;
    if trailerType != 'trailer' then
        raise_application_error(-20001, 'Trailer is not an movie trailer');
    end if;

    if peopleType != 'director' then
        raise_application_error(-20001, 'Person is not a director');
    end if;

    insert into movies(TITLE, DESCRIPTION, YEAR, IMAGE_ID, VIDEO_ID, GENRE_ID, DIRECTOR_ID, TRAILER_ID)
    values (movieName, movieDescription, movieYear, ImageID, VideoID, GenreID, DirectorID, TrailerID);
end AddNewMovie;


create or replace procedure findAllByProfession(professionIn IN varchar2, result OUT SYS_REFCURSOR) is
begin
    Open result for select * from PEOPLE_VIEW where professionIn = profession;
end findAllByProfession;

create or replace procedure addActor(actorName IN varchar2, photoId IN number) is
    photoType varchar2(20);
begin
    select "TYPE" into photoType from images where id = photoId;
    if photoType = 'actor' then
        insert into people(NAME, PROFESSION, PHOTO_ID)
        values (actorName, 'actor', photoId);
    else
        raise_application_error(-20001, 'Photo ID is not actor');
    end if;
end addActor;

create or replace procedure addDirector(directorName IN varchar2, photoId IN number) is
    photoType varchar2(20);
begin
    select "TYPE" into photoType from images where id = photoId;
    if photoType = 'director' then
        insert into people(NAME, PROFESSION, PHOTO_ID)
        values (directorName, 'director', photoId);
    else
        raise_application_error(-20001, 'Photo ID is not director');
    end if;

end addDirector;

create or replace procedure getAllMoviesWithoutMedia(result OUT SYS_REFCURSOR, page IN Number) is
begin
    Open result for select *
                    from MOVIES_VIEW
                    order by ID
                    offset (page - 1) * 50 rows fetch next 50 rows only;
end getAllMoviesWithoutMedia;



create or replace procedure getMovieByIdWithMedia(movieId IN number, result OUT SYS_REFCURSOR) is
begin
    Open result for select * from MOVIE_MEDIA_VIEW where ID = movieId;

end getMovieByIdWithMedia;

create or replace procedure getMovieByIdWithoutMedia(movieId IN number, result OUT SYS_REFCURSOR) is
begin
    Open result for select * from MOVIES_VIEW where id = movieId;
end getMovieByIdWithoutMedia;

create or replace procedure addActorToMovie(movieId IN number, actorId IN number, RoleIn IN varchar2) is
    actor varchar2(20);
begin
    select profession into actor from people where id = actorId;
    if actor != 'actor' then
        raise_application_error(-20001, 'Actor ID is not correct');
    end if;
    insert into MOVIE_CASTS(MOVIE_ID, ACTOR_ID, "Role")
    values (movieId, actorId, RoleIn);
end addActorToMovie;

create or replace procedure getActorsByMovieId(movieId IN number, result OUT SYS_REFCURSOR) is
begin
    Open result for select *
                    from MOVIE_ACTORS_VIEW
                    where MOVIE_ID = movieId;
end getActorsByMovieId;


create or replace procedure GetThumbnailByMovieId(movieId IN number, result OUT blob) is
begin
    select IMAGE
    into result
    from MOVIE_MEDIA_VIEW
    where ID = movieId;
end GetThumbnailByMovieId;

create or replace procedure GetCommentsByMovieId(movieId IN number, result OUT SYS_REFCURSOR) is
begin
    Open result for select *
                    from COMMENTS_VIEW
                             join movies on movies.TITLE = comments_view.TITLE
                    where movies.ID = movieId;
end GetCommentsByMovieId;

create or replace procedure GetPersonImagebyId(personId IN number, result OUT blob) is
    peopleType varchar2(20);
begin
    select "TYPE" into peopleType from IMAGES where id = personId;
    if peopleType = 'director' or peopleType = 'actor' then
        select IMAGE
        into result
        from IMAGES
        where ID = personId;
    else
        raise_application_error(-20001, 'Person ID is not actor or director');
    end if;
end GetPersonImagebyId;

create or replace procedure DeleteImageById(imageId IN number) is
begin
    delete
    from images
    where images.id = imageId;
end DeleteImageById;

create or replace procedure UpdateImageById(imageId IN number, nameIn IN varchar2, imageIn IN blob,
                                            typeIn IN varchar2) is
begin
    update images
    set NAME   = nameIn,
        IMAGE  = imageIn,
        "TYPE" = typeIn
    where images.id = imageId;
end UpdateImageById;


create or replace procedure getMoviesByActorId(actorId IN number, result OUT SYS_REFCURSOR) is
    existActor varchar2(20);
begin
    --check if id exists
    select (select ID from people where id = actorId) into existActor from dual;
    if actorId is null then
        raise_application_error(-20001, 'Actor not found');
    end if;
    Open result for select *
                    from MOVIE_ACTORS_VIEW
                    where ACTOR_ID = actorId;

end getMoviesByActorId;

create or replace procedure getMoviesByDirectorId(directorID IN number, result OUT SYS_REFCURSOR) is
begin
    Open result for select *
                    from MOVIES_VIEW
                             join people on people.NAME = movies_view.DIRECTOR
                    where people.ID = directorID;
    if result%rowcount = 0 then
        raise_application_error(-20001, 'Director ID is not correct');
    end if;
end getMoviesByDirectorId;

create or replace procedure addHistory(userId IN number, movieId IN number) is
begin
    insert into HISTORY(USER_ID, MOVIE_ID)
    values (userId, movieId);
end addHistory;

create or replace procedure getUserHistoryByUsername(name IN varchar2, result OUT SYS_REFCURSOR) is
begin
    Open result for select *
                    from HISTORY_VIEW
                    where USERNAME = name
                    order by HISTORY_VIEW.TIME desc;
end getUserHistoryByUsername;
*/


create or replace package AdminPackage
as
    procedure RegisterUser(user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,
                           user_role_id Number);
    procedure addFavourite(userID number, movieID number);
    procedure deleteFavourite(userID number, movieID number);
    procedure addComment(userID number, movieID number, comment_text varchar2);
    procedure addGenre(genreName IN varchar2);
    procedure deleteGenre(genreID IN number);
    procedure AddNewImage(imageName IN varchar2, image IN BLOB, ImType IN varchar2, imageId OUT number);
    procedure AddNewVideo(videoName IN varchar2, video IN BLOB, videoType IN varchar2, videoId OUT number);
    procedure AddNewMovie(movieName IN varchar2, movieDescription IN varchar2,
                          movieYear IN number,
                          ImageID IN number, VideoID IN number, GenreID IN number, DirectorID IN number,
                          TrailerID IN number, movieId OUT number);
    procedure addActor(actorName IN varchar2, photoId IN number, actorId OUT number);
    procedure addDirector(directorName IN varchar2, photoId IN number, directorId OUT number);
    procedure addActorToMovie(movieId IN number, actorId IN number, RoleIn IN varchar2);
    procedure DeleteImageById(imageId IN number);
    procedure UpdateImageById(imageId IN number, nameIn IN varchar2, imageIn IN blob,
                              typeIn IN varchar2);
    procedure addHistory(userId IN number, movieId IN number);
end AdminPackage;



create or replace package body AdminPackage
as
    procedure RegisterUser(user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,
                           user_role_id Number) is
    begin
        INSERT INTO users (USERNAME, PASSWORD_HASH, email, ROLE_ID)
        VALUES (user_login, (user_password), user_email, user_role_id);
    end RegisterUser;
    procedure addFavourite(userID number, movieID number) is
    begin
        insert into favourites(USER_ID, MOVIE_ID)
        values (userID, movieID);
    exception
        when dup_val_on_index then
            raise_application_error(-20001, 'Movie already in favourites');
    end addFavourite;
    procedure deleteFavourite(userID number, movieID number) is
    begin
        delete
        from favourites
        where FAVOURITES.user_id = userId
          and movie_id = movieID;
    exception
        when no_data_found then
            raise_application_error(-20001, 'Movie not in favourites');
    end deleteFavourite;
    procedure addComment(userID number, movieID number, comment_text varchar2) is
    begin
        insert into comments(USER_ID, MOVIE_ID, "COMMENT")
        values (userID, movieID, comment_text);

    end addComment;
    procedure addGenre(genreName IN varchar2) is
    begin
        insert into genres(NAME)
        values (genreName);
    exception
        when dup_val_on_index then
            raise_application_error(-20001, 'Genre already exists');
    end addGenre;
    procedure deleteGenre(genreID IN number) is
    begin
        delete
        from genres
        where genres.id = genreID;
    exception
        when no_data_found then
            raise_application_error(-20001, 'Genre not found');
    end deleteGenre;
    procedure AddNewImage(imageName IN varchar2, image IN BLOB, ImType IN varchar2, imageId OUT number) is
    begin
        insert into images(NAME, IMAGE, "TYPE")
        values (imageName, image, ImType)
        returning id into imageId;
    end AddNewImage;
    procedure AddNewVideo(videoName IN varchar2, video IN BLOB, videoType IN varchar2, videoId OUT number) is
    begin
        insert into videos(NAME, VIDEO, "TYPE")
        values (videoName, video, videoType)
        returning id into videoId;
    end AddNewVideo;
    procedure AddNewMovie(movieName IN varchar2, movieDescription IN varchar2,
                          movieYear IN number,
                          ImageID IN number, VideoID IN number, GenreID IN number, DirectorID IN number,
                          TrailerID IN number, movieId OUT number) is
        imageType   IMAGES.TYPE%TYPE;
        videoType   VIDEOS.TYPE%TYPE;
        trailerType VIDEOS.TYPE%TYPE;
        peopleType  PEOPLE.PROFESSION%TYPE;
        genre       GENRES.NAME%TYPE;
    begin
        select (select "TYPE" from images where id = ImageID) into imageType from dual;
        if imageType is null then
            raise_application_error(-20001, 'Image not found');
        end if;
        select (select "TYPE" from videos where id = VideoID) into videoType from dual;
        if videoType is null then
            raise_application_error(-20001, 'Video not found');
        end if;
        select (select "TYPE" from videos where id = TrailerID) into trailerType from dual;
        if trailerType is null then
            raise_application_error(-20001, 'Trailer not found');
        end if;
        select (select profession from people where id = DirectorID) into peopleType from dual;
        if peopleType is null then
            raise_application_error(-20001, 'Director not found');
        end if;

        select (select name from genres where id = GenreID) into genre from dual;
        if genre is null then
            raise_application_error(-20001, 'Genre not found');
        end if;


        if imageType != 'movie' then
            raise_application_error(-20001, 'Image is not an movie image');
        end if;
        if videoType != 'movie' then
            raise_application_error(-20001, 'Video is not an movie video');
        end if;
        if trailerType != 'trailer' then
            raise_application_error(-20001, 'Trailer is not an movie trailer');
        end if;

        if peopleType != 'director' then
            raise_application_error(-20001, 'Person is not a director');
        end if;

        insert into movies(TITLE, DESCRIPTION, YEAR, IMAGE_ID, VIDEO_ID, GENRE_ID, DIRECTOR_ID, TRAILER_ID)
        values (movieName, movieDescription, movieYear, ImageID, VideoID, GenreID, DirectorID, TrailerID)
        returning id into movieId;
    end AddNewMovie;
    procedure addActor(actorName IN varchar2, photoId IN number, ActorID OUT number) is
        photoType varchar2(20);
    begin
        select "TYPE" into photoType from images where id = photoId;
        if photoType = 'actor' then
            insert into people(NAME, PROFESSION, PHOTO_ID)
            values (actorName, 'actor', photoId)
            returning id into ActorID;
        else
            raise_application_error(-20001, 'Photo ID is not actor');
        end if;
    end addActor;
    procedure addDirector(directorName IN varchar2, photoId IN number, DirectorID OUT number) is
        photoType varchar2(20);
    begin
        select "TYPE" into photoType from images where id = photoId;
        if photoType = 'director' then
            insert into people(NAME, PROFESSION, PHOTO_ID)
            values (directorName, 'director', photoId)
            returning id into DirectorID;
        else
            raise_application_error(-20001, 'Photo ID is not director');
        end if;

    end addDirector;
    procedure addActorToMovie(movieId IN number, actorId IN number, RoleIn IN varchar2) is
        actor varchar2(20);
    begin
        select profession into actor from people where id = actorId;
        if actor != 'actor' then
            raise_application_error(-20001, 'Actor ID is not correct');
        end if;
        insert into MOVIE_CASTS(MOVIE_ID, ACTOR_ID, "ROLE")
        values (movieId, actorId, RoleIn);
    end addActorToMovie;
    procedure DeleteImageById(imageId IN number) is
    begin
        delete
        from images
        where images.id = imageId;
    end DeleteImageById;
    procedure UpdateImageById(imageId IN number, nameIn IN varchar2, imageIn IN blob,
                              typeIn IN varchar2) is
    begin
        update images
        set NAME   = nameIn,
            IMAGE  = imageIn,
            "TYPE" = typeIn
        where images.id = imageId;
    end UpdateImageById;
    procedure addHistory(userId IN number, movieId IN number) is
    begin
        insert into HISTORY(USER_ID, MOVIE_ID)
        values (userId, movieId);
    end addHistory;

end AdminPackage;



