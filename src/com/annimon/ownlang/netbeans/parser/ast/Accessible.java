package com.annimon.ownlang.netbeans.parser.ast;

import com.annimon.ownlang.lib.Value;

public interface Accessible extends Node {

    Value get();
    
    Value set(Value value);
}
