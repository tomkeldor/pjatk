object ex9 {
  def main(args: Array[String]): Unit = {

    println("Point1:")

    class Container[A](private val value: A) {
      def getContent: A = value
      def applyFunction[R](function: A => R): R = function(value)
    }

    println("\n" + "Point2:")

    trait Maybe[+A] {}

    class No extends Maybe[Nothing] {}

    class Yes[A](val value: A) extends Maybe[A] {}

    val new_no = new No
    val new_yes = new Yes[Int](2136)

    val is_new_no = new_no.isInstanceOf[Maybe[_]]
    val is_new_yes = new_yes.isInstanceOf[Maybe[_]]

    println("Czy No jest podtypem Maybe[_]]?: " + is_new_no)
    println("Czy Yes jest podtypem Maybe[_]?: " + is_new_yes)

    println("\n" + "Point3:")

    class YesNoContainer[A](private val value: Maybe[A]) {
      def applyFunction[R](function: A => R): Maybe[R] = {
        if (value.isInstanceOf[No]) {
          new No
        }
        else {
          new Yes[R](function(value.asInstanceOf[Yes[A]].value))
        }
      }
    }

    println("\n" + "Point4:")

    def getOrElse[A](value: Maybe[A], secondValue: A): A = {
      if (value.isInstanceOf[No]) {
        secondValue
      }
      else {
        value.asInstanceOf[Yes[A]].value
      }
    }

  }
}