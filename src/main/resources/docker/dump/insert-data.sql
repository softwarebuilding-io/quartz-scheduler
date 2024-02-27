SET SCHEMA 'public';

BEGIN;

INSERT INTO genre ( name )
VALUES ( 'Action' ),
       ( 'Comedy' ),
       ( 'Drama' ),
       ( 'Fantasy' ),
       ( 'Horror' ),
       ( 'Romance' ),
       ( 'Science Fiction' ),
       ( 'Thriller' ),
       ( 'Western' ),
       ( 'Documentary' );

END;