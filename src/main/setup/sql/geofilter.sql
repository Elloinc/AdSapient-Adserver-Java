
#
# Table structure for table 'geofilter'
#
drop table   geofilter;
CREATE TABLE  geofilter (
  GEO_FILTER_ID bigint NOT NULL  ,
  EXCLUDE_COUNTRYS VARCHAR(30000),
  CITIES VARCHAR(30000),
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  PRIMARY KEY (GEO_FILTER_ID)
);


#
# Dumping data for table 'geofilter'
#
