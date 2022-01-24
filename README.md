## 5. Создание и обновление структуры базы данных

### Запуск БД в докере

docker pull postgres

docker run -p 5432:5432 -e POSTGRES_PASSWORD=postgresql -e POSTGRES_USER=postgresql -e POSTGRES_DB=mydb -d postgres