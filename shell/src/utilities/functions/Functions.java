package utilities.functions;

import java.util.Arrays;
import java.util.function.Function;

public enum Functions {
  EXAMPLE( ( x )->( 5 * x * x * x - 2 * x * x + 7 * x - 14 ),
      "5x^3 - 2x^2 + 7x - 14",
      "$ 5x^3 - 2x^2 + 7x - 14 $",
      Parity.NOTHING
      ),
  FIRST( ( x )->( 1/x ),
      "1 / x",
      "$ \\frac{1}{x} $",
      Parity.ODD
  ),
  SECOND( ( x )->( Math.sin( 2 * x) / x ),
      "sin(2x) / x",
      "$ \\frac{\\sin{2x}}{x} $",
      Parity.EVEN
  ),
  THIRD( ( x )->( Math.tan( x ) ),
      "tg( x )",
      "$ \\tan{ x } $",
      Parity.ODD
  );

  public final Function<Double, Double> FUN;
  public final String TITLE;
  public final String MATH_TITLE;
  public final Parity PARITY;

  Functions( Function<Double, Double> fun, String title, String mathTitle, Parity parity ) {
    FUN = fun;
    TITLE = title;
    MATH_TITLE = mathTitle;
    PARITY = parity;
  }

  @Override
  public String toString() {
    return TITLE;
  }

  public static String[] functions() {
    return Arrays.stream( Functions.values( ) ).map( Functions::toString ).toArray( String[]::new );
  }
}
