package com.pjz.document;

import com.pjz.document.entity.structure.BTree;

public class DocumentApplication {
    public static void main(String[] args) {
        BTree bTree = new BTree(4);
        for (int i = 0; i < 100; i++) {
            bTree.insert(i);
        }
        bTree.show();
    }
}
