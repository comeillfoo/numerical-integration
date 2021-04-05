package root.validators;
import utilities.functions.Parity;

public class LimitsValidator {
  private Double a;
  private Double b;
  private Parity parity;
  private double k = 1;

  public LimitsValidator( double a, double b, Parity parity ) {
    this.a = a;
    this.b = b;
    this.parity = parity;
    validate();
  }

  private void validate() {
    if ( a == -b && parity == Parity.EVEN ) {
      a = 0.0;
      b = Math.abs( b );
      k = 2;
    } else if ( a == -b && parity == Parity.ODD )
      k = 0;
    else if ( a > b ) {
      Double tmp = a;
      a = b;
      b = tmp;
    }
  }

  public Double getLeftLimit() {
    return a;
  }

  public Double getRightLimit() {
    return b;
  }

  public Double getK() {
    return k;
  }
}
