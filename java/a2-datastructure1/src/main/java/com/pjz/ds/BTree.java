package com.pjz.ds;

import java.util.Arrays;

public class BTree {
    private Node root;
    private int t;//最小度数
    private final int MAX_KEY_NUMBER;
    private final int MIN_KEY_NUMBER;

    public BTree(int t) {
        this.t = t;
        root = new Node(t);
        MAX_KEY_NUMBER = 2 * t - 1;
        MIN_KEY_NUMBER = t - 1;
    }

    static class Node {
        boolean leaf = true; //是否是叶子节点
        int keyNumber; //有效key
        int t; //最小度
        int[] keys; // keys数组
        Node[] children; //孩子节点数组

        public Node(int t) {
            this.t = t;
            this.keys = new int[2 * t - 1];
            this.children = new Node[2 * t];//最大孩子节点个数为为2*t
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * 根据key获取对应节点
         *
         * @param key
         * @return
         */
        Node get(int key) {
            int i = 0;
            while (i < keyNumber) {
                //如果在该节点找到，那么直接返回即可
                if (keys[i] == key) {
                    return this;
                }
                //说明要找的元素可能在children[i]中
                if (keys[i] > key) {
                    break;
                }
                i++;
            }
            //如果是叶子节点，直接返回null
            if (leaf) {
                return null;
            }
            return children[i].get(key);
        }

        /**
         * 指定位置插入元素
         *
         * @param key
         * @param index
         */
        void insertKey(int key, int index) {
            System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
            keys[index] = key;
            keyNumber++;
        }

        /**
         * 向节点中插入孩子节点
         *
         * @param child
         * @param index
         */
        void insertChild(Node child, int index) {
            System.arraycopy(children, index, children, index + 1, keyNumber - index);
            children[index] = child;
        }

        //移除指定元素
        int removeKey(int index) {
            int t = keys[index];
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
            return t;
        }

        //移除最左边的元素
        int removeLeftmostKey() {
            return removeKey(0);
        }

        //移除最右边的元素
        int removeRightmostKey() {
            return removeKey(keyNumber - 1);
        }

        //移除指定位置的孩子节点
        Node removeChild(int index) {
            //获取被移除的节点
            Node t = children[index];
            //将被移除节点的后面元素向前移动一位
            System.arraycopy(children, index + 1, children, index, keyNumber - index);
            //将之前最后一位的引用释放
            children[keyNumber] = null;
            //返回被引用元素
            return t;
        }

        //移除最左边的孩子节点
        Node removeLeftmostChild() {
            return removeChild(0);
        }

        //移除最右边的孩子节点
        Node removeRightmostChild() {
            return removeChild(keyNumber);
        }

        //移动指定节点到目标节点
        void moveToTarget(Node target) {
            int start = target.keyNumber;
            if (!leaf) {
                for (int i = 0; i <= keyNumber; i++) {
                    target.children[start + i] = children[i];
                }
            }
            for (int i = 0; i < keyNumber; i++) {
                target.keys[target.keyNumber++] = keys[i];
            }
        }

        //获取指定孩子节点的左边节点
        Node leftSibling(int index) {
            return index > 0 ? children[index - 1] : null;
        }

        //获取指定孩子节点的右边节点
        Node rightSibling(int index) {
            return index == keyNumber ? null : children[index + 1];
        }

    }

    /**
     * 查询key值是否在树中
     *
     * @param key
     * @return
     */
    public boolean contains(int key) {
        return root.get(key) != null;
    }

    /**
     * 新增节点
     *
     * @param key
     */
    public void put(int key) {
        doPut(null, 0, root, key);
    }

    //index的作用是，给分割方法提供参数
    private void doPut(Node parent, int index, Node node, int key) {
        //寻找插入位置
        int i = 0;
        while (i < node.keyNumber && node.keys[i] < key) {
            i++;
        }
        //如果找到了，直接更新
        if (node.keys[i] == key) {
            return;
        }
        //如果没找到，查看是否是叶子节点，如果不是，向下寻找
        if (node.leaf) {
            node.insertKey(key, i);
        } else {
            doPut(node, i, node.children[i], key);
        }
        if (isFull(node)) {
            split(parent, index, node);
        }
    }

    private boolean isFull(Node node) {
        return node.keyNumber == MAX_KEY_NUMBER;
    }

    /**
     * 分裂节点
     *
     * @param parent
     * @param index  分割节点在父结点的孩子下标
     * @param split
     */
    private void split(Node parent, int index, Node split) {
        if (parent == null) {
            //说明分割根节点,除了需要创建right节点之外，还需要创建parent节点
            parent = new Node(t);
            parent.leaf = false;
            parent.insertChild(split, 0);
            root = parent;
        }
        Node right = new Node(t);
        //将被分裂节点的一部分key放入right节点中。
        System.arraycopy(split.keys, t, right.keys, 0, t - 1);
        //新建的节点与被分裂节点在同一层，因此leaf属性应该和被分裂节点一样
        right.leaf = split.leaf;
        right.keyNumber = t - 1;
        //如果被分裂节点不是叶子节点，也需要将孩子节点一并拷贝到right节点中
        if (!split.leaf) {
            System.arraycopy(split.children, t, right.children, 0, t);
            for (int i = t; i <= split.keyNumber; i++) {
                split.children[i] = null;
            }
        }
        split.keyNumber = t - 1;
        //将t-1节点放入父结点中
        int mid = split.keys[t - 1];
        parent.insertKey(mid, index);
        parent.insertChild(right, index + 1);
    }

