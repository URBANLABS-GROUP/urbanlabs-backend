# urbanlabs-backend

docker run \
--name postgres \
-e "POSTGRES_PASSWORD=postgres" \
-p 5432:5432 \
-d postgres:13

docker run \
--name pgadmin4 \
-e "PGADMIN_DEFAULT_EMAIL=admin@mail.com" \
-e "PGADMIN_DEFAULT_PASSWORD=admin" \
-p 5555:80 \
-d dpage/pgadmin4