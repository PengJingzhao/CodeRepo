package com.pjz.document.service.impl;

import com.pjz.document.dao.DocumentMapper;
import com.pjz.document.entity.po.Document;
import com.pjz.document.service.DocumentService;

import java.util.Date;

public class DocumentServiceImpl implements DocumentService {

    private static final DocumentMapper documentMapper = new DocumentMapper();

    @Override
    public void addDocument(String documentId, String title, String author, int quantity) {

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

    }
}
