printjson(db.people.insert({
    "sex": "Male",
    "first_name": "Tomasz",
    "last_name": "Smyczynski",
    "job": "Senior Developer",
    "email": "s23689@pja.edu.pl",
    "location": {
        "city": "Warsaw",
        "address": {
            "streetname": "Nalewki",
            "streetnumber": "123"
        }
    },
    "description": "condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque",
    "height": "182.51",
    "weight": "65.53",
    "birth_date": "1994-02-17T07:46:43Z",
    "nationality": "Poland",
    "credit": [
        {
            "type": "jcb",
            "number": "7526195112898593",
            "currency": "PLN",
            "balance": "17547.74"
        }
    ]
}
)
)