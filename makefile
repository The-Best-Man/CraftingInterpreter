LSRCS := AstPrinter.java Expr.java Lox.java Token.java TokenType.java Parser.java Scanner.java
TSRCS := GenerateAst.java

TOOlDIR := ./tool
LOXDIR := ./lox

LSRCS := $(addprefix $(LOXDIR)/, $(LSRCS))

OUT := Lox

$(OUT) : $(LSRCS)
	javac $^

.PHONY: run

