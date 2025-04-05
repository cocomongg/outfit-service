-- 카테고리 테이블
create table category
(
    category_id   bigint auto_increment comment '카테고리 pk'
        primary key,
    category_code varchar(10) not null comment '카테고리 식별 코드 (ex. 001)',
    category_name varchar(50) not null comment '카테고리 명',
    created_at    datetime    not null comment '생성일시',
    updated_at    datetime    null comment '수정일시',
    constraint category_category_code_uindex
        unique (category_code),
    constraint category_name_uindex
        unique (category_name)
)
    comment '상품 카테고리';

-- 브랜드 테이블
create table brand
(
    brand_id   bigint auto_increment comment '브랜드 pk'
        primary key,
    brand_name varchar(100) not null comment '브랜드 명',
    created_at datetime     not null comment '생성일시',
    updated_at datetime     null comment '수정일시',
    constraint brand_name_uindex
        unique (brand_name)
)
    comment '브랜드';

-- 상품 테이블
create table goods
(
    goods_id    bigint auto_increment comment '상품 pk'
        primary key,
    goods_name  varchar(200)   not null comment '상품 명',
    price       decimal(10, 2) not null comment '상품 가격',
    brand_id    bigint         not null comment '브랜드 pk',
    category_id bigint         not null comment '카테고리 pk',
    created_at  datetime       not null comment '생성일시',
    updated_at  datetime       null comment '수정일시'
)
    comment '상품';