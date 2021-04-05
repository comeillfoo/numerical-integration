package root.methods;

import root.methods.rectangles.*;
import root.methods.simpson.Simpson;
import root.methods.simpson.SimpsonVerbosed;
import root.methods.trapezoid.Trapezoid;
import root.methods.trapezoid.TrapezoidVerbosed;

import java.util.Arrays;

public enum NumericalIntegrationMethods {
  SIMPSON( "Симпсона", new Simpson(), new SimpsonVerbosed() ),
  TRAPEZOID( "трапеций", new Trapezoid(), new TrapezoidVerbosed() ),
  RECTANGLE_LEFT( "прямоугольников ( левый )", new LeftRectangle(), new LeftRectangleVerbosed() ),
  RECTANGLE_RIGHT( "прямоугольников ( правый )", new RightRectangle(), new RightRectangleVerbosed() ),
  RECTANGLE_MEAN( "прямоугольников ( средний )", new MeanRectangle(), new MeanRectangleVerbosed() );

  private final String NAME;
  public final Resolver SOLVER;
  public final Resolver VERBOSED;

  NumericalIntegrationMethods( String name, Resolver solver, Resolver verbose ) {
    NAME = name;
    SOLVER = solver;
    VERBOSED = verbose;
  }

  @Override
  public String toString() {
    return NAME;
  }

  public static String[] methods() {
    return Arrays.stream( NumericalIntegrationMethods.values( ) ).map( ( m )->( "Метод " + m.toString() ) ).toArray( String[]::new );
  }
}
