package com.annimon.ownlang.netbeans.editorenhancement;

import javax.swing.text.BadLocationException;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.IndentTask;

public class OwnLangIndentTask implements IndentTask {

    private final Context context;
    
    public OwnLangIndentTask(Context context) {
        this.context = context;
    }

    @Override
    public void reindent() throws BadLocationException {
        int currentLineStart = context.lineStartOffset(context.caretOffset());
        int currentIndent = context.lineIndent(currentLineStart);
        int previousLineIndex = currentLineStart - currentIndent - 1;
        if (previousLineIndex < 0) {
            return;
        }
        int previousLineStart = context.lineStartOffset(previousLineIndex);
        int previousIndent = context.lineIndent(previousLineStart);
        String previousLineText = context.document().getText(previousLineStart,
                currentLineStart - previousLineStart - 1);
        
        int indent = previousIndent;
        if (isBlockOpener(previousLineText)) {
            indent += 2;
        }
        context.modifyIndent(currentLineStart, indent);
    }
    
    private boolean isBlockOpener(String text) {
        for (int i = text.length() - 1; i >= 0; i--) {
            final char ch = text.charAt(i);
            if (ch == '{') {
                return true;
            } else if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public ExtraLock indentLock() {
        return null;
    }
}
