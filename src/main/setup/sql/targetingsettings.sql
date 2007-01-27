DROP TABLE targetingsettings;
CREATE TABLE targetingsettings (
      ID  bigint NOT NULL,
      TYPE_ID bigint,
      SETTINGS_KEY varchar (200) ,
      SETTING_VALUE bigint,
       PRIMARY KEY (ID)
    );
INSERT INTO targetingsettings VALUES('1','1','any',1);
INSERT INTO targetingsettings VALUES('2','1'	,'self',2);
INSERT INTO targetingsettings VALUES('3','1','new',3);
    INSERT INTO  targetingsettings VALUES('4','2','any',1);
    INSERT INTO targetingsettings VALUES('5','2','ordinary',2);
    INSERT INTO targetingsettings VALUES('6','2','popup',3);
    INSERT INTO targetingsettings VALUES('7','2','popunder',4);
    INSERT INTO targetingsettings VALUES('8','3','any',1);
    INSERT INTO targetingsettings VALUES('9','3','immediate',2);
    INSERT INTO targetingsettings VALUES('10','3','on.page.load',3);
    INSERT INTO targetingsettings VALUES('11','3','on.page.unload',4);




