var mapFunction2 = function () {
    for (var idx = 0; idx < this.credit.length; idx++) {
        var key = this.credit[idx].currency;
        var value = parseFloat(this.credit[idx].balance);
    }
    emit(key, value);
};

var reduceFuntion2 = function (key, objVals) {
    reducedVal = 0;
    for (var idx = 0; idx < objVals.length; idx++) {
        reducedVal += objVals[idx];
    }

    return reducedVal;
};

var finalizeFunction2 = function (key, reducedVal) {
};

printjson(db.runCommand(
    {
        mapReduce: "people",
        map: mapFunction2,
        reduce: reduceFuntion2,
        out: { inline: 1 },
        //finalize: finalizeFunction2
    }
))