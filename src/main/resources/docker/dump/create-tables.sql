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

CREATE TABLE public.scheduler_job_info
(
   id UUID NOT NULL,
   job_name VARCHAR(80) NOT NULL UNIQUE,
   job_group VARCHAR(80) NOT NULL,
   description VARCHAR(120) NOT NULL,
   cron_expression VARCHAR(40) NULL,
   job_class VARCHAR(80) NOT NULL,
   job_status SMALLINT NOT NULL,
   cron_job SMALLINT NOT NULL,
   start_time TIME NULL,
   repeat_interval BIGINT NULL,
   repeat_count INTEGER NULL,
   PRIMARY KEY (id)
);

CREATE TABLE public.scheduler_jobs (
   id BIGSERIAL NOT NULL,
   scheduled_jobs SMALLINT NOT NULL,
   job_group VARCHAR(80) NOT NULL,
   cron_job SMALLINT NOT NULL,
   PRIMARY KEY (id)
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