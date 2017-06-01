package com.annimon.ownlang.netbeans.parser;

import com.annimon.ownlang.netbeans.lexer.Lexer;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.openide.util.Exceptions;

public class OwnLangParser extends org.netbeans.modules.parsing.spi.Parser {

    private Snapshot snapshot;
    private Parser parser;
    
    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
        this.snapshot = snapshot;
        final Lexer lexer = new Lexer(snapshot.getText().toString());
        parser = new Parser(lexer.tokenize());
        try {
            parser.parse();
        } catch (RuntimeException re) {
            Exceptions.printStackTrace(re);
        }
    }

    @Override
    public Result getResult(Task task) throws ParseException {
        return new OwnLangParserResult(snapshot, parser);
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

}
