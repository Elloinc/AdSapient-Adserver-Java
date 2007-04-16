#
# Table structure for table 'statisticsaver'
#


DROP TABLE  systemsfilter;
CREATE TABLE systemsfilter (
  SYSTEMS_FILTER_ID bigint NOT NULL,
  CAMPAIN_ID bigint ,
  BANNER_ID bigint default NULL,
  USER_BROWSERS VARCHAR(10000),
  USER_SYSTEMS VARCHAR(10000),
  USER_LANGS VARCHAR(10000),
  PRIMARY KEY (SYSTEMS_FILTER_ID)
);
