CREATE PLUGGABLE DATABASE SpringTV ADMIN USER SpringTVAdmin IDENTIFIED BY qwerty
    roles = (dba, connect, resource)
    STORAGE (MAXSIZE UNLIMITED)
    DEFAULT TABLESPACE SpringTV_TS
        DATAFILE 'SpringTV_TS.DBF' SIZE 2 G AUTOEXTEND ON NEXT 100 M MAXSIZE 10 G
    PATH_PREFIX ='/opt/oracle/dbs/SpringTV/'
    FILE_NAME_CONVERT =('/opt/oracle/oradata/XE/pdbseed/','/opt/oracle/dbs/SpringTV/');

--drop pluggable database SpringTV including datafiles;
ALTER PLUGGABLE DATABASE SpringTV OPEN;
alter user SpringTVAdmin quota unlimited on SpringTV_TS;
/*
select name, cause, type, message
from PDB_PLUG_IN_VIOLATIONS
where status = 'PENDING';
*/
--change container
ALTER SESSION SET CONTAINER = SpringTV;

-------PROFILES---------------
CREATE PROFILE UserProfile LIMIT
    PASSWORD_LIFE_TIME 360
    SESSIONS_PER_USER 25
    FAILED_LOGIN_ATTEMPTS 7
    PASSWORD_LOCK_TIME 1
    PASSWORD_REUSE_TIME 10
    PASSWORD_GRACE_TIME DEFAULT
    CONNECT_TIME 180
    IDLE_TIME 120;


create role UserRole;
grant create session to UserRole;
grant execute on UserPackage to UserRole;


create user SpringTVUser identified by qwerty
    default tablespace SpringTV_TS
    temporary tablespace temp
    profile UserProfile
    ACCOUNT UNLOCK;


grant UserRole to SpringTVUser;



