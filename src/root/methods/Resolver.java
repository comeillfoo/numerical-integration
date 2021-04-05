package root.methods;

import root.xceptions.IndeterminedArgumentValueException;
import root.xceptions.IndeterminedFunctionValueException;
import root.xceptions.SecondKindBreakPointException;

import java.util.function.Function;

public interface Resolver {
  double solve( double prevInt, Function<Double, Double> fun, double a, double b, double eps, int n ) throws IndeterminedArgumentValueException, SecondKindBreakPointException, IndeterminedFunctionValueException;
}
