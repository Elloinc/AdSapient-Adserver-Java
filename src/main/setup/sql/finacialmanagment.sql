
#
# Table structure for table 'financialmanagment'
#
drop table  financialmanagment;
CREATE TABLE  financialmanagment (
  ACCOUNT_ID bigint NOT NULL,
  USER_ID bigint,
  Publishing_CPM_rate  bigint,
  Publishing_CPC_rate  bigint,
  Publishing_CPL_rate  bigint,
  Publishing_CPS_rate  bigint,
  Advertising_CPM_rate bigint,
  Advertising_CPC_rate bigint,
  Advertising_CPL_rate bigint,
  Advertising_CPS_rate bigint,
  COMMISSION_RATE bigint,
  Publishing_Type varchar(100) ,
  Advertising_Type varchar(100), 
  PRIMARY KEY (ACCOUNT_ID)
);


#
# Dumping data for table 'financialmanagment'
#

INSERT INTO financialmanagment VALUES(0,0, 0,0,0,0, 0,0,0,0, 10, '1,1,1,1','1,1,1,1');
INSERT INTO financialmanagment VALUES(1,1, 0,0,0,0, 0,0,0,0, 10, '1,1,1,1','1,1,1,1'); 