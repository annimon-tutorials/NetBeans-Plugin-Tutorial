package com.annimon.ownlang.netbeans.completion;

import java.util.EnumSet;
import java.util.Set;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.filesystems.FileObject;

public class OwnLangElementHandle implements ElementHandle {
    
    private final String name;
    private final OffsetRange offsetRange;
    private final ElementKind kind;
    
    public OwnLangElementHandle(String name, ElementKind kind) {
        this(name, new OffsetRange(2, 10), kind);
    }

    public OwnLangElementHandle(String name, OffsetRange offsetRange, ElementKind kind) {
        this.name = name;
        this.offsetRange = offsetRange;
        this.kind = kind;
    }

    @Override
    public FileObject getFileObject() {
        return null;
    }

    @Override
    public String getMimeType() {
        return "text/x-ownlang";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIn() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return kind;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return EnumSet.noneOf(Modifier.class);
    }

    @Override
    public boolean signatureEquals(ElementHandle handle) {
        return name.equals(handle.getName());
    }

    @Override
    public OffsetRange getOffsetRange(ParserResult pr) {
        return offsetRange;
    }
}
