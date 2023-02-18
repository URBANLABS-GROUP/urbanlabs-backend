# 🏢 URBANLABS Backend ⚙️

Сервер системы URBANLABS для смарт-управления недвижимостью.

## Стек

* Java 11+
* Spring Boot 2
* PostgreSQL 13+
* Docker

## Локальный запуск

Сборка и тесты:

```bash
$ ./gradlew clean build test check javadoc
```

```bash
$ docker run \
    --name postgres \
    -e "POSTGRES_PASSWORD=postgres" \
    -p 5432:5432 \
    -d timescale/timescaledb-postgis:2.3.0-pg12

$ docker run \
    --name pgadmin4 \
    -e "PGADMIN_DEFAULT_EMAIL=admin@mail.com" \
    -e "PGADMIN_DEFAULT_PASSWORD=admin" \
    -p 5555:80 \
    -d dpage/pgadmin4
```

```bash
$ ./gradlew bootRun
```

## Запуск в Docker

Сборка docker образа:

```bash
$ ./gradlew bootJar
$ docker build -t urbanlabs-backend .
```

Запуск docker контейнера:

```bash
$ docker run \
    --name urbanlabs-backend \
    -p 8080:8080 \
    -d urbanlabs-backend
```
