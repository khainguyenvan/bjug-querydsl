drop table if exists `COMMERCE_DB`.`BASKET_ITEM`;

drop table if exists `COMMERCE_DB`.`PRODUCT_VARIANT`;

drop table if exists `COMMERCE_DB`.`INVENTORY`;

drop table if exists `COMMERCE_DB`.`VALUE`;

drop table if exists `COMMERCE_DB`.`STOCK_KEEPING_UNIT`;

drop table if exists `COMMERCE_DB`.`BASKET`;

drop table if exists `COMMERCE_DB`.`ATTRIBUTE`;

drop table if exists `COMMERCE_DB`.`PRODUCT`;

drop table if exists `COMMERCE_DB`.`CUSTOMER`;

create table `COMMERCE_DB`.`ATTRIBUTE` (
	`ATTRIBUTE_ID` INTEGER  not null, 
	`NAME` VARCHAR (255) not null, 
	`PRODUCT_ID` INTEGER  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`BASKET` (
	`BASKET_ID` INTEGER  not null, 
	`CUSTOMER_ID` INTEGER  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`BASKET_ITEM` (
	`ADDED` TIMESTAMP  not null, 
	`BASKET_ID` INTEGER  not null, 
	`BASKET_ITEM_ID` INTEGER  not null, 
	`INVENTORY_ID` INTEGER  not null, 
	`SAVE_FOR_LATER` BOOLEAN  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`CUSTOMER` (
	`CUSTOMER_ID` INTEGER  not null, 
	`EMAIL` VARCHAR (255) not null, 
	`FIRST_NAME` VARCHAR (255) not null, 
	`JOINED` TIMESTAMP  not null, 
	`LAST_NAME` VARCHAR (255) not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`INVENTORY` (
	`CREATED` TIMESTAMP  not null, 
	`INVENTORY_ID` INTEGER  not null, 
	`LABEL` VARCHAR (36) not null, 
	`SKU_ID` INTEGER  not null, 
	`SOLD` TIMESTAMP ) ENGINE=InnoDB;

create table `COMMERCE_DB`.`PRODUCT` (
	`NAME` VARCHAR (255) not null, 
	`PRODUCT_ID` INTEGER  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`PRODUCT_VARIANT` (
	`SKU_ID` INTEGER  not null, 
	`VALUE_ID` INTEGER  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`STOCK_KEEPING_UNIT` (
	`NAME` VARCHAR (255) not null, 
	`PRICE` DECIMAL (10, 2) not null, 
	`PRODUCT_ID` INTEGER  not null, 
	`SKU_ID` INTEGER  not null) ENGINE=InnoDB;

create table `COMMERCE_DB`.`VALUE` (
	`ATTRIBUTE_ID` INTEGER  not null, 
	`NAME` VARCHAR (255) not null, 
	`VALUE_ID` INTEGER  not null) ENGINE=InnoDB;


alter table `COMMERCE_DB`.`ATTRIBUTE`
    add constraint `PK_ATTRIBUTE` primary key (`ATTRIBUTE_ID`);

alter table `COMMERCE_DB`.`BASKET`
    add constraint `PK_BASKET` primary key (`BASKET_ID`);

alter table `COMMERCE_DB`.`BASKET_ITEM`
    add constraint `PK_BASKET_ITEM` primary key (`BASKET_ITEM_ID`);

alter table `COMMERCE_DB`.`CUSTOMER`
    add constraint `PK_CUSTOMER` primary key (`CUSTOMER_ID`);

alter table `COMMERCE_DB`.`INVENTORY`
    add constraint `PK_INVENTORY` primary key (`INVENTORY_ID`);

alter table `COMMERCE_DB`.`PRODUCT`
    add constraint `PK_PRODUCT` primary key (`PRODUCT_ID`);

alter table `COMMERCE_DB`.`PRODUCT_VARIANT`
    add constraint `PK_PRODUCT_VARIANT` primary key (`SKU_ID`,`VALUE_ID`);

alter table `COMMERCE_DB`.`STOCK_KEEPING_UNIT`
    add constraint `PK_STOCK_KEEPING_UNIT` primary key (`SKU_ID`);

alter table `COMMERCE_DB`.`VALUE`
    add constraint `PK_VALUE` primary key (`VALUE_ID`);

alter table `COMMERCE_DB`.`CUSTOMER`
    add constraint `UK_CUSTOMER1` unique(`EMAIL`);

alter table `COMMERCE_DB`.`INVENTORY`
    add constraint `UK_INVENTORY1` unique(`LABEL`);

alter table `COMMERCE_DB`.`ATTRIBUTE`
    add constraint `FK_ATTRIBUTE_PRODUCT` foreign key (`PRODUCT_ID`) references `COMMERCE_DB`.`PRODUCT`(`PRODUCT_ID`);

alter table `COMMERCE_DB`.`BASKET`
    add constraint `FK_BASKET_CUSTOMER` foreign key (`CUSTOMER_ID`) references `COMMERCE_DB`.`CUSTOMER`(`CUSTOMER_ID`);

alter table `COMMERCE_DB`.`BASKET_ITEM`
    add constraint `FK_BASKET_ITEM_BASKET` foreign key (`BASKET_ID`) references `COMMERCE_DB`.`BASKET`(`BASKET_ID`);

alter table `COMMERCE_DB`.`BASKET_ITEM`
    add constraint `FK_BASKET_ITEM_INVENTORY` foreign key (`INVENTORY_ID`) references `COMMERCE_DB`.`INVENTORY`(`INVENTORY_ID`);

alter table `COMMERCE_DB`.`INVENTORY`
    add constraint `FK_INVENTORY_STOCK_KEEPING_UNIT` foreign key (`SKU_ID`) references `COMMERCE_DB`.`STOCK_KEEPING_UNIT`(`SKU_ID`);

alter table `COMMERCE_DB`.`PRODUCT_VARIANT`
    add constraint `FK_PRODUCT_VARIANT_SKU` foreign key (`SKU_ID`) references `COMMERCE_DB`.`STOCK_KEEPING_UNIT`(`SKU_ID`);

alter table `COMMERCE_DB`.`PRODUCT_VARIANT`
    add constraint `FK_PRODUCT_VARIANT_VALUE` foreign key (`VALUE_ID`) references `COMMERCE_DB`.`VALUE`(`VALUE_ID`);

alter table `COMMERCE_DB`.`STOCK_KEEPING_UNIT`
    add constraint `FK_STOCK_KEEPING_UNIT_PRODUCT` foreign key (`PRODUCT_ID`) references `COMMERCE_DB`.`PRODUCT`(`PRODUCT_ID`);

alter table `COMMERCE_DB`.`VALUE`
    add constraint `FK_VALUE_ATTRIBUTE` foreign key (`ATTRIBUTE_ID`) references `COMMERCE_DB`.`ATTRIBUTE`(`ATTRIBUTE_ID`);

