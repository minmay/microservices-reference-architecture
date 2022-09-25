-- this resets the database by removing data from all public tables (except liquibase) and setting the id sequence to 1.
create or replace procedure cleanDb()
AS '
DECLARE
    table_names varchar[] := array (select t.table_name from information_schema."tables" t where t.table_schema = ''public'' and t.table_type = ''BASE TABLE'' and t.table_name not in (''databasechangelog'', ''databasechangeloglock''));
    _table_name varchar;
begin
    FOREACH _table_name IN ARRAY table_names
        LOOP
            execute format(''ALTER TABLE %s DISABLE TRIGGER ALL'', _table_name);
        END LOOP;

    FOREACH _table_name IN ARRAY table_names
        LOOP
            execute format(''delete from %s'', _table_name);
        END LOOP;

    FOREACH _table_name IN ARRAY table_names
        LOOP
            execute format(''ALTER TABLE %s ENABLE TRIGGER ALL'', _table_name);
        END LOOP;

    FOREACH _table_name IN ARRAY table_names
        LOOP
            execute format(''ALTER SEQUENCE %s_id_seq RESTART WITH 1'', _table_name);
        END LOOP;
end;'
LANGUAGE plpgsql;

START TRANSACTION;
call cleanDb();
commit;
