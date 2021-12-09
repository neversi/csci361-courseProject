
# CSCI project

Required:
- docker-compose
- mvn (maven)
- make
  
To run:
`make build`

To shutdown:
`make down`

## /signup (POST) 

- Request
```
{
    "username": <string>,
    "name": <string>,
    "surname": <string>,
    "password": <string>
}
```
- Response
```
    Cookies: ["access_token", "refresh_token"]
    {}
```
## /login (POST) !Basic 
- Request
```
    Header: Authorization (Basic)
```
- Response
```
    Cookies: ["access_token", "refresh_token"]
    {}
```


## /rooms (GET)
 Params: 
- reserve = {"free", "busy"}
- hotel = <hotel_id: int>

Request body
```
{
    "cin": "yyyy-mm-dd",
    "cout": "yyyy-mm-dd"
}
```

Response body 
```
 [
     {
         "room_number": <int>,
         "floor": <int>,
         "type": <string>
     }, ...
 ]
```

## /hotels (GET)
Params:
- hotel: <hotel_name> (Optional)

Response body:
```
if params provided:
 {
    "hotel_id": 1,
    "hotel_name": "FIZMAT Aqtau",
    "hotel_address": "KZ-Aqtau-Barsakelmes-21"
 }
else 
[
    {
        "hotel_id": 1,
        "hotel_name": "FIZMAT Aqtau",
        "hotel_address": "KZ-Aqtau-Barsakelmes-21"
    }, ...
]
```

## /employees?hotel=<hotel_id>

Response Body
```
    [
        {
        "hotel_id": 1,
        "employee_id": 1,
        "rolle": 0,
        "name": "A1",
        "email": "bauka@mail.ru",
        "surname": "B1",
        "position": "manager",
        "salary": 400000
    }, ...
    ]
```