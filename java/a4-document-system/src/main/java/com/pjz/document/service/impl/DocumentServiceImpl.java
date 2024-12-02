package com.pjz.document.service.impl;

import com.pjz.document.dao.DocumentMapper;
import com.pjz.document.entity.po.Document;
import com.pjz.document.service.DocumentService;

import java.util.Date;

public class DocumentServiceImpl implements DocumentService {

    private static final DocumentMapper documentMapper = new DocumentMapper();

    @Override
    public void addDocument(String documentId, String title, String author, int quantity) {
        Document document;
        //根据文献好查询文献是否存在
        document = documentMapper.getDocumentById(documentId);

        if (document != null) {
            //文献存在，更新库存量即可
            document.setAvailableCopies(document.getAvailableCopies() + quantity);
            document.setTotalCopies(document.getTotalCopies() + quantity);
            documentMapper.updateDocument(document);
            return;
        }
        //文献不存在，需要增加到库中
        document = new Document();
        document.setId(documentId);
        document.setDocumentId(documentId);
        document.setTitle(title);
        document.setAuthor(author);
        document.setAvailableCopies(document.getAvailableCopies() + quantity);
        document.setTotalCopies(document.getTotalCopies() + quantity);

        documentMapper.addDocument(document);
    }

    @Override
    public void removeDocument(String documentId) {

    }

    @Override
    public boolean borrowDocument(String documentId, String borrowerId, Date returnDate) {
        return false;
    }

    @Override
    public boolean returnDocument(String documentId, String borrowerId) {
        return false;
    }

    @Override
    public void displayDocuments() {
        documentMapper.display();
    }
}
