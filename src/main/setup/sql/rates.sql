#
# Table structure for table 'rates'
#
drop table  rates;
   	CREATE TABLE  rates (
 	RATE_ID bigint NOT NULL ,
    CPC_RATE bigint,
    CPM_RATE bigint,
    CPL_RATE bigint,
    CPS_RATE bigint,
    RATE_TYPE varchar (50) NOT NULL,
    PRIMARY KEY (RATE_ID)
     );


#
# Dumping data for table 'rates'
#
INSERT INTO rates VALUES(0,0,0,0,0,1);