    /**
     * 移除指定元素
     *
     * @param key
     */
    public void remove(int key) {
        doRemove(null, root, 0, key);
    }

    private void doRemove(Node parent, Node node, int index, int key) {
        //首先要获取指定元素
        int i = 0;
        while (i < node.keyNumber && node.keys[i] < key) {
            i++;
        }

        if (node.leaf) {
            if (node.keys[i] == key) {
                //case 2:如果找到了并且是叶子节点
                node.removeKey(i);
            } else {
                //case 1:如果没找到并且是叶子节点
                return;
            }
        } else {
            if (node.keys[i] == key) {
                //case 4:如果找到了但不是叶子节点
                //找到后驱节点并交换位置
                Node child = node.children[i + 1];
                while (!child.leaf) {
                    child = child.children[0];
                }
                int nextKey = child.keys[0];
                node.keys[i] = nextKey;
                //之所以不直接调用孩子节点的removeKey方法是为了避免删除后发生不平衡
                //child.removeKey(0);
                doRemove(node, child, i + 1, nextKey);
            } else {
                //case 3:如果没找到但不是叶子节点
                doRemove(node, node.children[i], i, key);
            }
        }
        //如果删除后，节点中的key少于下限，那么需要进行调整
        if (node.keyNumber < MIN_KEY_NUMBER) {
            //平衡调整
            balance(parent, node, index);
        }
    }

    /**
     * 调整B树
     *
     * @param parent 父结点
     * @param node   被调整节点
     * @param index  被调整节点在父结点中的孩子数组下标
     */
    private void balance(Node parent, Node node, int index) {
        //case 6 根节点
        if (node == root) {
            if (node.keyNumber == 0 && node.children[0] != null) {
                root = node.children[0];
            }
            return;
        }
        Node leftChild = parent.leftSibling(index);
        Node rightChild = parent.rightSibling(index);
        //如果左边孩子节点中的key值充足
        if (leftChild != null && leftChild.keyNumber > MIN_KEY_NUMBER) {
            //将父结点中的key赋值给node
            node.insertKey(parent.keys[index - 1], 0);
            if (!leftChild.leaf) {
                //如果左侧孩子不是一个叶子节点，在旋转过后，会导致keysNumber+1！=children。
                //因此将多出来的孩子赋值更多出来一个key的被调整节点
                node.insertChild(leftChild.removeRightmostChild(), 0);
            }
            //将左孩子中最右侧元素赋值给父结点
            parent.keys[index - 1] = leftChild.removeRightmostKey();
            return;
        }
        //如果右边充足
        if (rightChild != null && rightChild.keyNumber > MIN_KEY_NUMBER) {
            node.insertKey(parent.keys[index], node.keyNumber);
            if (!rightChild.leaf) {
                node.insertChild(rightChild.removeLeftmostChild(), node.keyNumber);
            }
            parent.keys[index] = rightChild.removeLeftmostKey();
            return;
        }
        //合并
        //如果删除节点存在左兄弟，向左合并
        if (leftChild != null) {
            //将被删除节点从父结点上移除
            parent.removeChild(index);
            //将父结点的被移除节点的前驱节点移动到左兄弟上
            leftChild.insertKey(parent.removeKey(index - 1), leftChild.keyNumber);
            node.moveToTarget(leftChild);
        } else {
            //如果没有左兄弟，那么移除右兄弟节点，并将右兄弟移动到被删除节点上。
            parent.removeChild(index + 1);
            node.insertKey(parent.removeKey(index), node.keyNumber);
            rightChild.moveToTarget(node);
        }
    }

