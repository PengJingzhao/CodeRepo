package com.pjz.ds;

public class BTreeNode {
    //当前结点关键字个数
    int keynum;
    //当前节点存储的关键字
    int[] key;
    //B树的阶数
    int m;
    //双亲结点
    BTreeNode parent;
    //B树的孩子节点
    BTreeNode[] children;
    //记录数组
    boolean[] records;
}
