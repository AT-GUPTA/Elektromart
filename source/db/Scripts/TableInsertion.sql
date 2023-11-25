INSERT INTO `Users` VALUES 
(5,'test','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','test@test.com',1,'ACTIVE','c8892475-5a29-4eca-8b7c-f0949415a000'),
(6,'testLogin','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','testLogin@test.com',1,'ACTIVE','d73ea4e8-e43b-4b07-9875-29bd6d25132d'),
(7,'adminTest','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','adminTest@gmail.com',2,'ACTIVE','f9eae671-8fc5-4a89-98f4-8a28d78be43e'),
(8,'testCust','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','customer@cust.com',1,NULL,'73eba372-6bdd-41f8-b1a4-2b58d96d8908'),
(9,'custometTest','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','customer@test.com',1,NULL,'409a58bd-c0da-409e-8021-a619247b533b'),
(10,'testCartFromAnonymous','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','ex@ex.ex',1,NULL,'undefined'),
(11,'test1','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','t@t.t',1,NULL,'0f61054c-c7d7-4648-95b5-ad195a11099b'),
(14,'adminTesting','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','admin@testing.com',1,NULL,'undefined'),
(15,'abcf','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','1234@w.w',1,NULL,'371d3bab-7ace-4c00-b57e-c00a72bfc92c'),
(16,'testwalk','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','test@walk.com',1,NULL,'a7bd47b0-4389-422b-ba0d-797ce18b8985'),
(17,'adminTest2','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','adimin@2.com',2,NULL,'61bf0e57-1f31-4daf-bf55-64a7867d4746'),
(18,'userTest','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','userTest@test.com',1,NULL,'ba0edf28-c275-4d82-bc67-1b46ce680407'),
(19,'testRun','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','test@run.run',1,NULL,'66e7ad41-5c91-43aa-8633-ddfdf7484e2b'),
(20,'adminTestRun','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','adminTestRun@ex.ex',2,NULL,'6d230828-b439-4567-b9aa-6a6bc0460aca'),
(21,'testPasscodeFile','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','test@passcode.file',1,NULL,'8dd820da-f88a-436d-bf01-339cdb3b1b57'),
(22,'testPassFile','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','pass@file.file',1,NULL,'6a86d2b7-8dbf-4da8-9d59-b59e7da73751'),
(23,'passwordFileTest','$2a$10$jTN4XW6o45Nx8PYyKvmHM.Ud/pUpGAwSvr8YCNIZvbIvWznuDER3W','password@file.test',1,NULL,'830dd189-a988-4fa3-9880-36b66cd15de8');

INSERT INTO `Role` VALUES (1,'customer'),(2,'staff');

