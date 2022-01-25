# Bank System

## 설명
학교 디비설 수업에 진행한 은행 시스템을 Spring Boot를 이용하여 개발합니다.

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
  
  - ***[POST] /api/user/join***
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

    |QUERY PARAMETER|DEFAULT VALUE|  
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

    |QUERY PARAMETER|DEFAULT VALUE|  
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
                "to": 11,F
                "amount": 20000,
                "balance": 400000,
                "memo": "테스트 비용"
            }
        }
    }
    ```
  - ***[DELETE] /api/account/{accountId}***

    |PATH VARIABLE|DESCRIPTION|  
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
- ### Error Response
  - #### WRONG FORMAT

    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E000|json으로 변환 되지 않는 경우 발생(ex. wrong json format, wrong date format, wrong data type)|
    
    ```
    (1)
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E000",
            "detail": "wrong json format"
        }
    }
    
    (2) /api/user/join
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E000",
            "detail": "birthDate : Cannot deserialize value of type `java.time.LocalDate` from String \"2002-7-6\": Failed to deserialize java.time.LocalDate: (java.time.format.DateTimeParseException) Text '2002-7-6' could not be parsed at index 5"
        }
    }
    ```
  
  - #### INVALID INPUT VALUE

    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E001|잘못된 query parameter 또는 Path variable이 전달된 경우 발생 OR 설정한 Bean Validation이 지켜지지 않는 경우 발생|
  
    ```
    (1) /api/account/4?perPage=5&page=a
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E001",
            "detail": "page : a is not int"
        }
    }
    
    (2) /api/account/deposit
    {
        "status": 400,
        "message": "Bad Request",
        "code": "E001",
        "errors": [
            {
                "field": "amount",
                "value": 0,
                "detail": "must be greater than or equal to 100"
            },
            {
                "field": "account_id",
                "value": null,
                "detail": "must not be null"
            }
        ]
    } 
    ```
  
  - #### EMAIL ALREADY IN USE

    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E002|회원 가입시 사용한 email을 다른 사용자가 사용하고 있는 경우|
  
    ```
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E002",
            "detail": "email already in use"
        }
    }
    ```
    
  - #### LOGIN FAIL
  
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E003|LOGIN FAIL EMAIL, 잘 못된 이메일|
    |E004|LOGIN FAIL PASSWORD, 잘 못된 패스워드|
    |E005|LOGIN FAIL, 로그인 오류|
  
    ```
    (1) LOGIN FAIL EMAIL
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E003",
            "detail": "wrong email"
        }
    }
    
    (2) LOGIN FAIL PASSWORD
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E004",
            "detail": "wrong password"
        }
    }
    
    (3) LOGIN FAIL
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E005",
            "detail": "something's wrong! check your email or password!"
        }
    }
    ```
    
  - #### UNAUTHORIZED ACCESS
  
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E006|로그인을 하지 않은 상태에서 로그인이 필요한 기능 사용시 OR 타인의 계좌에 접근할 경우|
  
    ```
    (1)
    {
        "status": 401,
        "message": "Unauthorized",
        "error": {
            "code": "E006",
            "detail": "unauthorized access"
        }
    }
    
    (2)
    {
        "status": 401,
        "message": "Unauthorized",
        "error": {
            "code": "E006",
            "detail": "not your account"
        }
    }
    ```

  - #### NO USER
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E007|사용자 조회시 존재하지 않을 경우|
  
    ```
    {
        "status": 404,
        "message": "Not Found",
        "error": {
            "code": "E007",
            "detail": "non-existent user"
        }
    }
    ```

  - #### NO ACCOUNT
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E008|입금, 인출, 송금시 잘 못된 계좌번호를 이용할 경우|

    ```
    {
        "status": 404,
        "message": "Not Found",
        "error": {
            "code": "E008",
            "detail": "wrong account number"
        }
    }
    ``` 
    
  - #### NOT ENOUGH MONEY
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E009|송금, 인출시 잔액이 부족할 경우|
  
    ```
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E009",
            "detail": "lack of balance"
        }
    }
    ```
    
  - #### ACCOUNT REMAIN
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E010|회원 탈퇴시 계좌가 남아 있는 경우|
  
    ```
    {
        "status": 400,
        "message": "Bad Request",
        "error": {
            "code": "E010",
            "detail": "remove all accounts"
        }
    }
    ```
  
  - #### NO HANDLER FOUND EXCEPTION
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E011|존재 하지 않는 api를 사용할 경우|

    ```
    {
        "status": 404,
        "message": "Not Found",
        "error": {
            "code": "E011",
            "detail": "api does not exist"
        }
    }
    ```
  - ### INTERNAL SERVER ERROR
    |ERROR CODE|DESCRIPTION|  
    |:---:|:---:|   
    |E012|서버 내부 에러|
  
    ```
    {
        "status": 500,
        "message": "Internal Server Error",
        "error": {
            "code": "E012",
            "detail": "internal server error"
        }
    }
    ```
  