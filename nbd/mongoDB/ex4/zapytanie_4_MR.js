var mapFunction2 = function () {
    var key = this.nationality;
    var value = {
        count: 1,
        bmi: parseFloat(this.weight) / Math.pow(parseFloat(this.height)/100, 2),
        sumBMI: parseFloat(this.weight) / Math.pow(parseFloat(this.height)/100, 2),
        maxBMI: parseFloat(this.weight) / Math.pow(parseFloat(this.height)/100, 2),
        minBMI: parseFloat(this.weight) / Math.pow(parseFloat(this.height)/100, 2)
    };
    emit(key, value);
};

var reduceFuntion2 = function (key, avgObjVals) {
    var reducedVal = 0
    for (var idx = 0; idx < avgObjVals.length; idx++) {
        reducedVal.count += avgObjVals[idx].count;
        reducedVal.sumBMI += avgObjVals[idx].bmi;
        if (avgObjVals[idx].bmi > reducedVal.maxBMI)
            reducedVal.maxBMI = avgObjVals[idx].bmi;
        if (avgObjVals[idx].bmi < reducedVal.minBMI)
            reducedVal.minBMI = avgObjVals[idx].bmi;
    }

    return reducedVal;
};

var finalizeFunction2 = function (key, reducedVal) {
    reducedVal.avgBMI = reducedVal.sumBMI / reducedVal.count;

    return reducedVal;
};

printjson(db.runCommand(
    {
        mapReduce: "people",
        map: mapFunction2,
        reduce: reduceFuntion2,
        out: { inline: 1 },
        finalize: finalizeFunction2
    }
))