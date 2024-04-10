-- find all table under schema: public
SELECT * FROM information_schema.tables WHERE table_schema = 'public';

-- find all columns of all tables under scheme: public
SELECT * FROM information_schema.columns WHERE table_schema = 'public';


SELECT conname AS constraint_name,
       contype AS constraint_type,
       conrelid::regclass::text AS table_name,
       a.attname AS column_name,
       t.relname AS foreign_table,
       a.attname AS foreign_column
FROM pg_constraint c
         JOIN pg_namespace n ON c.connamespace = n.oid
         JOIN pg_class t ON c.conrelid = t.oid
         JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum = ANY(c.conkey)
WHERE n.nspname = 's336184' AND contype IN ('p', 'f')