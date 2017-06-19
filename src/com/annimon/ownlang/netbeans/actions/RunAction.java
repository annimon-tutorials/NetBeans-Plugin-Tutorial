package com.annimon.ownlang.netbeans.actions;

import com.annimon.ownlang.netbeans.exceptions.StoppedException;
import com.annimon.ownlang.netbeans.lexer.Lexer;
import com.annimon.ownlang.netbeans.parser.Parser;
import com.annimon.ownlang.netbeans.parser.ast.Statement;
import com.annimon.ownlang.netbeans.parser.visitors.FunctionAdder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.netbeans.api.io.IOProvider;
import org.netbeans.api.io.InputOutput;
import org.netbeans.api.io.OutputColor;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.io.ReaderInputStream;

@ActionID(
        category = "System",
        id = "com.annimon.ownlang.netbeans.actions.RunAction"
)
@ActionRegistration(
        displayName = "#CTL_RunAction"
)
@ActionReference(
        path = "Loaders/text/x-ownlang/Actions",
        position = 550,
        separatorAfter = 575
)
@Messages("CTL_RunAction=Run file")
public final class RunAction implements ActionListener {

    private final DataObject context;

    public RunAction(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        final InputOutput io = IOProvider.getDefault()
                .getIO(context.getName(), false);
        try {
            prepareIO(io);
            final String input = context.getPrimaryFile().asText();
            final Parser parser = new Parser(Lexer.tokenize(input));
            final Statement program = parser.parse();
            if (parser.getParseErrors().hasErrors()) {
                io.getErr().println(parser.getParseErrors());
                return;
            }
            program.accept(new FunctionAdder());
            program.execute();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (StoppedException se) {
            // skip
        } catch (RuntimeException re) {
            StringWriter sw = new StringWriter();
            re.printStackTrace(new PrintWriter(sw));
            io.getErr().println(sw.toString());
        } finally {
            System.setIn(System.in);
            System.setOut(System.out);
            System.setErr(System.err);
        }
    }

    private void prepareIO(final InputOutput io) throws IOException {
        io.getOut().println("Executing...", OutputColor.success());
        System.setIn(new ReaderInputStream(io.getIn()));
        System.setOut(new WriterPrintStream(io.getOut()));
        System.setErr(new WriterPrintStream(io.getErr()));
    }
}
