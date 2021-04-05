package root;

import root.methods.NumericalIntegrationMethods;
import utilities.Shell;

import java.io.InputStream;
import java.io.PrintStream;

public final class IntegralShell extends Shell {
  public IntegralShell( InputStream in, PrintStream out, PrintStream err ) {
    super( in, out, err );
  }

  public static NumericalIntegrationMethods readMethod() {
    Integer method = readMenu( "Выберите метод вычисления интеграла:", NumericalIntegrationMethods.methods() );
    return NumericalIntegrationMethods.values()[ method ];
  }

  public static Double[] readLimitsAndError() {
    return readDoubles( "Введите параметры", "a", "b", "eps" );
  }

}
