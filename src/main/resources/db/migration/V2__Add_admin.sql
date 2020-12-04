create extension if not exists pgcrypto;

INSERT INTO usr (id, username, password, active)
VALUES (1, 'admin', crypt('123', gen_salt('bf', 8)), true);

INSERT INTO user_role (user_id, roles)
VALUES (1, 'USER'),
       (1, 'ADMIN');