package root.validators;

import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public class FunctionalValidator implements Validator {

  private final Function<Double, Double> FUN;
  private final double LEFT;
  private final double RIGHT;
  private final double HSTEP;

  public FunctionalValidator( Function<Double, Double> fun, double left, double right ) {
    FUN = fun;
    LEFT = left;
    RIGHT = right;
    HSTEP = Double.MIN_VALUE;
  }

  public double f( double x ) throws IndeterminedArgumentValueException, SecondKindBreakPointException {
    // точка непрерывности
    if ( Double.isFinite( x ) && Double.isFinite( FUN.apply( x ) ) )
      return FUN.apply( x );
    // находим предел функции на бесконечности, для несобственного интеграла
    if ( Double.isInfinite( x ) ) {
      if ( Double.compare( x, Double.NEGATIVE_INFINITY ) == 0 && Double.compare( x, LEFT ) == 0 )
        x = -Double.MAX_VALUE;
      else if ( Double.compare( x, Double.POSITIVE_INFINITY ) == 0 && Double.compare( x, RIGHT ) == 0 )
        x = Double.MAX_VALUE;
      else return Double.NaN;
      while ( !Double.isFinite( FUN.apply( x ) ) )
        x /= 2;
      return FUN.apply( x );
      // в случае неопределенности
    } else if ( Double.isNaN( x ) )
      throw new IndeterminedArgumentValueException();
    // точка разрыва первого рода ( устранимая )
    double result;
    if ( Math.abs( FUN.apply( x - HSTEP ) - FUN.apply( x + HSTEP ) ) <= Double.MIN_VALUE )
      return ( FUN.apply( x - HSTEP ) + FUN.apply( x + HSTEP ) ) / 2;
    // иначе - точка разрыва второго рода ( неустранимая )
    else if ( Math.abs( x - LEFT ) <= Double.MIN_VALUE )
      result = FUN.apply( x + HSTEP );
    else if ( Math.abs( x - RIGHT ) <= Double.MIN_VALUE )
      result = FUN.apply( x - HSTEP );
    else throw new SecondKindBreakPointException( x, FUN.apply( x ) );

    if ( Double.isNaN( result ) || Double.isInfinite( result ) )
      throw new SecondKindBreakPointException( x, FUN.apply( x ) );
    else return result;
  }

  @Override
  public void validate( ) {}
}
