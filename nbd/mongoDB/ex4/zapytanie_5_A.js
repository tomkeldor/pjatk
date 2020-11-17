printjson(db.people.aggregate(
    {
        $match: { sex: { $eq: "Female" }, nationality: { $eq: "Poland" } }
    },
    {
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
            sum: { $sum: "$cBalance" },
            avg: { $avg: "$cBalance" }
        }
    }
).toArray())