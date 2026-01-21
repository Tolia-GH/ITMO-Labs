CREATE SCHEMA IF NOT EXISTS soa_lab2;

DROP TABLE IF EXISTS soa_lab2.space_marine CASCADE;
DROP TABLE IF EXISTS soa_lab2.starship CASCADE;
DROP TABLE IF EXISTS soa_lab2.chapter CASCADE;
DROP TABLE IF EXISTS soa_lab2.coordinates CASCADE;
DROP TYPE IF EXISTS soa_lab2.melee_weapon CASCADE;

CREATE TYPE soa_lab2.melee_weapon AS ENUM (
    'CHAIN_SWORD',
    'LIGHTING_CLAW',
    'POWER_BLADE'
);

CREATE TABLE IF NOT EXISTS soa_lab2.chapter (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    world VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS soa_lab2.coordinates (
    id SERIAL PRIMARY KEY,
    x INTEGER NOT NULL CHECK ( x <= 220 ),
    y DOUBLE PRECISION NOT NULL CHECK ( y <= 288 )
);

CREATE TABLE IF NOT EXISTS soa_lab2.space_marine (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinate_id INTEGER NOT NULL REFERENCES soa_lab2.coordinates(id),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    health INTEGER NOT NULL CHECK (health >= 1),
    heart_count INTEGER NOT NULL CHECK (heart_count BETWEEN 1 AND 3),
    height FLOAT NOT NULL,
    melee_weapon soa_lab2.melee_weapon,
    chapter_id INTEGER REFERENCES soa_lab2.chapter(id)
);

CREATE TABLE IF NOT EXISTS soa_lab2.starship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinate_id INTEGER NOT NULL REFERENCES soa_lab2.coordinates(id),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    space_marine_id_list INTEGER[] DEFAULT '{}'
);
