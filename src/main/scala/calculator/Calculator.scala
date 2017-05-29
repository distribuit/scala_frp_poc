package calculator

sealed abstract class Expr

final case class Literal(v: Double) extends Expr

final case class Ref(name: String) extends Expr

final case class Plus(a: Expr, b: Expr) extends Expr

final case class Minus(a: Expr, b: Expr) extends Expr

final case class Times(a: Expr, b: Expr) extends Expr

final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(
                     namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions mapValues {
      exprSignal => Var(eval(exprSignal(), namedExpressions))
    }
  }


  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double =
    evalHelper(expr, references, Set.empty)


  def evalHelper(expr: Expr, references: Map[String, Signal[Expr]], visited: Set[Expr]): Double = {
    visited contains expr match {
      case true => Double.NaN
      case false =>
        expr match {
          case Literal(v) => v
          case Ref(name) =>
            val ref: Expr = getReferenceExpr(name, references)
            evalHelper(ref, references, visited + expr)
          case Plus(a, b) =>
            val aResult = evalHelper(a, references, visited + expr)
            val bResult = evalHelper(b, references, visited + expr)
            aResult + bResult
          case Minus(a, b) =>
            val aResult = evalHelper(a, references, visited + expr)
            val bResult = evalHelper(b, references, visited + expr)
            aResult - bResult
          case Times(a, b) =>
            val aResult = evalHelper(a, references, visited + expr)
            val bResult = evalHelper(b, references, visited + expr)
            aResult * bResult
          case Divide(a, b) =>
            val aResult = evalHelper(a, references, visited + expr)
            val bResult = evalHelper(b, references, visited + expr)
            aResult / bResult
        }
    }
  }


  /** Get the Expr for a referenced variables.
    * If the variable is not known, returns a literal NaN.
    */
  private def getReferenceExpr(name: String,
                               references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
