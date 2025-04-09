-- Update agents
UPDATE agents SET status = 'approved' WHERE status IS NULL;

-- Update bookings with default values
UPDATE bookings SET saved_at = '2025-01-01' WHERE saved_at IS NULL;
UPDATE bookings SET packageid = 1 WHERE packageid IS NULL;
UPDATE bookings SET travelers = 'Legacy Traveler' WHERE travelers IS NULL;

-- Update existing customers - add emails and fixes
UPDATE customers SET custemail = 'laetia192@gmail.com' WHERE customerid = 104;
UPDATE customers SET custemail = 'amoskowitz@home.com' WHERE customerid = 105;
UPDATE customers SET custemail = 'jolvsade@aol.com' WHERE customerid = 106;
UPDATE customers SET custemail = 'cmierzwa@msn.com' WHERE customerid = 107;
UPDATE customers SET custemail = 'wlaporte9@outlook.com' WHERE customerid = 114;
UPDATE customers SET custemail = 'nancyk2@aol.com' WHERE customerid = 117;
UPDATE customers SET custemail = 'mabdou11@live.com' WHERE customerid = 119;
UPDATE customers SET custemail = 'ralph.alexander@gmail.com' WHERE customerid = 120;
UPDATE customers SET custemail = 'garya59@rogers.com' WHERE customerid = 127;
UPDATE customers SET custfirstname = 'Michelle' WHERE customerid = 135;
UPDATE customers SET custemail = 'angelog1991@mail.com' WHERE customerid = 139;
UPDATE customers SET custemail = 'derrickb1979@icloud.com' WHERE customerid = 140;
UPDATE customers SET custemail = 'rboyd10@gmx.com' WHERE customerid = 141;
UPDATE customers SET custemail = 'gerardb2000@telus.ca' WHERE customerid = 143;
UPDATE customers SET custfirstname = 'Damian', custlastname = 'Nolan', custaddress = '17 Athabasca Ave', custcity = 'Devon', custprov = 'AB', custpostal = 'T9G 1G3', custcountry = 'Canada', custhomephone = '7809872424', custbusphone = '7801290021', custemail = 'damian.nolan19@shaw.ca', agentid = 4 WHERE customerid = 144;

-- Update existing packages - add destination, coordinates, default rating
UPDATE packages SET destination = 'Bahamas', lat = 25.0744, lng = -77.3110, rating = 4 WHERE packageid = 1;
UPDATE packages SET destination = 'Hawaii', lat = 21.3005, lng = -157.8716, rating = 4 WHERE packageid = 2;
UPDATE packages SET destination = 'Thailand', lat = 13.6717, lng = 100.6106, rating = 4 WHERE packageid = 3;
UPDATE packages SET destination = 'France', lat = 48.8787, lng = 2.3133, rating = 4 WHERE packageid = 4;

-- Add some extra agencies
INSERT INTO agencies (agencyid, agncyaddress, agncycity, agncyprov, agncypostal, agncycountry, agncyphone, agncyfax) SELECT 3, '644 Spruce Way', 'Medicine Hat', 'AB', 'T1B 4X4', 'Canada', '4035281631', '4035281632' WHERE NOT EXISTS(SELECT 1 FROM agencies WHERE agncyaddress = '644 Spruce Way');
INSERT INTO agencies (agencyid, agncyaddress, agncycity, agncyprov, agncypostal, agncycountry, agncyphone, agncyfax) SELECT 4, '9802 Morrison St', 'Fort McMurray', 'AB', 'T9H 5B8', 'Canada', '7807913275', '7807913276' WHERE NOT EXISTS(SELECT 1 FROM agencies WHERE agncyaddress = '9802 Morrison St');

