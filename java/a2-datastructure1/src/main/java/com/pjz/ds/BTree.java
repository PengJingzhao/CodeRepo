package com.pjz.ds;

import java.util.Arrays;

public class BTree {
    //根节点
    BTreeNode root;
    //阶数,m和t是有对应关系的
    int m;

    //通过指定m来构建m阶b树
    public BTree(int m) {
        this.m = m;
        this.root = null;
    }

//    //向b树中插入新关键字
//    public void insertKey(int key) {
//        if (this.root == null) {
//            //根节点为null，则创建根节点
//            this.root = new BTreeNode(this.m, false);
//
//            this.root.insertKey(key);
//        }
//    }

    //打印b树的所有数据
    public void printBTree() {
        if (this.root == null) {
            throw new RuntimeException("当前b树不存在");
        }
        printChildren(this.root.children);
    }

    private void printChildren(BTreeNode[] children) {
        for (BTreeNode child : children) {
            printNode(child);
            printChildren(child.children);
        }
    }

    private void printNode(BTreeNode node) {
        if (node == null) {
            return;
        }
//        System.out.println("keys:" + Arrays.toString(node.keys));
//        for (int i = 0; i < node.keys.length; i++) {
//            System.out.println(node.keys[i]);
//        }
    }

    public Result searchBTree(int k) {
        int i = 0;
        int found = 0;
        Result result = null;
        BTreeNode node = this.root;
        BTreeNode parent = null;
        while (node != null && found == 0) {
            //在当前结点中查找k的位序
            i = search(node, k);
            if (i <= node.keynum && node.key[i] == k) {
                //成功找到
                found = 1;
            } else {
                //没找到，就到下一层子结点去找
                parent = node;
                node = node.children[i - 1];
            }
        }
        if (found == 1) {
            //找到就返回当前结点
            result = new Result(node, i, found);
        } else {
            //没找到就返回父结点的插入位序
            result = new Result(parent, i, found);
        }
        return result;
    }

    private int search(BTreeNode node, int k) {
        int i = 1;
        while (i <= node.keynum && k > node.key[i]) {
            i++;
        }
        return i;
    }

    public void insertBTree(BTreeNode node, int i, int k) {
        int finished = 0;
        int needNewRoot = 0;
        //根结点不存在，构建根节点
        if (this.root == null) {
            this.root = new BTreeNode();
        } else {
            BTreeNode newNode = null;
            while (needNewRoot == 0 && finished == 0) {
                insert(node, i, k, newNode);
                if (node.keynum<this.m){
                    //插入合法
                    finished=1;
                }else {
                    //当前结点关键字数目达到上限,分裂当前结点
                    
                }
            }
        }
    }

    private void insert(BTreeNode node, int i, int k, BTreeNode newNode) {
        for (int j = node.keynum; j >= i; j--) {
            //关键字后移，腾出i位置
            node.key[j + 1] = node.key[j];
            node.children[j + 1] = node.children[j];
        }
        //向当前结点的i位置插入关键字和子结点
        node.key[i] = k;
        node.children[i] = newNode;
        if (newNode != null) {
            newNode.parent = node;
        }
        node.keynum++;
    }

//    public static void main(String[] args) {
//        BTree bTree = new BTree(2);
//        bTree.insertKey(1);
//        bTree.printBTree();
//    }


}
