package com.pjz.ds;

import java.util.Scanner;

public class BTreeCommand {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用 BTree 控制台界面！");
        System.out.print("请输入 BTree 的阶数（m）：");

        // 获取阶数
        int m = scanner.nextInt();
        BTree<Integer, String> bTree = new BTree<>(m);

        while (true) {
            System.out.println("\n请选择操作：");
            System.out.println("1. 插入 (key, value)");
            System.out.println("2. 删除 key");
            System.out.println("3. 查找 key");
            System.out.println("4. 打印 BTree");
            System.out.println("5. 退出");
            System.out.print("请输入操作编号：");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // 插入操作
                    System.out.print("请输入 key (整数)：");
                    int key = scanner.nextInt();
                    System.out.print("请输入 value (字符串)：");
                    String value = scanner.next();
                    bTree.insert(key, value);
                    System.out.println("插入成功！");
                    break;

                case 2:
                    // 删除操作
                    System.out.print("请输入要删除的 key (整数)：");
                    int delKey = scanner.nextInt();
                    bTree.delete(delKey);
                    System.out.println("删除完成！");
                    break;

                case 3:
                    // 查找操作
                    System.out.print("请输入要查找的 key (整数)：");
                    int searchKey = scanner.nextInt();
                    String result = bTree.get(searchKey);
                    if (result != null) {
                        System.out.println("找到 key 对应的 value：" + result);
                    } else {
                        System.out.println("未找到 key！");
                    }
                    break;

                case 4:
                    // 打印 BTree
                    System.out.println("当前 BTree 的结构：");
                    bTree.show();
                    break;

                case 5:
                    // 退出
                    System.out.println("退出程序，再见！");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("无效输入，请重新选择！");
            }
        }
    }
}