-- Add some extra agents
INSERT INTO agents (agentid, agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid, role, status) SELECT 11, 'Francesca', 'S.', 'Hoppe', '4035282244', 'francesca.hoppe@travelexperts.com', 'Senior Agent', 3, 'manager', 'approved' WHERE NOT EXISTS(SELECT 1 FROM agents WHERE agtemail = 'francesca.hoppe@travelexperts.com');
INSERT INTO agents (agentid, agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid, role, status) SELECT 12, 'Dominic', 'T.', 'King', '4036783625', 'dominic.king@travelexperts.com', 'Junior Agent', 3, 'agent', 'approved' WHERE NOT EXISTS(SELECT 1 FROM agents WHERE agtemail = 'dominic.king@travelexperts.com');
INSERT INTO agents (agentid, agtfirstname, agtlastname, agtbusphone, agtemail, agtposition, agencyid, role, status) SELECT 13, 'Brie', 'McClure', '7809555685', 'brie.mcclure@travelexperts.com', 'Senior Agent', 4, 'manager', 'approved' WHERE NOT EXISTS(SELECT 1 FROM agents WHERE agtemail = 'brie.mcclure@travelexperts.com');
INSERT INTO agents (agentid, agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid, role, status) SELECT 14, 'Barry', 'G.', 'Krieger', '7809624707', 'barry.krieger@travelexperts.com', 'Junior Agent', 4, 'agent', 'approved' WHERE NOT EXISTS(SELECT 1 FROM agents WHERE agtemail = 'barry.krieger@travelexperts.com');

-- Add some packages
INSERT INTO packages (packageid, pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, destination, lat, lng, rating) SELECT 5, 'Mediterranean Escape', '2025-06-06', '2025-06-20', 'A luxurious cruise through the Mediterranean Sea, visiting historic coastal cities and stunning islands.', 2950, 350, 'Spain, Italy, Greece', 36.3932, 25.4615, 4.4 WHERE NOT EXISTS(SELECT 1 FROM packages WHERE pkgname = 'Mediterranean Escape');
INSERT INTO packages (packageid, pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, destination, lat, lng, rating) SELECT 6, 'Amazon Rainforest Adventure', '2025-07-05', '2025-07-18', 'A guided eco-tour deep into the heart of the Amazon rainforest, with wildlife treks and river excursions.', 3200, 400, 'Brazil', -3.1190, -60.0217, 4.2 WHERE NOT EXISTS(SELECT 1 FROM packages WHERE pkgname = 'Amazon Rainforest Adventure');
INSERT INTO packages (packageid, pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, destination, lat, lng, rating) SELECT 7, 'Tokyo City Lights', '2025-08-01', '2025-08-14', 'Immerse yourself in the vibrant energy of Tokyo and its surrounding spa towns, with exclusive tours of historic temples, shopping districts, and food hotspots.', 4500, 425, 'Japan', 35.6762, 139.6503, 4.6 WHERE NOT EXISTS(SELECT 1 FROM packages WHERE pkgname = 'Tokyo City Lights');
INSERT INTO packages (packageid, pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, destination, lat, lng, rating) SELECT 8, 'Safari and Savannahs', '2025-09-20', '2025-10-16', 'A thrilling safari through the Serengeti with expert guides, luxurious lodges, and unforgettable wildlife encounters.', 4250, 500, 'Tanzania', -2.0305, 34.7929, 4.7 WHERE NOT EXISTS(SELECT 1 FROM packages WHERE pkgname = 'Safari and Savannahs');
INSERT INTO packages (packageid, pkgname, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, destination, lat, lng, rating) SELECT 9, 'Nordic Winter Magic', '2025-11-28', '2025-12-14', 'Experience the enchanting northern lights and winter activities in the snowy landscapes of the Nordic wilderness.', 3700, 375, 'Norway, Sweden, Finland', 68.1279, 27.0154, 4.9 WHERE NOT EXISTS(SELECT 1 FROM packages WHERE pkgname = 'Nordic Winter Magic');

