#
# Table structure for table 'billing'
#


DROP TABLE billing;
CREATE TABLE   billing (
  ID  bigint not null,
  USER_ID bigint not null,
  PAYPAL_LOGIN varchar (240) not null,
  MINIMUM_PAYOUT bigint not null,
    PRIMARY KEY (ID)
);


#
# Dumping data for table 'billing'
#
INSERT INTO billing VALUES(0,0,'',5000);
INSERT INTO billing VALUES(1,1,'',5000);




