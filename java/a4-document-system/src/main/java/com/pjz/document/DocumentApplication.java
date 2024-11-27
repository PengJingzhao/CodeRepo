package com.pjz.document;

import com.pjz.document.entity.structure.BTree;

public class DocumentApplication {
    public static void main(String[] args) {
        BTree bTree = new BTree(4);
        for (int i = 0; i <= 10; i++) {
            bTree.insert(i);
        }
//        bTree.show();
//        for (int i = 0; i <= 10; i++) {
//            bTree.delete(i);
//        }
        bTree.delete(2);
        bTree.show();
    }
}
