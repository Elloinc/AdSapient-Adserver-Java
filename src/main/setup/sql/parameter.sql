DROP TABLE parameter;
CREATE TABLE parameter (
  PARAMETER_ID bigint NOT NULL ,
  PARAM_LABEL varchar(240) NOT NULL ,
  NAME varchar(240) NOT NULL ,
  TYPE_ID bigint NOT NULL ,
  PARAM_VALUE text,
  PRIMARY KEY  (PARAMETER_ID)
);

INSERT INTO parameter VALUES (1,'sex','sex',2,'male;female');
INSERT INTO parameter VALUES (2,'User age','age',1,'');
