package com.annimon.ownlang.netbeans.editorenhancement;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.BracesMatcherFactory;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;
import org.netbeans.spi.editor.bracesmatching.support.BracesMatcherSupport;

@MimeRegistration(mimeType = "text/x-ownlang", service = BracesMatcherFactory.class)
public class OwnLangBracesMatcherFactory implements BracesMatcherFactory {

    @Override
    public BracesMatcher createMatcher(MatcherContext mc) {
        return BracesMatcherSupport.defaultMatcher(mc, -1, -1);
    }
}
