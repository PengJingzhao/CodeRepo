package com.pjz.document.entity.po;

public class Document {
    // 属性定义
    private Integer id;//主键，用于建立索引，提升查找性能
    private String documentId;    // 文献号
    private String title;         // 文献名
    private String author;        // 著者
    private int availableCopies;  // 现存量
    private int totalCopies;      // 总库存量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // 构造函数
    public Document(String documentId, String title, String author, int availableCopies, int totalCopies) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
    }

    // Getter 和 Setter 方法
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    // 重写toString方法，方便输出文献信息
    @Override
    public String toString() {
        return "Document{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", availableCopies=" + availableCopies +
                ", totalCopies=" + totalCopies +
                '}';
    }
}
