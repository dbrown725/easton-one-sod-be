CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `screen_name` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(100) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `avatar_color` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
 );


CREATE TABLE `song` (
  `id` int NOT NULL AUTO_INCREMENT,
  `youtube_title` varchar(100) NOT NULL,
  `youtube_url` varchar(100) NOT NULL,
  `youtube_playlist` varchar(100) NOT NULL,
  `actual_band_name` varchar(100) DEFAULT NULL,
  `actual_song_name` varchar(100) NOT NULL,
  `user_id` int NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_ibfk_1` (`user_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `bullpen_song` (
  `id` int NOT NULL AUTO_INCREMENT,
  `youtube_title` varchar(100) NOT NULL,
  `youtube_url` varchar(100) NOT NULL,
  `actual_band_name` varchar(100) NOT NULL,
  `actual_song_name` varchar(100) NOT NULL,
  `message` varchar(250) DEFAULT NULL,
  `sort_order` int NOT NULL,
  `user_id` int NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `bullpen_song_ibfk_1` (`user_id`),
  CONSTRAINT `bullpen_song_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);



