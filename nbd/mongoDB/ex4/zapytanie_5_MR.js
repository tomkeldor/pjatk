var mapFunction2 = function () {
    for (var idx = 0; idx < this.credit.length; idx++) {
        var key = this.credit[idx].currency;
        var value = {
            count: 1,
            balance: parseFloat(this.credit[idx].balance)
        }
    emit(key, value);
    }
};

var reduceFuntion2 = function (key, objVals) {
    reducedVal = { count: 0, sumBalance: 0 };
    for (var idx = 0; idx < objVals.length; idx++) {
        reducedVal.count += objVals[idx].count;
        reducedVal.sumBalance += objVals[idx].balance;
    }

    return reducedVal;
};

var finalizeFunction2 = function (key, reducedVal) {
    reducedVal.avgBalance = reducedVal.sumBalance / reducedVal.count;

    return reducedVal;
};

printjson(db.runCommand(
    {
        mapReduce: "people",
        map: mapFunction2,
        reduce: reduceFuntion2,
        out: { inline: 1 },
        query: { sex: "Female" , nationality: "Poland" },
        finalize: finalizeFunction2
    }
))