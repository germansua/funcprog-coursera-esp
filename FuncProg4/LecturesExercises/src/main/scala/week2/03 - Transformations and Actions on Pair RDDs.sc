val ages = (1 until 97 by 7).toList
val grouped = ages.groupBy { age =>
  if (age >= 18 && age < 65) "adult"
  else if (age < 18) "child"
  else "senior"
}

List(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).reduce((a1, a2) => a1)


val a = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
a(2)