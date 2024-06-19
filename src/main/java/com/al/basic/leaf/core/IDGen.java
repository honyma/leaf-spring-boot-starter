package com.al.basic.leaf.core;


import com.al.basic.leaf.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
