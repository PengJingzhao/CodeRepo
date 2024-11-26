package com.pjz.document.entity.structure;

import org.w3c.dom.Node;

import java.util.Arrays;

public class BTree {

    /**
     * 阶数,最大子节点数，m=2*t
     */
    private int m;

    /**
     * 最小度数，实际为m/2
     */
    private int t;

    /**
     * b树的根节点
     */
    private Node root;

    public BTree(int m) {
        this.m = m;
        this.t = m / 2;
        this.root = new Node(t);
    }

    /**
     * 新增关键字
     *
     * @param key 关键字
     */
    public void insert(int key) {
        doInsert(null, root, 0, key);
    }

    /**
     * 向当前结点的keys数组的index位置处插入key关键字
     *
     * @param parent 当前节点的父节点
     * @param cur    当前结点
     * @param index  分裂结点时的基准，也是当前结点位于父节点children数组中的位序
     * @param key    关键字
     */
    private void doInsert(Node parent, Node cur, int index, int key) {
        //寻找正确的插入位置
        int i = 0;
        while (i < cur.keyNum && cur.keys[i] < key) {
            i++;
        }
        //若关键字已存在则直接退出插入
        if (cur.keys[i] == key) {
            return;
        }
        //如果是叶子节点，就可以插入了，如果是非叶子结点就要下移寻找
        if (cur.isLeaf) {
            //插入关键字
            cur.insertKey(key, i);
        } else {
            //向下寻找
            doInsert(cur, cur.children[i], i, key);
        }
        //在当前节点插入后还要判断关键字个数是否已经达到上限
        if (cur.keyNum == t * 2 - 1) {
            //分裂结点
            split(parent, cur, index);
        }
    }

    /**
     * @param parent 当前节点的父节点
     * @param cur    待分裂的节点
     * @param index  分裂的基准位置
     */
    private void split(Node parent, Node cur, int index) {
        //当前结点是根节点，就要另外创建新的根节点，作为当前结点的父节点
        if (parent == null) {
            parent = new Node(t);
            parent.isLeaf = false;
            root = parent;
        }
        //创建当前结点的右兄弟节点,并且将当前结点的一半关键字复制到兄弟节点中
        Node rightBro = new Node(t);
        rightBro.isLeaf = cur.isLeaf;
        rightBro.keyNum = t - 1;
        System.arraycopy(cur.keys, t, rightBro.keys, 0, t - 1);
        //还要把子节点也复制到新右兄弟结点中
        if (!cur.isLeaf) {
            System.arraycopy(cur.children, t, rightBro.children, 0, t);
            //将当前结点移走的子节点置空
            for (int i = t; i <= cur.keyNum; i++) {
                cur.children[i] = null;
            }
        }
        //更新父节点
        parent.insertKey(cur.keys[t - 1], index);
        parent.insertChild(rightBro, index + 1);
    }

    static class Node {
        /**
         * 是否为叶子结点，默认是
         */
        boolean isLeaf = true;

        /**
         * 存储的关键字个数
         */
        int keyNum;

        /**
         * 最小出度，其实是m/2
         */
        int t;

        /**
         * 关键字数组，底层存储关键字的地方,最大长度为2*t-1
         */
        int[] keys;

        /**
         * 孩子节点数组,最大长度为2*t
         */
        Node[] children;

        public Node(int t) {
            this.t = t;
            this.keys = new int[t * 2 - 1];
            this.children = new Node[t * 2];
        }

        /**
         * 在指定位置插入关键字
         *
         * @param key   关键字
         * @param index 目标位置
         */
        public void insertKey(int key, int index) {
            //在index处腾出空位
            System.arraycopy(keys, index, keys, index + 1, keyNum - index);
            //插入关键字
            keys[index] = key;
            //维护keyNum
            keyNum++;
        }

        /**
         * 当前结点插入子节点
         *
         * @param child 新的子节点
         * @param index 插入位置
         */
        void insertChild(Node child, int index) {
            System.arraycopy(children, index, children, index + 1, keyNum - index);
            children[index] = child;
        }
    }

    public void show() {
        doShow(root);
    }

    /**
     * 遍历打印b树
     */
    public void doShow(Node cur) {
        //打印当前结点
        System.out.println(Arrays.toString(cur.keys)+"\n");
        //遍历子结点
        if (cur.isLeaf) {
            return;
        }
        for (Node child : cur.children) {
            if (child == null) {
                continue;
            }
            doShow(child);
        }

    }

}
