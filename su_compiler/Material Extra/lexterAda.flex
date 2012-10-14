import java_cup.runtime.*;
%%
%unicode
%class lexer
%int
%caseless 
%ignorecase
%line
%column
%state COMMENTS, STRING
%cup

%{
  StringBuffer string = new StringBuffer();
  String erroresLexicos = "";
  int contadorErroresLexicos = 0;
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, String texto, int yyline, int yycolumn) {
    return new Symbol(type, new Token (type, texto, yyline, yycolumn));
  }
  
%}

// ---------------> simbolos
pointTok = "."
endLineTok = ";"
underscore = "_"
tab = "\t"
comaTok = ","
asignation = := 
anotation = ":"
commentInit = "--"
comillasTok = \"
lineBreak = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085
parIzq = "("
parDer = ")"
espacio = " " 
//-------------->Tipo Parametro
inTok = "in"
outTok = "out"
inOutTok = {inTok}{espacio}*{outTok}

//-------------------->Functions y procedures
funcionTok = function
returnTok = return
isTok = is
beginTok = begin
endTok = end
procedureTok = procedure
getTok = "get"
putTok = "put"

//------------------------->Tipos de dato y declaraciones
typeTok = type
integerTok = integer
booleanTok = boolean
floatTok = float
arrayTok = array
ofTok = of

//------------------------->Ciclos
whileTok = while
forTok = for
loop = loop
rangeTok = {pointTok}{pointTok}
exitTok = exit
whenTok = when
//------------------------>Decision
ifTok = if
thenTok = then
elsifTok = elsif
elseTok = else

//--------------------->Operadores 
opSuma = "+" | "-"
opMult = "*" | "/" 
opRelacional = ">" | ">=" | "<=" | "<" | "="
opBool = and | or | not
booleanValue = true | false

//------------------>General
digit = [0-9]
letter =  [a-zA-Z]
number = {digit}+
identifier = {letter}({digit} | {letter} | {underscore})*
 

%%
<YYINITIAL>{
	{pointTok}	{
		//System.out.println("pointTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.POINTTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{endLineTok}	{
		//System.out.println("endLineTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ENDLINE, yytext(), yyline + 1 , yycolumn + 1);
	}
	{underscore}	{
		//System.out.println("underscore, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.UNDERSCORE, yytext(), yyline + 1 , yycolumn + 1);
	}
	{comaTok}	{
		//System.out.println("comaTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.COMATOK, yytext(), yyline + 1 , yycolumn + 1);
	
	}
	{asignation}	{
		//System.out.println("asignation, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ASIGNATION, yytext(), yyline + 1 , yycolumn + 1);
	}
	{anotation}	{
		//System.out.println("anotation, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ANOTATION, yytext(), yyline + 1 , yycolumn + 1);
	}
	{commentInit}	{
		//System.out.println("commentInit, columna: " + yycolumn + " fila: " + yyline);
		yybegin(COMMENTS);
	}
	{lineBreak}	{
	
	}
	{parIzq}	{
		//System.out.println("Parentesis Izquierdo, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PARIZQ, yytext(), yyline + 1 , yycolumn + 1);
	}
	
	{parDer}	{
		//System.out.println("Parentesis Derecho, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PARDER, yytext(), yyline + 1 , yycolumn + 1);
	}
	{inTok}	{
		//System.out.println("inTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{outTok}	{
	//	System.out.println("outTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OUTTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{inOutTok}	{
		//System.out.println("inOutTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INOUTTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{funcionTok}	{
		//System.out.println("funcionTok, columna: " + yycolumn+ " fila: " + yyline);
		return symbol(sym.FUNCIONTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{returnTok}	{
		//System.out.println("returnTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.RETURNTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{isTok}	{
		//System.out.println("isTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ISTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{beginTok}	{
		//System.out.println("beginTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BEGINTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{endTok}	{
		//System.out.println("endTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ENDTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{procedureTok}	{
		//System.out.println("procedureTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PROCEDURETOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{typeTok}	{
		//System.out.println("typeTok, columna: " + yycolumn+ " fila: " + yyline);
		return symbol(sym.TYPETOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{integerTok}	{
		//System.out.println("integerTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.INTEGERTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{booleanTok}	{
		//System.out.println("booleanTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BOOLEANTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{floatTok}	{
		//System.out.println("floatTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.FLOATTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{whileTok}	{
		//System.out.println("whileTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.WHILETOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{forTok}	{
		//System.out.println("forTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.FORTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{loop}	{
		//System.out.println("loop, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.LOOPTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{rangeTok}	{
		//System.out.println("rangeTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.RANGETOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{ifTok}	{
		//System.out.println("ifTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.IFTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{thenTok}	{
		//System.out.println("thenTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.THENTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{elsifTok}	{
		//System.out.println("elsifTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ELSIFTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{elseTok}	{
		//System.out.println("elseTok, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ELSETOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{opSuma}	{
		//System.out.println("opSuma, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPSUMA, yytext(), yyline + 1 , yycolumn + 1);
	}
	{opMult}	{
		//System.out.println("opMult, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPMULT, yytext(), yyline + 1 , yycolumn + 1);
	}
	{opRelacional}	{
		//System.out.println("opRelacional, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPRELACIONAL, yytext(), yyline + 1 , yycolumn + 1);
	}
	{opBool}	{
		//System.out.println("opBool, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OPBOOL, yytext(), yyline + 1 , yycolumn + 1);
	}
	{booleanValue}	{
		//System.out.println("booleanValue, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.BOOLEANVALUE, yytext(), yyline + 1 , yycolumn + 1);
	}
	
	{getTok}	{
		//System.out.println("GET, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.GETTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{putTok}	{
		//System.out.println("PUT, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.PUTTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{arrayTok}	{
		//System.out.println("Array, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.ARRAYTOK, yytext(), yyline + 1 , yycolumn + 1);	
	}
	
	{exitTok} 	{
		//System.out.println("Exit, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.EXITTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{whenTok}	{
		//System.out.println("When, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.WHENTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{ofTok}		{
		//System.out.println("Of, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.OFTOK, yytext(), yyline + 1 , yycolumn + 1);
	}
	{comillasTok}	{
		yybegin(STRING);
	}
	{number}	{
		//System.out.println("Numero, columna: " + yycolumn + "fila: " + yyline);
		return symbol(sym.NUMBER, yytext(),  yyline + 1 , yycolumn + 1);
	}
	{identifier}	{
		//System.out.println("identifier, columna: " + yycolumn + " fila: " + yyline);
		return symbol(sym.IDENTIFIER, yytext(), yyline + 1 , yycolumn + 1);
	}
	{tab}			{}
	{espacio}		{}
	.	{
		erroresLexicos += "Entrada Inesperada: " + yytext() + ", columna: " + yycolumn + "fila: " + yyline + "\n";
		contadorErroresLexicos++;
	}
}
<COMMENTS> {
	{lineBreak}	{
		yybegin(YYINITIAL);
	}
	.	{}
}

<STRING> {
	{comillasTok}	{
		yybegin(YYINITIAL);
	}
	.	{}
}





