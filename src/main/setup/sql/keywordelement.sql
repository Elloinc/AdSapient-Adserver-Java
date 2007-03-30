#
# Table structure for table 'keywordelement'
#


DROP TABLE keywordelement;
CREATE TABLE keywordelement (
  ID  bigint NOT NULL,
  KEYWORD_FILTER_ID varchar(120) NOT NULL,
  KEYWORD_SET VARCHAR(30000) NOT NULL,
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'keywordelement'
#




