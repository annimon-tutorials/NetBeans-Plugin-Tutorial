package com.annimon.ownlang.netbeans.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public enum TokenType implements TokenId {

    NUMBER("number"),
    HEX_NUMBER("number"),
    WORD("identifier"),
    TEXT("string"),
    
    // keyword
    PRINT("keyword"),
    PRINTLN("keyword"),
    IF("keyword"),
    ELSE("keyword"),
    WHILE("keyword"),
    FOR("keyword"),
    DO("keyword"),
    BREAK("keyword"),
    CONTINUE("keyword"),
    DEF("keyword"),
    RETURN("keyword"),
    USE("keyword"),
    MATCH("keyword"),
    CASE("keyword"),
    EXTRACT("keyword"),
    INCLUDE("keyword"),
    
    PLUS("operator"), // +
    MINUS("operator"), // -
    STAR("operator"), // *
    SLASH("operator"), // /
    PERCENT("operator"),// %
    AT("operator"), // @
    
    EQ("operator"), // =
    EQEQ("operator"), // ==
    EXCL("operator"), // !
    EXCLEQ("operator"), // !=
    LTEQ("operator"), // <=
    LT("operator"), // <
    GT("operator"), // >
    GTEQ("operator"), // >=
    
    PLUSEQ("operator"), // +=
    MINUSEQ("operator"), // -=
    STAREQ("operator"), // *=
    SLASHEQ("operator"), // /=
    PERCENTEQ("operator"), // %=
    ATEQ("operator"), // @=
    AMPEQ("operator"), // &=
    CARETEQ("operator"), // ^=
    BAREQ("operator"), // |=
    COLONCOLONEQ("operator"), // ::=
    LTLTEQ("operator"), // <<=
    GTGTEQ("operator"), // >>=
    GTGTGTEQ("operator"), // >>>=
    
    PLUSPLUS("operator"), // ++
    MINUSMINUS("operator"), // --
    
    LTLT("operator"), // <<
    GTGT("operator"), // >>
    GTGTGT("operator"), // >>>

    DOTDOT("operator"), // ..
    STARSTAR("operator"), // **
    QUESTIONCOLON("operator"), // ?:
    
    TILDE("operator"), // ~
    CARET("operator"), // ^
    BAR("operator"), // |
    BARBAR("operator"), // ||
    AMP("operator"), // &
    AMPAMP("operator"), // &&
    
    QUESTION("operator"), // ?
    COLON("operator"), // :
    COLONCOLON("operator"), // ::

    LPAREN("operator"), // (
    RPAREN("operator"), // )
    LBRACKET("operator"), // [
    RBRACKET("operator"), // ]
    LBRACE("operator"), // {
    RBRACE("operator"), // }
    COMMA("operator"), // ,
    DOT("operator"), // .
    
    SINGLE_LINE_COMMENT("comment"),
    MULTI_LINE_COMMENT("comment"),
    
    WHITESPACE("whitespace"),
    EOF("whitespace");
    
    private final String category;

    TokenType(String category) {
        this.category = category;
    }
    
    @Override
    public String primaryCategory() {
        return category;
    }
    
    public static Language<TokenType> getLanguage() {
        return new OwnLangHierarchy().language();
    }
}
