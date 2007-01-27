
#
# Table structure for table 'datefilter'
#
drop table   datefilter;
CREATE TABLE  datefilter (
  DATE_FILTER_ID bigint NOT NULL ,
  EXCLUDE_DAYS varchar(20), 
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  PRIMARY KEY (DATE_FILTER_ID)
);


#
# Dumping data for table 'datefilter'
#
