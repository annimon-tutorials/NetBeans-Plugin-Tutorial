package com.annimon.ownlang.netbeans.completion;

import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.spi.DefaultCompletionProposal;
import org.openide.util.ImageUtilities;

public class OwnLangCompletionProposal extends DefaultCompletionProposal {

    @StaticResource
    private static final String
            OWNLANG_ICON_PATH = "com/annimon/ownlang/netbeans/filetype/ownlang.png";
    
    private final ElementHandle elementHandle;

    public OwnLangCompletionProposal(ElementHandle elementHandle) {
        this.elementHandle = elementHandle;
        super.elementKind = elementHandle.getKind();
    }
    
    @Override
    public ElementHandle getElement() {
        return elementHandle;
    }

    @Override
    public ImageIcon getIcon() {
        if (elementKind == ElementKind.KEYWORD) {
            return ImageUtilities.loadImageIcon(OWNLANG_ICON_PATH, false);
        }
        return null;
    }

    @Override
    public String getName() {
        return elementHandle.getName();
    }
}
