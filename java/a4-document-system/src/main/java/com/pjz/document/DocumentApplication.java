package com.pjz.document;

import com.pjz.document.entity.structure.BTree;

public class DocumentApplication {
    public static void main(String[] args) {
        BTree<String, String> bTree = new BTree<>(6);
        bTree.insert("a1", "a");
        bTree.show();
        bTree.insert("a2", "b");
        for (int i = 0; i <= 100; i++) {
            bTree.insert("a" + i, "hello" + i);
        }
//        bTree.show();
//        for (int i = 0; i <= 99; i++) {
//            bTree.delete(i);
//        }
//        bTree.delete(2);
//        bTree.show();
        System.out.println(bTree.get("a80"));
    }
}
