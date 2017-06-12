package com.annimon.ownlang.netbeans.editorenhancement;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.IndentTask;

@MimeRegistration(mimeType = "text/x-ownlang", service = IndentTask.Factory.class)
public class OwnLangIndentTaskFactory implements IndentTask.Factory {

    @Override
    public IndentTask createTask(Context context) {
        return new OwnLangIndentTask(context);
    }
}
