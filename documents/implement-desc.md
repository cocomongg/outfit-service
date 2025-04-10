## 구현범위에 대한 설명

### 1. 패키지 구조 설계
- domain layer에서 각 도메인에 대한 역할과 책임을 명확히 구분했습니다.
- application layer(UseCase)를 추가하여 도메인의 협력이 필요할 경우, 해당 layer에서 필요한 도메인을 주입받아 사용하도록 구현했습니다.
- infrastructure layer에서 각 도메인에 대한 repository를 구현했습니다. DI를 통해 domain에서 해당 repository를 주입받아 사용하도록 했습니다.
- 이렇게 구조를 설계하여 비지니스 로직이 외부에 영향을 받지 않도록 구현했습니다.
<br/><br/>

### 2. 브랜드 관리, 상품 관리
#### 구현 기능
- 브랜드 등록, 목록/상세 조회, 수정, 삭제 기능을 구현했습니다.
- 상품 등록, 목록/상세 조회, 수정, 삭제 기능을 구현했습니다.
<br/><br/>

### 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회 기능
```sql
SELECT
    g.goods_id,
    g.goods_name,
    g.price,
    b.brand_id,
    b.brand_name,
    c.category_id,
    c.category_name
FROM goods AS g
INNER JOIN brand AS b
    ON g.brand_id = b.brand_id
INNER JOIN category AS c
    ON g.category_id = c.category_id
WHERE
    g.category_id = ?           -- 바인딩 파라미터: categoryId
    AND g.deleted = FALSE       -- deleted 플래그가 false인 것만
    AND g.price = (
        SELECT MIN(g2.price)    -- 최고가인 경우는 MAX로 변경
        FROM goods AS g2
        WHERE
            g2.category_id = ?  -- 동일한 categoryId
            AND g2.deleted = FALSE
    )
```
- 카테고리 이름으로 먼저 카테고리 ID를 조회한 후, 해당 카테고리 ID로 상품을 조회합니다.
- 상품 가격이 최저가인 상품을 조회하기 위해 서브쿼리를 사용했습니다.
- `isLowest`라는 boolean 타입의 플래그를 사용하여, 최저가인 경우와 최고가인 경우를 구분하여 총 2번의 쿼리로 조회하도록 구현했습니다.
  - 하나의 api에 두번의 쿼리가 발생한다는 단점이 있지만, 쿼리 복잡도를 낮춰서 유지보수를 쉽게 하기 위해서 2번의 쿼리로 나누어 구현했습니다.
- 쿼리의 성능을 개선하기 위해, (category_id, is_deleted, price) 복합 인덱스를 추가했습니다.

### 4. 추후 구현 사항
#### 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
#### 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API

- 위 두개에 대한 API는 시간관계상 Mock API만 구현되어 있습니다.
  - 구현3)을 구현할 때, 현재 존재하는 테이블로 구현할 시 쿼리의 복잡도가 증가하고, 유지보수가 어려워진다고 느꼈습니다.
  - 추후에 구현할때는, 최저/최고 가격에 대한 집계 테이블을 별도로 두어, 해당 테이블을 기준으로 조회하도록 구현할 예정입니다.
  - 물론 실시간성은 조금 떨어지겠지만, 쿼리의 복잡도로 인한 복잡도와 쿼리 성능을 고려했을 때, 
    해당 테이블을 두는 것이 더 나은 선택이라고 판단했습니다.
<br/>

- 관리 API를 제외하고, 구현 1, 2, 3번에 대해서는 추후에 redis, 인메모리 캐시를 도입하여 성능을 개선할 예정입니다.