-- Add some customers
INSERT INTO customers (customerid, custfirstname, custlastname, custaddress, custcity, custprov, custpostal, custcountry, custhomephone, custbusphone, custemail, agentid) SELECT 145, 'Ariel', 'Gerhold', '4813 50 Ave', 'Bonnyville', 'AB', 'T9N 1A6', 'Canada', '7808268929', '7808265511', 'arielgerhold21@yahoo.com', 10 WHERE NOT EXISTS(SELECT 1 FROM customers WHERE custemail = 'arielgerhold21@yahoo.com');
INSERT INTO customers (customerid, custfirstname, custlastname, custaddress, custcity, custprov, custpostal, custcountry, custhomephone, custbusphone, custemail, agentid) SELECT 146, 'Saul', 'Fisher', '201 Banff Ave', 'Banff', 'AB', 'T1L 1C4', 'Canada', '4037620333', '4031820571', 'fisherman9@aol.com', 11 WHERE NOT EXISTS(SELECT 1 FROM customers WHERE custemail = 'fisherman9@aol.com');
INSERT INTO customers (customerid, custfirstname, custlastname, custaddress, custcity, custprov, custpostal, custcountry, custhomephone, custbusphone, custemail, agentid) SELECT 147, 'Helena', 'Goodman', '221 Chippewa Rd', 'Sherwood Park', 'AB', 'T8A 4E6', 'Canada', '7804670052', '7802126421', 'hgoodman129@shaw.ca', 12 WHERE NOT EXISTS(SELECT 1 FROM customers WHERE custemail = 'hgoodman129@shaw.ca');
INSERT INTO customers (customerid, custfirstname, custlastname, custaddress, custcity, custprov, custpostal, custcountry, custhomephone, custbusphone, custemail, agentid) SELECT 148, 'Elliot', 'Gronkowski', '908 53 Ave NE', 'Calgary', 'AB', 'T2E 6N9', 'Canada', '4032929595', '4036015890', 'elliot90@hotmail.com', 13 WHERE NOT EXISTS(SELECT 1 FROM customers WHERE custemail = 'elliot90@hotmail.com');

-- Add some bookings and booking details
INSERT INTO bookings (bookingid, bookingdate, bookingno, travelercount, customerid, triptypeid, packageid, saved_at, travelers) SELECT 1304, '2024-01-03', 'GERN9823', 2, 145, 'L', 5, '2024-01-03 12:54:32', 'Ariel Gerhold, Bobby Smith' WHERE NOT EXISTS(SELECT 1 FROM bookings WHERE bookingno = 'GERN9823');
INSERT INTO bookingdetails (bookingdetailid, itineraryno, tripstart, tripend, description, destination, baseprice, agencycommission, bookingid) SELECT 1304, '895', '2024-01-14', '2024-01-28', '', 'Spain, Italy, Greece', 2950, 350, 1304 WHERE NOT EXISTS(SELECT 1 FROM bookingdetails WHERE bookingid = 1304);
INSERT INTO bookings (bookingid, bookingdate, bookingno, travelercount, customerid, triptypeid, packageid, saved_at, travelers) SELECT 1305, '2024-02-11', 'BVMK1054', 1, 146, 'B', 6, '2024-02-11 08:05:21', 'Saul Fisher' WHERE NOT EXISTS(SELECT 1 FROM bookings WHERE bookingno = 'BVMK1054');
INSERT INTO bookingdetails (bookingdetailid, itineraryno, tripstart, tripend, description, destination, baseprice, agencycommission, bookingid) SELECT 1305, '120', '2024-02-26', '2024-03-09', '', 'Brazil', 3200, 400, 1305 WHERE NOT EXISTS(SELECT 1 FROM bookingdetails WHERE bookingid = 1305);
INSERT INTO bookings (bookingid, bookingdate, bookingno, travelercount, customerid, triptypeid, packageid, saved_at, travelers) SELECT 1306, '2024-02-20', 'PLOV0192', 2, 147, 'L', 7, '2024-02-20 14:32:10', 'Helena Goodman, David Brown' WHERE NOT EXISTS(SELECT 1 FROM bookings WHERE bookingno = 'PLOV0192');
INSERT INTO bookingdetails (bookingdetailid, itineraryno, tripstart, tripend, description, destination, baseprice, agencycommission, bookingid) SELECT 1306, '812', '2024-03-02', '2024-03-17', '', 'Japan', 4500, 425, 1306 WHERE NOT EXISTS(SELECT 1 FROM bookingdetails WHERE bookingid = 1306);
INSERT INTO bookings (bookingid, bookingdate, bookingno, travelercount, customerid, triptypeid, packageid, saved_at, travelers) SELECT 1307, '2024-04-02', 'JKNQ0451', 1, 148, 'L', 8, '2024-04-02 03:12:21', 'Elliot Gronkowski' WHERE NOT EXISTS(SELECT 1 FROM bookings WHERE bookingno = 'JKNQ0451');
INSERT INTO bookingdetails (bookingdetailid, itineraryno, tripstart, tripend, description, destination, baseprice, agencycommission, bookingid) SELECT 1307, '905', '2024-04-20', '2024-05-04', '', 'Tanzania', 4250, 500, 1307 WHERE NOT EXISTS(SELECT 1 FROM bookingdetails WHERE bookingid = 1307);

