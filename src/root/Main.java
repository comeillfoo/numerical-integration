package root;

import root.methods.Resolver;
import root.validators.InitialNValidator;
import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;
import utilities.functions.Functions;
import root.methods.NumericalIntegrationMethods;
import root.validators.ErrorValidator;
import root.validators.LimitsValidator;

import java.util.OptionalInt;
import java.util.function.Function;

public class Main {
  public static void main( String[] args ) {
    final NumericalIntegrationMethods method = IntegralShell.readMethod();
    final Functions function = IntegralShell.readFunction();
    final Double[] parameters = IntegralShell.readLimitsAndError();
    IntegralShell.stdOutPrintLn( "Введите начальное число разбиений [ 4 ]:" );
    final OptionalInt maybeN0 = IntegralShell.readInt();
    int n0 = InitialNValidator.INITIAL_N;
    if ( maybeN0.isPresent( ) )
      n0 = maybeN0.getAsInt( );
    else IntegralShell.stdOutPrintLn( "Не удалось прочитать начальное число разбиений. Выбрано значение по умолчанию + [ " + InitialNValidator.INITIAL_N + " ]" );
    InitialNValidator n0Valid = new InitialNValidator( n0 );
    if ( n0 != n0Valid.getN() ) {
      n0 = n0Valid.getN();
      IntegralShell.stdOutPrintLn( "Обнаружен невалидный параметр [ n ]. Автоматически заменен на " + n0 );
    }

    Double a = parameters[ 0 ];
    Double b = parameters[ 1 ];
    Double eps = parameters[ 2 ];
    LimitsValidator limiter = new LimitsValidator( a, b, function.PARITY );
    a = limiter.getLeftLimit();
    if ( Math.abs( parameters[ 0 ] - a ) >= Double.MIN_VALUE )
      IntegralShell.stdOutPrintLn( "Обнаружен оптимизируемый параметр [ a ]. Автоматически заменен на " + a );
    b = limiter.getRightLimit();
    if ( Math.abs( parameters[ 1 ] - b ) >= Double.MIN_VALUE )
      IntegralShell.stdOutPrintLn( "Обнаружен оптимизируемый параметр [ b ]. Автоматически заменен на " + b );
    eps = new ErrorValidator( eps ).getError();
    if ( Math.abs( parameters[ 2 ] - eps ) >= Double.MIN_VALUE )
      IntegralShell.stdOutPrintLn( "Обнаружен оптимизируемый параметр [ eps ]. Автоматически заменен на " + eps );
    final double k = limiter.getK();
    final Function<Double, Double> realFun = ( x )->( k * function.FUN.apply( x ) );
    final int option = IntegralShell.readMenu( "Нужна ли таблица с промежуточными вычислениями?", "Выводить таблицу", "Вывести только ответ" );
    double result;
    try {
      Resolver solver = ( option == 0 )? method.VERBOSED : method.SOLVER;
      result = ( k == 0 )? 0 : solver.solve( 0, realFun, a, b, eps, n0 );
      IntegralShell.stdOutPrintf( "%f∫%f (%s) dx = ( %f ± %f ) n = %d\n", parameters[ 0 ], parameters[ 1 ], function.TITLE, result, eps, solver.getN() );
    } catch ( SecondKindBreakPointException | IndeterminedArgumentValueException | IndeterminedFunctionValueException e ) {
      IntegralShell.stdErrPrintLn( e.getMessage() );
      IntegralShell.stdErrPrintLn( "Неинтегрируемая особенность подынтегрального выражения на интервале интегрирования" );
    }
  }
}