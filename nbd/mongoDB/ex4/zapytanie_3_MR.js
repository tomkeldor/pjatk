var mapFunction2 = function () {
        var key = this.job;
        var value = 1;
    emit(key, value);
};

var reduceFuntion2 = function (key, objVals) {
    return Array.sum(objVals);
};

printjson(db.runCommand(
    {
        mapReduce: "people",
        map: mapFunction2,
        reduce: reduceFuntion2,
        out: { inline: 1 },
    }
))