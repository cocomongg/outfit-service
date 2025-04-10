# 프로젝트 실행 방법

## 1. 저장소 클론

아래 명령어를 사용하여 Git 저장소를 클론합니다.

```bash
git clone https://github.com/cocomongg/outfit-service.git
```

## 2. Spring Boot 애플리케이션 실행

### 2.1 Gradle을 통한 애플리케이션 실행

프로젝트 루트 디렉토리로 이동한 후, 아래 명령어를 사용하여 Spring Boot 애플리케이션을 실행합니다.

```bash
./gradlew bootRun
```

## 3. 테스트 코드 실행

### 3.1 테스트 실행
 
아래 명령어로 테스트 코드를 실행합니다.

```bash
./gradlew test
```

## 4. API 문서 확인

API 문서는 Swagger를 통해 확인할 수 있습니다.
- 주소: http://localhost:8080/swagger-ui.html
