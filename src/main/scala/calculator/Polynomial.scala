package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
                   c: Signal[Double]): Signal[Double] = {
    Var {
      val bValue = b()
      val aValue = a()
      val cValue = c()
      (bValue * bValue) - 4.0 * aValue * cValue
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
                       c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Var {
      val bValue = b()
      val aValue = a()
      val deltaValue = delta()
      deltaValue < 0.0 match {
        case true => Set.empty
        case false =>
          val sqrt: Double = math.sqrt(deltaValue)
          Set(sqrt, -sqrt).map(
            value => (-bValue + value) / 2.0 * aValue
          )
      }
    }
  }
}
