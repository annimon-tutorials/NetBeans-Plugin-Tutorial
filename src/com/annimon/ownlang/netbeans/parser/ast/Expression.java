package com.annimon.ownlang.netbeans.parser.ast;

import com.annimon.ownlang.lib.Value;

/**
 *
 * @author aNNiMON
 */
public interface Expression extends Node {
    
    Value eval();
}
