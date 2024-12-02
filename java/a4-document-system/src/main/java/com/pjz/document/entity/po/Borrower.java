package com.pjz.document.entity.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * 记录借阅人的信息
 */
public class Borrower {

    /**
     * 学号/工号
     */
    private String identityId;

    /**
     * 归还日期
     */
    private String returnDate;

    public Borrower() {
    }

    public Borrower(String identityId, String returnDate) {
        this.identityId = identityId;
        this.returnDate = returnDate;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        //先对日期进行格式化
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.returnDate = returnDate.format(pattern);
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "identityId='" + identityId + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
