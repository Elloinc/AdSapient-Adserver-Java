DROP TABLE parameter;
CREATE TABLE parameter (
  PARAMETER_ID bigint NOT NULL ,
  PARAM_LABEL varchar(240) NOT NULL ,
  NAME varchar(240) NOT NULL ,
  TYPE_ID bigint NOT NULL ,
  PARAM_VALUE VARCHAR(30000),
  PRIMARY KEY  (PARAMETER_ID)
);

INSERT INTO parameter VALUES (1,'Gender','gender',2,'male;female');
INSERT INTO parameter VALUES (2,'Age','age',1,'');
