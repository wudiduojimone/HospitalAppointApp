package com.hospital.demo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppointManager {
    public static AppointManager appointManager = new AppointManager();//单例

    User curUser;

    Scanner sc = new Scanner(System.in);

    private AppointManager() {
        Department.init();
    }

    public void start() {
        System.out.println("=====欢迎进入医院挂号系统=====");
        while (true) {
            loginPrimer();
        }
    }


    //登陆引导页面
    private void loginPrimer() {
        OUT:
        while (true) {
            System.out.println("-------------");
            System.out.println("请选择您的操作:");
            System.out.println("1、登录");
            System.out.println("2、注册");
            System.out.println("3、退出当前界面");
            try {
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        break OUT;
                    default:
                        System.out.println("您的输入有误，请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("您的输入 " + sc.nextLine() + " 有误，请重新输入");
            }
        }
    }

    //账号注册
    private void register() {
        User user = registerUser();
        if (user instanceof Patient) {
            Patient.patientList.add((Patient) user);
        }
        if (user instanceof Doctor) {
            Doctor.doctorList.add((Doctor) user);
        }
    }

    private User registerUser() {
        System.out.println("------欢迎进入用户注册页面-----");
        User user;
        OUT:
        while (true) {
            try {
                System.out.println("--请选择您的身份,输入0退出:");
                System.out.println("1、病人");
                System.out.println("2、医生");
                int flag = sc.nextInt();
                switch (flag) {
                    case 0:
                        return null;
                    case 1:
                        user = Patient.registerPatient();
                        break OUT;
                    case 2:
                        user = Doctor.registerDoctor();
                        break OUT;
                    default:
                        System.out.println("您的输入有误，请重新输入：");
                }
            } catch (InputMismatchException e) {
                System.out.println("您的输入有误，错误：" + sc.nextLine());
            }
        }
        System.out.println("恭喜您注册成功，请牢记您的账号及密码，账号为：" + user.getAccountID() + ",密码为：" + user.getPassword());
        return user;
    }



    private void login() {
        while (true) {
            try {
                System.out.println("-----登陆界面-----");
                System.out.println("请输入您的账号：");
                String accountID = sc.next();
                System.out.println("请输入您的密码：");
                String password = sc.next();

                curUser = checkUserLogin(accountID, password);
                if (curUser != null) {
                    userOperator();
                    break;
                }
                System.out.println("您的账号或者密码输入有误，请重新输入");
            } catch (InputMismatchException e) {
                System.out.println("您的账号或者密码输入有误，请重新输入,错误信息：" + sc.nextLine());
            }
        }
    }

    private User checkUserLogin(String accountID, String password) {
        if (findUser(Patient.patientList, accountID, password) != null) {
            return findUser(Patient.patientList, accountID, password);
        } else if (findUser(Doctor.doctorList, accountID, password) != null) {
            return findUser(Doctor.doctorList, accountID, password);
        } else {
            return (Admin.admin.getAccountID().equals(accountID) &&
                    Admin.admin.getPassword().equals(password)) ? Admin.admin : null;
        }
    }

    private <T extends User> User findUser(ArrayList<T> userList, String accountID, String password) {
        for (T user : userList) {
            if (user.getAccountID().equals(accountID) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    //用户操作
    private void userOperator() {
        System.out.println("尊敬的" + curUser.getName().charAt(0) + (curUser.getGender() == '男' ? "先生" : "女士")
                + "，欢迎您进入操作页面，请选择您的操作：");
        if (curUser instanceof Patient) {
            ((Patient) curUser).patientOperator(Doctor.doctorList);
        } else if (curUser instanceof Doctor) {
            ((Doctor) curUser).doctorOperator();
        } else {
            ((Admin) curUser).adminOperator();
        }
    }
}