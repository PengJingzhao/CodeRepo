package com.pjz.job.utils;

import java.io.Serializable;

public class Key<T>  implements Serializable, Comparable<Key<T>> {

    public static final String DEFAULT_GROUP = "DEFAULT";

    private final String name;
    private final String group;

    public Key(String name, String group) {
        if(name == null)
            throw new IllegalArgumentException("Name cannot be null.");
        this.name = name;
        if(group != null)
            this.group = group;
        else
            this.group = DEFAULT_GROUP;
    }

    @Override
    public int compareTo(Key<T> tKey) {
        return 0;
    }
}
