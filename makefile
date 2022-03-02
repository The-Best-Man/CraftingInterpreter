LSRCS := Expr.java Stmt.java Lox.java Token.java TokenType.java Parser.java Scanner.java Return.java Resolver.java LoxCallable.java LoxFunction.java RuntimeError.java Environment.java LoxClass.java LoxInstance.java
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
