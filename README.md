# Bank System

## 프로젝트 기간
- 1차: 11월 16일 ~ (미정)    

## 기술 스택
- spring boot
- spring data jpa

## 설명
학교 디비설 수업에 진행한 은행 시스템을 spring boot을 이용하여 개발합니다.

## 기능
- 사용자: 가입, 탈퇴 , 사용자 정보 조회, 사용자 정보 수정
- 계좌: 계좌 생성, 계좌 삭제, 입급, 출금, 송금, 계좌 조회
- 입금, 출금, 송금에 따른 계좌 기록 생성
- (카드 관련 기능은 프로젝트가 너무 늘어져 구현하지 않음)

## API
- ### AUTH
  - ***[POST] /api/auth/login*** 
    - REQUEST
    
    ```
    {
      "email": "test1@test.com",
      "password": "980606"
    }
    ```
  
    - RESPONSE  
     
    ```
    {
      "status": 200,
      "message": "login success"
    }
    ```
    
  - ***[POST] /api/auth/logout***
    - RESPONSE
    ```
    {
      "status": 200,
      "message": "logout success"
    }
    ```

- ### USER
  - ***[GET] /api/user***
    - RESPONSE
    ```
    {
      "status": 200,
      "message": "get success",
      "result": {
          "user": {
              "name": "tester1",
              "birthDate": "2002-12-07",
              "address": "경기도 용인시 수지구 풍덕천동 ___",
              "email": "test1@test.com",
              "phoneNumber": "01087654321"
          }
      }
    }
    ```
  
  - ***[POST] /api/user***
    - REQUEST
    ```
    {
      "name": "tester4",
      "birthDate": "2001-04-06",
      "address": "경기도 용인시 기흥구 ~~",
      "email": "test4@test.com",
      "password": "980406",
      "phoneNumber": "01044442222"
    }
    ```
    
    - RESPONSE
    ```
    {
      "status": 201,
      "message": "join success",
      "result": {
          "user": {
              "id": 160,
              "name": "tester4",
              "birthDate": "2001-04-06",
              "address": "경기도 용인시 기흥구 ~~",
              "email": "test4@test.com",
              "phoneNumber": "01044442222"
          }
      }
    }  
    ```
    
  - ***[PUT] /api/user***
    - REQUEST
    ```
    {
      "name":"tester1",
      "birthDate": "2002-12-07",
      "address": "경기도 용인시 수지구 풍덕천동 +++",
      "phoneNumber" :"01087654321",
      "password": "980606"
    }
    ```
    
    - RESPONSE
    ```
    {
      "status": 200,
      "message": "update success",
      "result": {
          "user": {
              "id": 1,
              "name": "tester1",
              "birthDate": "2002-12-07",
              "address": "경기도 용인시 수지구 풍덕천동 +++",
              "phoneNumber": "01087654321"
          }
      }
    }
    ```
  - ***[DELETE] /api/user***
    - RESPONSE
    ```
    {
      "status": 200,
      "message": "user deleted",
      "result": {
          "user": {
              "id": 160,
              "name": "tester4"
          }
      }
    }
    ```