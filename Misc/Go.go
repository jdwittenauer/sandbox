package main

import "fmt"
import "math"

func hello() {
    var x string = "Hello World"
    fmt.Println(x)
}

func values() {
    fmt.Println("go" + "lang")
    fmt.Println("1+1 =", 1+1)
    fmt.Println("7.0/3.0 =", 7.0/3.0)
    fmt.Println(true && false)
    fmt.Println(true || false)
    fmt.Println(!true)
}

func variables() {
    var a string = "initial"
    fmt.Println(a)
	
    var b, c int = 1, 2
    fmt.Println(b, c)
	
    var d = true
    fmt.Println(d)
	
    var e int
    fmt.Println(e)
	
    f := "short"
    fmt.Println(f)
}

func forLoop() {
    i := 1
    for i <= 3 {
        fmt.Println(i)
        i = i + 1
    }
	
    for j := 7; j <= 9; j++ {
        fmt.Println(j)
    }
	
    for {
        fmt.Println("loop")
        break
    }
}

func ifElse() {
    if 7%2 == 0 {
        fmt.Println("7 is even")
    } else {
        fmt.Println("7 is odd")
    }
	
    if 8%4 == 0 {
        fmt.Println("8 is divisible by 4")
    }
	
    if num := 9; num < 0 {
        fmt.Println(num, "is negative")
    } else if num < 10 {
        fmt.Println(num, "has 1 digit")
    } else {
        fmt.Println(num, "has multiple digits")
    }
}

	
func arrays() {
    var a [5]int
    fmt.Println("emp:", a)
	
    a[4] = 100
    fmt.Println("set:", a)
    fmt.Println("get:", a[4])
	
    fmt.Println("len:", len(a))
	
    b := [5]int{1, 2, 3, 4, 5}
    fmt.Println("dcl:", b)
	
    var twoD [2][3]int
    for i := 0; i < 2; i++ {
        for j := 0; j < 3; j++ {
            twoD[i][j] = i + j
        }
    }
    fmt.Println("2d: ", twoD)
}

func slices() {
    s := make([]string, 3)
    fmt.Println("emp:", s)
	
    s[0] = "a"
    s[1] = "b"
    s[2] = "c"
    fmt.Println("set:", s)
    fmt.Println("get:", s[2])
	
    fmt.Println("len:", len(s))
	
    s = append(s, "d")
    s = append(s, "e", "f")
    fmt.Println("apd:", s)
	
    c := make([]string, len(s))
    copy(c, s)
    fmt.Println("cpy:", c)
	
    l := s[2:5]
    fmt.Println("sl1:", l)
	
    l = s[:5]
    fmt.Println("sl2:", l)
	
    l = s[2:]
    fmt.Println("sl3:", l)
	
    t := []string{"g", "h", "i"}
    fmt.Println("dcl:", t)
	
    twoD := make([][]int, 3)
    for i := 0; i < 3; i++ {
        innerLen := i + 1
        twoD[i] = make([]int, innerLen)
        for j := 0; j < innerLen; j++ {
            twoD[i][j] = i + j
        }
    }
    fmt.Println("2d: ", twoD)
}

	
func maps() {
    m := make(map[string]int)
	
    m["k1"] = 7
    m["k2"] = 13
	
    fmt.Println("map:", m)
	
    v1 := m["k1"]
    fmt.Println("v1: ", v1)
	
    fmt.Println("len:", len(m))
	
    delete(m, "k2")
    fmt.Println("map:", m)
	
    _, prs := m["k2"]
    fmt.Println("prs:", prs)
	
    n := map[string]int{"foo": 1, "bar": 2}
    fmt.Println("map:", n)
}

func range() {
    nums := []int{2, 3, 4}
    sum := 0
    for _, num := range nums {
        sum += num
    }
    fmt.Println("sum:", sum)
	
    for i, num := range nums {
        if num == 3 {
            fmt.Println("index:", i)
        }
    }
	
    kvs := map[string]string{"a": "apple", "b": "banana"}
    for k, v := range kvs {
        fmt.Printf("%s -> %s\n", k, v)
    }
	
    for i, c := range "go" {
        fmt.Println(i, c)
    }
}

func vals() (int, int) {
    return 3, 7
}

func sum(nums ...int) {
    fmt.Print(nums, " ")
    total := 0
    for _, num := range nums {
        total += num
    }
    fmt.Println(total)
}

func intSeq() func() int {
    i := 0
    return func() int {
        i += 1
        return i
    }
}

func fact(n int) int {
    if n == 0 {
        return 1
    }
    return n * fact(n-1)
}

type geometry interface {
    area() float64
    perim() float64
}

type square struct {
    width, height float64
}
type circle struct {
    radius float64
}

func (s square) area() float64 {
    return s.width * s.height
}
func (s square) perim() float64 {
    return 2*s.width + 2*s.height
}

func (c circle) area() float64 {
    return math.Pi * c.radius * c.radius
}
func (c circle) perim() float64 {
    return 2 * math.Pi * c.radius
}

func measure(g geometry) {
    fmt.Println(g)
    fmt.Println(g.area())
    fmt.Println(g.perim())
}