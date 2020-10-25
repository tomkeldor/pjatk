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
    val products : Map[String,Double]  = Map("Bike" -> 100.00, "TV" -> 200.00, "Speaker" -> 50.00, "Phone" -> 150.00)
    val discountedProducts = products.map(v => (v._1, v._2 * 0.9))
    discountedProducts.foreach(v => println("Name " + v._1 + " Price " + v._2))
  }
}