# Test Backend Developer Tenpo

## Technologies
- Java 21
- Apache Maven 3.9.7
- Docker (image of Redis and Postgresql)

## Execution project
```bash
git clone https://github.com/afrancom2/...
cd prueba tenpo
docker-compose up --build
```

> [!NOTE]
> Use docker compose, make sure run docker.exe in your PC, in CMD run command:

## Documentation API
- Swagger UI: http://localhost:8080/tenpo/swagger-ui/index.html

### Curls

#### Curl calculate percentage
```bash
curl --location 'http://localhost:8080/tenpo/percentage' \
--header 'Content-Type: application/json' \
--data '{
    "first": 122.0,
    "second": 122.0
}'
```
#### Response curl calculate percentage
```bash
{
    "status": "200 OK",
    "result": 273.28
}
```

#### Curl history endpoints
```bash
curl --location 'http://localhost:8080/tenpo/history'
```
#### Response curl history endpoints
```bash
{
    "content": [
        {
            "id": 1,
            "date": "2025-04-30T19:19:38.2998",
            "endpoint": "/tenpo/percentage",
            "requestParams": "SumRequest(first=122.0, second=122.0)",
            "response": "SumResponse(status=OK, result=273.28)",
            "error": false
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "numberOfElements": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "first": true,
    "empty": false
}
```

## Autor
- Andres Franco 
- Github: [@afrancom2](https://github.com/afrancom2)
- LinkedIn: [@andres-franco](https://www.linkedin.com/in/andres-felipe-franco-monroy-09b400b2)