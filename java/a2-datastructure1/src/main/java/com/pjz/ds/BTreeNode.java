//package com.pjz.ds;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BTreeNode {
//    int t;  // 最小度数
//    List<Integer> keys;  // 节点的关键字
//    List<BTreeNode> children;  // 子节点
//    boolean isLeaf;  // 是否为叶节点
//
//    BTreeNode(int t, boolean isLeaf) {
//        this.t = t;
//        this.isLeaf = isLeaf;
//        this.keys = new ArrayList<>();
//        this.children = new ArrayList<>();
//    }
//
//    // 插入一个非满节点
//    void insertNonFull(int key) {
//        int i = keys.size() - 1;
//
//        //叶子节点
//        if (isLeaf) {
//            keys.add(0);  // 为新key增加空间
//            while (i >= 0 && key < keys.get(i)) {
//                //后移腾出第i个位置
//                keys.set(i + 1, keys.get(i));
//                i--;
//            }
//            //在第i个位置插入
//            keys.set(i + 1, key);
//        } else {
//            //非叶子结点
//            while (i >= 0 && key < keys.get(i)) {
//                i--;
//            }
//            i++;
//            if (children.get(i).keys.size() == 2 * t - 1) {
//                splitChild(i, children.get(i));
//                if (key > keys.get(i)) {
//                    i++;
//                }
//            }
//            children.get(i).insertNonFull(key);
//        }
//    }
//
//    void splitChild(int i, BTreeNode y) {
//        BTreeNode z = new BTreeNode(y.t, y.isLeaf);
//
//        // 将 t-1 到 2t-2 的键复制到新节点 z
//        for (int j = 0; j < t - 1; j++) {
//            if (j + t < y.keys.size()) {
//                z.keys.add(y.keys.get(j + t));
//            }
//        }
//
//        // 如果 y 不是叶节点，则将 t 到 2t-1 的子节点复制到新节点 z
//        if (!y.isLeaf) {
//            for (int j = 0; j < t; j++) {
//                if (j + t < y.children.size()) {
//                    z.children.add(y.children.get(j + t));
//                }
//            }
//        }
//
//        // 移除 y 中 t-1 到 2t-2 的键
//        while (y.keys.size() > t - 1) {
//            y.keys.remove(y.keys.size() - 1);
//        }
//
//        // 如果 y 不是叶节点，则移除 t 到 2t-1 的子节点
//        while (y.children.size() > t) {
//            y.children.remove(y.children.size() - 1);
//        }
//
//        // 在父节点插入新节点 z 和中间键
//        children.add(i + 1, z);
//        if (t - 1 < y.keys.size()) {
//            keys.add(i, y.keys.remove(t - 1));
//        }
//    }
//}
//
//class BTree {
//    BTreeNode root;
//    int t;
//
//    BTree(int t) {
//        this.root = null;
//        this.t = t;
//    }
//
//    /**
//     *
//     * 插入新的关键字
//     * @param key 新的关键字
//     */
//    public void insert(int key) {
//        //空树要创建新的根节点
//        if (root == null) {
//            root = new BTreeNode(t, true);
//            root.keys.add(key);
//        } else {
//            //根节点的关键字个数达到上限
//            if (root.keys.size() == 2 * t - 1) {
//                BTreeNode s = new BTreeNode(t, false);
//                s.children.add(root);
//                s.splitChild(0, root);
//                int i = (key > s.keys.get(0)) ? 1 : 0;
//                s.children.get(i).insertNonFull(key);
//                root = s;
//            } else {
//                //没有达到上限
//                root.insertNonFull(key);
//            }
//        }
//    }
//
//    public void traverse() {
//        if (root != null) {
//            traverse(root);
//        }
//    }
//
//    private void traverse(BTreeNode node) {
//        int i;
//        for (i = 0; i < node.keys.size(); i++) {
//            if (!node.isLeaf) {
//                traverse(node.children.get(i));
//            }
//            System.out.print(node.keys.get(i) + " ");
//        }
//        if (!node.isLeaf) {
//            traverse(node.children.get(i));
//        }
//    }
//
//    public static void main(String[] args) {
//        int t = 3;  // 最小度数
//        BTree tree = new BTree(t);
//
//        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
//        for (int key : keys) {
//            tree.insert(key);
//        }
//
//        System.out.println("B树的遍历结果:");
//        tree.traverse();
//    }
//}
//
//
////public class BTreeNode {
////    //当前结点关键字个数
////    int keynum;
////    //当前节点存储的关键字
////    int[] key;
////    //B树的阶数
////    int m;
////    //双亲结点
////    BTreeNode parent;
////    //B树的孩子节点
////    BTreeNode[] children;
////    //记录数组
////    boolean[] records;
////
////    public BTreeNode() {
////        this.key = new int[10];
////        this.children = new BTreeNode[10];
////    }
////}
