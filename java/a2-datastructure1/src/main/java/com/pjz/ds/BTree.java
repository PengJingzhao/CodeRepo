package com.pjz.ds;

public class BTree<K extends Comparable<K>, V> {

    /**
     * 阶数,最大子节点数，m=2*t
     */
    private final int m;

    /**
     * 最小度数，实际为m/2
     */
    private final int t;

    /**
     * b树的根节点
     */
    private Node<K,V> root;

    /**
     * 每个结点最多存储的关键字个数
     */
    private final Integer MAX_KEY_NUMS;

    /**
     * 每个结点最少要存储的关键字个数
     */
    private final Integer MIN_KEY_NUMS;

    public BTree(int m) {
        this.m = m;
        this.t = m / 2;
        this.root = new Node<>(m);
        this.MAX_KEY_NUMS = m - 1;
        //最小关键字数应该是对m/2-1向上取整
        this.MIN_KEY_NUMS = (int) Math.ceil((float) m / 2 - 1);
    }

    /**
     * 新增关键字
     *
     * @param key 关键字
     */
    public void insert(K key, V val) {
        doInsert(null, root, 0, key, val);
    }

    /**
     * 向当前结点的keys数组的index位置处插入key关键字
     *
     * @param parent 当前节点的父节点
     * @param cur    当前结点
     * @param index  分裂结点时的基准，也是当前结点位于父节点children数组中的位序
     * @param key    关键字
     */
    private void doInsert(Node<K,V> parent, Node<K,V> cur, int index, K key, V val) {
        //寻找正确的插入位置
        int i = 0;
        while (i < cur.keyNum && cur.keys[i].key.compareTo(key) < 0) {
            i++;
        }
        //若关键字已存在则直接覆盖
        if (cur.keys[i] != null && cur.keys[i].key.compareTo(key) == 0) {
            cur.insertKey(key, val, i);
            return;
        }
        //如果是叶子节点，就可以插入了，如果是非叶子结点就要下移寻找
        if (cur.isLeaf) {
            //插入关键字
            cur.insertKey(key, val, i);
        } else {
            //向下寻找
            doInsert(cur, cur.children[i], i, key, val);
        }
        //在当前节点插入后还要判断关键字个数是否已经达到上限
        if (cur.keyNum == MAX_KEY_NUMS) {
            //分裂结点
            split(parent, cur, index);
        }
    }

    /**
     * @param parent 当前节点的父节点
     * @param cur    待分裂的节点
     * @param index  分裂的基准位置
     */
    private void split(Node<K,V> parent, Node<K,V> cur, int index) {
        //当前结点是根节点，就要另外创建新的根节点，作为当前结点的父节点
        if (parent == null) {
            parent = new Node<>(m);
            parent.isLeaf = false;
            parent.insertChild(cur, 0);
            root = parent;
        }
        //创建当前结点的右兄弟节点,并且将当前结点的一半关键字复制到兄弟节点中
        Node<K,V> rightBro = new Node<>(m);
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
        cur.keyNum = t - 1;
        //更新父节点
        Entry<K,V> entry = cur.keys[t - 1];
        parent.insertKey(entry.key, entry.val, index);
        parent.insertChild(rightBro, index + 1);
    }

    /**
     * 删除指定关键字key
     *
     * @param key 要删除的关键字
     */
    public void delete(K key) {
        doDelete(null, root, 0, key);
    }

