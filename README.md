# Spring Boot H2

Spring Boot REST application to demonstrate H2 (file-based mode) using [Liquibase](https://www.liquibase.com/community)

## Installation

Use Maven [mvn](https://maven.apache.org/) to install foobar.

```bash
mvn spring-boot:run
```

## Usage

#### Get all students

```http
  GET /students
```

#### Get student

```http
  GET /students/${id}
```

| Parameter | Type     | Description                       |
|:----------|:---------|:----------------------------------|
| `id`      | `string` | **Required**. Id of item to fetch |

#### Create student

```http
  POST /students
```

##### Request body

`Content-Type: application/json`

| Parameter | Type     | Description                       |
|:----------|:---------|:----------------------------------|
| `name`    | `string` | **Required**. Name of the student |
| `age`     | `number` | **Required**. Age of the student  |

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
