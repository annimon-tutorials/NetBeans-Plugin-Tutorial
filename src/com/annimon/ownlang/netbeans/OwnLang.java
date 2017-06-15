package com.annimon.ownlang.netbeans;

import com.annimon.ownlang.netbeans.completion.OwnLangCompletionHandler;
import com.annimon.ownlang.netbeans.lexer.TokenType;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;

@LanguageRegistration(mimeType = "text/x-ownlang")
public class OwnLang extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return TokenType.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "OwnLang";
    }

    @Override
    public CodeCompletionHandler getCompletionHandler() {
        return new OwnLangCompletionHandler();
    }
}
