package com.pjz.document;

import com.pjz.document.service.DocumentService;
import com.pjz.document.service.impl.DocumentServiceImpl;

public class DocumentServiceTest {

    private static final DocumentService documentService = new DocumentServiceImpl();

    public static void main(String[] args) {
        //测试文献入库
        documentService.addDocument("a123456", "测试文献一", "用户1", 1);
        documentService.addDocument("a123456", "测试文献一", "用户1", 10);
        documentService.addDocument("a100001", "测试文献二", "用户2", 100);

        //测试显示b树
        documentService.displayDocuments();
    }

}
