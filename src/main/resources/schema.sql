CREATE TABLE IF NOT EXISTS `USER` (
      userId bigint NOT NULL AUTO_INCREMENT
    , email varchar(100) NOT NULL
    , accountName varchar(100) NOT NULL
    , mobile varchar(100) NOT NULL
    , bankName varchar(100) NOT NULL
    , bankNumber varchar(100) NOT NULL
    , address varchar(100) NOT NULL
    , amount bigint NOT NULL
    , active boolean DEFAULT true
    , createdAt TIMESTAMP DEFAULT current_timestamp
    , PRIMARY KEY (userId)
);