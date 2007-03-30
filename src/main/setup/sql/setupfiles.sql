#
# Table structure for table 'setupfiles'
#


DROP TABLE setupfiles;
CREATE TABLE  setupfiles(
  ID  bigint not null,
  URL_PATH varchar (200),
  SERVER_PATH varchar (200),
  COMMAND varchar (200),
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'setupfiles'
#




