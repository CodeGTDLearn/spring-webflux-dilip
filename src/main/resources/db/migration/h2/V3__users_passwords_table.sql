CREATE TABLE IF NOT EXISTS userspasswords
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nameuser VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(150) NOT NULL,
    authorities VARCHAR(150) NOT NULL
);
