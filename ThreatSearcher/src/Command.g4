/**
 * Define a grammar called Hello
 */
grammar Command;
r : state_commands | commands;         // match keyword of possible commands and input
NUMS : ('0'..'9')+;
LOWER		: [a-z]												;
UPPER		: [A-Z]												;
NUMBER		: [0-9]												;
abr : 'A'|'T'|'TL'|'G'|'SP'|'IP' | 'D' | 'SU' | 'SP' | 'DP' | 'URL' | 'CA';
input : ( LOWER | UPPER | NUMBER )+;             // match lower-case identifiers
commands : ('FINDROW' NUMS '-') | ('FINDCOL' abr ('-' | (input '-')) | ('FILTER' input '-'));
state_commands : 'HOME' | 'BACK';

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

