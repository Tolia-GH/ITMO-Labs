CREATE OR REPLACE PROCEDURE get_pf_constraint_info(schema TEXT)
LANGUAGE plpgsql
AS
$$
    DECLARE
        constraint_record RECORD;
        last_constraint_name TEXT := NULL;--
    BEGIN
        RAISE INFO '% % % % % %',
            format('%-40s', 'Имя ограничения'),
            format('%-3s', 'Тип'),
            format('%-15s', 'Имя столбца'),
            format('%-20s', 'Имя таблицы'),
            format('%-20s', 'Имя таблицы'),
            format('%-15s', 'Имя столбца');
        RAISE INFO '% % % % % %',
            repeat('-', 40),
            repeat('-', 3),
            repeat('-', 15),
            repeat('-', 20),
            repeat('-', 20),
            repeat('-', 15);
        FOR constraint_record IN
            SELECT conname AS constraint_name,
                contype AS constraint_type,
                conrelid::regclass::text AS table_name,
                a.attname AS column_name,
                f.relname AS foreign_table,
                af.attname AS foreign_column
            FROM
                pg_constraint c
                JOIN pg_namespace namespace ON c.connamespace = namespace.oid
                JOIN pg_class t ON c.conrelid = t.oid
                JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum = ANY(c.conkey)
                JOIN pg_class f ON c.confrelid = f.oid
                LEFT JOIN pg_attribute af ON af.attrelid = f.oid AND af.attnum = ANY(c.confkey)
            WHERE namespace.nspname = schema AND contype IN ('p', 'f')
        LOOP
            IF last_constraint_name IS DISTINCT FROM constraint_record.constraint_name THEN--
                IF constraint_record.constraint_type = 'p' THEN
                    RAISE INFO '% % % %',
                        format('%-40s', constraint_record.constraint_name),
                        format('%-3s', 'P'),
                        format('%-15s', constraint_record.column_name),
                        format('%-20s', constraint_record.table_name);
                ELSIF constraint_record.constraint_type = 'f' THEN
                    RAISE INFO '% % % % % %',
                        format('%-40s', constraint_record.constraint_name),
                        format('%-3s', 'R'),
                        format('%-15s', constraint_record.column_name),
                        format('%-20s', constraint_record.table_name),
                        format('%-20s', constraint_record.foreign_table),
                        format('%-15s', constraint_record.foreign_column);
                END IF;
                last_constraint_name := constraint_record.constraint_name;--
            END IF;
        END LOOP;
    END
$$;

CALL get_pf_constraint_info('s336184');
