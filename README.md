# currency
Сервис для получения гиф-изображения в зависимости от изменения курса валют.

# endpoints

## Для получения всех кодов курсов валют:
 currency/find-all-currencies-codes
 
## Для получения гиф-изображения:
 currency/show-gif-by-currency-difference/RUB
 
# запуск jar:
java -jar .\build\libs\currency-0.0.1-SNAPSHOT.jar

# Docker:
### Docker build:  docker build --tag=currency:latest .
### Docker run: docker run -p8080:8080 currency:latest
