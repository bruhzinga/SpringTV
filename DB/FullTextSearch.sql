grant CTXAPP to SpringTvAdmin;

GRANT EXECUTE ON CTXSYS.CTX_CLS TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_DDL TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_DOC TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_OUTPUT TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_QUERY TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_REPORT TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_THES TO SpringTvAdmin;
GRANT EXECUTE ON CTXSYS.CTX_ULEXER TO SpringTvAdmin;

GRANT EXECUTE ON CTX_DDL TO SPRINGTVADMIN; -- for oracle text package execution i.e. index rebuild and synchronize etc. Ignore if CTX_DDL not going to be used.
grant CTXAPP to SPRINGTVADMIN;
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

begin
    ctx_ddl.drop_preference('my_wordlist');
    ctx_ddl.drop_preference('my_lexer');
end;

create index movie_title_oracle_description_idx on MOVIES (DESCRIPTION) indextype is CTXSYS.CONTEXT parameters ('LEXER my_lexer WORDLIST my_wordlist');

drop index movie_title_oracle_description_idx;

--about query
select *
from MOVIES_VIEW
where CONTAINS(DESCRIPTION, 'fuzzy(test10)', 1) > 0;



select *
from MOVIES_VIEW
where CONTAINS(DESCRIPTION, 'fuzzy(Interstellar)', 1) > 0;


select *
from VIDEOS
where CONTAINS(NAME, 'fuzzy(Interstellar)', 1) > 0;





