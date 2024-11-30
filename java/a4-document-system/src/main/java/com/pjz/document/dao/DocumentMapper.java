package com.pjz.document.dao;

import com.pjz.document.entity.po.Document;
import com.pjz.document.entity.structure.BTree;

public class DocumentMapper {

    private static BTree<Integer, Document> bTree = new BTree<>(6);

    public static void addDocument(Document document) {
        bTree.insert(document.getId(), document);
    }

    public static void deleteDocument(Document document) {
        bTree.delete(document.getId());
    }

    public static void updateDocument(Document document) {
        bTree.insert(document.getId(), document);
    }

}
