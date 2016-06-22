val test_list = List("hola", "que", "tal", "estas")
// Total: 4 + 3 + 3 + 5 = 15




val sum = (0 /: test_list){(sum, i) => sum + i.size}
println(sum)



val sum2 = test_list.foldLeft(0)((sum, i) => sum + i.size)
println(sum2)

