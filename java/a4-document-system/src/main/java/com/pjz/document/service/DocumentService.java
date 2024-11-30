package com.pjz.document.service;

import com.pjz.document.entity.po.Document;

import java.util.Date;

public interface DocumentService {
    void addDocument(String documentId, String title, String author, int quantity);

    void removeDocument(String documentId);

    boolean borrowDocument(String documentId, String borrowerId, Date returnDate);

    boolean returnDocument(String documentId, String borrowerId);

    void displayDocuments();
}
