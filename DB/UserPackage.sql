create or replace package UserPackage
as
    procedure getUserById(userId IN number, result OUT sys_refcursor);
    procedure GetUserIdByUsername(InUserName IN varchar2, outID OUT number);
    procedure GetUserEncryptedPasswordByLogin(InUserName IN varchar2, password OUT varchar2);
    procedure getUserFavouritesByUsername(UserUsername IN varchar2, result OUT SYS_REFCURSOR);
    procedure getAllGenres(result OUT SYS_REFCURSOR);
    procedure findAllByProfession(professionIn IN varchar2, result OUT SYS_REFCURSOR);
    procedure getAllMoviesWithoutMedia(result OUT SYS_REFCURSOR, page IN Number);
    procedure getMovieByIdWithMedia(movieId IN number, result OUT SYS_REFCURSOR);
    procedure getMovieByIdWithoutMedia(movieId IN number, result OUT SYS_REFCURSOR);
    procedure getActorsByMovieId(movieId IN number, result OUT SYS_REFCURSOR);
    procedure GetThumbnailByMovieId(movieId IN number, result OUT blob);
    procedure GetCommentsByMovieId(movieId IN number, result OUT SYS_REFCURSOR);
    procedure GetPersonImageById(personId IN number, result OUT blob);
    procedure getMoviesByActorId(actorId IN number, result OUT SYS_REFCURSOR);
    procedure getMoviesByDirectorId(directorID IN number, result OUT SYS_REFCURSOR);
    procedure getUserHistoryByUsername(name IN varchar2, result OUT SYS_REFCURSOR);
    procedure SearchTables(tableName IN varchar2, columnName varchar2, searchParameters IN varchar2,
                           OracleText IN Number, result OUT SYS_REFCURSOR);
    existence number := null;
end UserPackage;



create or replace package body UserPackage
as
    procedure getUserById(userId IN number, result OUT sys_refcursor) is
    begin
        open result for
            select * from USERS_VIEW where ID = userId;
    end getUserById;
    procedure GetUserIdByUsername(InUserName IN varchar2, outID OUT number) is
    begin
        select id into outID from users where username = InUserName;
    exception
        when no_data_found then
            raise_application_error(-20001, 'User not found');
    end GetUserIdByUsername;
    procedure GetUserEncryptedPasswordByLogin(InUserName IN varchar2, password OUT varchar2) is
    begin
        select password_hash into password from users where username = InUserName;
    exception
        when no_data_found then
            raise_application_error(-20001, 'User not found');
    end GetUserEncryptedPasswordByLogin;
    procedure getUserFavouritesByUsername(UserUsername IN varchar2, result OUT SYS_REFCURSOR) is
    begin
        Open result for select * from FAVOURITES_VIEW where USERNAME = UserUsername;
    end;
    procedure getAllGenres(result OUT SYS_REFCURSOR) is
    begin
        Open result for select * from GENRES_VIEW;
    end;
    procedure findAllByProfession(professionIn IN varchar2, result OUT SYS_REFCURSOR) is
    begin
        Open result for select * from PEOPLE_VIEW where professionIn = profession;
    end findAllByProfession;
    procedure getAllMoviesWithoutMedia(result OUT SYS_REFCURSOR, page IN Number) is
    begin
        Open result for select *
                        from MOVIES_VIEW
                        order by ID
                        offset (page - 1) * 50 rows fetch next 50 rows only;
    end getAllMoviesWithoutMedia;
    procedure getMovieByIdWithMedia(movieId IN number, result OUT SYS_REFCURSOR) is
    begin
        Open result for select * from MOVIE_MEDIA_VIEW where ID = movieId;

    end getMovieByIdWithMedia;
    procedure getMovieByIdWithoutMedia(movieId IN number, result OUT SYS_REFCURSOR) is
    begin
        Open result for select * from MOVIES_VIEW where id = movieId;
    end getMovieByIdWithoutMedia;
    procedure getActorsByMovieId(movieId IN number, result OUT SYS_REFCURSOR) is
    begin
        Open result for select *
                        from MOVIE_ACTORS_VIEW
                        where MOVIE_ID = movieId;
    end getActorsByMovieId;
    procedure GetThumbnailByMovieId(movieId IN number, result OUT blob) is
    begin
        select IMAGE
        into result
        from MOVIE_MEDIA_VIEW
        where ID = movieId;
    end GetThumbnailByMovieId;
    procedure GetCommentsByMovieId(movieId IN number, result OUT SYS_REFCURSOR) is
    begin
        Open result for select *
                        from COMMENTS_VIEW
                                 join movies on movies.TITLE = comments_view.TITLE
                        where movies.ID = movieId;
    end GetCommentsByMovieId;
    procedure GetPersonImageById(personId IN number, result OUT blob) is
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
    procedure getMoviesByActorId(actorId IN number, result OUT SYS_REFCURSOR) is
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
    procedure getMoviesByDirectorId(directorID IN number, result OUT SYS_REFCURSOR) is
    begin
        Open result for select *
                        from MOVIES_VIEW
                                 join people on people.NAME = movies_view.DIRECTOR
                        where people.ID = directorID;
        select (select ID from people where id = directorID) into existence from dual;
        if existence is null then
            raise_application_error(-20001, 'Actor not found');
        end if;
    end getMoviesByDirectorId;
    procedure getUserHistoryByUsername(name IN varchar2, result OUT SYS_REFCURSOR) is
    begin
        Open result for select *
                        from HISTORY_VIEW
                        where USERNAME = name
                        order by HISTORY_VIEW.TIME desc;
    end getUserHistoryByUsername;
    procedure SearchTables(tableName IN varchar2, columnName varchar2, searchParameters IN varchar2,
                           OracleText IN number, result OUT SYS_REFCURSOR) is
    begin

        select (select INSTANCES from user_tables where TABLE_NAME = tableName) into existence from dual;
        if existence is NOT null then
            goto here;
        end if;
        select (select TEXT_LENGTH from USER_VIEWS where VIEW_NAME = tableName) into existence from dual;
        if existence is null then
            raise_application_error(-20001, 'Table not found');
        end if;

        <<here>>
        select (select DATA_LENGTH from user_tab_columns where table_name = tableName and COLUMN_NAME = columnName)
        into existence
        from dual;
        if existence is null then
            raise_application_error(-20001, 'Incorrect column name');
        end if;

        if (OracleText = 1)
        then
            open result for
                    'select * from ' || tableName || ' where CONTAINS(' || columnName || ', ''' || searchParameters ||
                    ''', 1) > 0';
        else
            open result for 'select * from ' || tableName || ' where ' || columnName ||
                            searchParameters;
        end if;

    end SearchTables;
end UserPackage;

select *
from USER_VIEWS;

select *
from IMAGES_VIEW
where TYPE = 'movie'