INSERT INTO `Product` VALUES (1,'iPhone 13 Pro','The iPhone 13 Pro is Apple''s latest flagship smartphone, featuring a stunning Super Retina XDR display, A15 Bionic chip, and advanced camera system.','Apple Inc.','iphone-13-pro','SKU12345',999.99,0,0,0),(2,'Samsung Galaxy S21 Ultra','The Samsung Galaxy S21 Ultra is a high-end Android smartphone with a massive display, powerful Exynos processor, and versatile camera setup.','Samsung Electronics','galaxy-s21-ultra','SKU67890',1099.99,0,0,0),(3,'Google Pixel 6 Pro','The Google Pixel 6 Pro is known for its exceptional camera quality, stock Android experience, and fast performance.','Google LLC','pixel-6-pro','SKU54321',899.99,0,0,0),(4,'OnePlus 9 Pro','The OnePlus 9 Pro is a flagship killer with a smooth 120Hz display, Snapdragon 888 processor, and Hasselblad camera collaboration.','OnePlus Technology Co., Ltd.','oneplus-9-pro','SKU24680',899.00,0,0,0),(5,'Xiaomi Mi 11','The Xiaomi Mi 11 is a budget-friendly flagship phone with a high-resolution display, Snapdragon 888 chipset, and a capable camera system.','Xiaomi Corporation','xiaomi-mi-11','SKU13579',699.99,0,0,0),(6,'Sony Xperia 1 III','The Sony Xperia 1 III is a flagship device known for its 4K OLED display, Snapdragon 888 processor, and excellent audio capabilities.','Sony Corporation','xperia-1-iii','SKU78901',1099.00,0,0,0),(7,'Huawei P40 Pro','The Huawei P40 Pro boasts a Leica camera system, powerful Kirin 990 chipset, and a large AMOLED display.','Huawei Technologies Co., Ltd.','huawei-p40-pro','SKU65432',899.99,0,0,0),(8,'LG G8 ThinQ','The LG G8 ThinQ offers an OLED display, Snapdragon 855 processor, and advanced audio features like Crystal Sound OLED.','LG Electronics Inc.','lg-g8-thinq','SKU23456',599.00,0,0,0),(9,'Asus ROG Phone 5','The Asus ROG Phone 5 is a gaming smartphone with a high-refresh-rate AMOLED display, Snapdragon 888 chipset, and gaming-oriented features.','ASUSTeK Computer Inc.','asus-rog-phone-5','SKU98765',799.99,0,0,0),(10,'Motorola Moto G Power','The Motorola Moto G Power is known for its long-lasting battery life, clean Android experience, and affordable price point.','Motorola Solutions, Inc.','moto-g-power','SKU11111',249.99,0,0,0),(11,'Nokia 8.3 5G','The Nokia 8.3 5G offers 5G connectivity, a Qualcomm Snapdragon 765G chipset, and a pure Android experience.','HMD Global Oy','nokia-8-3-5g','SKU22222',499.00,0,0,0),(12,'OnePlus Nord N200','The OnePlus Nord N200 is an affordable 5G phone with a 90Hz display, Snapdragon 480 processor, and OxygenOS software.','OnePlus Technology Co., Ltd.','oneplus-nord-n200','SKU33333',249.99,0,0,0),(13,'Xiaomi Redmi Note 10','The Xiaomi Redmi Note 10 offers a high-resolution display, Snapdragon 678 chipset, and a versatile camera setup.','Xiaomi Corporation','redmi-note-10','SKU44444',199.99,0,0,0),(14,'Oppo Find X3 Pro','The Oppo Find X3 Pro is a flagship device with a high-refresh-rate display, Snapdragon 888 processor, and advanced camera features.','Guangdong Oppo Mobile Telecommunications Corp., Ltd.','oppo-find-x3-pro','SKU55555',1099.00,0,0,0),(15,'Realme 8 Pro','The Realme 8 Pro is a budget-friendly smartphone with a high-resolution display, Snapdragon 720G chipset, and a 108MP camera.','Realme Mobile Telecommunications (Shenzhen) Co., Ltd.','realme-8-pro','SKU66666',249.99,0,0,0),(16,'Sony Xperia 5 III','The Sony Xperia 5 III is a compact flagship device known for its 120Hz OLED display, Snapdragon 888 processor, and advanced camera capabilities.','Sony Corporation','xperia-5-iii','SKU77777',999.00,0,0,0),(17,'Motorola Razr 5G','The Motorola Razr 5G is a foldable smartphone with a nostalgic design, Snapdragon 765G chipset, and innovative foldable screen technology.','Motorola Solutions, Inc.','motorola-razr-5g','SKU88888',1399.99,0,0,0),(18,'Xiaomi Mi 11 Ultra','The Xiaomi Mi 11 Ultra is a flagship smartphone with a massive camera bump, Snapdragon 888 chipset, and a high-quality display.','Xiaomi Corporation','xiaomi-mi-11-ultra','SKU99999',1199.00,0,0,0),(19,'OnePlus 9','The OnePlus 9 is a mid-range flagship with a 120Hz display, Snapdragon 888 chipset, and Hasselblad camera collaboration.','OnePlus Technology Co., Ltd.','oneplus-9','SKU10000',699.00,0,0,0),(20,'Vivo X60 Pro','The Vivo X60 Pro is known for its gimbal stabilization camera, Exynos 1080 chipset, and stylish design.','Vivo Communication Technology Co. Ltd.','vivo-x60-pro','SKU10101',799.99,0,0,0),(21,'Testing createProduct','Testing createProduct Testing createProduct Testing createProduct Testing createProduct Testing createProduct Testing createProduct.','Test','testing-create-product','SKU111111111',999.99,0,0,0),(24,'Testing updateProduct','Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct Testing updateProduct.','Test','testing-update-product','SKU22222222',1000.99,0,0,0),(42,'Testing add from ui','testing add from ui to make sure that sku and slug are generated ','Test','testing-add-from-ui','2d55c3af-fa4d-4a9d-9d03-e7c4bdd23042',800.00,0,1,0),(48,'Test Edit success','test edit success test edit success test edit success','Test','test-edit-success','SKU-Ez4Ed4zB8-52-688',801.00,0,1,0),(49,'Test product','Testing an added product','Test','test-product','SKU-i8N1R1ufV-08-025',1290.00,12,1,0),(50,'Test product 3','testing add product','Test','test-product-3','SKU-0LkhMnFrC-86-199',1100.00,13,1,0),(51,'Test','Testing the admin page add/edit/show features','Test','test','SKU-G2CisvJoI-71-001',333.00,2,0,0),(52,'testRunTuesdayEdited','Testing the admin page add/edit/show features','Test','testruntuesday','SKU-pb1ae7Ynt-81-697',1299.00,16,1,0);

