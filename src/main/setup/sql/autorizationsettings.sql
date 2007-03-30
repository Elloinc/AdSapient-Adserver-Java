#
# Table structure for table 'autorizationsettings'
#
drop table  autorizationsettings;
CREATE TABLE autorizationsettings (
  ID bigint NOT NULL ,
   authorize_new_accounts varchar(2),
  USERS_SITES VARCHAR(30000),
  USERS_CAMPAIGNS VARCHAR(30000),
    PRIMARY KEY (ID));
#
# Dumping data for table 'autorizationsettings'
#

