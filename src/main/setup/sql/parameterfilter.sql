DROP TABLE  parameterfilter;
CREATE TABLE parameterfilter (
  PARAMETER_FILTER_ID varchar(128) NOT NULL,
  CAMPAIN_ID bigint default NULL,
  BANNER_ID bigint default NULL,
  PRIMARY KEY  (PARAMETER_FILTER_ID)
);