# Table structure for table 'plugins'
#

drop table  plugins;
CREATE TABLE  plugins (
   ID bigint not null,
   CLASS_NAME varchar (240),
   MAPPING_DATA VARCHAR(30000),
   PLUGIN_NAME varchar(240),
   PRESENT varchar(2),
   TYPE_ID bigint,
   PRIMARY KEY (ID)
);


#
# Dumping data for table 'plugins'
#
 insert into plugins values('1','com.adsapient.api_impl.ute.UteAdImpl','','UTE','1',4);
 insert into plugins values('2','com.adsapient.shared.mappable.ResourceImpl','','XML/XSLT','1',5);