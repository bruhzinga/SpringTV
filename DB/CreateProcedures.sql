--procedures
CREATE OR REPLACE PROCEDURE REGISTER_USER(user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,
                                          user_role_id Number) is
begin
    INSERT INTO users (USERNAME, PASSWORD_HASH, email, ROLE_ID)
    VALUES (user_login, (user_password), user_email, user_role_id);
end REGISTER_USER;

create or replace procedure add_favourite(userID number, movieID number) is
begin
    insert into favourites(USER_ID, MOVIE_ID)
    values (userID, movieID);
end add_favourite;

create or replace procedure delete_favourite(userID number, movieID number) is
begin
    delete
    from favourites
    where FAVOURITES.user_id = userId
      and movie_id = movieID;
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
end addGenre;

create or replace procedure deleteGenre(genreID IN number) is
begin
    delete
    from genres
    where genres.id = genreID;
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
begin
    select "TYPE" into imageType from images where id = ImageID;
    select "TYPE" into videoType from videos where id = VideoID;
    select "TYPE" into trailerType from videos where id = TrailerID;
    select "PROFESSION" into peopleType from people where id = DirectorID;
    if imageType = 'movie' then
        if videoType = 'movie' then
            if trailerType = 'trailer' then
                if peopleType = 'director' then
                    insert into movies(TITLE, DESCRIPTION, YEAR, IMAGE_ID, VIDEO_ID, GENRE_ID, DIRECTOR_ID, TRAILER_ID)
                    values (movieName, movieDescription, movieYear, ImageID, VideoID, GenreID, DirectorID, TrailerID);
                else
                    raise_application_error(-20001, 'Director ID is not correct');
                end if;
            else
                raise_application_error(-20001, 'Trailer ID is not correct');
            end if;
        else
            raise_application_error(-20001, 'Video ID is not correct');
        end if;
    else
        raise_application_error(-20001, 'Image ID is not correct');
    end if;

EXCEPTION
    WHEN NO_DATA_FOUND
        THEN
            raise_application_error(-20001, 'Non existent ID');
end AddNewMovie;

create or replace procedure findAllByProfession(professionIn IN varchar2, result OUT SYS_REFCURSOR) is
begin
    Open result for select * from PEOPLE_VIEW where professionIn = profession;
end findAllByProfession;

create or replace procedure addActor(actorName IN varchar2, photoId IN number) is
begin
    insert into people(NAME, PHOTO_ID, PROFESSION)
    values (actorName, photoId, 'actor');
end addActor;

create or replace procedure addDirector(directorName IN varchar2, photoId IN number) is
begin
    insert into people(NAME, PHOTO_ID, PROFESSION)
    values (directorName, photoId, 'director');
end addDirector;





