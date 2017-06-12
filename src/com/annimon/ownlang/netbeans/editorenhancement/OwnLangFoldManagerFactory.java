package com.annimon.ownlang.netbeans.editorenhancement;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.fold.FoldManager;
import org.netbeans.spi.editor.fold.FoldManagerFactory;

@MimeRegistration(mimeType = "text/x-ownlang", service = FoldManagerFactory.class)
public class OwnLangFoldManagerFactory implements FoldManagerFactory {

    @Override
    public FoldManager createFoldManager() {
        return new OwnLangFoldManager();
    }
}
