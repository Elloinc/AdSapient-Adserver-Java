#
# Table structure for table 'autorizationsettings'
#
drop table  autorizationsettings;
CREATE TABLE autorizationsettings (
  ID bigint NOT NULL ,
   authorize_new_accounts varchar(2),
  USERS_SITES VARCHAR(255),
  USERS_CAMPAIGNS VARCHAR(255),
    PRIMARY KEY (ID));
#
# Dumping data for table 'autorizationsettings'
#

