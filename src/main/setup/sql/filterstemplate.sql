
#
# Table structure for table 'filterstemplate'
#
drop table  filterstemplate;
CREATE TABLE  filterstemplate (
  TEMPLATE_ID bigint NOT NULL ,
  TIME_FILTER_ID varchar(128),
  GEO_FILTER_ID varchar(128),
  TRAFFIC_FILTER_ID varchar(128),
  CONTENT_FILTER_ID varchar (128),
  DATE_FILTER_ID varchar(128),
  USER_ID bigint not null,
  KEYWORD_FILTER_ID varchar(120),
  TEMPLATE_NAME varchar (128) not null,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  PARAMETER_FILTER_ID varchar(120),
  SYSTEMS_FILTER_ID varchar(120),
  BEHAVIOR_FILTER_ID varchar(120),
  PRIMARY KEY (TEMPLATE_ID)
);

#
# Dumping data for table 'filterstemplate'
#
