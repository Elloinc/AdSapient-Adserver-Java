#
# Table structure for table 'statisticsaver'
#


DROP TABLE  systemsfilter;
CREATE TABLE systemsfilter (
  SYSTEMS_FILTER_ID bigint NOT NULL,
  CAMPAIN_ID bigint ,
  BANNER_ID bigint default NULL,
  USER_BROWSERS VARCHAR(255),
  USER_SYSTEMS VARCHAR(255),
  USER_LANGS VARCHAR(255),
  PRIMARY KEY (SYSTEMS_FILTER_ID)
);
