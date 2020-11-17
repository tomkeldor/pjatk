printjson(db.people.aggregate(
    {
        $project: {
            nationality: "$nationality",
            cWeight: { $toDecimal: "$weight" },
            cHeight: { $toDecimal: "$height" },
        }
    },
    {
        $project: {
            nationality: "$nationality",
            cWeight: "$cWeight",
            cHeight: { $divide: ["$cHeight", 100] }
        }
    },
    {
        $project: {
            nationality: "$nationality",
            cWeight: "$cWeight",
            powHeight: { $pow: ["$cHeight", 2] },
        }
    },
    {
        $project: {
            nationality: "$nationality",
            bmi: { $divide: ["$cWeight", "$powHeight"] }
        }
    },
    {
        $group: {
            _id: "$nationality",
            maxBMI: { $max: "$bmi" },
            minBMI: { $min: "$bmi" },
            avgBMI: { $avg: "$bmi" }
        }
    }
).toArray())