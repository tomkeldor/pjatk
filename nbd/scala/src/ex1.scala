import scala.annotation.tailrec

object ex1 {
  def main(args: Array[String]) {
    val weekDays = List("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela")
    point1a(weekDays)
    point1b(weekDays)
    point1c(weekDays)
    point2a(weekDays, new StringBuilder())
    point2b(weekDays, new StringBuilder())
    point3(weekDays, new StringBuilder())
    point4a(weekDays)
    point4b(weekDays)
    point4c(weekDays)
    point5()
    point6(22, 10.20, "Prawa Kobiet")
    point7()
    val integers = List(99, 10, 2, 0, 2, 5, 0, 32, 35, 0, 37, 63, 21, 0, 0, 0)
    point8(integers)
    point9(integers)
    val reals = List(-7,5,-3,1,-1,3,6,9,12,15)
    point10(reals)
  }

  def point1a(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result: StringBuilder = new StringBuilder()
      for (i <- myList.indices) {
        result.append(myList(i))
        if (i < myList.length - 1) {
          result.append(',')
        }
      }
      println("1a")
      println(result)
    }
  }

  def point1b(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result: StringBuilder = new StringBuilder()
      for (i <- myList.indices) {
        if (myList(i).toString.startsWith("P")) {
          if (i > 0) {
            result.append(',')
          }
          result.append(myList(i))
        }
      }
      println("1b")
      println(result)
    }
  }

  def point1c(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result: StringBuilder = new StringBuilder()
      var i = 0
      while (i < myList.length) {
        result.append(myList(i))
        if (i < myList.length - 1) {
          result.append(',')
        }
        i += 1
      }
      println("1c")
      println(result)
    }
  }

  @tailrec
  def point2a(myList: List[Any], result: StringBuilder): Unit = {
    if (myList.nonEmpty) {
      result.append(myList.head)
      if (myList.length > 1) {
        result.append(',')
      }
      point2a(myList.slice(1, myList.length), result)
    }
    else {
      println("2a")
      println(result)
    }
  }

  @tailrec
  def point2b(myList: List[Any], result: StringBuilder): Unit = {
    if (myList.nonEmpty) {
      result.append(myList.last)
      if (myList.length > 1) {
        result.append(',')
      }
      val newList = myList.take(myList.length - 1)
      point2b(newList, result)
    }
    else {
      println("2b")
      println(result)
    }
  }

  @tailrec
  def point3(myList: List[Any], result: StringBuilder): Unit = {
    if (myList.nonEmpty) {
      result.append(myList.head)
      if (myList.length > 1) {
        result.append(',')
      }
      point3(myList.tail, result)
    }
    else {
      println("3")
      println(result)
    }
  }

  def point4a(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result = myList.tail.foldLeft(myList.head)(_ + "," + _)
      println("4a")
      println(result)
    }
  }

  def point4b(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result = myList.slice(0, myList.length - 1).foldRight(myList.last)(_ + "," + _)
      println("4b")
      println(result)
    }
  }

  def point4c(myList: List[Any]): Unit = {
    if (myList.nonEmpty) {
      val result = myList.filter(_.toString.startsWith("P")).tail.foldLeft(myList.head)(_ + "," + _)
      println("4c")
      println(result)
    }
  }

  def point5(): Unit = {
    val products: Map[String, Double] = Map("Bike" -> 100.00, "TV" -> 200.00, "Speaker" -> 50.00, "Phone" -> 150.00)
    val discountedProducts = products.map(v => (v._1, v._2 * 0.9))
    println("5")
    discountedProducts.foreach(v => println("Name " + v._1 + " Price " + v._2))
  }

  def point6(myTuple: (Int, Double, String)): Unit = {
    println("6")
    print(myTuple._1)
    print(',')
    println(String.format("%.2f", myTuple._2))
    println(myTuple._3)
  }

  def point7(): Unit = {
    val products: Map[String, Double] = Map("Bike" -> 10.00, "Phone" -> 15.00)
    val phone = products.get("Phone")
    val table = products.get("Table")
    println("7")
    println(phone)
    println(table)
  }

  def point8(myList: List[Int]): Unit = {
    if (myList.contains(0)) {
      val newList = myList filter (_.!=(0))
      point8(newList)
    }
    else {
      println("8")
      println(myList.foreach(v => println(v)))
    }
  }

  def point9(myList: List[Int]): Unit = {
    if (myList.nonEmpty) {
      val newList = myList.map(n => n + 1)
      println("9")
      println(newList.foreach(v => println(v)))
    }
  }

  def point10(myList: List[Int]): Unit = {
    if (myList.nonEmpty) {
      val newList = myList.filter(n => n >= -5 && n <= 12).map(_.abs)
      println("10")
      println(newList.foreach(v => println(v)))
    }
  }
}