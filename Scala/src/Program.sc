import math._
import java.util.{ Date, Locale }
import java.text.DateFormat
import java.text.DateFormat._

object Program {
  // -------------------------
  // Expressions
  // -------------------------
  1 + 2 * 3 / 4                                   //> res0: Int = 2

  // -------------------------
  // Print
  // -------------------------
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  // -------------------------
  // Functions
  // -------------------------
  def hello() {
    println("Hello, world!")
  }                                               //> hello: ()Unit
  hello()                                         //> Hello, world!

  def intRoot23(num: Int) = {
    val numSquare = num * num
    (cbrt(numSquare) + log(numSquare)).toInt
  }                                               //> intRoot23: (num: Int)Int
  intRoot23(1000)                                 //> res1: Int = 113

  // -------------------------
  // For Expressions
  // -------------------------
  val s = for (x <- 1 to 25 if x * x > 50) yield 2 * x
                                                  //> s  : scala.collection.immutable.IndexedSeq[Int] = Vector(16, 18, 20, 22, 24,
                                                  //|  26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50)

  // -------------------------
  // Classes
  // -------------------------
  class Complex(real: Double, imaginary: Double) {
    def re = real
    def im = imaginary
    override def toString() =
      "" + re + (if (im < 0) "" else "+") + im + "i"
  }

  val c = new Complex(1.2, 3.4)                   //> c  : Program.Complex = 1.2+3.4i
  c.toString()                                    //> res2: String = 1.2+3.4i

  // -------------------------
  // Type Inference
  // -------------------------
  def factorial(x: Int): Int =
    if (x == 0)
      1
    else
      x * factorial(x - 1)                        //> factorial: (x: Int)Int

  // -------------------------
  // Case Classes and Pattern Matching
  // -------------------------
  abstract class Tree
  case class Sum(l: Tree, r: Tree) extends Tree
  case class Var(n: String) extends Tree
  case class Const(v: Int) extends Tree

  type Environment = String => Int

  def eval(t: Tree, env: Environment): Int = t match {
    case Sum(l, r) => eval(l, env) + eval(r, env)
    case Var(n) => env(n)
    case Const(v) => v
  }                                               //> eval: (t: Program.Tree, env: Program.Environment)Int

  def derive(t: Tree, v: String): Tree = t match {
    case Sum(l, r) => Sum(derive(l, v), derive(r, v))
    case Var(n) if (v == n) => Const(1)
    case _ => Const(0)
  }                                               //> derive: (t: Program.Tree, v: String)Program.Tree

  val exp: Tree = Sum(Sum(Var("x"), Var("x")), Sum(Const(7), Var("y")))
                                                  //> exp  : Program.Tree = Sum(Sum(Var(x),Var(x)),Sum(Const(7),Var(y)))
  val env: Environment = { case "x" => 5 case "y" => 7 }
                                                  //> env  : Program.Environment = <function1>
  println("Expression: " + exp)                   //> Expression: Sum(Sum(Var(x),Var(x)),Sum(Const(7),Var(y)))
  println("Evaluation with x=5, y=7: " + eval(exp, env))
                                                  //> Evaluation with x=5, y=7: 24
  println("Derivative relative to x:\n " + derive(exp, "x"))
                                                  //> Derivative relative to x:
                                                  //|  Sum(Sum(Const(1),Const(1)),Sum(Const(0),Const(0)))
  println("Derivative relative to y:\n " + derive(exp, "y"))
                                                  //> Derivative relative to y:
                                                  //|  Sum(Sum(Const(0),Const(0)),Sum(Const(0),Const(1)))

  // -------------------------
  // Object-Oriented Extensions
  // -------------------------
  abstract class Window {
    // abstract
    def draw()
  }

  class SimpleWindow extends Window {
    def draw() {
      println("in SimpleWindow")
      // draw a basic window
    }
  }

  trait WindowDecoration extends Window {}

  trait HorizontalScrollbarDecoration extends WindowDecoration {
    // "abstract override" is needed here in order for "super()" to work because the parent
    // function is abstract. If it were concrete, regular "override" would be enough.
    abstract override def draw() {
      println("in HorizontalScrollbarDecoration")
      super.draw()
      // now draw a horizontal scrollbar
    }
  }

  trait VerticalScrollbarDecoration extends WindowDecoration {
    abstract override def draw() {
      println("in VerticalScrollbarDecoration")
      super.draw()
      // now draw a vertical scrollbar
    }
  }

  trait TitleDecoration extends WindowDecoration {
    abstract override def draw() {
      println("in TitleDecoration")
      super.draw()
      // now draw the title bar
    }
  }

  val mywin = new SimpleWindow with VerticalScrollbarDecoration with HorizontalScrollbarDecoration with TitleDecoration
                                                  //> mywin  : Program.SimpleWindow with Program.VerticalScrollbarDecoration with
                                                  //|  Program.HorizontalScrollbarDecoration with Program.TitleDecoration = Progr
                                                  //| am$$anonfun$main$1$$anon$1@69d9c55
  mywin.draw()                                    //> in TitleDecoration
                                                  //| in HorizontalScrollbarDecoration
                                                  //| in VerticalScrollbarDecoration
                                                  //| in SimpleWindow

  // -------------------------
  // Implicits
  // -------------------------
  implicit def arrayWrapper[A: ClassManifest](x: Array[A]) =
    new {
      def sort(p: (A, A) => Boolean) = {
        util.Sorting.stableSort(x, p); x
      }
    }                                             //> arrayWrapper: [A](x: Array[A])(implicit evidence$2: ClassManifest[A])AnyRef
                                                  //| {def sort(p: (A, A) => Boolean): Array[A]}

  val x = Array(2, 3, 1, 4)                       //> x  : Array[Int] = Array(2, 3, 1, 4)
  println("x = " + x.sort((x: Int, y: Int) => x < y))
                                                  //> x = [I@50c87b21

  // -------------------------
  // Maps
  // -------------------------
  val colors = Map("red" -> 0xFF0000,
    "turquoise" -> 0x00FFFF,
    "black" -> 0x000000,
    "orange" -> 0xFF8040,
    "brown" -> 0x804000)                          //> colors  : scala.collection.immutable.Map[String,Int] = Map(orange -> 167445
                                                  //| 12, black -> 0, brown -> 8404992, turquoise -> 65535, red -> 16711680)

  def colorMap(args: Array[String]) {
    for (name <- args) println(
      colors.get(name) match {
        case Some(code) =>
          name + " has code: " + code
        case None =>
          "Unknown color: " + name
      })
  }                                               //> colorMap: (args: Array[String])Unit

  colorMap(Array("red", "black"))                 //> red has code: 16711680
                                                  //| black has code: 0

  // -------------------------
  // Sort
  // -------------------------
  def sort(a: List[Int]): List[Int] = {
    if (a.length < 2)
      a
    else {
      val pivot = a(a.length / 2)
      sort(a.filter(_ < pivot)) :::
        a.filter(_ == pivot) :::
        sort(a.filter(_ > pivot))
    }
  }                                               //> sort: (a: List[Int])List[Int]

  val xs = List(6, 2, 8, 5, 1)                    //> xs  : List[Int] = List(6, 2, 8, 5, 1)
  println(xs)                                     //> List(6, 2, 8, 5, 1)
  println(sort(xs))                               //> List(1, 2, 5, 6, 8)
}