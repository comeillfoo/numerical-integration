package root.xceptions;

public final class IndeterminedArgumentValueException extends Exception {
  public IndeterminedArgumentValueException() {
    super( "Неопределенное значение аргумента функции" );
  }
}
