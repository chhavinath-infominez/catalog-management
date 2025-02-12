-- Table: public.user

DROP TABLE IF EXISTS public."users";

CREATE TABLE IF NOT EXISTS public."users"
(
    id BIGSERIAL PRIMARY KEY,
    first_name CHARACTER VARYING(255) COLLATE pg_catalog."default" NOT NULL,
    last_name CHARACTER VARYING(255) COLLATE pg_catalog."default" NOT NULL,
    email CHARACTER VARYING(255) COLLATE pg_catalog."default" NOT NULL,
    phone CHARACTER VARYING(15) COLLATE pg_catalog."default",
    password CHARACTER(255) COLLATE pg_catalog."default",
    avatar CHARACTER(255) COLLATE pg_catalog."default",
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
    user_id INTEGER NOT NULL,
    address_line_1 CHARACTER VARYING(255) COLLATE pg_catalog."default" NOT NULL,
    address_line_2 CHARACTER VARYING(255) COLLATE pg_catalog."default",
    city CHARACTER VARYING(100) COLLATE pg_catalog."default" NOT NULL,
    state CHARACTER VARYING(100) COLLATE pg_catalog."default" NOT NULL,
    postal_code CHARACTER VARYING(20) COLLATE pg_catalog."default" NOT NULL,
    country CHARACTER VARYING(100) COLLATE pg_catalog."default" NOT NULL,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
    name CHARACTER VARYING(100) COLLATE pg_catalog."default" NOT NULL,
    slug CHARACTER VARYING(150) COLLATE pg_catalog."default",
    description TEXT COLLATE pg_catalog."default",
    image_url CHARACTER VARYING(255) COLLATE pg_catalog."default",
    status BOOLEAN DEFAULT true,
    display_order INTEGER,
    parent_category_id INTEGER,
    meta_title CHARACTER VARYING(150) COLLATE pg_catalog."default",
    meta_keywords TEXT COLLATE pg_catalog."default",
    meta_description TEXT COLLATE pg_catalog."default",
    view_count INTEGER DEFAULT 0,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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

-- Table: public.brand

CREATE TABLE IF NOT EXISTS public.brand (
    id BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL,
    description TEXT COLLATE pg_catalog."default",
    email CHARACTER VARYING(255) NOT NULL,
    mobile CHARACTER VARYING(255) NOT NULL,
    status BOOLEAN DEFAULT true,
    parent_brand_id INTEGER,
    address TEXT COLLATE pg_catalog."default",
    city CHARACTER VARYING(100) COLLATE pg_catalog."default",
    state CHARACTER VARYING(100) COLLATE pg_catalog."default",
    country CHARACTER VARYING(100) COLLATE pg_catalog."default",
    postal_code CHARACTER VARYING(10) COLLATE pg_catalog."default",
    logo_url TEXT COLLATE pg_catalog."default",
    website TEXT COLLATE pg_catalog."default",
    CONSTRAINT fk_parent_brand FOREIGN KEY (parent_brand_id)
    REFERENCES public.brand (id) MATCH SIMPLE
                            ON UPDATE NO ACTION
                            ON DELETE SET NULL
);



-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    id BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(100) COLLATE pg_catalog."default" NOT NULL,
    slug CHARACTER VARYING(150) COLLATE pg_catalog."default",
    description TEXT COLLATE pg_catalog."default",
    price NUMERIC(10,2) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    category_id INTEGER NOT NULL,
    brand_id INTEGER NOT NULL,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
    product_id INTEGER NOT NULL,
    tag CHARACTER VARYING(50) COLLATE pg_catalog."default" NOT NULL,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
    sku CHARACTER VARYING(100) NOT NULL,
    attributes jsonb NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    stock_quantity INTEGER DEFAULT 0,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
    product_variant_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    created_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
    updated_by CHARACTER VARYING(50) COLLATE pg_catalog."default",
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
