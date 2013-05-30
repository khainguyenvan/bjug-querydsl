DROP ALL OBJECTS;


create table "PUBLIC"."ATTRIBUTE" (
	"ATTRIBUTE_ID" INTEGER  not null,
	"NAME" VARCHAR (255) not null,
	"PRODUCT_ID" INTEGER  not null
);

create table "PUBLIC"."BASKET" (
	"BASKET_ID" INTEGER  not null,
	"CUSTOMER_ID" INTEGER  not null
);

create table "PUBLIC"."BASKET_ITEM" (
	"ADDED" TIMESTAMP  not null,
	"BASKET_ID" INTEGER  not null,
	"BASKET_ITEM_ID" INTEGER  not null,
	"INVENTORY_ID" INTEGER  not null,
	"SAVE_FOR_LATER" BOOLEAN  not null
);

create table "PUBLIC"."CUSTOMER" (
	"CUSTOMER_ID" INTEGER  not null,
	"EMAIL" VARCHAR (255) not null,
	"FIRST_NAME" VARCHAR (255) not null,
	"JOINED" TIMESTAMP  not null,
	"LAST_NAME" VARCHAR (255) not null
);

create table "PUBLIC"."INVENTORY" (
	"CREATED" TIMESTAMP  not null,
	"INVENTORY_ID" INTEGER  not null,
	"LABEL" VARCHAR (36) not null,
	"SKU_ID" INTEGER  not null,
	"SOLD" TIMESTAMP 
);

create table "PUBLIC"."PRODUCT" (
	"NAME" VARCHAR (255) not null,
	"PRODUCT_ID" INTEGER  not null
);

create table "PUBLIC"."PRODUCT_VARIANT" (
	"SKU_ID" INTEGER  not null,
	"VALUE_ID" INTEGER  not null
);

create table "PUBLIC"."STOCK_KEEPING_UNIT" (
	"NAME" VARCHAR (255) not null,
	"PRICE" DECIMAL (10, 2) not null,
	"PRODUCT_ID" INTEGER  not null,
	"SKU_ID" INTEGER  not null
);

create table "PUBLIC"."VALUE" (
	"ATTRIBUTE_ID" INTEGER  not null,
	"NAME" VARCHAR (255) not null,
	"VALUE_ID" INTEGER  not null
);

alter table "PUBLIC"."ATTRIBUTE"
    add constraint "PUBLIC"."PK_ATTRIBUTE" primary key ("ATTRIBUTE_ID");

alter table "PUBLIC"."BASKET"
    add constraint "PUBLIC"."PK_BASKET" primary key ("BASKET_ID");

alter table "PUBLIC"."BASKET_ITEM"
    add constraint "PUBLIC"."PK_BASKET_ITEM" primary key ("BASKET_ITEM_ID");

alter table "PUBLIC"."CUSTOMER"
    add constraint "PUBLIC"."PK_CUSTOMER" primary key ("CUSTOMER_ID");

alter table "PUBLIC"."INVENTORY"
    add constraint "PUBLIC"."PK_INVENTORY" primary key ("INVENTORY_ID");

alter table "PUBLIC"."PRODUCT"
    add constraint "PUBLIC"."PK_PRODUCT" primary key ("PRODUCT_ID");

alter table "PUBLIC"."PRODUCT_VARIANT"
    add constraint "PUBLIC"."PK_PRODUCT_VARIANT" primary key ("SKU_ID","VALUE_ID");

alter table "PUBLIC"."STOCK_KEEPING_UNIT"
    add constraint "PUBLIC"."PK_STOCK_KEEPING_UNIT" primary key ("SKU_ID");

alter table "PUBLIC"."VALUE"
    add constraint "PUBLIC"."PK_VALUE" primary key ("VALUE_ID");

alter table "PUBLIC"."CUSTOMER"
    add constraint "PUBLIC"."UK_CUSTOMER1" unique ("EMAIL");

alter table "PUBLIC"."INVENTORY"
    add constraint "PUBLIC"."UK_INVENTORY1" unique ("LABEL");

alter table "PUBLIC"."ATTRIBUTE"
    add constraint "PUBLIC"."FK_ATTRIBUTE_PRODUCT" foreign key ("PRODUCT_ID") references "PUBLIC"."PRODUCT"("PRODUCT_ID");

alter table "PUBLIC"."BASKET"
    add constraint "PUBLIC"."FK_BASKET_CUSTOMER" foreign key ("CUSTOMER_ID") references "PUBLIC"."CUSTOMER"("CUSTOMER_ID");

alter table "PUBLIC"."BASKET_ITEM"
    add constraint "PUBLIC"."FK_BASKET_ITEM_BASKET" foreign key ("BASKET_ID") references "PUBLIC"."BASKET"("BASKET_ID");

alter table "PUBLIC"."BASKET_ITEM"
    add constraint "PUBLIC"."FK_BASKET_ITEM_INVENTORY" foreign key ("INVENTORY_ID") references "PUBLIC"."INVENTORY"("INVENTORY_ID");

alter table "PUBLIC"."INVENTORY"
    add constraint "PUBLIC"."FK_INVENTORY_STOCK_KEEPING_UNIT" foreign key ("SKU_ID") references "PUBLIC"."STOCK_KEEPING_UNIT"("SKU_ID");

alter table "PUBLIC"."PRODUCT_VARIANT"
    add constraint "PUBLIC"."FK_PRODUCT_VARIANT_SKU" foreign key ("SKU_ID") references "PUBLIC"."STOCK_KEEPING_UNIT"("SKU_ID");

alter table "PUBLIC"."PRODUCT_VARIANT"
    add constraint "PUBLIC"."FK_PRODUCT_VARIANT_VALUE" foreign key ("VALUE_ID") references "PUBLIC"."VALUE"("VALUE_ID");

alter table "PUBLIC"."STOCK_KEEPING_UNIT"
    add constraint "PUBLIC"."FK_STOCK_KEEPING_UNIT_PRODUCT" foreign key ("PRODUCT_ID") references "PUBLIC"."PRODUCT"("PRODUCT_ID");

alter table "PUBLIC"."VALUE"
    add constraint "PUBLIC"."FK_VALUE_ATTRIBUTE" foreign key ("ATTRIBUTE_ID") references "PUBLIC"."ATTRIBUTE"("ATTRIBUTE_ID");

