DROP TABLE  referrerelement;
CREATE TABLE referrerelement (
  ID bigint NOT NULL ,
  SYSTEMS_FILTER_ID varchar(120) NOT NULL ,
  TARGET_URL text NOT NULL,
  REFERRER_TYPE int default NULL,
  PRIMARY KEY  (ID)
);
