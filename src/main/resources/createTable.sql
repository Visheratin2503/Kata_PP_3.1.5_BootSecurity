insert into SpringBootApp1.users (name, last_name, password, username)
values ('Vlad', 'Visheratin' , '$2a$12$rxXnG3TM3GXY2JiwPtStK.9Mdy9lMDkCg.L7gqbDp9HHkHCqto8Pm', 'Vlad');
#pass: 1234
insert into SpringBootApp1.users (name, last_name, password, username)
values ('Elena', 'Sidorova' , '$2a$12$5UJWvGe7.xiLmFrzjxyIO.K06JP.LsJPQ0hgS7BnQzgcdUqoQrAHW', 'elena');
#pass: 1111
insert into SpringBootApp1.roles value (1, 'ROLE_USER');
insert into SpringBootApp1.roles value (2, 'ROLE_ADMIN');
insert into SpringBootApp1.users_roles (users_id, roles_id) values (1,2);
insert into SpringBootApp1.users_roles (users_id, roles_id) values (2,1);