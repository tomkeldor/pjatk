printjson(db.people.aggregate({
    $unwind: "$credit"
},
    {
        $project: {
            currency: "$credit.currency",
            cBalance: { $toDecimal: "$credit.balance" }
        }
    },
    {
        $group: {
            _id: "$currency",
            sum: { $sum: "$cBalance" }
        }
    }
).toArray())