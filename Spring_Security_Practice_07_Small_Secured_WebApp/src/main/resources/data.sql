INSERT  INTO users  (id,username, password, algorithm) VALUES (1,'john', '{bcrypt}$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT  INTO authorities (id,name, user) VALUES (1,'READ', 1 );
INSERT  INTO authorities (id,name, user) VALUES (2,'WRITE', 1);


INSERT INTO products (id, name, price, currency) VALUES (1, 'Chocolate', 10, 'USD');
