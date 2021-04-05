package root.xceptions;

public class IndeterminedFunctionValueException extends Exception {
  public IndeterminedFunctionValueException( double x ) {
    super( String.format( "Неопределенное значение функции в точке [ %f ]", x ) );
  }
}
