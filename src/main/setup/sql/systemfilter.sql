
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
INSERT INTO systemfilter VALUES(1,'contentFilter','com.adsapient.api_impl.filter.ContentFilter');
INSERT INTO systemfilter VALUES(2,'trafficFilter','com.adsapient.api_impl.filter.TrafficFilter');
INSERT INTO systemfilter VALUES(3,'geoFilter','com.adsapient.api_impl.filter.GeoFilter');
#INSERT INTO systemfilter VALUES(4,'behaviorFilter','com.adsapient.api_impl.filter.BehaviorFilter');
INSERT INTO systemfilter VALUES(5,'keywordsFilter','com.adsapient.api_impl.filter.KeywordsFilter');
INSERT INTO systemfilter VALUES(6,'parametersFilter','com.adsapient.api_impl.filter.ParametersFilter');
INSERT INTO systemfilter VALUES(7,'systemsFilter','com.adsapient.api_impl.filter.SystemsFilter');
INSERT INTO systemfilter VALUES(8,'dateFilter','com.adsapient.api_impl.filter.DateFilter');
INSERT INTO systemfilter VALUES(9,'timeFilter','com.adsapient.api_impl.filter.TimeFilter');







#INSERT INTO systemfilter VALUES(2,'contextFilter','com.adsapient.api_impl.filter.ContextFilter');