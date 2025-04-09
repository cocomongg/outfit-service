-- 카테고리 테이블
create table categories
(
    category_id   bigint auto_increment
        primary key,
    category_code varchar(10) not null,
    category_name varchar(50) not null,
    created_at    datetime    not null,
    updated_at    datetime    null,
    constraint category_category_code_uindex
        unique (category_code),
    constraint category_name_uindex
        unique (category_name)
);

-- 브랜드 테이블
create table brands
(
    brand_id   bigint auto_increment
        primary key,
    brand_name varchar(100) not null,
    is_deleted boolean      not null,
    created_at datetime     not null,
    updated_at datetime     null,
    constraint brand_name_uindex
        unique (brand_name)
);

-- 상품 테이블
create table goods
(
    goods_id    bigint auto_increment
        primary key,
    goods_name  varchar(200)   not null,
    price       decimal(10, 2) not null,
    brand_id    bigint         not null,
    category_id bigint         not null,
    created_at  datetime       not null,
    updated_at  datetime       null
);