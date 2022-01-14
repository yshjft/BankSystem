# Bank System

## 프로젝트 기간
- 1차: 11월 16일 ~ (미정)    

## 기술 스택
- spring boot
- spring data jpa

## 설명
학교 디비설 수업에 진행한 은행 시스템을 spring boot을 이용하여 개발합니다.

## 기능
- 세션 로그인
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

- ### ACCOUNT
  - ***[GET] /api/account?page=0&perPage=5***

    |query parameter|default value|  
    |:---:|:---:|   
    |page|0|
    |perPage|5|

    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "metadata": {
                "page": 0,
                "page_count": 2,
                "per_page": 5,
                "total_count": 10
            },
            "accounts": [
                {
                    "id": 4,
                    "balance": 400000
                },
                {
                    "id": 5,
                    "balance": 10000
                },
                {
                    "id": 6,
                    "balance": 10000000
                },
                {
                    "id": 7,
                    "balance": 100000000
                },
                {
                    "id": 8,
                    "balance": 2100
                }
            ]
        }
    }
    ```

  - ***[GET] /api/account/4?page=0&perPage=5***

    |query parameter|default value|  
    |:---:|:---:|   
    |page|0|
    |perPage|5|

    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "metadata": {
                "page": 0,
                "page_count": 2,
                "per_page": 5,
                "total_count": 7
            },
            "account_logs": [
                {
                    "type": "OUT",
                    "amount": 20000,
                    "info": "tester2(테스트 비용)",
                    "createdAt": "2022-01-13T14:06:32.277349",
                    "balance": 400000
                },
                {
                    "type": "OUT",
                    "amount": 20000,
                    "info": "tester2(테스트 비용)",
                    "createdAt": "2022-01-13T14:06:30.932115",
                    "balance": 420000
                },
                {
                    "type": "OUT",
                    "amount": 20000,
                    "info": "tester2(테스트 비용)",
                    "createdAt": "2022-01-13T14:06:29.575433",
                    "balance": 440000
                },
                {
                    "type": "OUT",
                    "amount": 20000,
                    "info": "tester2(테스트 비용)",
                    "createdAt": "2022-01-13T14:06:22.188593",
                    "balance": 460000
                },
                {
                    "type": "OUT",
                    "amount": 20000,
                    "info": "돈 출금",
                    "createdAt": "2022-01-13T14:03:52.064531",
                    "balance": 480000
                }
            ],
            "account": {
                "account_id": 4,
                "owner": "tester1",
                "balance": 400000,
                "createdAt": "2022-01-05T17:42:47.325524"
            }
        }
    }
    ```   
  - ***[GET] /api/account/checkAccount***
    - REQUEST
    ```
    {
        "account_id": 11
    }
    ```
    
    - RESPONSE
    ```
     {
        "status": 200,
        "message": "OK",
        "result": {
            "account": {
                "account_id": 11,
                "owner": "tester2"
            }
        }
    }
    ```
  - ***[POST] /api/account/create***
     - REQUEST
    ```
    {
        "balance": 1000
    }
    ```
    
    - RESPONSE
    ```
    {
        "status": 201,
        "message": "account created",
        "result": {
            "account": {
                "accountId": 172,
                "balance": 1000,
                "ownerId": 1,
                "ownerName": "tester1"
            }
        }
    }
    ```
    
  - ***[PUT] /api/account/deposit*** 
    - REQUEST
    ```
    {
        "account_id": 4,
        "amount" : 200000,
        "info": "deposit test money"
    }
    ```
    
    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "deposit": {
                "account_id": 4,
                "amount": 200000,
                "balance": 500000,
                "type": "IN"
            }
        }
    }
    ```
    
  - ***[PUT] /api/account/withdraw***
    - REQUEST
    ```
    {
        "account_id":4,
        "amount" : 20000,
        "info": "돈 출금"
    }
    ```      

    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "withdraw": {
                "account_id": 4,
                "amount": 20000,
                "balance": 480000,
                "type": "OUT"
            }
        }
    }
    ```
    
  - ***[PUT] /api/account/sendMoney***
    - REQUEST
    ```
    {
        "from_id": 4,
        "to_id": 11,
        "amount": 20000,
        "memo": "테스트 비용"
    }
    ```

    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "send_money": {
                "from": 4,
                "to": 11,
                "amount": 20000,
                "balance": 400000,
                "memo": "테스트 비용"
            }
        }
    }
    ```
  - ***[DELETE] /api/account/{accountId}***

    |path variable|explanation|  
    |:---:|:---:|   
    |accountId|삭제를 원하는 account id 입력|

    - RESPONSE
    ```
    {
        "status": 200,
        "message": "OK",
        "result": {
            "account": {
                "account_id": 8,
                "balance": 2100,
                "deletedAt": "2022-01-13T14:41:29.557953"
            }
        }
    }
    ```   
- ### Errors
  