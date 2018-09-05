-- DATABASE NAME IS rms_db

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
SET default_tablespace = '';
SET default_with_oids = false;


CREATE TABLE books (
    id integer NOT NULL,
    title character varying(100) NOT NULL,
    author character varying(100) NOT NULL
);

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE books_id_seq OWNED BY books.id;
ALTER TABLE ONLY books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);
ALTER TABLE ONLY books ADD CONSTRAINT pk_books PRIMARY KEY (id);

INSERT INTO books VALUES (1, 'Oathbringer', 'Brandon Sanderson');
SELECT pg_catalog.setval('public.books_id_seq', 1, true);
