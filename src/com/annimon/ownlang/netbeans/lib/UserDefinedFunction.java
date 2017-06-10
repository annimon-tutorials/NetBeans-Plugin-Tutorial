package com.annimon.ownlang.lib;

import com.annimon.ownlang.netbeans.exceptions.ArgumentsMismatchException;
import com.annimon.ownlang.netbeans.parser.ast.Argument;
import com.annimon.ownlang.netbeans.parser.ast.Arguments;
import com.annimon.ownlang.netbeans.parser.ast.ReturnStatement;
import com.annimon.ownlang.netbeans.parser.ast.Statement;

/**
 *
 * @author aNNiMON
 */
public final class UserDefinedFunction implements Function {
    
    public final Arguments arguments;
    public final Statement body;
    
    public UserDefinedFunction(Arguments arguments, Statement body) {
        this.arguments = arguments;
        this.body = body;
    }
    
    public int getArgsCount() {
        return arguments.size();
    }
    
    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return arguments.get(index).getName();
    }

    @Override
    public Value execute(Value... values) {
        final int size = values.length;
        final int requiredArgsCount = arguments.getRequiredArgumentsCount();
        if (size < requiredArgsCount) {
            throw new ArgumentsMismatchException(String.format("Arguments count mismatch. %d < %d", size, requiredArgsCount));
        }
        final int totalArgsCount = getArgsCount();
        if (size > totalArgsCount) {
            throw new ArgumentsMismatchException(String.format("Arguments count mismatch. %d > %d", size, totalArgsCount));
        }

        try {
            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.define(getArgsName(i), values[i]);
            }
            // Optional args if exists
            for (int i = size; i < totalArgsCount; i++) {
                final Argument arg = arguments.get(i);
                Variables.define(arg.getName(), arg.getValueExpr().eval());
            }
            body.execute();
            return NumberValue.ZERO;
        } catch (ReturnStatement rt) {
            return rt.getResult();
        } finally {
            Variables.pop();
        }
    }

    @Override
    public String toString() {
        if (body instanceof ReturnStatement) {
            return String.format("def%s = %s", arguments, ((ReturnStatement)body).expression);
        }
        return String.format("def%s %s", arguments, body);
    }
}
