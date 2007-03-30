
#
# Table structure for table 'money'
#
drop table money;
CREATE TABLE  money (
  ACCOUNT_ID bigint NOT NULL ,
  USER_ID bigint,
  MONEY  bigint,
  PRIMARY KEY (ACCOUNT_ID)
);


#
# Dumping data for table 'money'
# 
INSERT INTO money VALUES(0,0,0);
INSERT INTO money VALUES(1,1,0);

