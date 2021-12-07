
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
## /login (POST)
- Request
```
    Header: Authorization (Basic)
```
- Response
```
    Cookies: ["access_token", "refresh_token"]
    {}
```

## /working schedule (GET) 
```
    {
        "schedule": [<Object>]
    }
```

## /reservations (GET)
```
    {
        "schedules": [<Object>]
    }
```

## /reservations (UPDATE)
```
    {

    }
```