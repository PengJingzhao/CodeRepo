package com.pjz.job;

import java.io.Serializable;

/**
 * 定义作业何时被触发的组件
 */
public interface Trigger extends Serializable, Cloneable ,Comparable<Trigger>{
}
