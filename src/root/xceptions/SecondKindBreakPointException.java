package root.xceptions;

public final class SecondKindBreakPointException extends Exception {
  
  public SecondKindBreakPointException( double x, double fx ) {
    super( String.format( "Обнаружена точка ( неустранимого ) разрыва второго рода: B( %f, %f )", x, fx ) );
  }
}