    /**
     * 删除关键字key
     *
     * @param parent 当前结点的父节点
     * @param cur    当前结点
     * @param index  用于记录目标位置的基准位置
     * @param key    要删除的关键字
     */
    private void doDelete(Node<K,V> parent, Node<K,V> cur, int index, K key) {
        //首先要获取指定元素
        int i = 0;
        while (i < cur.keyNum && cur.keys[i].key.compareTo(key) < 0) {
            i++;
        }

        if (cur.isLeaf) {
            if (cur.keys[i].key.compareTo(key) == 0) {
                //叶子节点,并且已经匹配上，直接删除
                cur.removeKey(i);
            } else {
                //到达了叶子结点，但是仍旧没找到，结束查找
                return;
            }
        } else {
            //非叶子节点
            if (cur.keys[i].key.compareTo(key) == 0) {
                //在非叶子结点处匹配成功
                //找到后驱节点并交换位置
                Node<K,V> child = cur.children[i + 1];
                while (!child.isLeaf) {
                    child = child.children[0];
                }
                Entry<K,V> nextKey = child.keys[0];
                cur.keys[i] = nextKey;
                doDelete(cur, child, i + 1, nextKey.key);
            } else {
                //非叶子结点处未匹配，继续向下查找
                doDelete(cur, cur.children[i], i, key);
            }
        }
        //判断是否需要合并结点
        if (cur.keyNum < MIN_KEY_NUMS) {
            //当前节点的节点数不足，需要借用其他结点的关键字或者合并两个节点
            balance(parent, cur, index);
        }
    }

    /**
     * 调整B树
     *
     * @param parent 父结点
     * @param cur    被调整节点
     * @param index  被调整节点在父结点中的孩子数组下标
     */
    private void balance(Node<K,V> parent, Node<K,V> cur, int index) {
        if (cur == root) {
            if (cur.keyNum == 0 && cur.children[0] != null) {
                root = cur.children[0];
            }
            return;
        }
        //通过在index两边的子节点之间迁移关键字来将关键字数量维持在合理范围内
        Node<K,V> leftChild = parent.leftSibling(index);
        Node<K,V> rightChild = parent.rightSibling(index);
        //向左兄弟借关键字
        if (leftChild != null && leftChild.keyNum > t - 1) {
            //将父结点中的key赋值给cur
            Entry<K,V> entry = parent.keys[index - 1];
            cur.insertKey(entry.key, entry.val, 0);
            if (!leftChild.isLeaf) {
                //如果左侧孩子不是一个叶子节点，在旋转过后，会导致keysNumber+1！=children。
                //因此将多出来的孩子赋值更多出来一个key的被调整节点
                cur.insertChild(leftChild.removeRightmostChild(), 0);
            }
            //将左孩子中最右侧元素赋值给父结点
            parent.keys[index - 1] = leftChild.removeRightmostKey();
            return;
        }
        //向右兄弟借关键字
        if (rightChild != null && rightChild.keyNum > t) {
            Entry<K,V> entry = parent.keys[index];
            cur.insertKey(entry.key, entry.val, cur.keyNum);
            if (!rightChild.isLeaf) {

                cur.insertChild(rightChild.removeLeftmostChild(), cur.keyNum);
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
            Entry<K,V> entry = parent.removeKey(index - 1);
            leftChild.insertKey(entry.key, entry.val, leftChild.keyNum);
            cur.moveToTarget(leftChild);
        } else {
            //如果没有左兄弟，那么移除右兄弟节点，并将右兄弟移动到被删除节点上。
            parent.removeChild(index + 1);
            Entry<K,V> entry = parent.removeKey(index);
            cur.insertKey(entry.key, entry.val, cur.keyNum);
            assert rightChild != null;
            rightChild.moveToTarget(cur);
        }
    }

    public V get(K key) {
        return doGet(key, root);
    }

    private V doGet(K key, Node<K,V> cur) {
        int i = 0;
        while (i < cur.keyNum && cur.keys[i].key.compareTo(key) < 0) {
            i++;
        }

        //匹配成功
        if (cur.keys[i] != null && cur.keys[i].key.compareTo(key) == 0) {
            return (V) cur.keys[i].val;
        }
        //当前节点没找到，向下匹配
        if (cur.isLeaf) {
            //到叶子结点也没有找到
            return null;
        } else {
            //非叶子节点
            //下到子节点继续匹配
            return doGet(key, cur.children[i]);
        }
    }

    static class Entry<K extends Comparable<K>, V> {
        K key;
        V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "[" + key + ":" + val + "]";
        }
    }

    static class Node<K extends Comparable<K>, V> {
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
//        int[] keys;
        Entry<K, V>[] keys;

        /**
         * 孩子节点数组,最大长度为2*t
         */
        Node<K,V>[] children;

