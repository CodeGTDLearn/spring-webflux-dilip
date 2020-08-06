INSERT INTO userspasswords (nameuser, username, password, authorities)
VALUES ('paulo',
        'paulo',
        '{bcrypt}$2a$10$lBm1Qy45bR/fNT5i5OUBseNPcONtZs10earjLZ773qq.byhK/yKmS',
        'ROLE_ADMIN,ROLE_USER');
--         Special character $ nao e aceito na senha - somente com POSTGRES
--         '{bcrypt}$2a$10$lBm1Qy45bR/fNT5i5OUBseNPcONtZs10earjLZ773qq.byhK/yKmS',

INSERT INTO userspasswords (nameuser, username, password, authorities)
VALUES ('demetria',
        'demetria',
        'devdojo',
        'ROLE_USER');