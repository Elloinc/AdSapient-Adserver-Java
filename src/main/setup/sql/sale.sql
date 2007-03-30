#
# Table structure for table 'sale'
#


DROP    TABLE  sale;
CREATE TABLE sale(
  ID varchar(254) not null,
  PLACE_ID bigint ,
  BANNER_ID bigint,
  CAMPAIN_ID bigint,
  SITE_ID bigint, 
  TIME bigint  ,
  ADVERTIZER_ID bigint ,
  BANNER_RATE bigint,
  PLACES_RATE bigint,
  PUBLISHER_ID bigint ,
  POSITION_ID bigint,
  COUNTRY varchar(5),
  CATEGORY_ID bigint,
  END_TIME bigint,
  UNIQUE_USER_ID bigint,
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'sale'
#
