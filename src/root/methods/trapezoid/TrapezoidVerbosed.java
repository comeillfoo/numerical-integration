package root.methods.trapezoid;

import root.IntegralShell;
import root.methods.Resolver;
import root.validators.FunctionalValidator;
import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public final class TrapezoidVerbosed implements Resolver {
  @Override
  public int getN( ) {
    return n;
  }

  private int n = 0;

  @Override
  public double solve( double prevInt, Function<Double, Double> fun, double a, double b, double eps, int n ) throws IndeterminedArgumentValueException, SecondKindBreakPointException, IndeterminedFunctionValueException {
    IntegralShell.stdOutPrintf( "n_0 = % 8d\n", n );
    double sum = 0;
    do {
      prevInt = sum;
      sum = ( fun.apply( a ) + fun.apply( b ) ) / 2;
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
          sum = sum * hstep;
          IntegralShell.stdOutPrintf( "∫ f( x ) dx ≈ %5.8e\n", sum );
          IntegralShell.stdOutPrintf( "Δ = %5.2e; Δ = %5.2e; n = %d\n\n", Math.abs( sum - prevInt ), eps, n );
          throw e;
        }
        if ( !Double.isNaN( fx ) )
          sum += fx;
        else throw new IndeterminedFunctionValueException( x );
        // IntegralShell.stdOutPrintf( "| % 5.4e\t| % 5.4e\t| % 5.4e\t| % 5.2e\t| % 5.2e\t|\n", x, x + hstep, x, fx, sum );
      }
      // IntegralShell.stdOutPrintf( --------------+---------------+---------------+-----------+-----------+\n" );
      sum = sum * hstep;
      IntegralShell.stdOutPrintf( "∫ f( x ) dx ≈ %5.8e\n", sum );
      IntegralShell.stdOutPrintf( "Δ = %5.2e; Δ = %5.2e; n = %d\n\n", Math.abs( sum - prevInt ), eps, n );
      n *= 2;
    } while ( Math.abs( sum - prevInt ) > eps );
    this.n = n / 2;
    return sum;
  }
}
