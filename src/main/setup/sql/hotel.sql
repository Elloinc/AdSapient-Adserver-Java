#
# Table structure for table 'hotel'
#


DROP TABLE hotel;
CREATE TABLE hotel (
  ID  bigint NOT NULL,
  BANNER_ID bigint NOT NULL,
  NAME varchar(240),
  CITY varchar(240),
  DESCRIPTION varchar(240),
  CATEGORY bigint,
  TYPE varchar(10),
  IMAGE varchar(255),
  FEATURES text,
  UID varchar(255),
  DEST_LOCATION text,
  PLUS varchar(6),
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'hotel'
#




