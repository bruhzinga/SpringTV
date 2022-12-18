create index movies_imaged_id_idx
    on movies (image_id);
create index movies_description_idx
    on movies (description);
create index movies_title_idx
    on movies (title);
create index movies_year_idx
    on movies (year);
create index movies_number_of_views_idx
    on movies (number_of_views);
create index movies_director_idx
    on movies (DIRECTOR_ID);


-------PEOPLE TABLE------------------
create index people_name_idx
    on people (name);

create index people_image_id_idx
    on people (PHOTO_ID);

----GENRES TABLE---------------------
create index genres_name_idx
    on genres (name);
