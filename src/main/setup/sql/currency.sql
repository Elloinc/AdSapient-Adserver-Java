#
# Table structure for table 'currency'
#

drop table  currency;
CREATE TABLE  currency (
  ID bigint NOT NULL ,
  CURRENCY_CODE varchar(10),
  CURRENCY_KEY varchar (200),
  IS_SYSTEM varchar(2),
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'currency'
# 

INSERT INTO currency VALUES(0,'GBP','currency.pounds.sterling',0);
INSERT INTO currency VALUES(1,'AUD','currency.australian.dollars',0);
INSERT INTO currency VALUES(2,'CAD','currency.canadian.dollars',0);
INSERT INTO currency VALUES(3,'EUR','currency.euros',0);
INSERT INTO currency VALUES(4,'USD','currency.us.dollars',1);
INSERT INTO currency VALUES(5,'JPY','currency.yen',0);






