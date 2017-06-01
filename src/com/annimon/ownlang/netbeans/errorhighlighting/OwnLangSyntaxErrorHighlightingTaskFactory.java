package com.annimon.ownlang.netbeans.errorhighlighting;

import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.SchedulerTask;
import org.netbeans.modules.parsing.spi.TaskFactory;

@MimeRegistration(mimeType = "text/x-ownlang", service = TaskFactory.class)
public class OwnLangSyntaxErrorHighlightingTaskFactory extends TaskFactory {

    @Override
    public Collection<? extends SchedulerTask> create(Snapshot snapshot) {
        return Collections.singleton(new OwnLangSyntaxErrorHighlightingTask());
    }
}
