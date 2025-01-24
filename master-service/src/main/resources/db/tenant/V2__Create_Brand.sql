DROP TABLE IF EXISTS public.brand;

CREATE TABLE IF NOT EXISTS public.brand
(
    id BIGSERIAL PRIMARY KEY,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    image_url character varying(255) COLLATE pg_catalog."default",
    status boolean DEFAULT true,
    display_order integer,
    parent_brand_id integer,
    meta_title character varying(150) COLLATE pg_catalog."default",
    meta_keywords text COLLATE pg_catalog."default",
    meta_description text COLLATE pg_catalog."default",
    view_count integer DEFAULT 0,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT brand_parent_brand_id_fkey FOREIGN KEY (parent_brand_id)
    REFERENCES public.brand (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE SET NULL
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.brand
    OWNER to postgres;