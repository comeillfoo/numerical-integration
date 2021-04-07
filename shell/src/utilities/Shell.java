package utilities;

import utilities.functions.Functions;

import java.io.*;
import java.util.*;

public class Shell implements AutoCloseable, Closeable {
  protected static final InputStream stdin = System.in;
  protected static final PrintStream stdout = System.out;
  protected static final PrintStream stderr = System.err;
  protected static final Scanner stdscn = new Scanner( stdin );

  protected final Scanner ctmin;
  protected final PrintStream ctmout;
  protected final PrintStream ctmerr;

  public Shell( InputStream in, OutputStream out, PrintStream err ) {
    ctmin = new Scanner( in );
    ctmout = new PrintStream( out );
    ctmerr = err;
  }

  public static void stdOutPrint( String arg ) {
    stdout.print( arg );
  }

  public static void stdErrPrint( String arg ) {
    stderr.print( arg );
  }

  public static void stdOutPrintLn( String arg ) {
    stdout.println( arg );
  }

  public static void stdErrPrintLn( String arg ) {
    stderr.println( arg );
  }

  public static void stdOutPrintf( String format, Object...args ) {
    stdout.printf( format, args );
  }

  public static void stdErrPrintf( String format, Object... args ) {
    stderr.printf( format, args );
  }

  public void print( String arg ) {
    ctmout.print( arg );
  }

  public void println( String arg ) {
    ctmout.println( arg );
  }

  public void printf( String format, Object...args ) {
    ctmout.printf( format, args );
  }

  public static OptionalInt readInt() {
    Integer value = null;
    try {
      while ( value == null ) {
        stdOutPrint( "> " );
        String str = stdscn.nextLine();
        try {
          value = Integer.parseInt( str );
        } catch ( NumberFormatException e ) {
          stdErrPrintLn( "Формат не соответствует заданному типу" );
        }
      }
    } catch ( NoSuchElementException e ) {
      stdErrPrintLn( "Поток данных поврежден." );
      return OptionalInt.empty();
    }
    return OptionalInt.of( value );
  }

  public static OptionalDouble readDouble() {
    Double value = null;
    try {
      while ( value == null ) {
        String str = stdscn.nextLine();
        try {
          value = Double.parseDouble( str );
        } catch ( NumberFormatException e ) {
          stdErrPrintLn( "Формат не соответствует заданному типу" );
        }
      }
    } catch ( NoSuchElementException e ) {
      stdErrPrintLn( "Поток данных поврежден." );
      return OptionalDouble.empty();
    }
    return OptionalDouble.of( value );
  }

  public static void printMenu( String header, String... items ) {
    stdOutPrintLn( header );
    for ( int i = 0; i < items.length; ++i )
      stdOutPrintLn( "[ " + ( i + 1 ) + " ] " + items[ i ] );
    stdOutPrintLn( "[ X ] Выйти" );
  }

  public static OptionalInt readItem( String... items ) {
    OptionalInt maybe = readInt();
    if ( maybe.isPresent() )
      if ( maybe.getAsInt( ) <= 0 || maybe.getAsInt( ) > items.length ) {
        stdOutPrintLn( "Осуществляем выход." );
        System.exit( 1 );
        return OptionalInt.empty( );
      }
    return maybe;
  }

  public static Optional<String> readLine() {
    String value = null;
    try {
      while ( value == null ) {
        stdOutPrint( "> " );
        value = stdscn.nextLine();
      }
    } catch ( NoSuchElementException e ) {
      stdErrPrintLn( "Поток данных поврежден." );
      stdOutPrintLn( "Осуществляем выход." );
      System.exit( 1 );
      return Optional.empty();
    }
    if ( value.isEmpty() )
      return Optional.empty();
    return Optional.of( value );
  }

  public static boolean readProposal() {
    stdOutPrint( "Подтвердить [ y/n ]: " );
    Optional<String> value = Optional.empty();
    try {
      while ( !value.isPresent() ) {
        value = readLine();
      }
    } catch ( NoSuchElementException e ) {
      stdErrPrintLn( "Поток данных поврежден." );
      return true;
    }
    if ( "y".equalsIgnoreCase( String.valueOf( value.get().charAt( 0 ) ) ) )
      return true;
    else return false;
  }

  public static Integer readMenu( String header, String... items ) {
    OptionalInt item;
    while ( true ) {
      printMenu( header, items );
      item = readItem( items );
      if ( item.isPresent() ) {
        stdOutPrintLn( "Выбрана опция [ " + items[ item.getAsInt() - 1 ] + " ]." );
        boolean prompt = readProposal();
        if ( prompt )
          return item.getAsInt() - 1;
        else item = OptionalInt.empty();
      } else {
        stdOutPrintLn( "Осуществляем выход." );
        System.exit( 1 );
        return 0;
      }
    }
  }

  public static Functions readFunction() {
    Integer function = readMenu( "Выберите функцию для интегрирования:", Functions.functions() );
    return Functions.values()[ function ];
  }



  public static Double[] readDoubles( String header, String... parameters ) {
    Double[] doubles = null;
    int number = parameters.length;
    try {
      String line;
      while ( doubles == null ) {
        stdOutPrint( header + "[ " );
        for ( String param : parameters )
          stdOutPrint( param + ", " );
        stdOutPrint( "]: " );
        line = stdscn.nextLine( );
        if ( !line.isEmpty() ) {
          String[] numbers = line.split( " " );
          if ( numbers.length >= number ) {
            Double[] maybe = new Double[ number ];
            for ( int i = 0; i < number; ++i ) {
              try {
                maybe[ i ] = Double.parseDouble( numbers[ i ] );
              } catch ( NumberFormatException e ) {
                stdOutPrintLn( "Формат не соответствует заданному типу" );
                maybe = null;
                break;
              }
            }
            doubles = maybe;
          }
        }
      }
      return doubles;
    } catch ( NoSuchElementException e ) {
      stdErrPrintLn( "Поток данных поврежден." );
      stdOutPrintLn( "Осуществляем выход." );
      System.exit( 1 );
      return new Double[0];
    }
  }

  public static Double[] readLimitsAndError() {
    return readDoubles( "Введите параметры", "a", "b", "eps" );
  }

  @Override
  public void close( ) throws IOException {
    ctmin.close();
    ctmout.close();
    ctmerr.close();
  }
}
