package com.annimon.ownlang.netbeans.errorhighlighting;

import com.annimon.ownlang.netbeans.parser.OwnLangParserResult;
import com.annimon.ownlang.netbeans.parser.ParseError;
import com.annimon.ownlang.netbeans.parser.Parser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.util.Exceptions;

public class OwnLangSyntaxErrorHighlightingTask extends ParserResultTask<OwnLangParserResult> {

    @Override
    public void run(OwnLangParserResult result, SchedulerEvent event) {
        try {
            final Parser parser = result.getParser();
            final Document document = result.getSnapshot().getSource().getDocument(false);
            final List<ErrorDescription> errors;
            if (!parser.getParseErrors().hasErrors()) {
                // clear error hints
                errors = Collections.<ErrorDescription>emptyList();
            } else {
                errors = new ArrayList<>();
                for (ParseError parseError : parser.getParseErrors()) {
                    ErrorDescription error = ErrorDescriptionFactory.createErrorDescription(
                            Severity.ERROR, parseError.getException().getMessage(),
                            Collections.<Fix>emptyList() /* fix list */,
                            document, parseError.getLine()
                    );
                    errors.add(error);
                }
            }
            HintsController.setErrors(document, "ownlang", errors);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    @Override
    public void cancel() {
    }

}
