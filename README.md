# CouchSurfBackend

#Виды запросов

1. Аутентификация

*Регистрация* (POST)
localhost:port/auth/register

Тело запроса: 

{
    "securedUser": {
        "username": "gddd",
        "password": "ew"
    },
    "age": 21
}

*Логин* (POST)
localhost:port/auth/login

Тело запроса:
{
	"username" : "gddd",
	"password" : "ew"
}

Ответ:
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZGRkIiwiaWF0IjoxNTg5ODkxNzkzLCJleHAiOjE1ODk5MDk3OTN9.de2jEQong34eARdg3k_Qhgms22OEUbe9yiLtSk67tJIHWdrFPfu23vo8VkMBVpxcGXNMfRvxKpWrRL7xjmVBDQ"
}


2. Остальные запросы

*Отправка запроса за добавление объявления*: (Post)
..пока что имя должно быть без пробелов
localhost:port/adchange/add
Требуется аутентификация

{
"header": "header",
"message": "mes",
"place" : {
	"country":"Russia",
	"city":"Msk",
	"home":"Lenina"
},
 "advertType" : "HOUSE_SEARCH",
"peopleNumber": 0,
"arrivingDate":"2020-08-19",
"checkOutDate":"2020-08-20"
}


Ответ на добавление объявления:
//subscribers - пользователи, откликнувшиеся на объявление
{
    "adId": 50,
    "owner": {
        "userid": 1,
        "username": "FirstUser",
        "age": 23,
        "couchSerferRating": 0.0,
        "couchSerferRatingsNum": 0,
        "houseProvisionRating": 0.0,
        "houseProvisionRatingsNum": 0
    },
    "publicationDate": 1589384947924,
    "subscribers": [],
    "header": "header",
    "message": "mes",
    "advertType": 0,
    "place": {
        "country": "Russia",
        "city": "Msk",
        "home": "Lenina"
    },
    "peopleNumber": 0,
    "arrivingDate": 1597795200000,
    "checkOutDate": 1597881600000
}


Ответ на запрос на *получение всех объявлений* - массив [] из json из предыдушего ответа (GET)
В том числе можно запросить не все объявления
Не требует аутентификации

localhost:8083/advert
localhost:8083/advert?type=HOUSE_SEARCH
localhost:8083/advert?type=HOUSE_PROVISION
localhost:8083/advert?limit=2&pos=2
//limit - максимально число объявлений, который хотим получить
//pos - позиция в списе объявлений, начиная с которой получим необходимое число объявлений
//Если limit не указан, pos не учитывается 


*Получение конкретного объявления* (GET)
Не требует аутентификации
localhost:8083/advert/{adId}


*Получение общего числа объявлений* (GET)
Не требует аутентификации
localhost:8083/advert/count

*запрос на получение информации о пользователе* (GET)
Не требует аутентификации

localhost:8083/userinfo/{имя пользователя}
Ответ на этот запрос:
{
    "userid": 1,
    "username": "FirstUser",
    "age": 23,
    "couchSerferRating": 0.0,
    "couchSerferRatingsNum": 0,
    "houseProvisionRating": 0.0,
    "houseProvisionRatingsNum": 0
}


*Изменение рейтинга:*
Необходима аутентификация

запрос на изменение рейтинга пользователя как съемщика(PUT)
localhost:8083/user/{имя пользователя}/changeCsRate?rate=4
аналогично запрос для изменения рейтинга как предоставителя жилья
localhost:8083/user/{имя пользователя}/changeHcRate?rate=4

{
    "comments": [],
    "advert": {
        "adId": 50,
        "owner": {
            "userid": 1,
            "username": "FirstUser",
            "age": 23,
            "couchSerferRating": 0.0,
            "couchSerferRatingsNum": 0,
            "houseProvisionRating": 0.0,
            "houseProvisionRatingsNum": 0
        },
        "publicationDate": 1589391329000,
        "subscribers": [],
        "header": "header",
        "message": "mes",
        "advertType": 0,
        "place": {
            "country": "Russia",
            "city": "Msk",
            "home": "Lenina"
        },
        "peopleNumber": 0,
        "arrivingDate": 1597795200000,
        "checkOutDate": 1597881600000
    }
}


*добавить комментарий к объявлению *(PUT)
Необходима аутентификация

//adId - номер объявления
localhost:8083/comments/{adId}/add
{
	"message": "vaaa"
}

Ответ: (в дальшнейшем можно убрать, чтобы лишнее не передавать)

{
    "commentId": 50,
    "commentAdvert": {
        "adId": 50,
        "owner": {
            "userid": 1,
            "username": "FirstUser",
            "age": 23,
            "couchSerferRating": 0.0,
            "couchSerferRatingsNum": 0,
            "houseProvisionRating": 0.0,
            "houseProvisionRatingsNum": 0
        },
        "publicationDate": 1589393127000,
        "subscribers": [],
        "header": "header",
        "message": "mes",
        "advertType": 0,
        "place": {
            "country": "Russia",
            "city": "Msk",
            "home": "Lenina"
        },
        "peopleNumber": 0,
        "arrivingDate": 1597795200000,
        "checkOutDate": 1597881600000
    },
    "message": "vaaa",
    "author": {
        "userid": 1,
        "username": "FirstUser",
        "age": 23,
        "couchSerferRating": 0.0,
        "couchSerferRatingsNum": 0,
        "houseProvisionRating": 0.0,
        "houseProvisionRatingsNum": 0
    }
}




*Добавить пользователя отозвавшегося на объявление*
Необходима аутентификация
localhost:8083/adchange/{adId}/addsubscriber

{
    "adId": 50,
    "owner": {
        "userid": 1,
        "username": "FirstUser",
        "age": 23,
        "couchSerferRating": 0.0,
        "couchSerferRatingsNum": 0,
        "houseProvisionRating": 0.0,
        "houseProvisionRatingsNum": 0
    },
    "publicationDate": 1589393693000,
    "subscribers": [
        {
            "userid": 2,
            "username": "Sec",
            "age": 34,
            "couchSerferRating": 0.0,
            "couchSerferRatingsNum": 0,
            "houseProvisionRating": 0.0,
            "houseProvisionRatingsNum": 0
        }
    ],
    "header": "header",
    "message": "mes",
    "advertType": 0,
    "place": {
        "country": "Russia",
        "city": "Msk",
        "home": "Lenina"
    },
    "peopleNumber": 0,
    "arrivingDate": 1597795200000,
    "checkOutDate": 1597881600000
}

3. API чата
