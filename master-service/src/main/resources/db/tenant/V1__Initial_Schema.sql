-- Table: public.user

DROP TABLE IF EXISTS public."users";

CREATE TABLE IF NOT EXISTS public."users"
(
    id BIGSERIAL PRIMARY KEY,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(15) COLLATE pg_catalog."default",
    password character(255) COLLATE pg_catalog."default",
    avatar character(255) COLLATE pg_catalog."default",
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT user_email_key UNIQUE (email),
    CONSTRAINT user_phone_key UNIQUE (phone)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."users"
    OWNER to postgres;

-- Table: public.address

DROP TABLE IF EXISTS public.user_address;

CREATE TABLE IF NOT EXISTS public.user_address
(
    id BIGSERIAL PRIMARY KEY,
    user_id integer NOT NULL,
    address_line_1 character varying(255) COLLATE pg_catalog."default" NOT NULL,
    address_line_2 character varying(255) COLLATE pg_catalog."default",
    city character varying(100) COLLATE pg_catalog."default" NOT NULL,
    state character varying(100) COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying(20) COLLATE pg_catalog."default" NOT NULL,
    country character varying(100) COLLATE pg_catalog."default" NOT NULL,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT address_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public."users" (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_address
    OWNER to postgres;

-- Table: public.category

-- DROP TABLE IF EXISTS public.category;

CREATE TABLE IF NOT EXISTS public.category
(
    id BIGSERIAL PRIMARY KEY,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    slug character varying(150) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    image_url character varying(255) COLLATE pg_catalog."default",
    status boolean DEFAULT true,
    display_order integer,
    parent_category_id integer,
    meta_title character varying(150) COLLATE pg_catalog."default",
    meta_keywords text COLLATE pg_catalog."default",
    meta_description text COLLATE pg_catalog."default",
    view_count integer DEFAULT 0,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT category_slug_key UNIQUE (slug),
    CONSTRAINT category_parent_category_id_fkey FOREIGN KEY (parent_category_id)
    REFERENCES public.category (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE SET NULL
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.category
    OWNER to postgres;

-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    id BIGSERIAL PRIMARY KEY,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    slug character varying(150) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    price numeric(10,2) NOT NULL,
    is_active boolean DEFAULT true,
    category_id integer NOT NULL,
    brand_id integer NOT NULL,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_slug_key UNIQUE (slug),
    CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id)
    REFERENCES public.category (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;

-- Table: public.product_tag

-- DROP TABLE IF EXISTS public.product_tag;

CREATE TABLE IF NOT EXISTS public.product_tag
(
    id BIGSERIAL PRIMARY KEY,
    product_id integer NOT NULL,
    tag character varying(50) COLLATE pg_catalog."default" NOT NULL,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_tag_product_id_fkey FOREIGN KEY (product_id)
    REFERENCES public.product (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_tag
    OWNER to postgres;

-- Table: public.product_variant

-- DROP TABLE IF EXISTS public.product_variant;

CREATE TABLE IF NOT EXISTS public.product_variant
(
    id BIGSERIAL PRIMARY KEY,
    product_id integer NOT NULL,
    sku character varying(100) NOT NULL COLLATE pg_catalog."default",
    attributes jsonb NOT NULL,
    price numeric(10,2) NOT NULL,
    stock_quantity integer DEFAULT 0,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_variant_sku_key UNIQUE (sku),
    CONSTRAINT product_variant_product_id_fkey FOREIGN KEY (product_id)
    REFERENCES public.product (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_variant
    OWNER to postgres;

-- Table: public.product_inventory

-- DROP TABLE IF EXISTS public.product_inventory;

CREATE TABLE IF NOT EXISTS public.product_inventory
(
    id BIGSERIAL PRIMARY KEY,
    product_variant_id integer NOT NULL,
    quantity integer NOT NULL DEFAULT 0,
    created_by character varying(50) COLLATE pg_catalog."default",
    updated_by character varying(50) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_inventory_product_variant_id_key UNIQUE (product_variant_id),
    CONSTRAINT product_inventory_variant_id_fkey FOREIGN KEY (product_variant_id)
    REFERENCES public.product_variant (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_inventory
    OWNER to postgres;
