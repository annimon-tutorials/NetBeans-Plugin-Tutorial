package com.annimon.ownlang.netbeans.editorenhancement;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ReformatTask;

@MimeRegistration(mimeType = "text/x-ownlang", service = ReformatTask.Factory.class)
public class OwnLangReformatTaskFactory implements ReformatTask.Factory {

    @Override
    public ReformatTask createTask(Context context) {
        return new OwnLangReformatTask(context);
    }
}
