
DROP TABLE IF EXISTS `ajk`.`online_documents`;
DROP TABLE IF EXISTS `ajk`.`gallery_images`;
DROP TABLE IF EXISTS `ajk`.`images`;
DROP TABLE IF EXISTS `ajk`.`image_galleries`;
DROP TABLE IF EXISTS `ajk`.`image_categories`;
DROP TABLE IF EXISTS `ajk`.`visitor_comments`;
DROP TABLE IF EXISTS `ajk`.`visitors`;
DROP TABLE IF EXISTS `ajk`.`hit_counter`;
DROP TABLE IF EXISTS `ajk`, `packages`;
DROP TABLE IF EXISTS `ajk`.`hit_counter`;
DROP TABLE IF EXISTS `ajk`.`about`;
DROP TABLE IF EXISTS `ajk`.`system_properties`;
DROP TABLE IF EXISTS `ajk`.`security`;

CREATE TABLE  `ajk`.`security` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE  `ajk`.`system_properties` (
  `property_key` varchar(255) NOT NULL,
  `property_value_default`  varchar(255) NOT NULL,
  `property_value` varchar(255) NULL,
  PRIMARY KEY  (`property_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE  `ajk`.`about` (
  `id` bigint(20) NOT NULL,
  `about_heading` varchar(255) not null,
  `about_description` varchar(1024) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE  `ajk`.`hit_counter` (
  `id` bigint(20) NOT NULL auto_increment,
  `hit_time` timestamp NULL default CURRENT_TIMESTAMP,
  `hit_action` varchar(255) NOT NULL,
  `hit_lang_code` varchar(5) default NULL,
  `hit_type_code` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE  `ajk`.`online_documents` (
  `document_name` varchar(255) NOT NULL default '',
  `document_type` varchar(45) NOT NULL,
  `mime_type` varchar(255) NOT NULL default '',
  `file_size` mediumtext NOT NULL,
  `document_url` varchar(128) NOT NULL,
  `short_description` varchar(1024) default NULL,
  `document_data` longblob,
  PRIMARY KEY  (`document_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


CREATE TABLE  `ajk`.`image_categories` (
  `category_code` bigint(20) not null auto_increment,
  `category_description` varchar(255) not null,
  `category_image_url` varchar(255),
  `category_image` longblob,
  PRIMARY KEY (`category_code`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


CREATE TABLE  `ajk`.`image_galleries` (
  `gallery_code` bigint(20) not null auto_increment,
  `category_code` bigint(20) not null,
  `gallery_image_url` varchar(255),
  `gallery_description` varchar(255) not null,
  `gallery_image` longblob,
  PRIMARY KEY (`gallery_code`, `category_code`),
  CONSTRAINT `gallery_fk_category` FOREIGN KEY (`category_code`) REFERENCES `image_categories` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


CREATE TABLE  `ajk`.`images` (
  `image_code` bigint(20) not null auto_increment,
  `category_code` bigint(20) not null,
  `gallery_code` bigint(20) not null,
  `image_url` varchar(255) not null,
  `image_description` varchar(1024),
  `image_data` longblob,
  PRIMARY KEY (`image_code`, `category_code`, `gallery_code`),
  CONSTRAINT `image_fk_gallery` FOREIGN KEY (`category_code`,`gallery_code`) REFERENCES `image_galleries` (`category_code`,`gallery_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


CREATE TABLE `ajk`.`visitors` (
  `visitor_id` bigint(20) not null auto_increment,
  `visitor_name` varchar(255) not null,
  `visitor_surname` varchar(255) not null,
  `visitor_email` varchar(255) not null,
  PRIMARY KEY (`visitor_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


DROP TABLE IF EXISTS `ajk`.`visitor_comments`;
CREATE TABLE  `ajk`.`visitor_comments` (
  `comment_id` bigint(20) NOT NULL auto_increment,
  `visitor_id` bigint(20) NOT NULL,
  `comment_text` varchar(1024) NOT NULL,
  `published` varchar(1) NOT NULL default 'N',
  `date_submitted` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`comment_id`),
  KEY `comments_fk_visitors` (`visitor_id`),
  CONSTRAINT `comments_fk_visitors` FOREIGN KEY (`visitor_id`) REFERENCES `visitors` (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE `ajk`.`packages` (
  `package_id` bigint(20) not null auto_increment,
  `package_name` varchar(255) not null,
  `what_i_do` varchar(1024) not null,
  `what_you_do` varchar(1024) not null,
  `what_you_get` varchar(1024) not null,
  `cost` numeric(10,2) not null,
  PRIMARY KEY (`package_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

INSERT INTO system_properties (property_key, property_value_default, property_value) values ('css','ajk.css','ajk.css');
commit;
insert into system_properties (property_key, property_value_default, property_value)
values ('app_name','My own website !!!','My own website');
commit;
insert into system_properties (property_key, property_value_default, property_value)
values ('logo_name','logo.jpg','logo.jpg');
commit;
insert into system_properties(property_key, property_value_default, property_value)
values ('categories_height_size', '190','190');
insert into system_properties(property_key, property_value_default, property_value)
values ('galleries_height_size', '190','190');
insert into system_properties(property_key, property_value_default, property_value)
values ('images_height_size', '190','190');
insert into system_properties(property_key, property_value_default, property_value)
values ('images_large_height_size', '800','800');
commit;
