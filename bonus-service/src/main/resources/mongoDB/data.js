db.createCollection("rates")

db.rates.insertMany([
    {
        "rate": 100,
        "projectId": 1
    },
    {
        "rate": 220,
        "projectId": 2
    },
    {
        "rate": 350,
        "projectId": 3
    }
]);