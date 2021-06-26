insert into authorities(id, name) values (1000, 'ROLE_USER');
insert into users(id, mail, nick, password) values (1000, 'user@user.pl', 'commonUser', 'passsss');
insert into users_authorities(user_entity_id, authorities_id) values (1000, 1000);