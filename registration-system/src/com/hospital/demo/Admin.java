package com.hospital.demo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Admin extends User {
    public static Admin admin; //只有一个管理员
    public static Scanner sc;

    static {
        admin = new Admin("管理员", 55, '男', "admin", "admin");
        sc = new Scanner(System.in);
    }

    private Admin() {
    }

    private Admin(String name, int age, char gender, String accountID, String password) {
        super(name, age, gender, accountID, password);
    }

    public void adminOperator() {
        while (true) {
            try {
                System.out.println("-----管理员操作界面-----");
                System.out.println("1、查看科室");
                System.out.println("2、新增科室");
                System.out.println("3、删除科室");
                System.out.println("4、删除医生");
                System.out.println("5、退出");
                System.out.println("请输入操作序号：");

                int flag = sc.nextInt();
                switch (flag) {
                    case 1:
                        Department.printDepartments();
                        break;
                    case 2:
                        addDepartment();
                        break;
                    case 3:
                        removeDepartment();
                        break;
                    case 4:
                        removeDoctor();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("您的输入有误，请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("您的输入有误，请重新输入：");
            }
        }

    }

    private void addDepartment() {
        while (true) {
            try {
                System.out.println("---新增科室---");
                System.out.println("输入科室名：");
                String name = sc.next();
                System.out.println("输入科室描述");
                String description = sc.next();
                if (Department.addDepartment(name, description)) {
                    System.out.println("科室创建成功！");
                    break;
                } else {
                    System.out.println("科室创建失败，原因：重复科室");
                    System.out.println("是否再次尝试创建：输入“是”再次尝试创建：");
                    char flag = sc.next().charAt(0);
                    if (flag != '是') {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("您的输入有误，请重新输入：");
            }
        }


    }

    private void removeDepartment() {
        while (true) {
            try {
                System.out.println("-----删除科室-----");
                Department.printDepartments();
                System.out.println("请输入需要删除的科室序号，输入0退出：");
                int index = sc.nextInt();
                if (index == 0) {
                    return;
                }
                if (index < 1 || index > Department.getDepartments().size()) {
                    System.out.println("输入错误，请重新输入：");
                    continue;
                }
                Department department = Department.getDepartments().get(index - 1);
                System.out.println("请注意，删除科室会将该科室的所有医生删除");
                System.out.println("是否继续删除" +
                        department.getName() + "科室，输入“是”继续该操作");
                char flag = sc.next().charAt(0);
                if (flag != '是') {
                    System.out.println("删除科室操作取消！");
                    break;
                }
                //移除该科室下的所有医生
                if (!Doctor.removeDoctorForDepartment(department)) {
                    System.out.println("删除该科室失败，请联系开发人员，输入“exit”退出当前页面");
                    String exit = sc.next();
                    if (exit.equals("exit")) {
                        break;
                    }
                }
                //删除科室
                Department.removeDepartment(department);
                System.out.println(department.getName()
                        + "科室及该科室的医生已经被全部删除");
                break;
            } catch (InputMismatchException e) {
                System.out.println("非法输入，" + sc.nextLine() + "请重新输入：");
            }
        }
    }


    private void removeDoctor() {
        while (true) {
            //选择科室
            System.out.println("-----删除医生账户-----");
            System.out.println("请选择科室,输入“0”退出");
            Department.printDepartments();

            //查看科室下的所有医生
            try {
                int departmentId = sc.nextInt();
                if (departmentId < 0 || departmentId > Department.getDepartments().size()) {
                    System.out.println("您的输入有误，请重新输入：");
                    continue;
                }
                if (departmentId == 0) {
                    break;
                }
                ArrayList<Doctor> specialDoctors = Doctor.getDoctorInfoByDepartment(
                        Department.getDepartments().get(departmentId - 1));
                if (specialDoctors.isEmpty()) {
                    System.out.println("当前科室没有医生");
                    continue;
                }
                //展示该科室的所有医生
                while (true) {
                    System.out.println(Department.getDepartments().get(departmentId - 1).getName() + "科室下的医生有：");
                    for (int i = 0; i < specialDoctors.size(); i++) {
                        System.out.println(i + 1 + "、" + specialDoctors.get(i).getName());
                    }
                    System.out.println("请选择需要删除的医生序号,输入0退出：");
                    int doctorId = sc.nextInt();
                    if (doctorId < 0 || doctorId > specialDoctors.size()) {
                        System.out.println("非法输入，请重新输入");
                        continue;
                    }
                    if (doctorId == 0) {
                        return;
                    }
                    break;
                }
                //删除该医生
                if (Doctor.removeDoctorForDepartment(Department.getDepartments().get(departmentId - 1))) {
                    System.out.println("删除成功！");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("非法输入" + sc.nextLine() + "，请重新输入");
            }
        }

    }

}
