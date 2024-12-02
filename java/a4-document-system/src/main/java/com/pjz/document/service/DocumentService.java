package com.pjz.document.service;

import com.pjz.document.entity.po.Document;

import java.util.Date;

public interface DocumentService {
    /**
     * 入库：新购入的文献，确定文献号之后登记到系统。如果这种文献在系统中已有，则
     * 只将总库存量增加。
     * @param documentId
     * @param title
     * @param author
     * @param quantity
     */
    void addDocument(String documentId, String title, String author, int quantity);

    /**
     * 清除：某种文献己无保留价值，将它从系统中注销
     * @param documentId
     */
    void removeDocument(String documentId);

    /**
     * 借阅：如果一种文献的现存量大于零，则借出一本，登记借阅者的证件和归还期限
     * @param documentId
     * @param borrowerId
     * @param returnDate
     * @return
     */
    boolean borrowDocument(String documentId, String borrowerId, Date returnDate);

    /**
     * 归还：注销对借阅者的登记，改变该文献的现存量
     * @param documentId
     * @param borrowerId
     * @return
     */
    boolean returnDocument(String documentId, String borrowerId);

    /**
     * 显示：以凹入表的形式显示B树
     */
    void displayDocuments();
}