        int m;

        public Node(int m) {
            this.t = m / 2;
            this.m = m;
//            this.keys = new int[t * 2 - 1];
            this.keys = new Entry[m - 1];
            this.children = new Node[m];
        }

        /**
         * 在指定位置插入关键字
         *
         * @param key   关键字
         * @param index 目标位置
         */
        public void insertKey(K key, V val, int index) {
            //在index处腾出空位
            System.arraycopy(keys, index, keys, index + 1, keyNum - index);
            //插入关键字
            if (keys[index] != null && !(keys[index].key.compareTo(key) == 0)) {
                //不是覆盖
                //维护keyNum
                keyNum++;
            } else if (keys[index] == null) {
                keyNum++;
            }
//            keys[index] = key;
            keys[index] = new Entry<K, V>(key, val);
        }

        /**
         * 当前结点插入子节点
         *
         * @param child 新的子节点
         * @param index 插入位置
         */
        void insertChild(Node<K, V> child, int index) {
            System.arraycopy(children, index, children, index + 1, keyNum - index);
            children[index] = child;
        }

        /**
         * 删除指定位置的关键字
         *
         * @param index 指定位置
         * @return 被移除的关键字
         */
        Entry<K,V> removeKey(int index) {
//            int key = keys[index];
            Entry<K,V> key = keys[index];
            System.arraycopy(keys, index + 1, keys, index, (keyNum - 1) - index);
//            keys[keyNum] = -1000;
            keyNum--;
            return key;
        }


        /**
         * 获取指定位置的左孩子节点
         *
         * @param index 指定的位置
         * @return 返回相应的左孩子结点
         */
        Node<K,V> leftSibling(int index) {
            if (index <= 0) {
                return null;
            }
            return children[index - 1];
        }


        /**
         * 获取指定位置的右孩子节点
         *
         * @param index 获取指定位置的右孩子结点
         * @return 返回相应的右孩子结点
         */
        Node<K,V> rightSibling(int index) {
            if (index == keyNum) {
                return null;
            }
            return children[index + 1];
        }


        /**
         * 移除当前节点最左边的关键字
         *
         * @return 返回被移除的关键字
         */
        Entry<K,V> removeLeftmostKey() {
            return removeKey(0);
        }

        //移除最右边的元素
        Entry<K,V> removeRightmostKey() {
            return removeKey(keyNum - 1);
        }

        //移除指定位置的孩子节点
        Node<K,V> removeChild(int index) {
            //获取被移除的节点
            Node<K,V> t = children[index];
            //将被移除节点的后面元素向前移动一位
            System.arraycopy(children, index + 1, children, index, keyNum - index);
            //将之前最后一位的引用释放
            children[keyNum] = null;
            //返回被引用元素
            return t;
        }

        //移除最左边的孩子节点
        Node<K,V> removeLeftmostChild() {
            return removeChild(0);
        }

        //移除最右边的孩子节点
        Node<K,V> removeRightmostChild() {
            return removeChild(keyNum);
        }

        //移动指定节点到目标节点
        void moveToTarget(Node<K,V> target) {
            int start = target.keyNum;
            if (!isLeaf) {
                if (keyNum + 1 >= 0) System.arraycopy(children, 0, target.children, start, keyNum + 1);
            }
            for (int i = 0; i < keyNum; i++) {
                target.keys[target.keyNum++] = keys[i];
            }
        }
    }

    public void show() {
        doShow(root, 0);
    }

    /**
     * 遍历打印b树
     */
    public void doShow(Node cur, int cnt) {
        for (int i = 0; i < cnt; i++) {
            //下一层
            System.out.print("\t");
        }
        //打印当前结点
        for (int i = 0; i < cur.keyNum; i++) {
            System.out.print(cur.keys[i]);
            System.out.print(" ");
        }
        System.out.println("\n");
        //遍历子结点
        if (cur.isLeaf) {
            return;
        }
        for (int i = 0; i <= cur.keyNum; i++) {
            doShow(cur.children[i], cnt + 1);
        }

    }

}
