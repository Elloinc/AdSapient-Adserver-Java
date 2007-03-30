


#
# Table structure for table 'statistic'
#


DROP TABLE  statistic;
CREATE TABLE  statistic(
  STATISTIC_ID BIGINT NOT NULL ,
  PLACE_ID bigint  ,
  BANNER_ID bigint ,
  CAMPAIN_ID bigint,
  SITE_ID bigint, 
  TIME bigint,
  END_TIME bigint,
  UNIQUE_USER_ID bigint,
  ADVERTIZER_ID bigint ,
  BANNER_RATE bigint,
  PLACES_RATE bigint,
  PUBLISHER_ID bigint,
  POSITION_ID bigint,
  COUNTRY varchar(5),
  CATEGORY_ID bigint,
  KEYWORD varchar(240),
  PRIMARY KEY (STATISTIC_ID)
);


#
# Dumping data for table 'statistic'
#