INSERT INTO `Cart` VALUES ('0f61054c-c7d7-4648-95b5-ad195a11099b',NULL),('1426807c-c35c-40f9-b6fe-95d804520a47',NULL),('18a1116d-9154-4e19-861d-ce0b989277d3',NULL),('200d91c4-6263-4dfb-aff7-ca18ac9d3e11',NULL),('21637c2f-5ed3-41cc-89ad-e1fe53588074',NULL),('371d3bab-7ace-4c00-b57e-c00a72bfc92c',NULL),('3e0da040-7f83-4a4b-b491-7077b947997e',NULL),('409a58bd-c0da-409e-8021-a619247b533b',NULL),('61bf0e57-1f31-4daf-bf55-64a7867d4746',NULL),('66e7ad41-5c91-43aa-8633-ddfdf7484e2b',NULL),('6a86d2b7-8dbf-4da8-9d59-b59e7da73751',NULL),('6d230828-b439-4567-b9aa-6a6bc0460aca',NULL),('704b69ae-35c6-4246-bfcc-2730d7f8c3ec',NULL),('73eba372-6bdd-41f8-b1a4-2b58d96d8908',NULL),('7401e12b-583d-4c4b-90ef-d4e204373d2b',NULL),('743f6039-3345-417a-8021-f8b1944b39f9',NULL),('7914c9db-44d5-4ec7-9fee-a7044c90ed08',NULL),('7db97e62-482a-4b4a-b993-dc96e05a4333',NULL),('8095aafa-f266-408c-afe3-ca347c25cd45',NULL),('830dd189-a988-4fa3-9880-36b66cd15de8',NULL),('85be37bf-5212-4d75-a346-6460c10b5db9',NULL),('85f4055d-158a-48e0-85d3-2e915ee20b45',NULL),('8dd820da-f88a-436d-bf01-339cdb3b1b57',NULL),('9405d460-99d9-48b7-aebb-0ed754833282',NULL),('95a1db1a-48b3-45d2-bf7a-1764ae305627',NULL),('95d1fd1f-5383-41a6-952c-6271a61439a3',NULL),('a546468b-889a-49b0-b235-7a822d852ffa',NULL),('a7bd47b0-4389-422b-ba0d-797ce18b8985',NULL),('a84c94a3-b7dc-447b-858e-71e51dcd7148',NULL),('ba0edf28-c275-4d82-bc67-1b46ce680407',NULL),('bc388775-6404-4e21-b2cf-44bf3a2621cb',NULL),('c14cb5aa-f4f0-4cc0-a28a-8d4dda43fe7f',NULL),('c1885b4d-257f-4e49-a716-2bc129b29de4',NULL),('c8892475-5a29-4eca-8b7c-f0949415a000',NULL),('cf84219f-5747-4d4b-a872-e1f70c510a58',NULL),('d73ea4e8-e43b-4b07-9875-29bd6d25132d',NULL),('db5312aa-a52b-43fc-ad61-59105ce0ad47',NULL),('f15432a2-f730-464d-a424-d1461b1ee5e7',NULL),('f2e5812a-3627-40d2-baaa-12e48214cc9f',NULL),('f5193b60-8d2e-407d-abdc-fd96ada0262b',NULL),('f9eae671-8fc5-4a89-98f4-8a28d78be43e',NULL),('fc290684-f163-4c99-a34b-88c3a54ab5b4',NULL),('undefined',NULL);

