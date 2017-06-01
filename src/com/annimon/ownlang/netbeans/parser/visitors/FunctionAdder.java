package com.annimon.ownlang.netbeans.parser.visitors;

import com.annimon.ownlang.netbeans.parser.ast.*;

/**
 *
 * @author aNNiMON
 */
public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }
}
