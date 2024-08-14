package com.hospital.demo;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class User {
    private String name;
    private int age;
    private char gender;

    private String accountID;
    private String password;

    private static Scanner sc = new Scanner(System.in);

    public User() {
    }

    public User(String name, int age, char gender, String accountID, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.accountID = accountID;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAllAttributes(String name, int age, char gender, String accountID, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.accountID = accountID;
        this.password = password;
    }

    public void printUserInfo() {
        System.out.println("----个人信息---");
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
        System.out.println("性别: " + gender);
        System.out.println("账号: " + accountID);
        System.out.println("密码: " + password);
    }

    public void inputAttributes() {
        System.out.println("------欢迎进入用户注册页面-----");
        while (true) {
            try {
                System.out.println("请输入您的姓名：");
                String name = sc.next();
                System.out.println("请输入您的年龄：");
                int age = sc.nextInt();
                System.out.println("请输入您的性别：");
                char gender = sc.next().charAt(0);
                System.out.println("请设置您的登录密码");
                String password = sc.next();
                String accountID = registerNewAccID();

                setAllAttributes(name, age, gender, accountID, password);
                break;
            } catch (InputMismatchException e) {
                System.out.println("您的输入有误，请重新输入，错误：" + sc.nextLine());
            }
        }

    }

    private static String registerNewAccID() {
        Random rn = new Random();
        StringBuilder accID;
        do {
            accID = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                accID.append(rn.nextInt(10));
            }
        } while (exist(accID.toString()));
        return accID.toString();
    }

    private static boolean exist(String accID) {
        //保证账号全局唯一
        for (User patient : Patient.patientList) {
            if (patient.getAccountID().equals(accID)) return true;
        }
        for (User doctor : Doctor.doctorList) {
            if (doctor.getAccountID().equals(accID)) return true;
        }
        return Admin.admin.getAccountID().equals(accID);
    }
}
