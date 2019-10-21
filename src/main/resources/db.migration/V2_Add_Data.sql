INSERT INTO public.users (id, active, fights, loss, password, username, win)
VALUES (1, true, null, null, '$2a$10$fnerpE1b0vSR2ziT11nT7OUclYdzzcSQscCwPQ/.Sp.5XkhgX6/uy', 'demo', null);
INSERT INTO public.users (id, active, fights, loss, password, username, win)
VALUES (2, true, null, null, '$2a$10$45jbPdm974AhP904VYS36uyDkX9n1SBGXLG0PXzkUv4rULZWE0Jtq', 'demo2', null);

INSERT INTO public.user_role (user_id, roles)
VALUES (1, 'USER');
INSERT INTO public.user_role (user_id, roles)
VALUES (2, 'USER');

INSERT INTO public.cat (id, cat_belt, cat_cheast, cat_expirience, cat_head, cat_hp, cat_legs, cat_level,
                        cat_maxexpirience, cat_straight, name, user_id)
VALUES (1, 0, 0, 0, 0, 0, 0, 1, 1000, 0, 'Trainee', 1);
INSERT INTO public.cat (id, cat_belt, cat_cheast, cat_expirience, cat_head, cat_hp, cat_legs, cat_level,
                        cat_maxexpirience, cat_straight, name, user_id)
VALUES (2, 0, 0, 0, 0, 0, 0, 1, 1000, 0, 'Junior', 1);
INSERT INTO public.cat (id, cat_belt, cat_cheast, cat_expirience, cat_head, cat_hp, cat_legs, cat_level,
                        cat_maxexpirience, cat_straight, name, user_id)
VALUES (3, 0, 0, 0, 0, 0, 0, 1, 1000, 0, 'JuniorPro', 1);
INSERT INTO public.cat (id, cat_belt, cat_cheast, cat_expirience, cat_head, cat_hp, cat_legs, cat_level,
                        cat_maxexpirience, cat_straight, name, user_id)
VALUES (4, 0, 0, 0, 0, 0, 0, 1, 1000, 0, 'Middle', 1);
INSERT INTO public.cat (id, cat_belt, cat_cheast, cat_expirience, cat_head, cat_hp, cat_legs, cat_level,
                        cat_maxexpirience, cat_straight, name, user_id)
VALUES (5, 0, 0, 0, 0, 0, 0, 1, 1000, 0, 'Senior', 1);

INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (1, 0, 0, null, 1, 0, 0, 3, 'Trainee', null, 0, 1);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (6, 0, 450, null, 5, 0, 0, 7, 'Senior', null, 0, 1);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (2, 0, 150, null, 2, 0, 0, 4, 'Junior', null, 0, 1);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (5, 0, 350, null, 4, 0, 0, 6, 'Middle', null, 0, 1);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (3, 0, 250, null, 3, 0, 0, 5, 'JuniorPRO', null, 0, 1);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (7, 0, 0, null, 1, 0, 0, 3, 'Tapok', null, 0, 2);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (8, 0, 0, null, 1, 0, 0, 3, 'Drisch', null, 0, 2);
INSERT INTO public.tarakan (id, draw, experience, img_id, level, loss, running, step, tarname, way_for_bot, win,
                            user_id)
VALUES (9, 0, 0, null, 1, 0, 0, 3, 'Shurup', null, 0, 2);