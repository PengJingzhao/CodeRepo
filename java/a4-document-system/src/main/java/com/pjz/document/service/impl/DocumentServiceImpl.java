package com.pjz.document.service.impl;

import com.pjz.document.dao.DocumentMapper;
import com.pjz.document.entity.po.Borrower;
import com.pjz.document.entity.po.Document;
import com.pjz.document.service.DocumentService;

import java.time.LocalDateTime;
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
    public void removeDocumentById(String documentId) {
        documentMapper.deleteDocumentById(documentId);
    }

    @Override
    public boolean borrowDocument(String documentId, String identityId, LocalDateTime returnDate) {
        //判断该文献是否存在
        Document document = documentMapper.getDocumentById(documentId);
        if (document == null) {
            //文献不存在，借阅失败
            return false;
        }
        //文献现存数量为零，借阅失败
        if (document.getAvailableCopies() <= 0) {
            return false;
        }
        //借阅成功，记录信息
        document.setTotalCopies(document.getTotalCopies() - 1);
        //更新数据
        documentMapper.updateDocument(document);
        //记录借阅人信息
        Borrower borrower = new Borrower();
        borrower.setIdentityId(identityId);
        borrower.setReturnDate(returnDate);
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
