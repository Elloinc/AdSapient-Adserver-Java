
#
# Table structure for table 'contextfilter'
#

drop table contextfilter;
CREATE TABLE  contextfilter (
  CONTEXT_FILTER_ID varchar(128) NOT NULL  ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  CATEGORYS VARCHAR(255),
  PAGEPOSITION VARCHAR(255),
  PLACES VARCHAR(255),
  PRIMARY KEY (CONTEXT_FILTER_ID)
);


#
# Dumping data for table 'contextfilter'
#
