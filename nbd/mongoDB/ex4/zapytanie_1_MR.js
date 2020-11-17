var mapFunction2 = function () {
    var key = this.sex;
    var value = {
        count: 1,
        height: parseFloat(this.height),
        weight: parseFloat(this.weight)
    };
    emit(key, value);
};

var reduceFuntion2 = function (key, avgObjVals) {
    reducedVal = { count: 0, height: 0, weight: 0 };
    for (var idx = 0; idx < avgObjVals.length; idx++) {
        reducedVal.count += avgObjVals[idx].count;
        reducedVal.height += avgObjVals[idx].height;
        reducedVal.weight += avgObjVals[idx].weight;
    }

    return reducedVal;
};

var finalizeFunction2 = function (key, reducedVal) {
    reducedVal.avgH = reducedVal.height / reducedVal.count;
    reducedVal.avgW = reducedVal.weight / reducedVal.count;

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