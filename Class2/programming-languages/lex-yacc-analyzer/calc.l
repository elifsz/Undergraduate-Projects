%{
#include <stdlib.h>
#include <stdio.h>
void yyerror(char*);
extern int yylval;
#include "calc.tab.h"
#include<time.h>
#include<math.h>
%}
%%
[ \t]+ ;
[ONE]* {yylval = 1; return INTEGER;}
[TWO]* {yylval = 2; return INTEGER;}
[THREE]* {yylval = 3; return INTEGER;}
[FOUR]* {yylval = 4; return INTEGER;}
[TEN]* {yylval = 10; return INTEGER;}
[MAGIC]* {srand(time(0)); yylval = rand()%100+10; return INTEGER;}
[0-9]+ {yylval = atoi(yytext); return INTEGER;}
[-/*+%^] {return *yytext;}
\n {return *yytext;}
; {return *yytext;}
. {char msg[25]; sprintf(msg,"%s <%s>","invalid character",yytext); yyerror(msg);}
%%
