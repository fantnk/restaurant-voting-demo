INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (id, name)
VALUES (1, 'Papa Joss'),
       (2, 'Eat Meat');

INSERT INTO MENUS (id, effective_date, restaurant_id)
VALUES (1, CURRENT_DATE, 1),
       (2, CURRENT_DATE, 2);

INSERT INTO DISHES (name, price, menu_id)
VALUES ('Soup', 12.99, 1),
       ('Bread', 0.99, 1),
       ('Meat', 22, 2);