INSERT INTO `CartProduct` VALUES ('0f61054c-c7d7-4648-95b5-ad195a11099b','galaxy-s21-ultra',1),('18a1116d-9154-4e19-861d-ce0b989277d3','galaxy-s21-ultra',2),('18a1116d-9154-4e19-861d-ce0b989277d3','oneplus-9-pro',1),('18a1116d-9154-4e19-861d-ce0b989277d3','pixel-6-pro',3),('371d3bab-7ace-4c00-b57e-c00a72bfc92c','iphone-13-pro',1),('409a58bd-c0da-409e-8021-a619247b533b','iphone-13-pro',2),('61bf0e57-1f31-4daf-bf55-64a7867d4746','asus-rog-phone-5',1),('61bf0e57-1f31-4daf-bf55-64a7867d4746','oneplus-nord-n200',2),('61bf0e57-1f31-4daf-bf55-64a7867d4746','pixel-6-pro',6),('61bf0e57-1f31-4daf-bf55-64a7867d4746','xperia-5-iii',1),('6d230828-b439-4567-b9aa-6a6bc0460aca','asus-rog-phone-5',2),('6d230828-b439-4567-b9aa-6a6bc0460aca','iphone-13-pro',2),('6d230828-b439-4567-b9aa-6a6bc0460aca','xiaomi-mi-11',3),('743f6039-3345-417a-8021-f8b1944b39f9','iphone-13-pro',1),('95a1db1a-48b3-45d2-bf7a-1764ae305627','iphone-13-pro',1),('95d1fd1f-5383-41a6-952c-6271a61439a3','iphone-13-pro',1),('a7bd47b0-4389-422b-ba0d-797ce18b8985','galaxy-s21-ultra',2),('a7bd47b0-4389-422b-ba0d-797ce18b8985','iphone-13-pro',3),('cf84219f-5747-4d4b-a872-e1f70c510a58','asus-rog-phone-5',5),('cf84219f-5747-4d4b-a872-e1f70c510a58','galaxy-s21-ultra',1),('db5312aa-a52b-43fc-ad61-59105ce0ad47','galaxy-s21-ultra',3),('db5312aa-a52b-43fc-ad61-59105ce0ad47','oneplus-9-pro',6),('db5312aa-a52b-43fc-ad61-59105ce0ad47','xperia-1-iii',1),('f15432a2-f730-464d-a424-d1461b1ee5e7','galaxy-s21-ultra',1),('f15432a2-f730-464d-a424-d1461b1ee5e7','iphone-13-pro',1),('f15432a2-f730-464d-a424-d1461b1ee5e7','pixel-6-pro',1),('f2e5812a-3627-40d2-baaa-12e48214cc9f','iphone-13-pro',5),('f5193b60-8d2e-407d-abdc-fd96ada0262b','pixel-6-pro',1),('f9eae671-8fc5-4a89-98f4-8a28d78be43e','galaxy-s21-ultra',5),('f9eae671-8fc5-4a89-98f4-8a28d78be43e','iphone-13-pro',1);

