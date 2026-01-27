DROP TABLE IF EXISTS space_marine CASCADE;
DROP TABLE IF EXISTS starship CASCADE;
DROP TABLE IF EXISTS chapter CASCADE;
DROP TABLE IF EXISTS coordinates CASCADE;
DROP TYPE IF EXISTS melee_weapon CASCADE;

CREATE TYPE melee_weapon AS ENUM (
    'CHAIN_SWORD',
    'LIGHTING_CLAW',
    'POWER_BLADE'
);

CREATE TABLE IF NOT EXISTS chapter (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    world VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS coordinates (
    id SERIAL PRIMARY KEY,
    x INTEGER NOT NULL CHECK ( x <= 220 ),
    y DOUBLE PRECISION NOT NULL CHECK ( y <= 288 )
);

CREATE TABLE IF NOT EXISTS space_marine (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinate_id INTEGER NOT NULL REFERENCES coordinates(id),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    health INTEGER NOT NULL CHECK (health >= 1),
    heart_count INTEGER NOT NULL CHECK (heart_count BETWEEN 1 AND 3),
    height FLOAT NOT NULL,
    melee_weapon melee_weapon,
    chapter_id INTEGER REFERENCES chapter(id)
);

CREATE TABLE IF NOT EXISTS starship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinate_id INTEGER NOT NULL REFERENCES coordinates(id),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    space_marine_id_list INTEGER[] DEFAULT '{}'
);
