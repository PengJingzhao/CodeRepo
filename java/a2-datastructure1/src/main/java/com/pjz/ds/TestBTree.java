package com.pjz.ds;

public class TestBTree {
    public static void main(String[] args) {
        //初始化b树
        BTree<String, Integer> bTree = new BTree<>(4);
        //插入节点
        for (int i = 0; i < 50; i++) {
            bTree.insert(String.valueOf(i), i);
        }
        System.out.println("插入结点后的b树：");
        bTree.show();

        //删除结点
        System.out.println("删除第10个结点后的b树：");
        bTree.delete("10");
        bTree.show();

        //根据键值查询节点
        System.out.println("测试查询结点：");
        Integer val = bTree.get("25");
        System.out.println(val);
        //打印b树
        System.out.println("测试打印b树：");
        bTree.show();
    }
}
