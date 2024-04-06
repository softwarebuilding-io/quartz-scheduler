SET SCHEMA 'public';

BEGIN;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE IF EXISTS public.title_genre
    DROP CONSTRAINT IF EXISTS fk_genre_title;

ALTER TABLE IF EXISTS public.title_genre
    DROP CONSTRAINT IF EXISTS fk_title_genre;

DROP TABLE IF EXISTS public.genre CASCADE;

DROP TABLE IF EXISTS public.title CASCADE;

DROP TABLE IF EXISTS public.title_genre CASCADE;

CREATE TABLE public.genre
(
    id         UUID                        NOT NULL DEFAULT uuid_generate_v4(),
    name       VARCHAR(255)                NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE public.title
(
    id           UUID                        NOT NULL DEFAULT uuid_generate_v4(),
    name         VARCHAR(255)                NOT NULL UNIQUE,
    description  VARCHAR(4000)               NOT NULL,
    release_year SMALLINT                    NOT NULL,
    type         INTEGER                     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.title_genre
(
    genre_id UUID NOT NULL,
    title_id UUID NOT NULL,
    PRIMARY KEY (genre_id, title_id)
);

ALTER TABLE IF EXISTS public.title_genre
    ADD CONSTRAINT fk_genre_title
        FOREIGN KEY (genre_id)
            REFERENCES public.genre
            ON DELETE CASCADE;

ALTER TABLE IF EXISTS public.title_genre
    ADD CONSTRAINT fk_title_genre
        FOREIGN KEY (title_id)
            REFERENCES public.title;

END;