-- Add customer types
INSERT INTO customer_type (discount_percentage, name) SELECT 0.15, 'Bronze' WHERE NOT EXISTS(SELECT 1 FROM customer_type WHERE name = 'Bronze');
INSERT INTO customer_type (discount_percentage, name) SELECT 0.10, 'Platinum' WHERE NOT EXISTS(SELECT 1 FROM customer_type WHERE name = 'Platinum');
INSERT INTO customer_type (discount_percentage, name) SELECT 0, 'Guest' WHERE NOT EXISTS(SELECT 1 FROM customer_type WHERE name = 'Guest');

-- Add some web users - all passwords are 'password' hashed with 10 round bcrypt
INSERT INTO web_user (is_agent, password, points, customer_id, customer_type_id, email) SELECT false, '$2a$10$XywW3ff9olJVSk1y3HB9D.pMgt6RF2PDROmgv1BDT7N9UzG79yrKa', 15080, 104, 1, 'laetia192@gmail.com' WHERE NOT EXISTS(SELECT 1 FROM web_user WHERE customer_id = 104);
INSERT INTO web_user (is_agent, password, points, customer_id, customer_type_id, email) SELECT false, '$2a$10$16tIcmMBB8OfiT/NGBuNOexDb3jEvmOl7OOEAZH2abHt6qJ3lsXxO', 797, 105, 3, 'amoskowitz@home.com'  WHERE NOT EXISTS(SELECT 1 FROM web_user WHERE customer_id = 105);
INSERT INTO web_user (is_agent, password, points, customer_id, customer_type_id, email) SELECT false, '$2a$10$Bnv3uADO/IJhI.dARIPj4eXOR8KZXPVaVBOE9rCPG7RpDkhOjOr5O', 4356, 106, 3, 'jolvsade@aol.com'  WHERE NOT EXISTS(SELECT 1 FROM web_user WHERE customer_id = 106);
INSERT INTO web_user (is_agent, password, points, customer_id, customer_type_id, email) SELECT false, '$2a$10$mfNopq1dDSvmEB8CrkHEaelfdJwG.5ONlAICnFkOcDJEwg8d275sK', 1155, 107, 3, 'cmierzwa@msn.com' WHERE NOT EXISTS(SELECt 1 FROM web_user WHERE customer_id = 107);

-- Add some reviews
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 3, 'Fun but too expensive!', 1, 1 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 1 AND user_id = 1);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 5, 'Very enjoyable, would recommend!', 2, 1 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 2 AND user_id = 1);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 5, 'Trying new foods and dishes every day was really an eye-opener!', 3, 2 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 3 AND user_id = 2);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 4, 'The cruise was absolutely lovely.', 4, 2 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 4 AND user_id = 2);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 4, 'I was thoroughly impressed by the stunning beaches!', 5, 3 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 5 AND user_id = 3);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 4, 'The Amazon rainforest blew me away!', 6, 3 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 6 AND user_id = 3);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 5, 'Each onsen we visited was a unique experience.', 7, 4 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 7 AND user_id = 4);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 3, 'Those zebras were so cute!', 8, 4 WHERE NOT EXISTS(SELECT 1 FROM package_review WHERE package_id = 8 AND user_id = 4);
INSERT INTO package_review (rating, review, package_id, user_id) SELECT 4, 'The fjords were overwhelmingly beautiful.', 9, 4 WHERE NOT EXISTS(SELECt 1 FROM package_review WHERE package_id = 9 AND user_id = 4);