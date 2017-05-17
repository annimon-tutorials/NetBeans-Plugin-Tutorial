package com.annimon.ownlang.netbeans.lexer;

public final class Token {

    private final TokenType type;
    private final String text;
    private final int length;
    private final int row, col;
    
    public Token(TokenType type, String text, int row, int col) {
        this(type, text, text.length(), row, col);
    }

    public Token(TokenType type, String text, int length, int row, int col) {
        this.type = type;
        this.text = text;
        this.length = length;
        this.row = row;
        this.col = col;
    }

    public TokenType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
    
    public String getText() {
        return text;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}
