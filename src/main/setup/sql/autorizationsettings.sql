#
# Table structure for table 'autorizationsettings'
#
drop table  autorizationsettings;
CREATE TABLE autorizationsettings (
  ID bigint NOT NULL ,
   authorize_new_accounts varchar(2),
  USERS_SITES text,
  USERS_CAMPAIGNS text,
    PRIMARY KEY (ID));
#
# Dumping data for table 'autorizationsettings'
#

