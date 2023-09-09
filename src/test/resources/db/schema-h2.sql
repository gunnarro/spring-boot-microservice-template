CREATE SCHEMA IF NOT EXISTS erp;
SET SCHEMA erp;

--
-- create address table
--
create table ADDRESS (
   ADDRESS_ID BIGINT NOT NULL,
   STREET_NAME varchar(255) NOT NULL,
   STREET_NUMBER varchar(255) NOT NULL,
   CITY varchar(255) NOT NULL,
   COUNTRY varchar(255) NOT NULL,
   constraint pk_address_id PRIMARY KEY (address_id)
);

--
-- create person table
--
create table PERSON (
    PERSON_ID int not null,
    FIRST_NAME varchar(100),
    MIDDLE_NAME varchar(100),
    LAST_NAME varchar(100),
    SOCIAL_SECURITY_NUMBER bigint,
    BIRTH_DATE date,
    FK_ADDRESS_ID int,
    constraint PK_PERSON_ID primary key (PERSON_ID),
    foreign key (FK_ADDRESS_ID) references ADDRESS(ADDRESS_ID)
);

--
-- create customer table
--
create table CUSTOMER (
    CUSTOMER_ID int not null,
    FK_PERSON_ID int not null,
    constraint PK_CUSTOMER_ID primary key (CUSTOMER_ID),
    foreign key (FK_PERSON_ID) references PERSON(PERSON_ID)
);

--
-- create subscription table
--
create table SUBSCRIPTION (
    SUBSCRIPTION_ID int not null,
    FK_CUSTOMER_ID int not null,
    NAME varchar(256) not null,
    TYPE varchar(128) not null,
    STATUS varchar(48) not null,
    START_DATE date not null,
    END_DATE date not null,
    constraint PK_SUBSCRIPTION_ID primary key (SUBSCRIPTION_ID),
    foreign key (FK_CUSTOMER_ID) references CUSTOMER(CUSTOMER_ID)
);



