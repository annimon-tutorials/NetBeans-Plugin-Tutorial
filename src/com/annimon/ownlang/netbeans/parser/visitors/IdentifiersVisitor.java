package com.annimon.ownlang.netbeans.parser.visitors;

import com.annimon.ownlang.netbeans.parser.Identifier;
import com.annimon.ownlang.netbeans.parser.ast.DestructuringAssignmentStatement;
import com.annimon.ownlang.netbeans.parser.ast.FunctionDefineStatement;
import com.annimon.ownlang.netbeans.parser.ast.Statement;
import com.annimon.ownlang.netbeans.parser.ast.VariableExpression;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.modules.csl.api.ElementKind;

public class IdentifiersVisitor extends AbstractVisitor {

    private final Set<Identifier> identifiers;

    public IdentifiersVisitor() {
        this.identifiers = new HashSet<>();
    }

    public Set<Identifier> accept(Statement statement) {
        identifiers.clear();
        statement.accept(this);
        return identifiers;
    }

    @Override
    public void visit(VariableExpression s) {
        super.visit(s);
        identifiers.add(new Identifier(s.name, ElementKind.VARIABLE));
    }

    @Override
    public void visit(DestructuringAssignmentStatement s) {
        super.visit(s);
        for (String variable : s.variables) {
            identifiers.add(new Identifier(variable, ElementKind.VARIABLE));
        }
    }

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        identifiers.add(new Identifier(s.name, ElementKind.METHOD));
    }
    
}
