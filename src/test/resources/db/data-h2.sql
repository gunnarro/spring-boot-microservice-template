
--
-- add person data
--
insert into person(PERSON_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, SOCIAL_SECURITY_NUMBER, BIRTH_DATE, FK_ADDRESS_ID) values(1,'gunnar', null, 'ronneberg', 1107196922222, CURRENT_DATE, null);

--
-- add address data
--


--
-- add customer data
--
insert into customer(CUSTOMER_ID, FK_PERSON_ID) values (1,1);

--
-- add subscription data
--
insert into subscription(SUBSCRIPTION_ID, FK_CUSTOMER_ID, NAME, TYPE, STATUS, START_DATE, END_DATE) values (100, 1, 'mobile 4G', 'mobile', 'ACTIVE', CURRENT_DATE, CURRENT_DATE);
insert into subscription(SUBSCRIPTION_ID, FK_CUSTOMER_ID, NAME, TYPE, STATUS, START_DATE, END_DATE) values (200, 1, 'tv package 2', 'tv', 'TERMINATED', CURRENT_DATE, CURRENT_DATE);

