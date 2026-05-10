# My Spring Boot MySQL Application

This is an educational Spring Boot REST API for managing employees,
departments, addresses, profiles, and simple login/logout state. The app uses
Spring Web, Spring Data JPA, Bean Validation, and MySQL for local development.

## Tech Stack

- Java 11
- Spring Boot 2.7.x
- Spring Web
- Spring Data JPA
- MySQL Connector/J
- H2 for automated tests
- Maven Wrapper

## Project Structure

```text
.
|-- pom.xml
|-- mvnw / mvnw.cmd
|-- src/
|   |-- main/
|   |   |-- java/com/hemant/db/
|   |   |   |-- SpringbootMysqlApplication.java
|   |   |   |-- exception/
|   |   |   |-- model/
|   |   |   |-- repository/
|   |   |   |-- resource/
|   |   |   `-- service/
|   |   `-- resources/application.yml
|   `-- test/
|       |-- java/com/hemant/db/
|       `-- resources/application-test.yml
`-- .github/workflows/ci.yml
```

### Main Packages

`model` contains the JPA entities that map to database tables. Examples:
`Employee`, `Department`, `Address`, and `Profile`.

`repository` contains Spring Data JPA repositories. These interfaces provide
database access methods such as `findByEmail`, `findByCity`, and `findByName`.

`service` contains business logic. Controllers call services instead of talking
directly to repositories, which keeps request handling and application rules
separate.

`resource` contains REST controllers. These preserve the original `/rest/...`
routes while delegating work to services.

`exception` contains shared API error handling. Missing records, duplicate
records, bad requests, and validation failures return consistent JSON error
responses.

## Configuration

The app reads database settings from environment variables with local defaults:

| Variable | Default |
| --- | --- |
| `MYSQL_URL` | `jdbc:mysql://localhost:3306/employee_db` |
| `MYSQL_USERNAME` | `root` |
| `MYSQL_PASSWORD` | empty |
| `JPA_DDL_AUTO` | `update` |
| `JPA_SHOW_SQL` | `true` |

Example PowerShell setup:

```powershell
$env:MYSQL_URL="jdbc:mysql://localhost:3306/employee_db"
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="your-password"
```

## Run Locally

Start MySQL and create the database:

```sql
CREATE DATABASE employee_db;
```

Then run the application:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

The API starts on the default Spring Boot port:

```text
http://localhost:8080
```

## API Overview

The existing route style is preserved.

### Employees

- `GET /rest/Employee/all`
- `POST /rest/Employee/insert`
- `GET /rest/Employee/findbyname?name=...`
- `GET /rest/Employee/findbydesignation?designation=...`
- `POST /rest/Employee/updatedesignation?Id=...&designation=...`
- `POST /rest/Employee/updatemobile?Id=...&mobile=...`
- `POST /rest/Employee/updatepassword?Id=...&password=...`
- `DELETE /rest/Employee/delete?Id=...`

### Departments

- `GET /rest/Department/all`
- `POST /rest/Department/insert`
- `GET /rest/Department/findbyname?name=...`
- `GET /rest/Department/findbyaddress?address=...`
- `POST /rest/Department/updatefloor?Id=...&floor=...`
- `POST /rest/Department/updateaddress?Id=...&address=...`
- `DELETE /rest/Department/delete?Id=...`

### Addresses

- `GET /rest/Address/all`
- `POST /rest/Address/insert`
- `GET /rest/Address/findbycity?city=...`
- `GET /rest/Address/findbystate?state=...`
- `POST /rest/Address/updateaddress?...`
- `DELETE /rest/Address/delete?Id=...`

### Profiles

- `GET /rest/Profile/all`
- `POST /rest/Profile/insert`
- `GET /rest/Profile/findbygender?gender=...`
- `GET /rest/Profile/findbyhobbies?hobbies=...`
- `POST /rest/Profile/updatehobbies?Id=...&hobbies=...`
- `DELETE /rest/Profile/delete?Id=...`

### Login

- `GET /rest/Login/findbyemail?email=...`
- `POST /rest/Login/login?email=...&password=...`
- `POST /rest/Login/logout?email=...`

## Testing

Tests use H2 in MySQL compatibility mode, so they do not require a local MySQL
server.

```powershell
.\mvnw.cmd test
```

On macOS/Linux:

```bash
./mvnw test
```

The GitHub Actions workflow also runs the Maven test suite on Java 11 for pushes
to `main` and pull requests.

## Notes

- Passwords are accepted for create/update/login operations but are hidden from
  JSON responses.
- This project intentionally keeps the original route naming style to avoid
  breaking existing clients.
- Authentication is educational only. It stores a login status and plain
  password values, so it should not be used as-is for production security.
