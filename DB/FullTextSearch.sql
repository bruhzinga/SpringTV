grant CTXAPP to SpringTvAdmin;

GRANT EXECUTE ON CTXSYS.CTX_CLS TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_DDL TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_DOC TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_OUTPUT TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_QUERY TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_REPORT TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_THES TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_ULEXER TO SpringTvAdmin;

/*begin
    ctx_ddl.DROP_INDEX_SET('movie_set');
    ctx_ddl.add_index('movie_set', 'ID');
end;*/

/*CREATE INDEX movie_idx ON MOVIES (DESCRIPTION)
    INDEXTYPE IS CTXSYS.CTXCAT
    PARAMETERS ('index set movie_set');*/

begin
    ctx_ddl.create_preference('my_wordlist', 'BASIC_WORDLIST');
    ctx_ddl.create_preference('my_lexer', 'AUTO_LEXER');
    ctx_ddl.set_attribute('my_lexer', 'INDEX_STEMS', 'YES');
end;

create index movie_idx on MOVIES (DESCRIPTION)
    INDEXTYPE IS CTXSYS.CONTEXT;
create index image_idx on IMAGES (NAME) INDEXTYPE IS CTXSYS.CONTEXT;
create index video_idx on VIDEOS (NAME) INDEXTYPE IS CTXSYS.CONTEXT;


--about query
select *
from MOVIES_VIEW
where CONTAINS(DESCRIPTION, 'earth', 1) > 0;



select *
from IMAGES
where CONTAINS(NAME, 'fuzzy(Interstellar)', 1) > 0;


select *
from VIDEOS
where CONTAINS(NAME, 'fuzzy(Interstellar)', 1) > 0;