package com.annimon.ownlang.netbeans.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Lexer {
    
    public static List<Token> tokenize(String input) {
        return new Lexer(input).tokenize();
    }
    
    private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:";
    
    private static final Map<String, TokenType> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);
        OPERATORS.put("%", TokenType.PERCENT);
        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("[", TokenType.LBRACKET);
        OPERATORS.put("]", TokenType.RBRACKET);
        OPERATORS.put("{", TokenType.LBRACE);
        OPERATORS.put("}", TokenType.RBRACE);
        OPERATORS.put("=", TokenType.EQ);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);
        OPERATORS.put(".", TokenType.DOT);
        OPERATORS.put(",", TokenType.COMMA);
        OPERATORS.put("^", TokenType.CARET);
        OPERATORS.put("~", TokenType.TILDE);
        OPERATORS.put("?", TokenType.QUESTION);
        OPERATORS.put(":", TokenType.COLON);
        
        OPERATORS.put("!", TokenType.EXCL);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);
        
        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);
        
        OPERATORS.put("+=", TokenType.PLUSEQ);
        OPERATORS.put("-=", TokenType.MINUSEQ);
        OPERATORS.put("*=", TokenType.STAREQ);
        OPERATORS.put("/=", TokenType.SLASHEQ);
        OPERATORS.put("%=", TokenType.PERCENTEQ);
        OPERATORS.put("&=", TokenType.AMPEQ);
        OPERATORS.put("^=", TokenType.CARETEQ);
        OPERATORS.put("|=", TokenType.BAREQ);
        OPERATORS.put("::=", TokenType.COLONCOLONEQ);
        OPERATORS.put("<<=", TokenType.LTLTEQ);
        OPERATORS.put(">>=", TokenType.GTGTEQ);
        OPERATORS.put(">>>=", TokenType.GTGTGTEQ);

        OPERATORS.put("++", TokenType.PLUSPLUS);
        OPERATORS.put("--", TokenType.MINUSMINUS);
        
        OPERATORS.put("::", TokenType.COLONCOLON);
        
        OPERATORS.put("&&", TokenType.AMPAMP);
        OPERATORS.put("||", TokenType.BARBAR);
        
        OPERATORS.put("<<", TokenType.LTLT);
        OPERATORS.put(">>", TokenType.GTGT);
        OPERATORS.put(">>>", TokenType.GTGTGT);

        OPERATORS.put("@", TokenType.AT);
        OPERATORS.put("@=", TokenType.ATEQ);
        OPERATORS.put("..", TokenType.DOTDOT);
        OPERATORS.put("**", TokenType.STARSTAR);
        OPERATORS.put("?:", TokenType.QUESTIONCOLON);
    }
    
    private static final Map<String, TokenType> KEYWORDS;
    static {
        KEYWORDS = new HashMap<>();
        KEYWORDS.put("print", TokenType.PRINT);
        KEYWORDS.put("println", TokenType.PRINTLN);
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("for", TokenType.FOR);
        KEYWORDS.put("do", TokenType.DO);
        KEYWORDS.put("break", TokenType.BREAK);
        KEYWORDS.put("continue", TokenType.CONTINUE);
        KEYWORDS.put("def", TokenType.DEF);
        KEYWORDS.put("return", TokenType.RETURN);
        KEYWORDS.put("use", TokenType.USE);
        KEYWORDS.put("match", TokenType.MATCH);
        KEYWORDS.put("case", TokenType.CASE);
        KEYWORDS.put("extract", TokenType.EXTRACT);
        KEYWORDS.put("include", TokenType.INCLUDE);
    }
    
    private static final Token WHITESPACE = new Token(
            TokenType.WHITESPACE, " ", 0, 0);

    private final String input;
    private final int length;
    
    private final List<Token> tokens;
    private final StringBuilder buffer;
    
    private int pos;
    private int row, col;

    public Lexer(String input) {
        this.input = input;
        length = input.length();
        
        tokens = new ArrayList<>();
        buffer = new StringBuilder();
        row = col = 1;
    }
    
    public List<Token> tokenize() {
        while (pos < length) {
            nextToken();
        }
        return tokens;
    }
    
    public boolean isEOF() {
        return pos >= length;
    }
    
    public Token nextToken() {
        final char current = peek(0);
        if (Character.isDigit(current)) return tokenizeNumber();
        else if (isOwnLangIdentifierStart(current)) return tokenizeWord();
        else if (current == '`') return tokenizeExtendedWord();
        else if (current == '"') return tokenizeText();
        else if (current == '#') {
            next();
            return tokenizeHexNumber(1);
        }
        else if (OPERATOR_CHARS.indexOf(current) != -1) {
            return tokenizeOperator();
        }
        else if (Character.isWhitespace(current)) {
            return tokenizeWhitespaces();
        }
        else {
            // other
            next();
        }
        return WHITESPACE;
    }
    
    private Token tokenizeNumber() {
        clearBuffer();
        char current = peek(0);
        if (current == '0' && (peek(1) == 'x' || (peek(1) == 'X'))) {
            next();
            next();
            return tokenizeHexNumber(2);
        }
        while (true) {
            if (current == '.') {
                // if (buffer.indexOf(".") != -1) throw error("Invalid float number");
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        return addToken(TokenType.NUMBER, buffer.toString());
    }
    
    private Token tokenizeHexNumber(int skipped) {
        clearBuffer();
        char current = peek(0);
        while (isHexNumber(current) || (current == '_')) {
            if (current != '_') {
                // allow _ symbol
                buffer.append(current);
            } else skipped++;
            current = next();
        }
        return addToken(TokenType.HEX_NUMBER, buffer.toString(), buffer.length() + skipped);
    }

    private static boolean isHexNumber(char current) {
        return Character.isDigit(current)
                || ('a' <= current && current <= 'f')
                || ('A' <= current && current <= 'F');
    }
    
    private Token tokenizeOperator() {
        char current = peek(0);
        if (current == '/') {
            if (peek(1) == '/') {
                next();
                next();
                return tokenizeComment();
            } else if (peek(1) == '*') {
                next();
                next();
                return tokenizeMultilineComment();
            }
        }
        clearBuffer();
        while (true) {
            final String text = buffer.toString();
            if (!text.isEmpty() && !OPERATORS.containsKey(text + current)) {
                return addToken(OPERATORS.get(text), "", text.length());
            }
            buffer.append(current);
            current = next();
        }
    }
    
    private Token tokenizeWord() {
        clearBuffer();
        buffer.append(peek(0));
        char current = next();
        while (true) {
            if (!isOwnLangIdentifierPart(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        
        final String word = buffer.toString();
        if (KEYWORDS.containsKey(word)) {
            return addToken(KEYWORDS.get(word), "", word.length());
        }
        return addToken(TokenType.WORD, word);
    }

    private Token tokenizeExtendedWord() {
        next();// skip `
        clearBuffer();
        char current = peek(0);
        while (true) {
            if (current == '`') break;
            if (current == '\0' || current == '\n' || current == '\r') {
                return addToken(TokenType.WORD, buffer.toString(), buffer.length() + 1);
            }
            buffer.append(current);
            current = next();
        }
        next(); // skip closing `
        return addToken(TokenType.WORD, buffer.toString(), buffer.length() + 2);
    }
    
    private Token tokenizeText() {
        next();// skip "
        clearBuffer();
        char current = peek(0);
        while (true) {
            if (current == '"') break;
            if (current == '\0') {
                return addToken(TokenType.TEXT, buffer.toString(), buffer.length() + 1);
            }
            buffer.append(current);
            current = next();
        }
        next(); // skip closing "
        return addToken(TokenType.TEXT, buffer.toString(), buffer.length() + 2);
    }
    
    private Token tokenizeComment() {
        clearBuffer();
        buffer.append("//");
        char current = peek(0);
        while ("\r\n\0".indexOf(current) == -1) {
            buffer.append(current);
            current = next();
        }
        return createToken(TokenType.SINGLE_LINE_COMMENT);
     }
    
    private Token tokenizeMultilineComment() {
        clearBuffer();
        buffer.append("/*");
        char current = peek(0);
        while (true) {
            if (current == '*' && peek(1) == '/') break;
            if (current == '\0') {
                return createToken(TokenType.MULTI_LINE_COMMENT);
            }
            buffer.append(current);
            current = next();
        }
        next(); // *
        next(); // /
        buffer.append("*/");
        return createToken(TokenType.MULTI_LINE_COMMENT);
    }
    
    private Token tokenizeWhitespaces() {
        clearBuffer();
        char current = peek(0);
        while (Character.isWhitespace(current)) {
            buffer.append(current);
            current = next();
        }
        return createToken(TokenType.WHITESPACE);
    }

    private boolean isOwnLangIdentifierStart(char current) {
        return (Character.isLetter(current) || (current == '_') || (current == '$'));
    }

    private boolean isOwnLangIdentifierPart(char current) {
        return (Character.isLetterOrDigit(current) || (current == '_') || (current == '$'));
    }
    
    private void clearBuffer() {
        buffer.setLength(0);
    }
    
    private char next() {
        pos++;
        final char result = peek(0);
        if (result == '\n') {
            row++;
            col = 1;
        } else col++;
        return result;
    }
    
    private char peek(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return input.charAt(position);
    }
    
    private Token addToken(TokenType type) {
        return addToken(type, buffer.toString());
    }
    
    private Token addToken(TokenType type, String text) {
        return addToken(type, text, text.length());
    }
    
    private Token addToken(TokenType type, String text, int length) {
        final Token token = new Token(type, text, length, row, col);
        tokens.add(token);
        return token;
    }
    
    private Token createToken(TokenType type) {
        return new Token(type, buffer.toString(), row, col);
    }
}
