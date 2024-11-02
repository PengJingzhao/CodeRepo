package com.pjz.ds;

public class Result {
    //找到的结点
    BTreeNode node;
    //在结点中的位序
    int i;
    //查找是否成功,1=成功,0=失败
    int tag;

    public Result(BTreeNode node, int i, int tag) {
        this.node = node;
        this.i = i;
        this.tag = tag;
    }
}
