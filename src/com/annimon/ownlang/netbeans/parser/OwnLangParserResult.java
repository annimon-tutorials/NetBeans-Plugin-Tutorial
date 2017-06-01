package com.annimon.ownlang.netbeans.parser;

import java.util.Collections;
import java.util.List;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.ParseException;

public class OwnLangParserResult extends ParserResult {

    private final Parser parser;
    private boolean isValid;
    
    public OwnLangParserResult(Snapshot snapshot, Parser parser) {
        super(snapshot);
        this.parser = parser;
        isValid = true;
    }

    public Parser getParser() throws ParseException {
        if (!isValid) {
            throw new ParseException();
        }
        return parser;
    }

    @Override
    public List<? extends Error> getDiagnostics() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected void invalidate() {
        isValid = false;
    }

}
