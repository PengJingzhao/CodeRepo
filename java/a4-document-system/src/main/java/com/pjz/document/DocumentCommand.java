package com.pjz.document;

import com.pjz.document.service.DocumentService;
import com.pjz.document.service.impl.DocumentServiceImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

public class DocumentCommand {

    private final static DocumentService documentService = new DocumentServiceImpl();

    private final static Scanner scanner = new Scanner(System.in);

    public static void displayMenu() throws Exception {

        System.out.println("文献管理系统");
        System.out.println("1.文献入库");
        System.out.println("2.根据文献号注销文献");
        System.out.println("3.借阅文献");
        System.out.println("4.归还文献");
        System.out.println("5.显示全部文献(凹入表)");
        System.out.println("6.退出");
        System.out.println("请输入命令编号：");

        String command = scanner.nextLine();
        executeCommand(command);

    }

    public static void executeCommand(String command) throws Exception {

        switch (command) {
            case "1":
                addDocument();
                break;

            case "2":
                removeDocument();
                break;

            case "3":
                borrowDocument();
                break;

            case "4":
                returnDocument();
                break;

            case "5":
                documentService.displayDocuments();
                Thread.sleep(1000);
                break;

            case "6":
                scanner.close();
                System.exit(0);
                break;

        }
    }

    private static void addDocument() throws Exception {
        System.out.println("请输入文献号：");
        String documentId = scanner.nextLine();

        System.out.println("请输入文献标题：");
        String title = scanner.nextLine();

        System.out.println("请输入作者：");
        String author = scanner.nextLine();

        System.out.println("请输入初始数量：");
        String quantity = scanner.nextLine();
        documentService.addDocument(documentId, title, author, Integer.parseInt(quantity));
        System.out.println("入库成功");
        Thread.sleep(1000);
    }

    private static void borrowDocument() throws Exception {
        System.out.println("请输入文献号：");
        String documentId = scanner.nextLine();
        System.out.println("请输入学号/工号：");
        String identityId = scanner.nextLine();
        boolean result = documentService.borrowDocument(documentId, identityId, LocalDateTime.now());
        if (result) {
            System.out.println("借阅成功");
        } else {
            System.out.println("借阅失败");
        }
        Thread.sleep(1000);
    }

    private static void removeDocument() throws Exception {
        System.out.println("请输入文献号：");
        String documentId = scanner.nextLine();
        documentService.removeDocumentById(documentId);
        System.out.println("注销成功");
        Thread.sleep(1000);
    }

    private static void returnDocument() throws Exception {
        System.out.println("请输入文献号：");
        String documentId = scanner.nextLine();
        System.out.println("请输入学号/工号：");
        String identityId = scanner.nextLine();
        boolean result = documentService.returnDocument(documentId, identityId);
        if (result) {
            System.out.println("归还成功");
        } else {
            System.out.println("归还失败");
        }
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        try {
            while (true) {
                displayMenu();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
