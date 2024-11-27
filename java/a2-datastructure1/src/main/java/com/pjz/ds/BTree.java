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
        //判断是否需要分裂结点
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
        for (int i = 0; i < 100; i++) {
            tree.put(i);
        }
        for (int i = 0; i < 100; i++) {
            tree.remove(i);
        }
    }

}
