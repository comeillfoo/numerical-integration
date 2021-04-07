package root.methods.simpson;

import root.methods.Resolver;
import root.validators.FunctionalValidator;
import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public final class Simpson implements Resolver {
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
      sum = a + b;
      double hstep = ( b - a ) / n;
      for ( int i = 1; i < n; ++i ) {
        double x = a + i * hstep;
        FunctionalValidator fv = new FunctionalValidator( fun, x, x + hstep );
        double fx = fv.f( x );
        if ( !Double.isNaN( fx ) )
          sum += fx * ( 2 + 2 * ( i % 2 ) );
        else throw new IndeterminedFunctionValueException( x );
      }
      sum = sum * hstep / 3;
      n *= 2;
    } while ( Math.abs( sum - prevInt ) > eps );
    this.n = n / 2;
    return sum;
  }
}
