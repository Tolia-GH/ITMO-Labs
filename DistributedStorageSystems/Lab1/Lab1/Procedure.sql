CREATE OR REPLACE PROCEDURE get_pf_constraint_info(schema TEXT)
LANGUAGE plpgsql
AS
$$
    DECLARE
        constraint_record RECORD;
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
                attribute.attname AS column_name,
                class.relname AS foreign_table,
                attribute.attname AS foreign_column
            FROM pg_constraint c
                JOIN pg_namespace namespace ON c.connamespace = namespace.oid
                JOIN pg_class class ON c.conrelid = class.oid
                JOIN pg_attribute attribute ON attribute.attrelid = class.oid AND attribute.attnum = ANY(c.conkey)
            WHERE namespace.nspname = schema AND contype IN ('p', 'f')
        LOOP
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
        END LOOP;
    END
$$;

CALL get_pf_constraint_info('s336184');