INSERT INTO `Orders` VALUES (10001,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-05 00:00:00','DELIVERED','1234, main st. Maintown, Canada',1,'Cash on delivery'),(10002,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-05 00:00:00','DELIVERED','784, Crescent st., Montréal, Canada',1,'Cash on delivery'),(10003,6,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-05 00:00:00','DELIVERED','784, Crescent st., Montréal, Canada',1,'Cash on delivery'),(10004,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:01:29','DELIVERED','fdsfsd',920224599,'Cash on delivery'),(10005,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:02:15','DELIVERED','fasdfasd',1683033683,'Cash on delivery'),(10006,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:10:47','PENDING','grte',1186332174,'Cash on delivery'),(10007,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:13:14','SHIPPED','rwe',1627960521,'Cash on delivery'),(10008,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:14:03','SHIPPED','423423',809063438,'Cash on delivery'),(10009,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:18:35','PENDING','rwerwe',1491959925,'Cash on delivery'),(10010,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:19:15','PENDING','fsdfsd',1911026962,'Cash on delivery'),(10011,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:22:45','PENDING','fsdf',128654913,'Cash on delivery'),(10012,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:24:06','PENDING','fdsdfds',898658118,'Cash on delivery'),(10013,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:28:24','PENDING','321 main street',958199194,'Cash on delivery'),(10014,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:33:51','PENDING','4324',1953639501,'Cash on delivery'),(10015,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:34:15','PENDING','fsd',906765851,'Cash on delivery'),(10016,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:36:10','PENDING','dasd',416050808,'Cash on delivery'),(10017,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:36:57','PENDING','das',1738640215,'Cash on delivery'),(10018,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:38:26','PENDING','dd',234126130,'Cash on delivery'),(10019,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:40:50','PENDING','fsd',724665378,'Cash on delivery'),(10020,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:41:26','PENDING','fwe',151436648,'Cash on delivery'),(10021,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:41:59','PENDING','gfs',1981010961,'Cash on delivery'),(10022,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:42:56','PENDING','fvsdgf',1285528914,'Cash on delivery'),(10023,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:45:06','PENDING',',kojerwrf',1888166647,'Cash on delivery'),(10024,7,'61bf0e57-1f31-4daf-bf55-64a7867d4746','2023-11-07 19:46:14','PENDING','65756756',1107252603,'Cash on delivery'),(10025,7,'db5312aa-a52b-43fc-ad61-59105ce0ad47','2023-11-07 20:19:37','PENDING','ergdrf',6573877,'Cash on delivery'),(10026,7,'f2e5812a-3627-40d2-baaa-12e48214cc9f','2023-11-07 20:25:26','PENDING','799 Guinness  Street ',1243122299,'Cash on delivery'),(10027,19,'18a1116d-9154-4e19-861d-ce0b989277d3','2023-11-07 20:43:22','PENDING','test address ijo pkp',943299833,'Cash on delivery'),(10028,19,'743f6039-3345-417a-8021-f8b1944b39f9','2023-11-07 20:45:59','PENDING','3456789',372021295,'Cash on delivery'),(18753,7,'6d230828-b439-4567-b9aa-6a6bc0460aca','2023-11-08 00:04:27','PENDING','853 De liverpool',116074142,'Cash on delivery'),(18754,7,'95d1fd1f-5383-41a6-952c-6271a61439a3','2023-11-08 00:13:47','PENDING','fsdf',662751676,'Cash on delivery'),(18755,7,'cf84219f-5747-4d4b-a872-e1f70c510a58','2023-11-08 00:30:43','PENDING','fsdfsddasdfsd',1917040290,'Cash on delivery'),(18756,7,'f5193b60-8d2e-407d-abdc-fd96ada0262b','2023-11-08 00:41:13','PENDING','asd',1006257506,'Cash on delivery'),(18757,18,'f15432a2-f730-464d-a424-d1461b1ee5e7','2023-11-08 08:25:15','DELIVERED','3233 ffe',1962212895,'Cash on delivery');
