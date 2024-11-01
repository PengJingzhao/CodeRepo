package com.pjz.ds;

public class BTreeNode {
    //当前节点存储的关键字
    int[] keys;
    //B树的最小度数
    int t;
    //B树的孩子节点
    BTreeNode[] children;
    //当前节点的实际关键字个数
    int len;
    //当前节点是否为叶子节点
    boolean isLeaf;

    //通过指定最小度数和是否为叶子节点来构建节点
    public BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.len = 0;
    }

    //向当前节点插入关键字
    public void insertKey(int key) {
        //首先要确认key的插入位置
        int index = this.len - 1;
        //分叶子节点和非叶子节点两种情况
        if (this.isLeaf) {
            //根据keys数组的有序性确认关键字插入位置
            while (index >= 0 && this.keys[index] > key) {
                this.keys[index + 1] = this.keys[index];
                index--;
            }
            //由于index已经前移，实际空出来的位置应该是index+1
            this.keys[index + 1] = key;
            this.len--;
        } else {
            //非叶子节点
            while (index >= 0 && this.keys[index] > key) {
                index--;
            }
            if (children[index + 1].len == 2 * t - 1) {
                //分裂节点
                splitChildAndInsertKey(index + 1, children[index + 1]);
                if (this.keys[index + 1] < key) {
                    index++;
                }
            }
            children[index + 1].insertKey(key);
        }
    }

    //分裂孩子节点
    public void splitChildAndInsertKey(int index, BTreeNode child) {
        BTreeNode newChild = new BTreeNode(child.t, child.isLeaf);
        newChild.len = this.t - 1;

        //将child的后半部分的关键字移动到新节点处
        for (int i = 0; i < this.t - 1; i++) {
            newChild.keys[i] = child.keys[i + this.t];
        }
        //非叶子节点分裂时还要移动子树
        if (!child.isLeaf) {
            for (int i = 0; i < this.t; i++) {
                newChild.children[i] = newChild.children[i + this.t];
            }
        }

        //此时child只剩下t-1个关键字
        child.len = this.t - 1;

        //将newChild插入到当前节点
        for (int i = this.len; i >= index + 1; i--) {
            this.children[i + 1] = this.children[i];
        }
        this.children[index + 1] = newChild;

        //将中间位置的关键字插入到当前节点
        for (int i = this.len - 1; i >= index; i--) {
            this.keys[i + 1] = this.keys[i];
        }
        this.keys[index] = child.keys[this.t - 1];

        this.len++;
    }

}
