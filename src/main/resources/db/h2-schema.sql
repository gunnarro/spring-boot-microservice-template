CREATE SCHEMA IF NOT EXISTS masterdata;
SET SCHEMA erp;

CREATE TABLE PERSON (
    PERSON_ID int NOT NULL,
    FIRST_NAME varchar(100),
    MIDDLE_NAME varchar(100),
    LAST_NAME varchar(100),
    SOCIAL_SECURITY_NUMBER int,
    BIRTH_DATE date,
    CONSTRAINT PK_PERSON_ID PRIMARY KEY (PERSON_ID)
    FK_ADDRESS_ID int FOREIGN KEY REFERENCES ADDRESS(ADDRESS_ID) NOT NULL
);


CREATE TABLE ADDRESS (
   ADDRESS_ID BIGINT NOT NULL,
   STREET_NAME VARCHAR(255) NOT NULL,
   STREET_NUMBER VARCHAR(255) NOT NULL,
   CITY VARCHAR(255) NOT NULL,
   COUNTRY VARCHAR(255) NOT NULL,
   CONSTRAINT pk_address_id PRIMARY KEY (address_id)
);

CREATE TABLE CUSTOMER (
    CUSTOMER_ID int NOT NULL PRIMARY KEY,
    FK_PERSON_ID int FOREIGN KEY REFERENCES PERSON(PERSON_ID) NOT NULL
);

