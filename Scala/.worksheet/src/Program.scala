import math._
import java.util.{ Date, Locale }
import java.text.DateFormat
import java.text.DateFormat._

object Program {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(219); val res$0 = 
  // -------------------------
  // Expressions
  // -------------------------
  1 + 2 * 3 / 4;System.out.println("""res0: Int = """ + $show(res$0));$skip(119); 

  // -------------------------
  // Print
  // -------------------------
  println("Welcome to the Scala worksheet");$skip(128); 

  // -------------------------
  // Functions
  // -------------------------
  def hello() {
    println("Hello, world!")
  };System.out.println("""hello: ()Unit""");$skip(10); 
  hello();$skip(111); 

  def intRoot23(num: Int) = {
    val numSquare = num * num
    (cbrt(numSquare) + log(numSquare)).toInt
  };System.out.println("""intRoot23: (num: Int)Int""");$skip(18); val res$1 = 
  intRoot23(1000);System.out.println("""res1: Int = """ + $show(res$1));$skip(140); 

  // -------------------------
  // For Expressions
  // -------------------------
  val s = for (x <- 1 to 25 if x * x > 50) yield 2 * x

  // -------------------------
  // Classes
  // -------------------------
  class Complex(real: Double, imaginary: Double) {
    def re = real
    def im = imaginary
    override def toString() =
      "" + re + (if (im < 0) "" else "+") + im + "i"
  };System.out.println("""s  : scala.collection.immutable.IndexedSeq[Int] = """ + $show(s ));$skip(290); 

  val c = new Complex(1.2, 3.4);System.out.println("""c  : Program.Complex = """ + $show(c ));$skip(15); val res$2 = 
  c.toString();System.out.println("""res2: String = """ + $show(res$2));$skip(175); 

  // -------------------------
  // Type Inference
  // -------------------------
  def factorial(x: Int): Int =
    if (x == 0)
      1
    else
      x * factorial(x - 1)

  // -------------------------
  // Case Classes and Pattern Matching
  // -------------------------
  abstract class Tree
  case class Sum(l: Tree, r: Tree) extends Tree
  case class Var(n: String) extends Tree
  case class Const(v: Int) extends Tree

  type Environment = String => Int;System.out.println("""factorial: (x: Int)Int""");$skip(451); 

  def eval(t: Tree, env: Environment): Int = t match {
    case Sum(l, r) => eval(l, env) + eval(r, env)
    case Var(n) => env(n)
    case Const(v) => v
  };System.out.println("""eval: (t: Program.Tree, env: Program.Environment)Int""");$skip(174); 

  def derive(t: Tree, v: String): Tree = t match {
    case Sum(l, r) => Sum(derive(l, v), derive(r, v))
    case Var(n) if (v == n) => Const(1)
    case _ => Const(0)
  };System.out.println("""derive: (t: Program.Tree, v: String)Program.Tree""");$skip(74); 

  val exp: Tree = Sum(Sum(Var("x"), Var("x")), Sum(Const(7), Var("y")));System.out.println("""exp  : Program.Tree = """ + $show(exp ));$skip(57); 
  val env: Environment = { case "x" => 5 case "y" => 7 };System.out.println("""env  : Program.Environment = """ + $show(env ));$skip(32); 
  println("Expression: " + exp);$skip(57); 
  println("Evaluation with x=5, y=7: " + eval(exp, env));$skip(61); 
  println("Derivative relative to x:\n " + derive(exp, "x"));$skip(61); 
  println("Derivative relative to y:\n " + derive(exp, "y"))

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
  };$skip(1254); 

  val mywin = new SimpleWindow with VerticalScrollbarDecoration with HorizontalScrollbarDecoration with TitleDecoration;System.out.println("""mywin  : Program.SimpleWindow with Program.VerticalScrollbarDecoration with Program.HorizontalScrollbarDecoration with Program.TitleDecoration = """ + $show(mywin ));$skip(15); 
  mywin.draw();$skip(246); 

  // -------------------------
  // Implicits
  // -------------------------
  implicit def arrayWrapper[A: ClassManifest](x: Array[A]) =
    new {
      def sort(p: (A, A) => Boolean) = {
        util.Sorting.stableSort(x, p); x
      }
    };System.out.println("""arrayWrapper: [A](x: Array[A])(implicit evidence$2: ClassManifest[A])AnyRef{def sort(p: (A, A) => Boolean): Array[A]}""");$skip(30); 

  val x = Array(2, 3, 1, 4);System.out.println("""x  : Array[Int] = """ + $show(x ));$skip(54); 
  println("x = " + x.sort((x: Int, y: Int) => x < y));$skip(217); 

  // -------------------------
  // Maps
  // -------------------------
  val colors = Map("red" -> 0xFF0000,
    "turquoise" -> 0x00FFFF,
    "black" -> 0x000000,
    "orange" -> 0xFF8040,
    "brown" -> 0x804000);System.out.println("""colors  : scala.collection.immutable.Map[String,Int] = """ + $show(colors ));$skip(237); 

  def colorMap(args: Array[String]) {
    for (name <- args) println(
      colors.get(name) match {
        case Some(code) =>
          name + " has code: " + code
        case None =>
          "Unknown color: " + name
      })
  };System.out.println("""colorMap: (args: Array[String])Unit""");$skip(36); 

  colorMap(Array("red", "black"));$skip(302); 

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
  };System.out.println("""sort: (a: List[Int])List[Int]""");$skip(33); 

  val xs = List(6, 2, 8, 5, 1);System.out.println("""xs  : List[Int] = """ + $show(xs ));$skip(14); 
  println(xs);$skip(20); 
  println(sort(xs))}
}
