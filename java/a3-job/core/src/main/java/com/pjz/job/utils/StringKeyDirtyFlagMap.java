package com.pjz.job.utils;

public class StringKeyDirtyFlagMap extends DirtyFlagMap<String, Object> {

    public StringKeyDirtyFlagMap() {
        super();
    }

    public StringKeyDirtyFlagMap(final int initialCapacity) {
        super(initialCapacity);
    }

    public StringKeyDirtyFlagMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public void put(String key, String value) {
        super.put(key, value);
    }
}