    public static void main(String[] args) {
        BTree tree = new BTree(3);
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int key : keys) {
            tree.put(key);
        }
        System.out.println(tree);
    }

}
//
//import java.util.Arrays;
//
//public class BTree {
//    //根节点
//    BTreeNode root;
//    //阶数,m和t是有对应关系的
//    int m;
//
//    //通过指定m来构建m阶b树
//    public BTree(int m) {
//        this.m = m;
//        this.root = null;
//    }
//
////    //向b树中插入新关键字
////    public void insertKey(int key) {
////        if (this.root == null) {
////            //根节点为null，则创建根节点
////            this.root = new BTreeNode(this.m, false);
////
////            this.root.insertKey(key);
////        }
////    }
//
//    //打印b树的所有数据
//    public void printBTree() {
//        if (this.root == null) {
//            throw new RuntimeException("当前b树不存在");
//        }
//        printChildren(this.root.children);
//    }
//
//    private void printChildren(BTreeNode[] children) {
//        for (BTreeNode child : children) {
//            printNode(child);
//            printChildren(child.children);
//        }
//    }
//
//    private void printNode(BTreeNode node) {
//        if (node == null) {
//            return;
//        }
////        System.out.println("keys:" + Arrays.toString(node.keys));
////        for (int i = 0; i < node.keys.length; i++) {
////            System.out.println(node.keys[i]);
////        }
//    }
//
//    public Result searchBTree(int k) {
//        int i = 0;
//        int found = 0;
//        Result result = null;
//        BTreeNode node = this.root;
//        BTreeNode parent = null;
//        while (node != null && found == 0) {
//            //在当前结点中查找k的位序
//            i = search(node, k);
//            if (i <= node.keynum && node.key[i] == k) {
//                //成功找到
//                found = 1;
//            } else {
//                //没找到，就到下一层子结点去找
//                parent = node;
//                node = node.children[i - 1];
//            }
//        }
//        if (found == 1) {
//            //找到就返回当前结点
//            result = new Result(node, i, found);
//        } else {
//            //没找到就返回父结点的插入位序
//            result = new Result(parent, i, found);
//        }
//        return result;
//    }
//
//    private int search(BTreeNode node, int k) {
//        int i = 1;
//        while (i <= node.keynum && k > node.key[i]) {
//            i++;
//        }
//        return i;
//    }
//
//
//    /**
//     * 在node结点的关键字数组的第i个位置插入关键字k
//     *
//     * @param node 当前操作的结点
//     * @param i    插入的位置
//     * @param k    要插入的关键字
//     */
//    public void insertBTree(int i, int k) {
//        int finished = 0;
//        int needNewRoot = 0;
//        int center = 0;
//        BTreeNode node;
//        //根结点不存在，构建根节点
//        node = new BTreeNode();
//        BTreeNode newNode = null;
//        int tmp = 0;
//        while (needNewRoot == 0 && finished == 0) {
//            insert(node, i, k, newNode);
//            //判断是否需要分裂
//            if (node.keynum < this.m) {
//                //插入合法
//                finished = 1;
//            } else {
//                //当前结点关键字数目达到上限,分裂当前结点
//                center = (m + 1) / 2;
//                split(node, center, newNode);
//                tmp = node.key[center];
//                //当前结点不是根节点时还要修改当前结点的位置
//                if (node.parent != null) {
//                    node = node.parent;
//                    i = search(node, tmp);
//                } else {
//                    //根节点也分裂了，需要产生新的根节点
//                    needNewRoot = 1;
//                }
//            }
//        }
//        if (needNewRoot == 1) {
//            newRoot(this.root, tmp, node, newNode);
//        }
//    }
//
//    private void newRoot(BTreeNode node, int x, BTreeNode p, BTreeNode ap) {
//        //创建根节点
//        node = new BTreeNode();
//        node.keynum = 1;
//        node.key[1] = x;
//        node.children[0] = p;
//        node.children[1] = ap;
//        if (p != null) {
//            p.parent = node;
//        }
//        if (ap != null) {
//            ap.parent = node;
//        }
//        node.parent = null;
//    }
//
//    /**
//     * 将当前结点分裂成两个结点，数据也分成两半分别放入两个节点
//     *
//     * @param node   当前结点
//     * @param center 中心位置
//     */
//    private void split(BTreeNode node, int center, BTreeNode newNode) {
//        //分裂出的新节点
//        newNode = new BTreeNode();
//        newNode.children[0] = node.children[center];
//        //后一半数据移入新节点
//        for (int i = center + 1, j = 1; i <= node.keynum; i++, j++) {
//            newNode.key[j] = node.key[i];
//            newNode.children[j] = node.children[i];
//        }
//        newNode.keynum = node.keynum - center;
//        newNode.parent = node.parent;
//        //子节点的父指针也要修改
//        for (int i = 0; i <= newNode.keynum; i++) {
//            if (newNode.children[i] != null) {
//                newNode.children[i].parent = newNode;
//            }
//        }
//        node.keynum = center - 1;
//    }
//
//    /**
//     * 关键字k和新节点分别插入到当前结点的第i个位置的key数组和第i个位置的子节点数组
//     *
//     * @param node    当前结点
//     * @param i       位置
//     * @param k       关键字
//     * @param newNode 新节点
//     */
//    private void insert(BTreeNode node, int i, int k, BTreeNode newNode) {
//        for (int j = node.keynum; j >= i; j--) {
//            //关键字后移，腾出i位置
//            node.key[j + 1] = node.key[j];
//            node.children[j + 1] = node.children[j];
//        }
//        //向当前结点的i位置插入关键字和子结点
//        node.key[i] = k;
//        node.children[i] = newNode;
//        if (newNode != null) {
//            newNode.parent = node;
//        }
//        node.keynum++;
//    }
//
//    public static void main(String[] args) {
//        BTree bTree = new BTree(3);
//        bTree.insertBTree(null, 1, 3);
//
//    }
//
//
//}
