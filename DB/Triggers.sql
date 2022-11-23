create or replace trigger AddViewCount
    after insert
    on HISTORY
    for each row
begin
    update movies
    set number_of_views = number_of_views + 1
    where id = :new.movie_id;
end;