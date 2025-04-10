# API 명세서

## 목차
0. [공통](#0-공통)
    - [공통 응답](#공통-응답)
    - [공통 에러](#공통-에러)
1. [API 목록](#1-api-목록)
    - [카테고리별 최저가격 조회 API](#1-1-카테고리별-최저가격-조회-api)
    - [단일 브랜드 전체 최저 가격 조회 API](#1-2-단일-브랜드-전체-최저-가격-조회-api)
    - [카테고리 이름별 최저/최고 가격 조회 API](#1-3-카테고리-이름별-최저-최고-가격-조회-api)
    - [브랜드 및 상품을 추가/업데이트/삭제 API](#1-4-브랜드-및-상품을-추가업데이트삭제-api)
        - [브랜드 추가 API](#브랜드-추가-api)
        - [브랜드 목록 조회 API](#브랜드-목록-조회-api)
        - [브랜드 상세 조회 API](#브랜드-상세-조회-api)
        - [브랜드 업데이트 API](#브랜드-업데이트-api)
        - [브랜드 삭제 API](#브랜드-삭제-api)
        - [카테고리 목록 조회 API](#카테고리-목록-조회-api)
        - [상품 추가 API](#상품-추가-api)
        - [상품 목록 조회 API](#상품-목록-조회-api)
        - [상품 상세 조회 API](#상품-상세-조회-api)
        - [상품 업데이트 API](#상품-업데이트-api)
        - [상품 삭제 API](#상품-삭제-api)
<br/>
<br/>

## 0. 공통
### 공통 응답
- Response Body
    - status (Integer, 필수): HTTP 상태 코드.
    - data (Object, 선택): 응답 데이터.
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "key": "value"
  }
}
```
<br/>

### 공통 에러
- Response Body
    - code (String, 필수): 에러 코드.
    - message (String, 필수): 에러 메시지.
```json
{
  "code": "ERROR_CODE",
  "message": "에러 메시지"
}
```

- 공통 에러 (http status code: 500 Internal Server Error)
```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "서버 에러가 발생했습니다."
}
```
<br/>

## 1. API 목록
### 1-1. 카테고리별 최저가격 조회 API
**설명**: 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/categories/lowest-price`
- **Http Method**: `GET`

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
- 성공 응답 (http status code: 200)

```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "categories": [
      {
        "categoryCode": "001",
        "categoryName": "상의",
        "brand": {
          "brandId": 3,
          "brandName": "C"
        },
        "goods": {
          "goodsId": 1,
          "goodsName": "상의1",
          "price": 10000
        }
      },
      {
        "categoryCode": "002",
        "categoryName": "아우터",
        "brand": {
          "brandId": 5,
          "brandName": "E"
        },
        "goods": {
          "goodsId": 2,
          "goodsName": "아우터1",
          "price": 5000
        }
      }, //...생략
    ]
    "totalPrice": 34100
  }
}
```
<br/>

### 1-2. 단일 브랜드 전체 최저 가격 조회 API
**설명**: 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/brands/lowest-price`
- **Http Method**: `GET`

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
- 성공 응답 (http status code: 200)

```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "brand": {
      "brandId": 4,
      "brandName": "D"
    },
    "categories": [
      {
        "categoryCode": "001",
        "categoryName": "상의",
        "goods": {
          "goodsId": 1,
          "goodsName": "상의1",
          "price": 10100
        }
      },
      {
        "categoryCode": "002",
        "categoryName": "아우터",
        "goods": {
          "goodsId": 2,
          "goodsName": "아우터2",
          "price": 5100
        }
      }, //...생략
    ],
    "totalPrice": 36100
  }
}
```
<br/>

### 1-3. 카테고리 이름별 최저/최고 가격 조회 API
**설명**: 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/categories/extreme-prices`
- **Http Method**: `GET`

**Request(요청 데이터)**
- **Query Parameter**
    - `categoryName` (String, 필수): 카테고리 명

**Response(응답 데이터)**
- 성공 응답 (http status code: 200)
```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "category": {
      "categoryCode": "001",
      "categoryName": "상의"
    },
    "lowestPriceDetails": [
      {
        "brand": {
          "brandId": 3,
          "brandName": "C"
        },
        "goods": {
          "goodsId": 1,
          "goodsName": "상의1",
          "price": 10000
        }
      }
    ],
    "highestPriceDetails": [
      {
        "brand": {
          "brandId": 9,
          "brandName": "I"
        },
        "goods": {
          "goodsId": 2,
          "goodsName": "상의2",
          "price": 11400
        }
      }
    ]
  }
}
```
- 실패 - 유효하지 않은 입력값(http status: 400 Bad Request)
```json
{
  "code": "INVALID_REQUEST",
  "message": "유효하지 않은 값입니다."
}
```
- 실패 - categoryName에 해당하는 category가 존재하지 않음(http status: 404 Not Found)
```json
{
  "code": "CATEGORY_NOT_FOUND",
  "message": "카테고리를 찾을 수 없습니다."
}
```
<br/>

### 1-4. 브랜드 및 상품을 추가/업데이트/삭제 API
#### 브랜드 추가 API
**설명**: 브랜드를 추가한다.

**API 기본 정보**
- **URI**: `/api/v1/brands`
- **Http Method**: `POST`

**Request(요청 데이터)**
- **Request Body**
```json
{
  "brandName": "브랜드명"
}
```

**Response(응답 데이터)**
**Response**
- 성공 응답(http status: 201 Created)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "brandId": 1
  }
}
```
- 실패 - 브랜드 이름이 이미 존재(http status: 400 Bad Request)
```json
{
  "code": "BRAND_NAME_ALREADY_EXISTS",
  "message": "브랜드 이름이 이미 존재합니다."
}
```
<br/>

#### 브랜드 목록 조회 API
**설명**: 모든 브랜드 목록을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/brands`
- **Http Method**: `GET`

**Request(요청 데이터)**
- **Query Parameters**
    - `pageNo` (Integer, 선택): 페이지 번호 (기본값: 0)
    - `pageSize` (Integer, 선택): 페이지 크기 (기본값: 10)

**Response(응답 데이터)**
- 성공 응답 (http status code: 200)
```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "pageNo": 1,
    "pageSize": 20,
    "totalElements": 60,
    "totalPages": 3,
    "brands": [
      {
        "brandId": "1",
        "brandName": "A",
        "createdAt": "2025-04-05T12:00:00Z"
      },
      {
        "brandId": "2",
        "brandName": "B",
        "createdAt": "2025-04-05T12:00:00Z"
      }, //..생략
    ]
  }
}
```
<br/>

#### 브랜드 상세 조회 API
**설명**: 특정 브랜드의 상세 정보를 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/brands/{brandId}`
- **Http Method**: `GET`
- **Path Variable**
    - `brandId` (Integer, 필수): 브랜드 ID

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
- 성공 응답 (HTTP 상태 코드: 200)
```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "brandId": 1,
    "brandName": "브랜드명",
    "createdAt": "2025-04-05T12:00:00Z"
  }
}
```
- 실패 - brandId 해당하는 brand가 존재하지 않음(http status: 404 Not Found)
```json
{
  "code": "BRAND_NOT_FOUND",
  "message": "브랜드를 찾을 수 없습니다."
}
```
<br/>

#### 브랜드 업데이트 API
**설명**: 브랜드 정보를 수정한다.

**API 기본 정보**
- **URI**: `/api/v1/brands/{brandId}`
- **Http Method**: `PUT`
- **Path Variable**
    - `brandId` (Integer, 필수): 브랜드 ID

**Request(요청 데이터)**
- **Request Body**
```json
{
  "brandName": "브랜드명"
}
```

**Response(응답 데이터)**
**Response**
- 성공 응답(http status: 200)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "brandId": 1,
    "brandName": "브랜드명",
    "createdAt": "2025-04-05T12:00:00Z"
  }
}
```
- 실패 - 브랜드 이름이 이미 존재(http status: 400 Bad Request)
```json
{
  "code": "BRAND_NAME_ALREADY_EXISTS",
  "message": "브랜드 이름이 이미 존재합니다."
}
```
<br/>

#### 브랜드 삭제 API
**설명**: 브랜드 정보를 삭제한다.

**API 기본 정보**
- **URI**: `/api/v1/brands/{brandId}`
- **Http Method**: `DELETE`
- **Path Variable**
    - `brandId` (Integer, 필수): 브랜드 ID

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
**Response**
- 성공 응답(http status: 200)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {

  }
}
```
<br/>

#### 카테고리 목록 조회 API
**설명**: 모든 카테고리 목록을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/categories`
- **Http Method**: `GET`

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
- 성공 응답 (HTTP 상태 코드: 200)
```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": [
    {
      "categoryCode": "001",
      "categoryName": "상의"
    },
    {
      "categoryCode": "002",
      "categoryName": "아우터"
    }
    // ... 생략
  ]
}
```
<br/>

#### 상품 추가 API
**설명**: 상품을 추가한다.

**API 기본 정보**
- **URI**: `/api/v1/goods`
- **Http Method**: `POST`

**Request(요청 데이터)**
- **Request Body**
```json
{
  "brandId": "1",
  "categoryId": "001",
  "goodsName": "상품",
  "price": 10000
}
```

**Response(응답 데이터)**
- 성공 응답(http status: 201 Created)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "상품이 성공적으로 추가되었습니다."
  },
  "data": {
    "goodsId": 1
  }
}
```
- 실패 - 유효하지 않은 입력값(http status: 400 Bad Request)
```json
{
  "code": "INVALID_REQUEST",
  "message": "유효하지 않은 값입니다."
}
```
- 실패 - 상품 이름이 이미 존재(http status: 400 Bad Request)
```json
{
  "code": "GOODS_NAME_ALREADY_EXISTS",
  "message": "상품 이름이 이미 존재합니다."
}
```
- 실패 - 브랜드를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "BRAND_NOT_FOUND",
  "message": "브랜드를 찾을 수 없습니다."
}
```
- 실패 - 카테고리를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "CATEGORY_NOT_FOUND",
  "message": "카테고리를 찾을 수 없습니다."
}
```
<br/>

#### 상품 목록 조회 API
**설명**: 상품 목록을 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/goods`
- **Http Method**: `GET`

**Request(요청 데이터)**
- **Query Parameters**
    - `pageNo` (Integer, 선택): 페이지 번호 (기본값: 0)
    - `pageSize` (Integer, 선택): 페이지 크기 (기본값: 10)
    - `brandId` (Integer, 선택): 브랜드 ID 
    - `categoryId` (String, 선택): 카테고리 ID

**Response(응답 데이터)**
- 성공 응답 (HTTP 상태 코드: 200)
```jsonc
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "pageNo": 0,
    "pageSize": 10,
    "totalElements": 100,
    "totalPages": 10,
    "goods": [
      {
        "goodsId": 1,
        "goodsName": "상품명",
        "price": 10000,
        "createdAt": "2025-04-05T12:00:00Z"
      }
      // ... 생략
    ]
  }
}
```
<br/>

#### 상품 상세 조회 API
**설명**: 특정 상품의 상세 정보를 조회한다.

**API 기본 정보**
- **URI**: `/api/v1/goods/{goodsId}`
- **Http Method**: `GET`
- **Path Variable**:
    - `goodsId` (Integer, 필수): 상품 ID

**Request(요청 데이터)**
- 없음

**Response(응답 데이터)**
- 성공 응답 (HTTP 상태 코드: 200)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "goodsId": 1,
    "goodsName": "상품명",
    "price": 10000,
    "brand": {
      "brandId": 1,
      "brandName": "A"
    },
    "category": {
      "categoryId": "001",
      "categoryName": "상의"
    },
    "createdAt": "2025-04-15T12:00:00"
  }
}
```
- 실패 - goodsId에 해당하는 goods가 존재하지 않음(http status: 404 Not Found)
```json
{
  "code": "GOODS_NOT_FOUND",
  "message": "상품을 찾을 수 없습니다."
}
```
- 실패 - 브랜드를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "BRAND_NOT_FOUND",
  "message": "브랜드를 찾을 수 없습니다."
}
```
- 실패 - 카테고리를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "CATEGORY_NOT_FOUND",
  "message": "카테고리를 찾을 수 없습니다."
}
```

<br/>

#### 상품 업데이트 API 명세서  
**설명**: 특정 상품의 정보를 수정한다.

**API 기본 정보**  
- **URI**: `/api/v1/goods/{goodsId}`  
- **Http Method**: `PUT`  
- **Path Variable**:  
  - `goodsId` (Integer, 필수): 상품 ID  

**Request(요청 데이터)**  
- **Request Body**  
```json
{
  "goodsName": "수정된 상품명",
  "price": 12000
}
```  
**Response(응답 데이터)**
- 성공 응답 (HTTP 상태 코드: 200)
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "성공적으로 처리하였습니다."
  },
  "data": {
    "goodsId": 1,
    "goodsName": "상품명",
    "price": 10000,
    "brand": {
      "brandId": 1,
      "brandName": "A"
    },
    "category": {
      "categoryId": "001",
      "categoryName": "상의"
    },
    "createdAt": "2025-04-15T12:00:00"
  }
}
```  
- 실패 응답
    - 잘못된 요청 (HTTP 상태 코드: 400 Bad Request)
```json
{
  "code": "INVALID_REQUEST",
  "message": "유효하지 않은 요청입니다."
}
```  
- 실패 - 상품 이름이 이미 존재(http status: 400 Bad Request)
```json
{
  "code": "GOODS_NAME_ALREADY_EXISTS",
  "message": "상품 이름이 이미 존재합니다."
}
```
- 상품 미존재 (HTTP 상태 코드: 404 Not Found)
```json
{
  "code": "GOODS_NOT_FOUND",
  "message": "상품을 찾을 수 없습니다."
}
```
- 실패 - 브랜드를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "BRAND_NOT_FOUND",
  "message": "브랜드를 찾을 수 없습니다."
}
```
- 실패 - 카테고리를 찾을 수 없음(http status: 404 Not Found
```json
{
  "code": "CATEGORY_NOT_FOUND",
  "message": "카테고리를 찾을 수 없습니다."
}
```
<br/>

#### 상품 삭제 API 명세서
**설명**: 특정 상품을 삭제한다.

**API 기본 정보**  
- **URI**: `/api/v1/goods/{goodsId}`  
- **Http Method**: `DELETE`  
- **Path Variable**:  
  - `goodsId` (Integer, 필수): 상품 ID  

**Request(요청 데이터)**  
- 없음  

**Response(응답 데이터)**  
- 성공 응답 (HTTP 상태 코드: 200)  
```json
{
  "meta": {
    "result": "SUCCESS",
    "message": "상품이 성공적으로 삭제되었습니다."
  },
  "data": {}
}
```  

- 실패 응답
    - 상품 미존재 (HTTP 상태 코드: 404 Not Found)
```json
{
  "code": "GOODS_NOT_FOUND",
  "message": "상품을 찾을 수 없습니다."
}
```