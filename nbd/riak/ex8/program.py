import os
import riak

databaseConnection = riak.RiakClient(pb_port=8098)
nameOfTheBucket = databaseConnection.bucket("s23689")

gameInput = {"gameName": "GenshinImpact", "gameReleaseYear": 2020, "gameDeveloperName": "miHoYo", "gameGenre": "ActionRolePlaying", "gameUserScore": 6.10, "gameUpdates": "True"}

print("Dodanie, pobranie, wypisanie:")
firstKey = nameOfTheBucket.new(gameInput["gameName"], data = gameInput)
firstKey.store()
firstGet = nameOfTheBucket.get(gameInput["gameName"])
print(str(firstGet.data) + "\n")

print("Edycja, pobranie, wypisanie:")
firstGet.data["gameName"] = "FinalFantasyXV"
firstGet.store()
secondGet = nameOfTheBucket.get(gameInput["gameName"])
print(str(secondGet.data) + "\n")

print("Usuniecie, proba pobrania:")
secondGet.delete()
try:
    thirdGet = nameOfTheBucket.get(gameInput["gameName"])
    print(str(thirdGet.data))
except Exception as e:
    print(e)