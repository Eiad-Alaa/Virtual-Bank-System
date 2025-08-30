
CREATE TABLE IF NOT EXISTS vbank.users (
    user_id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    is_active boolean,
    last_name character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    username character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT user_email_unique UNIQUE (email),
    CONSTRAINT user_username_unique UNIQUE (username)
);



