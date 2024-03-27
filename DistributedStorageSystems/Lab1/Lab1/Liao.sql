create or replace procedure Read_Tables_By_Name(rel text) as
    $$
    DECLARE
alloid int [] := ARRAY(select C.oid from pg_description D, pg_attribute A,pg_class C,pg_type T where
                C.relname='account' and
                A.attrelid = C.oid and
                A.atttypid = T.oid AND
                A.attnum = D.objsubid and
                t.typname NOT LIKE '%id' and
                A.attnum > 0 order by C.oid);
        types text []:=ARRAY(select  T.typname from pg_description D, pg_attribute A,pg_class C,pg_type T where
                C.relname='account' and
                A.attrelid = C.oid and
                A.atttypid = T.oid AND
                A.attnum = D.objsubid and
                t.typname NOT LIKE '%id' and
                A.attnum > 0 order by C.oid);
        coloums_name text [] := ARRAY(select A.attname from pg_description D, pg_attribute A,pg_class C,pg_type T where
                C.relname='account' and
                A.attrelid = C.oid and
                A.atttypid = T.oid AND
                A.attnum = D.objsubid and
                t.typname NOT LIKE '%id' and
                A.attnum > 0 order by C.oid);
        descriptions text [] := ARRAY(select D.description from pg_description D, pg_attribute A,pg_class C,pg_type T where
                C.relname='account' and
                A.attrelid = C.oid and
                A.atttypid = T.oid AND
                A.attnum = D.objsubid and
                t.typname NOT LIKE '%id' and
                A.attnum > 0 order by C.oid);
        attnotnull bool [] := ARRAY(select A.attnotnull from pg_description D, pg_attribute A,pg_class C,pg_type T where
                C.relname='account' and
                A.attrelid = C.oid and
                A.atttypid = T.oid AND
                A.attnum = D.objsubid and
                t.typname NOT LIKE '%id' and
                A.attnum > 0 order by C.oid);
        result text := format(E'Таблица:%s\noid:%s\n\n%5s%15s%40s\n%s',rel,alloid[1],'No.','Имя столбца','Атрибуты','---- ----------------- ------------------------------------------------------------');
        i int := 1;
        num int := 1;
        o int;
last int := alloid[1];
BEGIN
            foreach o in array alloid loop
                if o != last then
                    num := 1;
last := o;
                    result := result|| format(E'\n\noid:%s\n%5s%15s%40s\n%s',o,'No.','Имя столбца','Атрибуты','---- ----------------- ------------------------------------------------------------');
                    result := result || E'\n';
                    result := E'\n%'||result;
end if;
                if attnotnull[i] then
                    result:=result|| E'\n'||format('%-5s',num)||format('%15s',coloums_name[i])||format('%15s','Type:')||format('%25s',types[i]||' not null');
                elsif not attnotnull[i] then
                    result:=result|| E'\n'||format('%-5s',num)||format('%15s',coloums_name[i])||format('%15s','Type:')||format('%25s',types[i]);
end if;
                result:=result||E'\n'||format('%15s','COMMEN:')||format('%-50s',descriptions[i])||E'\n'||E'\n';
                num := num + 1;
                i := i + 1;
end loop;
            raise info '%',result;
end;
$$ language plpgsql;

call Read_Tables_By_Name('account');
