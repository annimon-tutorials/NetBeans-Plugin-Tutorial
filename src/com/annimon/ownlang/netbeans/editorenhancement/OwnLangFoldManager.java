package com.annimon.ownlang.netbeans.editorenhancement;

import com.annimon.ownlang.netbeans.lexer.Lexer;
import com.annimon.ownlang.netbeans.lexer.Token;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import org.netbeans.api.editor.fold.Fold;
import org.netbeans.api.editor.fold.FoldHierarchy;
import org.netbeans.api.editor.fold.FoldTemplate;
import org.netbeans.api.editor.fold.FoldType;
import org.netbeans.spi.editor.fold.FoldHierarchyTransaction;
import org.netbeans.spi.editor.fold.FoldManager;
import org.netbeans.spi.editor.fold.FoldOperation;
import org.openide.util.Exceptions;

public class OwnLangFoldManager implements FoldManager {

    private FoldOperation operation;
    
    @Override
    public void init(FoldOperation operation) {
        this.operation = operation;
    }

    @Override
    public void initFolds(FoldHierarchyTransaction transaction) {
        final FoldHierarchy hierarchy = operation.getHierarchy();
        Lexer lexer = new Lexer(hierarchy.getComponent().getText().replace("\r", ""));
        int offset = 0;
        while (!lexer.isEOF()) {
            final Token token = lexer.nextToken();
            switch (token.getType()) {
                case MULTI_LINE_COMMENT:
                    try {
                        operation.addToHierarchy(
                                FoldType.COMMENT,
                                offset, offset + token.getLength(),
                                null,
                                FoldTemplate.DEFAULT,
                                "/* ... */",
                                null, transaction);
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    break;
            }
            offset += token.getLength();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent evt, FoldHierarchyTransaction transaction) {
    }

    @Override
    public void removeUpdate(DocumentEvent evt, FoldHierarchyTransaction transaction) {
    }

    @Override
    public void changedUpdate(DocumentEvent evt, FoldHierarchyTransaction transaction) {
    }

    @Override
    public void removeEmptyNotify(Fold epmtyFold) {
    }

    @Override
    public void removeDamagedNotify(Fold damagedFold) {
    }

    @Override
    public void expandNotify(Fold expandedFold) {
    }

    @Override
    public void release() {
    }

}
