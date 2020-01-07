


INSERT INTO DOCTOR (LAST_NAME, FIRST_NAME, patronymic, specialization)
 values ('Сивцова' ,'Ирина', 'Анатольевна','эндокринолог'),
        ('Маркелов','Вячеслав','Михайлович','Андролог'),
        ('Медведев', 'Виктор', 'Александрович','хирург'),
        ('Студеникина', 'Светлана', 'Геннадьевна','Гастроэнтеролог'),
        ('Архипова', 'Ольга', 'Витальевна','Акушер'),
        ('Рождественский', 'Анатолий', 'Игоревич', 'Проктолог'),
        ('Бичурина', 'Наталья', 'Юрьевна', 'Врач-косметолог'),
        ('Гудкова', 'Валентина', 'Петровна', 'Аллерголог'),
        ('Филиппова', 'Татьяна', 'Юрьевна', 'Гинеколог'),
        ('Бочкарев', 'Игорь', 'Александрович' ,'Врач УЗИ');

INSERT INTO PATIENT(LAST_NAME,FIRST_NAME,  PATRONYMIC, PHONE_NUMBER)
VALUES ('Волковинская', 'Елена' ,'Владимировна','(8482) 90-30-12'),
       ('Кемкин' ,'Анатолий' ,'Николаевич','(8482) 90-30-12'),
       ('Думбалов' ,'Руслан', 'Усманович','(8482) 65-03-14');

INSERT INTO RECIEPT (CREATION_DATE, DESCRIPTION, PRIORITY, VALIDITY, DOCTOR_ID, PATIENT_ID)
VALUES ('2019-01-05','Rp.: Codeini phosphatis 0,18 Solutionis Kalii bromidi 6,0 – 180 ml','STATIM','2019-05-22',1,1),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',2,2),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',1,3),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',1,1),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',1,2),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',4,3),
       ('2019-01-06','Rp.: Tabulettas Acetylcysteini 0,2 D.t.d. N 30','CITO','2019-05-22',4,1),
       ('2019-01-02','Rp.: Aer. Salbutamoli 120 doses (а 1 dosae – 0,0001)','NORMAL','2019-02-22',3,2);