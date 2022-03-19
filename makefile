CSRCS := memory.c chunk.c main.c debug.c value.c vm.c 

SRCDIR := src
INCDIR := inc
OBJDIR := obj

CLSRCS := $(addprefix $(SRCDIR)/, $(CSRCS))
CLOBJS := $(addprefix $(OBJDIR)/, $(patsubst %.c, %.o, $(CSRCS)))

COUT := clox

CRGS := -I $(INCDIR)
LRGS :=

$(OBJDIR)/%.o : $(SRCDIR)/%.c | $(OBJDIR) 
	$(CC) -c $(CRGS) $< -o $@

$(COUT) : $(CLOBJS) | $(OBJDIR)
	$(CC) $(LRGS) $^ -o $@

$(OBJDIR):
	mkdir $(OBJDIR)

.PHONY: clean run

clean:
	rm -d -r -f $(CLOBJS) $(COUT) $(OBJDIR)

run:
	./clox

#JLOX SRCS
JLSRCS := Expr.java Stmt.java Lox.java Token.java TokenType.java Parser.java Scanner.java Return.java Resolver.java LoxCallable.java LoxFunction.java RuntimeError.java Environment.java LoxClass.java LoxInstance.java
JTSRCS := GenerateAst.java

JTOOlDIR := ./tool
JLOXDIR := lox

JLSRCS := $(addprefix $(JLOXDIR)/, $(JLSRCS)) 
JOUT := Lox.class

$(JOUT) : $(JLSRCS)
	javac $^

.PHONY: jclean jrun

jclean: 
	rm -d -r -f $(JLOXDIR)/*.class

jrun : $(JOUT)
	java -cp . $(addprefix lox/, $(patsubst %.class, %, $(JOUT)))
