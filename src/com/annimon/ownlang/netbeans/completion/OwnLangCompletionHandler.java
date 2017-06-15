package com.annimon.ownlang.netbeans.completion;

import com.annimon.ownlang.netbeans.lexer.Lexer;
import com.annimon.ownlang.netbeans.parser.Identifier;
import com.annimon.ownlang.netbeans.parser.OwnLangParserResult;
import com.annimon.ownlang.netbeans.parser.Parser;
import com.annimon.ownlang.netbeans.parser.visitors.IdentifiersVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.api.CodeCompletionResult;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.ParameterInfo;
import org.netbeans.modules.csl.spi.DefaultCompletionResult;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.spi.ParseException;
import org.openide.util.Exceptions;

public class OwnLangCompletionHandler implements CodeCompletionHandler {

    @Override
    public CodeCompletionResult complete(CodeCompletionContext context) {
        final List<CompletionProposal> proposals = new ArrayList<>();
        // identifiers
        try {
            OwnLangParserResult result = (OwnLangParserResult) context.getParserResult();
            final Parser parser = result.getParser();
            if (parser.getParsedStatement() != null) {
                IdentifiersVisitor visitor = new IdentifiersVisitor();
                final Set<Identifier> identifiers = visitor.accept(parser.getParsedStatement());
                for (Identifier identifier : identifiers) {
                    proposals.add(new OwnLangCompletionProposal(
                        new OwnLangElementHandle(identifier.getName(), identifier.getKind())));
                }
            }
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        // keywords
        for (String keyword : Lexer.getKeywords()) {
            proposals.add(new OwnLangCompletionProposal(
                    new OwnLangElementHandle(keyword, ElementKind.KEYWORD)));
        }
        return new DefaultCompletionResult(proposals, false);
    }

    @Override
    public String document(ParserResult pr, ElementHandle eh) {
        return null;
    }

    @Override
    public ElementHandle resolveLink(String string, ElementHandle eh) {
        return null;
    }

    @Override
    public String getPrefix(ParserResult pr, int i, boolean bln) {
        return null;
    }

    @Override
    public QueryType getAutoQuery(JTextComponent jtc, String string) {
        return QueryType.NONE;
    }

    @Override
    public String resolveTemplateVariable(String string, ParserResult pr, int i, String string1, Map map) {
        return null;
    }

    @Override
    public Set<String> getApplicableTemplates(Document dcmnt, int i, int i1) {
        return null;
    }

    @Override
    public ParameterInfo parameters(ParserResult pr, int i, CompletionProposal cp) {
        return ParameterInfo.NONE;
    }
}
