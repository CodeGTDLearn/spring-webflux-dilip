CREATE TABLE IF NOT EXISTS userspasswords
(
    id SERIAL PRIMARY KEY,
    nameuser VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    authorities VARCHAR(255) NOT NULL
);
