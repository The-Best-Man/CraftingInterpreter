package lox;

import java.util.List;

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void>
{
	void interpret(List<Stmt> statements)
        {
                try
                {
                        for (Stmt statement : statements)
                        {
                                execute(statement);
                        }
                }
                catch (RuntimeError error)
                {
                        Lox.runtimeError(error);
                }
        }


        @Override
	public Object visitLiteralExpr(Expr.Literal expr)
        {
                return expr.value;
        }

        @Override
        public Object visitUnaryExpr(Expr.Unary expr)
        {
                Object right = evaluate(expr.right);

                switch (expr.operator.type)
                {
                        case BANG:
                                return !isTruthy(right);
                        case MINUS:
                                return -(double)right;
                }

                return null;
        }

        private boolean isTruthy(Object object)
        {
                if (object == null) return false;
                if (object instanceof Boolean) return (boolean)object;
                return true;        
        }

        private boolean isEqual(Object a, Object b)
        {
                if (a == null && b == null) return true;
                if (a == null) return false;

                return a.equals(b);
        }

        private String stringify(Object object)
        {
                if (object == null) return "nil";

                if (object instanceof Double)
                {
                        String text = object.toString();
                        if (text.endsWith(".0"))
                        {
                                text = text.substring(0, text.length() - 2);
                        }
                        return text;
                }

                return object.toString();
        }

        @Override
        public Object visitGroupingExpr(Expr.Grouping expr)
        {
                return evaluate(expr.expression);
        }

        @Override
        public Object visitBinaryExpr(Expr.Binary expr)
        {
                Object left = evaluate(expr.left);
                Object right = evaluate(expr.right);

                switch (expr.operator.type)
                {
                        case BANG_EQUAL: return !isEqual(left,right);
                        case EQUAL_EQUAL: return isEqual(left,right);
                        case GREATER:
                                return (double)left > (double)right;
                        case GREATER_EQUAL:
                                return (double)left >= (double)right;
                        case LESS:
                                return (double)left < (double)right;
                        case LESS_EQUAL:
                                return (double)left <= (double)right;
                        case MINUS:
                                return (double)left - (double)right;
                        case PLUS:
                                if (left instanceof Double && right instanceof Double)
                                {
                                        return (double)left + (double)right;
                                }

                                if (left instanceof String && right instanceof String)
                                {
                                        return (String)left + (String)right;
                                }
                                break;
                        case SLASH:
                                return (double)left / (double)right;
                        case STAR:
                                return (double)left * (double)right;
                }
                return null;
        }

        @Override
        public Void visitExpressionStmt(Stmt.Expression stmt)
        {
                evaluate(stmt.expression);
                return null;
        }

        @Override
        public Void visitPrintStmt(Stmt.Print stmt)
        {
                Object value = evaluate(stmt.expression);
                System.out.println(stringify(value));
                return null;
        }

        private Object evaluate(Expr expr)
        {
                return expr.accept(this);
        }

        private void execute(Stmt stmt)
        {
                stmt.accept(this);
        }

}