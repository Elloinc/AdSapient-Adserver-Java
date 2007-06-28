DROP TABLE totalsreports;
CREATE TABLE  totalsreports (
  id BIGINT NOT NULL AUTO_INCREMENT,
  entityid BIGINT NOT NULL,
  entityclass VARCHAR(255),
  adviews INT,
  clicks INT,
  leads INT,
  sales INT,
  earnedspent DOUBLE,
  uniques INT,
  PRIMARY KEY (id),
  UNIQUE (entityid, entityclass)
  );
