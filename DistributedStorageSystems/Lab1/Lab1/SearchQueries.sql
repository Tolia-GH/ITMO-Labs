-- find all table under schema: public
SELECT * FROM information_schema.tables WHERE table_schema = 'public';

-- find all columns of all tables under scheme: public
SELECT * FROM information_schema.columns WHERE table_schema = 'public';
