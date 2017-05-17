package com.annimon.ownlang.netbeans.lexer;

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class OwnLangHierarchy extends LanguageHierarchy<TokenType> {

    @Override
    protected Collection<TokenType> createTokenIds() {
        return EnumSet.allOf(TokenType.class);
    }

    @Override
    protected Lexer<TokenType> createLexer(LexerRestartInfo<TokenType> info) {
        return new OwnLangLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-ownlang";
    }

}
