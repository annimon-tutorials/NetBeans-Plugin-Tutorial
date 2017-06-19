package com.annimon.ownlang.netbeans.parser.ast;

/**
 *
 * @author aNNiMON
 */
public final class PrintlnStatement extends InterruptableNode implements Statement {
    
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        System.out.println(expression.eval().asString());
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "println " + expression;
    }
}
