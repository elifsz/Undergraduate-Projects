%{
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <time.h>
int yylex();
void yyerror(const char *s);
%}
%token INTEGER
%%
program:
       program line | line
line:
    exp ';' { printf("%d\n",$1); } ; | '\n'
exp:
          exp '+' prio{ $$ = $1 + $3; } 
         |exp'-' prio{ $$ = $1 - $3; }
	 |prio
prio:    
          prio '*' highestprio{ $$ = $1 * $3; } 
         | prio '/' highestprio{ $$ = $1 / $3; } 
         | prio '%' highestprio{ $$ = $1 % $3; }  
      	 |highestprio
highestprio:
         highestprio '^' term{$$ = pow ($1 , $3); }
        | term { $$ = $1; }
term:
    INTEGER { $$ = $1; }
%%
void yyerror(const char *s) { fprintf(stderr,"%s\n",s); return ; }
int main(void) { /*yydebug=1;*/ yyparse(); return 0; }
