printjson(db.people.aggregate({
    $project: {
        sex: "$sex",
        cHeight: { $toDecimal: "$height" },
        cWeight: { $toDecimal: "$weight" }
    }
},
    {
        $group: {
            _id: "$sex",
            avgHeight: { $avg: "$cHeight" },
            avgWeight: { $avg: "$cWeight" }
        }
    }).toArray())