

INSERT INTO taracat.user (id, active, fights, loss, password, username, win) VALUES
(1, true, null, null, '', 'q', null);
(2, true, null, null, '', 'w', null);
(3, true, null, null, '', 'bot', null);

INSERT INTO taracat.user_role (user_id, roles) VALUES
(1, 'USER');
(2, 'USER');
(3, 'BOT');

INSERT INTO taracat.tarakan (id, experience, level, step, tarname, user_id, loss, running, win, draw, way_for_bot, img_id) VALUES
(23, 0, 1, 3, 'qw cdfe', 2, 0, 0, 0, 0, null, null);
(22, 0, 1, 3, 'www', 2, 0, 0, 0, 0, null, null);
(20, 0, 1, 3, 'eee', 2, 0, 0, 0, 0, null, null);
(21, 0, 1, 3, 'qwqw', 2, 0, 0, 0, 0, null, null);
(26, 411, 5, 7, 'w', 1, 14, 24, 10, 0, null, null);
(32, 520, 5, 7, 'dmsghbdfjisbn', 1, 2, 10, 8, 0, null, null);
(31, 0, 1, 3, 'bot1', 3, 0, 0, 0, 0, null, null);
(33, 0, 2, 4, 'bot2', 3, 0, 0, 0, 0, null, null);
(34, 0, 3, 5, 'bot3', 3, 0, 0, 0, 0, null, null);
(35, 0, 4, 6, 'bot4', 3, 0, 0, 0, 0, null, null);
(36, 0, 5, 7, 'bot5', 3, 0, 0, 0, 0, null, null);
(38, 101, 2, 4, 'zzzzz', 1, 98, 202, 101, 3, null, null);
(39, 29, 1, 3, 'hghghghg', 1, 27, 58, 29, 2, null, null);

INSERT INTO taracat.cat (id, cat_belt, cat_cheast, cat_head, cat_hp, cat_legs, cat_level, cat_straight, name, user_id, cat_expirience, cat_maxexpirience) VALUES
(1, 0, 0, 0, 0, 0, 1, 0, 'bb', 1, 0, 0);
(2, 0, 0, 0, 0, 0, 1, 0, 'rr', 1, 0, 0);