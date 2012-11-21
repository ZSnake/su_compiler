package su_compiler;

/* The following code was generated by JFlex 1.4.3 on 21/11/12 10:18 */

import java_cup.runtime.*;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 21/11/12 10:18 from the specification file
 * <tt>C:/Users/Solid Snake/Documents/EntregaProyecto_10911203_10911246/su_compiler/Material Extra/lexterAdaSemantic.flex</tt>
 */
class lexerSemantic implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 4;
  public static final int YYINITIAL = 0;
  public static final int COMMENTS = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\4\1\13\1\14\1\14\1\12\22\0\1\17\1\0\1\11"+
    "\5\0\1\15\1\16\1\45\1\44\1\5\1\10\1\1\1\45\12\47"+
    "\1\6\1\2\1\46\1\7\1\46\2\0\1\40\1\32\1\26\1\34"+
    "\1\30\1\25\1\33\1\42\1\20\2\50\1\37\1\50\1\21\1\22"+
    "\1\35\1\50\1\27\1\31\1\24\1\23\1\50\1\41\1\43\1\36"+
    "\1\50\4\0\1\3\1\0\1\40\1\32\1\26\1\34\1\30\1\25"+
    "\1\33\1\42\1\20\2\50\1\37\1\50\1\21\1\22\1\35\1\50"+
    "\1\27\1\31\1\24\1\23\1\50\1\41\1\43\1\36\1\50\12\0"+
    "\1\14\u1fa2\0\1\14\1\14\udfd6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\5\1\13\1\14\16\15\1\11"+
    "\1\16\1\10\1\17\2\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\2\15\1\27\1\30\24\15\1\0\2\15"+
    "\1\31\3\15\1\32\4\15\1\33\4\15\1\34\1\35"+
    "\5\15\1\0\2\15\1\36\1\37\1\40\4\15\1\41"+
    "\1\42\3\15\1\43\2\15\1\44\1\0\1\45\2\15"+
    "\1\46\1\15\1\47\1\15\1\50\1\15\1\51\1\52"+
    "\1\45\2\15\1\53\2\15\1\54\1\15\1\55\1\15"+
    "\1\56\1\15\1\57";

  private static int [] zzUnpackAction() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\51\0\122\0\173\0\244\0\173\0\173\0\173"+
    "\0\173\0\315\0\173\0\366\0\173\0\u011f\0\173\0\173"+
    "\0\u0148\0\u0171\0\u019a\0\u01c3\0\u01ec\0\u0215\0\u023e\0\u0267"+
    "\0\u0290\0\u02b9\0\u02e2\0\u030b\0\u0334\0\u035d\0\173\0\173"+
    "\0\u0386\0\u03af\0\u03d8\0\173\0\173\0\173\0\173\0\u0401"+
    "\0\u01c3\0\u01c3\0\u042a\0\u0453\0\u01c3\0\u01c3\0\u047c\0\u04a5"+
    "\0\u04ce\0\u04f7\0\u0520\0\u0549\0\u0572\0\u059b\0\u05c4\0\u05ed"+
    "\0\u0616\0\u063f\0\u0668\0\u0691\0\u06ba\0\u06e3\0\u070c\0\u0735"+
    "\0\u075e\0\u0787\0\u07b0\0\u07d9\0\u0802\0\u01c3\0\u082b\0\u0854"+
    "\0\u087d\0\u01c3\0\u08a6\0\u08cf\0\u08f8\0\u0921\0\u01c3\0\u094a"+
    "\0\u0973\0\u099c\0\u09c5\0\u01c3\0\u01c3\0\u09ee\0\u0a17\0\u0a40"+
    "\0\u0a69\0\u0a92\0\u0abb\0\u0ae4\0\u0b0d\0\u01c3\0\u01c3\0\u01c3"+
    "\0\u0b36\0\u0b5f\0\u0b88\0\u0bb1\0\u01c3\0\u01c3\0\u0bda\0\u0c03"+
    "\0\u0c2c\0\u01c3\0\u0c55\0\u0c7e\0\u01c3\0\u0ca7\0\u01c3\0\u0cd0"+
    "\0\u0cf9\0\u01c3\0\u0d22\0\u01c3\0\u0d4b\0\u01c3\0\u0d74\0\u01c3"+
    "\0\u01c3\0\173\0\u0d9d\0\u0dc6\0\u01c3\0\u0def\0\u0e18\0\u01c3"+
    "\0\u0e41\0\u01c3\0\u0e6a\0\u01c3\0\u0e93\0\u01c3";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\2\10\1\17\1\20\1\10\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\1\24\1\27\1\30"+
    "\1\24\1\31\1\32\1\24\1\33\1\24\1\34\1\35"+
    "\1\36\2\24\1\37\1\40\1\41\1\42\1\24\12\10"+
    "\1\43\2\44\45\10\1\44\1\10\1\0\35\10\52\0"+
    "\1\45\56\0\1\46\51\0\1\47\53\0\1\10\40\0"+
    "\1\24\14\0\1\24\1\50\3\24\1\51\3\24\1\52"+
    "\12\24\3\0\2\24\3\0\1\24\14\0\2\24\1\53"+
    "\21\24\3\0\2\24\3\0\1\24\14\0\3\24\1\54"+
    "\1\24\1\55\1\24\1\56\14\24\3\0\2\24\3\0"+
    "\1\24\14\0\24\24\3\0\2\24\3\0\1\24\14\0"+
    "\7\24\1\57\6\24\1\60\3\24\1\61\1\24\3\0"+
    "\2\24\3\0\1\24\14\0\2\24\1\62\1\63\13\24"+
    "\1\64\1\65\3\24\3\0\2\24\3\0\1\24\14\0"+
    "\10\24\1\66\13\24\3\0\2\24\3\0\1\24\14\0"+
    "\1\24\1\67\15\24\1\70\3\24\1\71\3\0\2\24"+
    "\3\0\1\24\14\0\2\24\1\72\5\24\1\73\13\24"+
    "\3\0\2\24\3\0\1\24\14\0\10\24\1\74\13\24"+
    "\3\0\2\24\3\0\1\24\14\0\3\24\1\75\3\24"+
    "\1\76\14\24\3\0\2\24\3\0\1\24\14\0\2\24"+
    "\1\77\21\24\3\0\2\24\3\0\1\24\14\0\1\24"+
    "\1\100\5\24\1\101\14\24\3\0\2\24\3\0\1\24"+
    "\14\0\22\24\1\102\1\24\3\0\2\24\7\0\1\13"+
    "\110\0\1\42\14\0\1\44\40\0\1\24\13\0\1\103"+
    "\2\24\1\104\1\24\1\105\17\24\3\0\2\24\3\0"+
    "\1\24\14\0\4\24\1\56\17\24\3\0\2\24\3\0"+
    "\1\24\14\0\4\24\1\106\17\24\3\0\2\24\3\0"+
    "\1\24\14\0\3\24\1\107\20\24\3\0\2\24\3\0"+
    "\1\24\14\0\15\24\1\110\6\24\3\0\2\24\3\0"+
    "\1\24\14\0\10\24\1\111\13\24\3\0\2\24\3\0"+
    "\1\24\14\0\7\24\1\112\14\24\3\0\2\24\3\0"+
    "\1\24\14\0\1\24\1\113\22\24\3\0\2\24\3\0"+
    "\1\24\14\0\2\24\1\114\21\24\3\0\2\24\3\0"+
    "\1\24\14\0\17\24\1\115\4\24\3\0\2\24\3\0"+
    "\1\24\14\0\4\24\1\116\17\24\3\0\2\24\3\0"+
    "\1\24\14\0\14\24\1\117\7\24\3\0\2\24\3\0"+
    "\1\24\14\0\11\24\1\120\12\24\3\0\2\24\3\0"+
    "\1\24\14\0\1\121\23\24\3\0\2\24\3\0\1\24"+
    "\14\0\2\24\1\122\21\24\3\0\2\24\3\0\1\24"+
    "\14\0\13\24\1\123\10\24\3\0\2\24\3\0\1\24"+
    "\14\0\4\24\1\124\17\24\3\0\2\24\3\0\1\24"+
    "\14\0\4\24\1\125\17\24\3\0\2\24\3\0\1\24"+
    "\14\0\2\24\1\126\21\24\3\0\2\24\3\0\1\24"+
    "\14\0\2\24\1\127\21\24\3\0\2\24\3\0\1\24"+
    "\14\0\14\24\1\56\7\24\3\0\2\24\3\0\1\24"+
    "\14\0\7\24\1\130\14\24\3\0\2\24\3\0\1\24"+
    "\14\0\1\131\7\24\1\132\13\24\3\0\2\24\17\0"+
    "\1\103\2\0\1\133\31\0\1\24\14\0\3\24\1\134"+
    "\20\24\3\0\2\24\3\0\1\24\14\0\10\24\1\135"+
    "\13\24\3\0\2\24\3\0\1\24\14\0\10\24\1\136"+
    "\13\24\3\0\2\24\3\0\1\24\14\0\10\24\1\137"+
    "\13\24\3\0\2\24\3\0\1\24\14\0\1\24\1\140"+
    "\22\24\3\0\2\24\3\0\1\24\14\0\6\24\1\141"+
    "\15\24\3\0\2\24\3\0\1\24\14\0\20\24\1\142"+
    "\3\24\3\0\2\24\3\0\1\24\14\0\11\24\1\107"+
    "\12\24\3\0\2\24\3\0\1\24\14\0\3\24\1\143"+
    "\20\24\3\0\2\24\3\0\1\24\14\0\1\144\7\24"+
    "\1\145\13\24\3\0\2\24\3\0\1\24\14\0\4\24"+
    "\1\146\17\24\3\0\2\24\3\0\1\24\14\0\17\24"+
    "\1\147\4\24\3\0\2\24\3\0\1\24\14\0\1\150"+
    "\23\24\3\0\2\24\3\0\1\24\14\0\6\24\1\151"+
    "\15\24\3\0\2\24\3\0\1\24\14\0\15\24\1\152"+
    "\6\24\3\0\2\24\3\0\1\24\14\0\20\24\1\153"+
    "\3\24\3\0\2\24\3\0\1\24\14\0\17\24\1\154"+
    "\4\24\3\0\2\24\3\0\1\24\14\0\1\24\1\155"+
    "\22\24\3\0\2\24\23\0\1\156\30\0\1\24\14\0"+
    "\4\24\1\157\17\24\3\0\2\24\3\0\1\24\14\0"+
    "\13\24\1\160\10\24\3\0\2\24\3\0\1\24\14\0"+
    "\4\24\1\161\17\24\3\0\2\24\3\0\1\24\14\0"+
    "\4\24\1\162\17\24\3\0\2\24\3\0\1\24\14\0"+
    "\7\24\1\163\14\24\3\0\2\24\3\0\1\24\14\0"+
    "\5\24\1\164\16\24\3\0\2\24\3\0\1\24\14\0"+
    "\10\24\1\165\13\24\3\0\2\24\3\0\1\24\14\0"+
    "\1\24\1\166\22\24\3\0\2\24\3\0\1\24\14\0"+
    "\10\24\1\167\13\24\3\0\2\24\3\0\1\24\14\0"+
    "\16\24\1\170\5\24\3\0\2\24\3\0\1\24\14\0"+
    "\10\24\1\171\13\24\3\0\2\24\24\0\1\172\27\0"+
    "\1\24\14\0\10\24\1\173\13\24\3\0\2\24\3\0"+
    "\1\24\14\0\1\174\23\24\3\0\2\24\3\0\1\24"+
    "\14\0\1\24\1\175\22\24\3\0\2\24\3\0\1\24"+
    "\14\0\20\24\1\176\3\24\3\0\2\24\3\0\1\24"+
    "\14\0\14\24\1\177\7\24\3\0\2\24\3\0\1\24"+
    "\14\0\7\24\1\200\14\24\3\0\2\24\3\0\1\24"+
    "\14\0\2\24\1\201\21\24\3\0\2\24\3\0\1\24"+
    "\14\0\1\24\1\202\22\24\3\0\2\24\3\0\1\24"+
    "\14\0\3\24\1\203\20\24\3\0\2\24\3\0\1\24"+
    "\14\0\1\24\1\204\22\24\3\0\2\24\3\0\1\24"+
    "\14\0\7\24\1\205\14\24\3\0\2\24\3\0\1\24"+
    "\14\0\10\24\1\206\13\24\3\0\2\24";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3772];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\1\1\4\11\1\1\1\11\1\1\1\11"+
    "\1\1\2\11\16\1\2\11\3\1\4\11\33\1\1\0"+
    "\27\1\1\0\22\1\1\0\13\1\1\11\14\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  StringBuffer string = new StringBuffer();
  String erroresLexicos = "";
  int contadorErroresLexicos = 0;
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, String texto, int yyline, int yycolumn) {
    return new Symbol(type, new Token (type, texto, yyline, yycolumn));
  }
  


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  lexerSemantic(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  lexerSemantic(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 170) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 47: 
          { //System.out.println("procedureTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PROCEDURETOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 48: break;
        case 46: 
          { //System.out.println("funcionTok, columna: " + yycolumn+ " fila: " + yyline);
		return symbol(sym.FUNCIONTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 49: break;
        case 21: 
          { //System.out.println("ifTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.IFTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 50: break;
        case 45: 
          { //System.out.println("booleanTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BOOLEANTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 51: break;
        case 7: 
          { //System.out.println("anotation, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ANOTATION, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 52: break;
        case 17: 
          { //System.out.println("rangeTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.RANGETOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 53: break;
        case 28: 
          { //System.out.println("GET, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.GETTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 54: break;
        case 29: 
          { //System.out.println("PUT, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PUTTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 55: break;
        case 13: 
          { //System.out.println("identifier, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.IDENTIFIER, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 56: break;
        case 24: 
          { //System.out.println("opBool, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPBOOL, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 57: break;
        case 2: 
          { //System.out.println("pointTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.POINTTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 58: break;
        case 25: 
          { //	System.out.println("outTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OUTTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 59: break;
        case 34: 
          { //System.out.println("Exit, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.EXITTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 60: break;
        case 22: 
          { //System.out.println("isTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ISTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 61: break;
        case 9: 
          { //System.out.println("opSuma, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPSUMA, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 62: break;
        case 44: 
          { //System.out.println("integerTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INTEGERTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 63: break;
        case 16: 
          { yybegin(YYINITIAL);
          }
        case 64: break;
        case 35: 
          { //System.out.println("loop, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.LOOPTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 65: break;
        case 12: 
          { //System.out.println("Parentesis Derecho, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PARDER, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 66: break;
        case 8: 
          { //System.out.println("opRelacional, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPRELACIONAL, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 67: break;
        case 11: 
          { //System.out.println("Parentesis Izquierdo, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PARIZQ, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 68: break;
        case 32: 
          { //System.out.println("thenTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.THENTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 69: break;
        case 36: 
          { //System.out.println("When, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.WHENTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 70: break;
        case 14: 
          { //System.out.println("opMult, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPMULT, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 71: break;
        case 4: 
          { //System.out.println("underscore, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.UNDERSCORE, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 72: break;
        case 6: 
          { //System.out.println("comaTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.COMATOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 73: break;
        case 41: 
          { //System.out.println("Array, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ARRAYTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 74: break;
        case 38: 
          { //System.out.println("floatTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.FLOATTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 75: break;
        case 40: 
          { //System.out.println("beginTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BEGINTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 76: break;
        case 19: 
          { //System.out.println("commentInit, columna: " + yycolumn + " fila: " + yyline);
		yybegin(COMMENTS);
          }
        case 77: break;
        case 27: 
          { //System.out.println("endTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ENDTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 78: break;
        case 33: 
          { //System.out.println("elseTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ELSETOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 79: break;
        case 42: 
          { //System.out.println("whileTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.WHILETOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 80: break;
        case 15: 
          { //System.out.println("Numero, columna: " + yycolumn + "fila: " + yyline);
		return symbol(sym.NUMBER, yytext(),  yyline + 1 , yycolumn + 1);
          }
        case 81: break;
        case 1: 
          { erroresLexicos += "Entrada Inesperada: " + yytext() + ", columna: " + yycolumn + "fila: " + yyline + "\n";
		contadorErroresLexicos++;
          }
        case 82: break;
        case 39: 
          { //System.out.println("elsifTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ELSIFTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 83: break;
        case 30: 
          { //System.out.println("booleanValue, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BOOLEANVALUE, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 84: break;
        case 10: 
          { yybegin(STRING);
          }
        case 85: break;
        case 43: 
          { //System.out.println("returnTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.RETURNTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 86: break;
        case 3: 
          { //System.out.println("endLineTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ENDLINE, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 87: break;
        case 31: 
          { //System.out.println("typeTok, columna: " + yycolumn+ " fila: " + yyline);
		return symbol(sym.TYPETOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 88: break;
        case 26: 
          { //System.out.println("forTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.FORTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 89: break;
        case 23: 
          { //System.out.println("Of, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OFTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 90: break;
        case 37: 
          { //System.out.println("inOutTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INOUTTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 91: break;
        case 20: 
          { //System.out.println("inTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INTOK, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 92: break;
        case 18: 
          { //System.out.println("asignation, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ASIGNATION, yytext(), yyline + 1 , yycolumn + 1);
          }
        case 93: break;
        case 5: 
          { 
          }
        case 94: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
