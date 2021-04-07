package root.methods.trapezoid;

import root.methods.Resolver;
import root.validators.FunctionalValidator;
import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public final class Trapezoid implements Resolver {
  @Override
  public int getN( ) {
    return n;
  }

  private int n = 0;

  @Override
  public double solve( double prevInt, Function<Double, Double> fun, double a, double b, double eps, int n ) throws SecondKindBreakPointException, IndeterminedArgumentValueException, IndeterminedFunctionValueException {
    double sum = 0;
    do {
      prevInt = sum;
      sum = ( fun.apply( a ) + fun.apply( b ) ) / 2;
      double hstep = ( b - a ) / n;
      for ( int i = 1; i < n - 1; ++i ) {
        FunctionalValidator fv = new FunctionalValidator( fun, a + i * hstep, a + ( i + 1 ) * hstep );
        double fx = fv.f( a + i * hstep );
        if ( !Double.isNaN( fx ) )
          sum += fx;
        else throw new IndeterminedFunctionValueException( a + i * hstep );
      }
      sum = sum * hstep;
      n *= 2;
    } while ( Math.abs( sum - prevInt ) > eps );
    this.n = n / 2;
    return sum;
  }
}
