package com.pjz.job.utils;

import java.util.*;

public class DirtyFlagMap<K, V> implements Map<K, V> {

    /**
     * 是否为dirty
     */
    private boolean dirty = false;

    /**
     * 储存数据的map结构
     */
    private Map<K, V> map;

    public DirtyFlagMap() {
        this.map = new HashMap<>();
    }

    public DirtyFlagMap(final int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    public DirtyFlagMap(final int initialCapacity, final float loadFactor) {
        this.map = new HashMap<>(initialCapacity, loadFactor);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public V get(Object o) {
        return null;
    }

    @Override
    public V put(K k, V v) {
        return null;
    }

    @Override
    public V remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public Collection<V> values() {
        return List.of();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Set.of();
    }
}
