FROM postgres:latest
ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD root123
ENV POSTGRES_DB rest-api-db
COPY init.sql /docker-entrypoint-initdb.d/

# docker build -t rest-api-service-db ./
# docker run -d --name rest-api-service-container -p 5433:5432 rest-api-service-db