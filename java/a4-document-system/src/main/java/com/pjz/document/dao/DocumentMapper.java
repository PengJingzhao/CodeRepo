package com.pjz.document.dao;

import com.pjz.document.entity.po.Document;
import com.pjz.document.entity.structure.BTree;

public class DocumentMapper {

    private static final BTree<String, Document> bTree = new BTree<>(6);

    public void addDocument(Document document) {
        bTree.insert(document.getId(), document);
    }

    public void deleteDocument(Document document) {
        bTree.delete(document.getId());
    }

    public void updateDocument(Document document) {
        //先找出原来存储的数据
        Document old = bTree.get(document.getId());
        //新旧数据合并一下
        document.merge(old);
        //覆盖旧数据
        bTree.insert(document.getId(), document);
    }

    public Document getDocumentById(String id) {
        return bTree.get(id);
    }

    public void display() {
        bTree.show();
    }

}
