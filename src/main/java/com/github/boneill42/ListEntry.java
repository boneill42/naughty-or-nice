package com.github.boneill42;

import com.netflix.astyanax.annotations.Component;

public class ListEntry {
    @Component(ordinal = 0)
    public String state;
    @Component(ordinal = 1)
    public String zip;
    @Component(ordinal = 2)
    public String childId;
    @Component(ordinal = 3)
    public String valueField;   
}