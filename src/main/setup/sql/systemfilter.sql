
#
# Table structure for table 'systemfilter'
#
drop table  systemfilter;
CREATE TABLE   systemfilter (
  ID  bigint NOT NULL ,
  FILTER_NAME varchar(200), 
  FILTER_CLASS varchar(200),
   PRIMARY KEY (ID)
);


#
# Dumping data for table 'systemfilter'
#
INSERT INTO systemfilter VALUES(1,'contentFilter','com.adsapient.shared.mappable.ContentFilter');
INSERT INTO systemfilter VALUES(2,'trafficFilter','com.adsapient.shared.mappable.TrafficFilter');
INSERT INTO systemfilter VALUES(3,'geoFilter','com.adsapient.shared.mappable.GeoFilter');
#INSERT INTO systemfilter VALUES(4,'behaviorFilter','com.adsapient.shared.mappable.BehaviorFilter');
INSERT INTO systemfilter VALUES(5,'keywordsFilter','com.adsapient.shared.mappable.KeywordsFilter');
INSERT INTO systemfilter VALUES(6,'parametersFilter','com.adsapient.shared.mappable.ParametersFilter');
INSERT INTO systemfilter VALUES(7,'systemsFilter','com.adsapient.shared.mappable.SystemsFilter');
INSERT INTO systemfilter VALUES(8,'dateFilter','com.adsapient.shared.mappable.DateFilter');
INSERT INTO systemfilter VALUES(9,'timeFilter','com.adsapient.shared.mappable.TimeFilter');







#INSERT INTO systemfilter VALUES(2,'contextFilter','com.adsapient.shared.mappable.ContextFilter');