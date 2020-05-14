# CouchSurfBackend

#Виды запросов

Отправка запроса за добавление объявления от имени FirstUser: (Post)
..пока что имя должно быть без пробелов
localhost:8080/hpad - объявление о предоставлении жилья
localhost:8080/hsad - объявление о поиске жилья

{"username":"FirstUser",
"header": "header",
"message": "mes",
"country":"Russia",
"city":"Msk",
"home":"Lenina",
"peopleNumber":0,
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


Ответ на запрос на получение всех объявлений - массив [] из json из предыдушего ответа (GET)
В том числе можно запросить не все объявления
localhost:8080/advert
localhost:8080/advert?type=HOUSE_SEARCH
localhost:8080/advert?type=HOUSE_PROVISION
localhost:8080/advert?limit=2&pos=2
//limit - максимально число объявлений, который хотим получить
//pos - позиция в списе объявлений, начиная с которой получим необходимое число объявлений

запрос на получение информации о пользователе (GET)
localhost:8080/user/{имя пользователя}
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


запрос на изменение рейтинга пользователя как съемщика(PUT)
localhost:8080/user/changeCsRate?username=FirstUser&rate=4

аналогично запрос для изменения рейтинга как предоставителя жилья
localhost:8080/user/changeHcRate?username=FirstUser&rate=4

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


добавить комментарий к объявлению (PUT)
//adId - номер объявления
localhost:8080/comments/add
{
	"adId" : 50,
	"message": "vaaa",
	"author": "FirstUser"
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




Добавить пользователя отозвавшегося на объявление
localhost:8080/adver/addsubscriber?username=Sec&ad=50

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

