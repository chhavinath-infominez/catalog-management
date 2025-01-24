-- Table: public.admin

DROP TABLE IF EXISTS public.admin;

CREATE TABLE IF NOT EXISTS public.admin
(
    id BIGSERIAL PRIMARY KEY,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(50) COLLATE pg_catalog."default" DEFAULT 'SUPER_ADMIN'::character varying,
    status character varying(50) COLLATE pg_catalog."default" DEFAULT 'ACTIVE'::character varying,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    updated_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_email_key UNIQUE (email),
    CONSTRAINT admin_username_key UNIQUE (username),
    CONSTRAINT admin_role_check CHECK (role IN ('SUPER_ADMIN', 'ADMIN')),
    CONSTRAINT admin_status_check CHECK (status IN ('ACTIVE', 'INACTIVE'))
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin
    OWNER TO postgres;



-- Table: public.tenant

DROP TABLE IF EXISTS public.tenant;

CREATE TABLE IF NOT EXISTS public.tenant
(
    id BIGSERIAL PRIMARY KEY,
    tenant_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status character varying(50) COLLATE pg_catalog."default" DEFAULT 'ACTIVE'::character varying,
    logo character varying(255) COLLATE pg_catalog."default",
    address character varying(255) COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    updated_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tenant_status_check CHECK (status::text = ANY (ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tenant
    OWNER to postgres;


-- Table: public.database_config

DROP TABLE IF EXISTS public.database_config;

CREATE TABLE IF NOT EXISTS public.database_config
(
    id BIGSERIAL PRIMARY KEY,
    tenant_id character varying COLLATE pg_catalog."default" NOT NULL,
    url character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.database_config
    OWNER to postgres;