package root.methods.simpson;

import root.IntegralShell;
import root.methods.Resolver;
import root.validators.FunctionalValidator;
import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public final class SimpsonVerbosed implements Resolver {
  @Override
  public int getN( ) {
    return n;
  }

  private int n = 0;

  @Override
  public final double solve( double prevInt, Function<Double, Double> fun, double a, double b, double eps, int n ) throws IndeterminedArgumentValueException, SecondKindBreakPointException, IndeterminedFunctionValueException {
    IntegralShell.stdOutPrintf( "n_0 = % 8d\n", n );
    double sum = 0;
    do {
      prevInt = sum;
      sum = fun.apply( a )  + fun.apply( b );
      double hstep = ( b - a ) / n;
      IntegralShell.stdOutPrintf( "n = % 8d\n", n );
      IntegralShell.stdOutPrintf( "h = % 5.2e\n", hstep );
      // IntegralShell.stdOutPrintf( "+---------------+---------------+---------------+-----------+-----------+\n" );
      // IntegralShell.stdOutPrintf( "| a_i           | b_i           | x_i           | f(x_i)    | sum       |\n" );
      for ( int i = 1; i < n; ++i ) {
        double x = a + i * hstep;
        FunctionalValidator fv = new FunctionalValidator( fun, x, x + hstep );

        double fx;
        try {
           fx = fv.f( x );
        } catch ( Exception e ) {
          // IntegralShell.stdOutPrintf( "+---------------+---------------+---------------+-----------+-----------+\n" );
          sum = sum * hstep / 3;
          IntegralShell.stdOutPrintf( "∫ f( x ) dx ≈ %5.2e\n", sum );
          IntegralShell.stdOutPrintf( "Δ = %5.2e; Δ = %5.2e n = %d\n\n", Math.abs( sum - prevInt ), eps, n );
          throw e;
        }
        if ( !Double.isNaN( fx ) )
          sum += fx * ( 2 + 2 * ( i % 2 ) );
        else throw new IndeterminedFunctionValueException( a + i * hstep );
        // IntegralShell.stdOutPrintf( "| % 5.4e\t| % 5.4e\t| % 5.4e\t| % 5.2e\t| % 5.2e\t|\n", x, x + hstep, x, fx, sum );
      }
      // IntegralShell.stdOutPrintf( "+---------------+---------------+---------------+-----------+-----------+\n" );
      sum = sum * hstep / 3;
      IntegralShell.stdOutPrintf( "∫ f( x ) dx ≈ %5.2e\n", sum );
      IntegralShell.stdOutPrintf( "Δ = %5.2e; Δ = %5.2e; n = %d\n\n", Math.abs( sum - prevInt ), eps, n );
      n *= 2;
    } while ( Math.abs( sum - prevInt ) > eps );
    this.n = n / 2;
    return sum;
  }
}
