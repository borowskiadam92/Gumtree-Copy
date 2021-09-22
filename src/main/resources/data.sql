insert into categories(id, name, searchable_name, parent_id) values(1, 'Wszystkie kategorie', 'wszystkie-kategorie', null);
insert into categories(id, name, searchable_name, parent_id) values(2, 'Nieruchomości', 'nieruchomosci', 1);
insert into categories(id, name, searchable_name, parent_id) values(3, 'Dom i ogród', 'dom-i-ogrod', 1);
insert into categories(id, name, searchable_name, parent_id) values(4, 'Muzyka i rozrywka', 'muzyka-i-rozrywka', 1);
insert into categories(id, name, searchable_name, parent_id) values(5, 'Motoryzacja', 'motoryzacja', 1);
insert into categories(id, name, searchable_name, parent_id) values(6, 'Antyki i kolekcje', 'antyki-i-kolekcje', 1);
insert into categories(id, name, searchable_name, parent_id) values(7, 'Oferty Pracy', 'oferty-pracy', 1);
insert into categories(id, name, searchable_name, parent_id) values(8, 'Łodzie i Pojazdy wodne', 'lodzie-i-pojazdy-wodne', 1);
insert into categories(id, name, searchable_name, parent_id) values(9, 'Dla dziecka', 'dla-dziecka', 1);
insert into categories(id, name, searchable_name, parent_id) values(10, 'Elektronika', 'elektronika', 1);
insert into categories(id, name, searchable_name, parent_id) values(11, 'Zwierzaki', 'zwierzaki', 1);
insert into categories(id, name, searchable_name, parent_id) values(12, 'Moda', 'moda', 1);
insert into categories(id, name, searchable_name, parent_id) values(13, 'Usługi', 'uslugi', 1);
insert into categories(id, name, searchable_name, parent_id) values(14, 'Społeczność', 'spolecznosc', 1);
insert into categories(id, name, searchable_name, parent_id) values(15, 'Sport i Fitness', 'sport-i-fitness', 1);

insert into categories(id, name, searchable_name, parent_id) values(16, 'pokoje do wynajęcia', 'pokoje-do-wynajecia', 2);
insert into categories(id, name, searchable_name, parent_id) values(17, 'mieszkania i domy do wynajęcia', 'mieszkania-i-domy-do-wynajecia', 2);
insert into categories(id, name, searchable_name, parent_id) values(18, 'mieszkania i domy - sprzedam', 'mieszkania-i-domy-sprzedam', 2);

insert into categories(id, name, searchable_name, parent_id) values(19, 'agd', 'agd', 3);
insert into categories(id, name, searchable_name, parent_id) values(20, 'meble', 'meble', 3);
insert into categories(id, name, searchable_name, parent_id) values(21, 'narzędzia i materiały budowlane', 'narzedzia-i-materialy-budowlane', 3);

insert into categories(id, name, searchable_name, parent_id) values(22, 'szafy, komody, regały', 'szafy-komody-regaly', 20);


insert into localization(id, name, searchable_name, parent_id) values(1, 'Wszystkie lokalizacje', 'wszystkie-lokalizacje', null);
insert into localization(id, name, searchable_name, parent_id) values(2, 'Polska', 'polska', 1);
insert into localization(id, name, searchable_name, parent_id) values(3, 'Dolnośląskie', 'dolnoslaskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(4, 'Kujawsko - pomorskie', 'kujawsko-pomorskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(5, 'Lubelskie', 'lubelskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(6, 'Lubuskie', 'lubuskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(7, 'Łódzkie', 'lodzkie', 2);
insert into localization(id, name, searchable_name, parent_id) values(8, 'Małopolskie', 'malopolskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(9, 'Mazowieckie', 'mazowieckie', 2);
insert into localization(id, name, searchable_name, parent_id) values(10, 'Opolskie', 'opolskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(11, 'Podkarpackie', 'podkarpackie', 2);
insert into localization(id, name, searchable_name, parent_id) values(12, 'Podlaskie', 'podlaskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(13, 'Pomorskie', 'pomorskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(14, 'Śląskie', 'slaskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(15, 'Świętokrzyskie', 'swietokrzyskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(16, 'Warmińsko-mazurskie', 'warminsko-mazurskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(17, 'Wielkopolskie', 'wielkopolskie', 2);
insert into localization(id, name, searchable_name, parent_id) values(18, 'Zachodniopomorskie', 'zachodniopomorskie', 2);

insert into localization(id, name, searchable_name, parent_id) values(19, 'Kraków', 'krakow', 8);
insert into localization(id, name, searchable_name, parent_id) values(20, 'Śródmieście', 'krakow-srodmiescie', 19);

insert into users(id, mail, nick, password, active) values(1, 'admin@admin.pl', 'admin', 'admin123', true);
insert into users(id, mail, nick, password, active) values(2, 'user@user.pl', 'user', 'user123', true);

insert into authorities(id, name) values(1, 'ROLE_ADMIN');
insert into authorities(id, name) values(2, 'ROLE_USER');

insert into USERS_AUTHORITIES(user_entity_id, authorities_id) values(1,1);
insert into USERS_AUTHORITIES(user_entity_id, authorities_id) values(1,2);
insert into USERS_AUTHORITIES(user_entity_id, authorities_id) values(2,2);














