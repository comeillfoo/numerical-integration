package root.validators;

public class InitialNValidator implements Validator {
  private int n;
  public static final int INITIAL_N = 4;

  public InitialNValidator( int n ) {
    this.n = n;
    validate();
  }

  public int getN() {
    return n;
  }

  @Override
  public void validate( ) {
    if ( n <= 0 )
      n = INITIAL_N;
  }
}
