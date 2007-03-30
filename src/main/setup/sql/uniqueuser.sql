#
# Table structure for table 'uniqueuser'
#
DROP TABLE uniqueuser;
CREATE TABLE  uniqueuser (
  USER_ID bigint NOT NULL ,
  USER_IP varchar (128) not null,
   PRIMARY KEY (USER_ID)
);


#
# Dumping data for table 'uniqueuser'
#