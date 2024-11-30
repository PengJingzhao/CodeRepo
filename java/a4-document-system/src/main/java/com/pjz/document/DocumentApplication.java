package com.pjz.document;

import com.pjz.document.entity.structure.BTree;

public class DocumentApplication {
    public static void main(String[] args) {
        BTree<Integer, String> bTree = new BTree<>(6);
        bTree.insert(1, "a");
        bTree.show();
        bTree.insert(1, "b");
//        for (int i = 0; i <= 100; i++) {
//            bTree.insert(i, "hello" + i);
//        }
//        bTree.show();
//        for (int i = 0; i <= 99; i++) {
//            bTree.delete(i);
//        }
//        bTree.delete(2);
        bTree.show();
    }
}
