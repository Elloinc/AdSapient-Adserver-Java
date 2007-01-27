#
# Table structure for table 'statisticsaver'
#


DROP TABLE  systemsfilter;
CREATE TABLE systemsfilter (
  SYSTEMS_FILTER_ID bigint NOT NULL,
  CAMPAIN_ID bigint ,
  BANNER_ID bigint default NULL,
  USER_BROWSERS text,
  USER_SYSTEMS text,
  USER_LANGS text,
  PRIMARY KEY (SYSTEMS_FILTER_ID)
);
