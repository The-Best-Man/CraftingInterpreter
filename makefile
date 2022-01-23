LSRCS := AstPrinter.java Expr.java Stmt.java Lox.java Token.java TokenType.java Parser.java Scanner.java
TSRCS := GenerateAst.java

TOOlDIR := ./tool
LOXDIR := lox

LSRCS := $(addprefix $(LOXDIR)/, $(LSRCS))

OUT := Lox.class

$(OUT) : $(LSRCS)
	javac $^

.PHONY: run

run : $(OUT)
	java -cp . $(addprefix lox/, $(patsubst %.class, %, $(OUT)))
