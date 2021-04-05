package root.validators;

public class ErrorValidator implements Validator {
  private Double eps;

  public ErrorValidator( Double eps ) {
    this.eps = eps;
    validate();
  }


  @Override
  public void validate() {
    if ( !Double.isFinite( eps ) || eps - Double.MIN_VALUE <= 0 )
      eps = 0.01;
  }

  public Double getError() {
    return eps;
  }
}
