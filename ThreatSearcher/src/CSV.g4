grammar CSV														;
r			: NEWLINE* row+										;
row			: STRING (',' STRING?)* (NEWLINE+ | EOF)			;
STRING		: CHAR+												;
CHAR		: (LOWER | UPPER | NUMBER | WHITESPACE | SYMBOLS)	;
LOWER		: [a-z]												;
UPPER		: [A-Z]												;
NUMBER		: [0-9]												;
WHITESPACE	: [ \t]												;
SYMBOLS		: [$%>{}+@()\\/:_#=.;?-]							;
NEWLINE		: [\r\n]											;
