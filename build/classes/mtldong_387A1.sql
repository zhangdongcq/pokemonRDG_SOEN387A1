DROP TABLE IF EXISTS `challenge`;
CREATE TABLE `challenge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `challenger` bigint(255),
  `challengee` bigint(255),
  `status` int(255),
  `game` bigint(255),
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `private_card_id` bigint(20),
  `type` varchar(255),
  `name` varchar(255),
  `position` varchar(255),
  `owner` bigint(255),
  `game` bigint(255),
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `challenger` bigint(255),
  `challengee` bigint(255),
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `hand`;
CREATE TABLE `hand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` bigint(255),
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `deck`;
CREATE TABLE `deck` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` bigint(255),
  PRIMARY KEY (`id`)
) ;
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255),
  `password` varchar(255),
  `status` varchar(255)  DEFAULT 'playing',
  `version` int(255) DEFAULT '1',
  PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;
DROP TABLE IF EXISTS `discard`;
CREATE TABLE `discard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` bigint(255),
  PRIMARY KEY (`id`)
) ;