## 실행 방법

깃허브 클론 후 `application.properties` 파일에서 데이터베이스 연결 정보를 수정헤주세요.

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

## API 문서

### 회원 가입 (POST /api/user/join)

사용자를 등록하는 API입니다.

#### 요청

- **Method**: POST
- **Path**: `/api/user/join`
- **Request Body**:
  ```json
  {
    "user_id": "string",
    "password": "string",
    "nickname": "string",
    "name": "string",
    "phone_number": "string",
    "email": "string"
  }
  ```
- **응답**
  - **Status**: 201 Created
  - **Response Body**:
    ```json
    {
      "createdAt": "date",
      "user_id": "string",
      "nickname": "string",
      "name": "string",
      "phone_number": "string",
      "email": "string"
    }
    ```

### 회원 목록 조회 (GET /api/user/list)

사용자 정보를 조회하는 API입니다.

#### 요청

- **Method**: GET
- **Path**: `/api/user/list`
- **Query Parameter**:
  - `page`: 페이지 번호
  - `pageSize`: 한 페이지에 표시될 수 있는 최대 회원 수
  - `sortKey`: 정렬 기준, 가입일순(`createdAt`)/이름순(`name`)
  - `sortOrder`: 정렬 방식, 오름차순(`asc`)/내림차순(`desc`)
- **응답**
  - **Status**: 200 OK
  - **Response Body**:
    ```json
    {
      "users": [
        {
          "createdAt": "date",
          "user_id": "string",
          "nickname": "string",
          "name": "string",
          "phone_number": "string",
          "email": "string"
        }
      ],
      "paginationInfo": {
        "page": "int",
        "size": "int",
        "total_pages": "int"
      }
    }
    ```

### 회원 수정 (PUT /api/user/{userId})

회원 정보를 수정하는 API입니다. 비밀번호가 일치해야 회원 정보가 수정됩니다.

#### 요청

- **Method**: PUT
- **Path**: `/api/user/{userId}`
- **Path Parameter**:
  - `userId`: 수정할 사용자의 ID
- **Request Body**:
  ```json
  {
    "password": "string",
    "nickname": "string",
    "name": "string",
    "phoneNumber": "string",
    "email": "string"
  }
  ```
- **응답**
  - **Status**: 200 OK
  - **Response Body**:
    ```json
    {
      "updatedAt": "date",
      "userId": "string",
      "nickname": "string",
      "name": "string",
      "phoneNumber": "string",
      "email": "string"
    }
    ```

## 회원 엔티티 제약 조건

| 필드        | 조건                                              |
|-------------|-------------------------------------------------|
| userId      | - 최소 4자 이상, 최대 20자 이하<br>- 알파벳(대소문자), 숫자만 허용    |
| password    | - 최소 8자 이상<br>- 알파벳(대소문자), 숫자, 특수문자 모두 하나 이상 포함 |
| nickname    | - 최대 20자 이하<br>- 알파벳(대소문자), 숫자, 한글만 허용          |
| name        | - 최대 10자 이하<br>- 알파벳(대소문자), 한글만 허용         |
| phoneNumber | - 유효한 전화번호 형식이어야 함 (e.g., 010-1234-5678)        |
| email       | - 유효한 이메일 형식이어야 함                               |
