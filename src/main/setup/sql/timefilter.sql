
#
# Table structure for table 'timefilter'
#
drop table   timefilter;
CREATE TABLE timefilter (
  TIME_FILTER_ID bigint NOT NULL  ,
  EXCLUDE_TIME varchar (100)  not null,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  PRIMARY KEY (TIME_FILTER_ID)
);


#
# Dumping data for table 'timefilter'
#
