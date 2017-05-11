grammar CSV													;
r		: NEWLINE* row+										;
row		: col (',' col?)* (NEWLINE+ | EOF)					;
col		: CHAR												;
CHAR	: ~[,\r\n]+											;
NEWLINE	: [\r\n]											;
