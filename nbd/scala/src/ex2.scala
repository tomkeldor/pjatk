object ex2 {
  def main(args: Array[String]) {
    println("Point1")
    println(point1("Poniedziałek"))
    println(point1("Sobota"))
    println(point1("Void"))
    println("Point3")
    val osoba1 = new Osoba("Jan", "Kowalski")
    val osoba2 = new Osoba("Adam", "Nowak")
    val osoba3 = new Osoba("Janina", "Kowalska")
    val osoba4 = new Osoba("Anna", "Nowak")
    println(point3(osoba1))
    println(point3(osoba2))
    println(point3(osoba3))
    println(point3(osoba4))
    println("Point4")
    val myFunction = (myInt: Int) => myInt * 2
    point4(2, myFunction)
    println("Point5")
    val student = new Osoba5("Jan", "Kowalski", 50) with Student
    val nauczyciel = new Osoba5("Adam", "Nowak", 50) with Nauczyciel
    val pracownik = new Osoba5(imie = "Janina", "Kowalska", 50) with Pracownik
    println("Student")
    println(student.podatek)
    println("Nauczyciel")
    println(nauczyciel.pensja)
    println("Pracownik")
    println(pracownik.pensja)
    val studentPracownik = new Osoba5("Anna", "Nowak", 50) with Student with Pracownik
    val pracownikStudent = new Osoba5("Jan", "Kowalski", 50) with Pracownik with Student
    println("StudentPracownik")
    println(studentPracownik.pensja)
    println("PracownikStudent")
    println(pracownikStudent.pensja)
  }

  def point1(myString: String): String = myString match {
    case "Poniedziałek" => "Praca"
    case "Wtorek" => "Praca"
    case "Środa" => "Praca"
    case "Czwartek" => "Praca"
    case "Piątek" => "Praca"
    case "Sobota" => "Weekend"
    case "Niedziela" => "Weekend"
    case x => "Nie ma takiego dnia"
  }

  class KontoBankowe() {
    var _stanKonta = 0.00

    def stanKonta: Double = _stanKonta

    def stanKonta_=(value: Double): Unit = _stanKonta = value

    def one(stanPoczatkowy: Double) {
      stanKonta_=(stanPoczatkowy)
    }

    def two() {
      stanKonta_=(0.00)
    }

    def wplata(wartosc: Double): Unit = {
      stanKonta_=(this.stanKonta + wartosc)
    }

    def wyplata(wartosc: Double): Unit = {
      stanKonta_=(this.stanKonta - wartosc)
    }
  }

  class Osoba(val imie: String, val nazwisko: String)

  def point3(myOsoba: Osoba): String = myOsoba match {
    case myOsoba: Osoba if myOsoba.imie + myOsoba.nazwisko == "JanKowalski" => s"Witaj ${myOsoba.imie} ${myOsoba.nazwisko}!"
    case myOsoba: Osoba if myOsoba.imie + myOsoba.nazwisko == "AdamNowak" => s"Cześć ${myOsoba.imie} ${myOsoba.nazwisko}!"
    case myOsoba: Osoba if myOsoba.imie + myOsoba.nazwisko == "JaninaKowalska" => s"Hej ${myOsoba.imie} ${myOsoba.nazwisko}!"
    case xyz => s"Czołem ${myOsoba.imie} ${myOsoba.nazwisko}!"
  }

  def point4(myInteger: Int, point4Help: Int => Int): Unit = {
    point4Help(myInteger)
    point4Help(myInteger)
    println(point4Help(myInteger))
  }

  class Osoba5(val imie: String, val nazwisko: String, val podatek: Double)

  trait Student extends Osoba5 {
    override val podatek = 0
  }

  trait Nauczyciel extends Pracownik {
    override val podatek = 0.1
  }

  trait Pracownik extends Osoba5 {
    var _pensja = 100.00

    def pensja: Double = _pensja * podatek

    def pensja_=(value: Double): Unit = _pensja = value

    override val podatek = 0.2
  }

}