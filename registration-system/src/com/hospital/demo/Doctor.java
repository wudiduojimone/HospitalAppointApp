package com.hospital.demo;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Doctor extends User {
    public static int maxAppointNum2Doctor = 50;

    private Department department;
    private Schedule todaySchedule;

    public static ArrayList<Doctor> doctorList;

    public static Scanner sc;
    private static final DateTimeFormatter formatter;

    static {
        doctorList = new ArrayList<>();
        sc = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public Doctor() {
    }

    public Doctor(String name, int age, char gender, String userName, String password, Department department) {
        super(name, age, gender, userName, password);
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment() {
        this.department = registerDoctorDepartment();
    }

    public Schedule getTodaySchedule() {
        return todaySchedule;
    }

    public void setTodaySchedule(Schedule todaySchedule) {
        this.todaySchedule = todaySchedule;
    }

    public static Doctor registerDoctor() {
        Doctor doctor = new Doctor();
        doctor.inputAttributes();
        doctor.setDepartment();
        doctor.setTodaySchedule(new Schedule());
        return doctor;
    }


    //医生选择科室信息
    private Department registerDoctorDepartment() {
        while (true) {
            try {
                System.out.println("请选择您的科室:");
                Department.printDepartments();
                int departmentId = sc.nextInt();
                if (departmentId > 0 && departmentId < Department.getDepartments().size()) {
                    return Department.getDepartments().get(departmentId - 1);
                }
                System.out.println("您的输入有误，请重新输入");
            } catch (InputMismatchException e) {
                System.out.println("您的输入有误，请重新输入");
            }
        }
    }

    //输出医生基本信息及当日排班信息
    public void printDoctorInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("-------------------");
        System.out.println(this.getName().charAt(0) + "医生的今日排班计划如下：");
        Schedule s = this.getTodaySchedule();
        System.out.println("是否出诊：" + (s.IsTreat() ? "是" : "否"));
        if (s.IsTreat()) {
            System.out.println("出诊时间：" + s.getStartTime().format(formatter) + "---" +
                    s.getEndTime().format(formatter));
            System.out.println("剩余可挂诊号数：" + (s.getAllAppointNum() - s.getCurAppointNum()));
        }
    }

    //返回某个科室的所有医生信息
    public static ArrayList<Doctor> getDoctorInfoByDepartment(Department department) {
        ArrayList<Doctor> res = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if (doctor.getDepartment().getId() == department.getId()) {
                res.add(doctor);
            }
        }
        return res;
    }


    public void doctorOperator() {
        while (true) {
            try {
                System.out.println("----医生操作界面----");
                System.out.println("1、我的信息");
                System.out.println("2、排班");
                System.out.println("3、查看当前排班");
                System.out.println("4、退出");
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        printUserInfo();
                        break;
                    case 2:
                        arrangeSchedule();
                        break;
                    case 3:
                        printDoctorInfo();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("您的输入有误，请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("非法输入，请重新输入：");
            }
        }
    }

    private void arrangeSchedule() {
        System.out.println("----今日排班-----");
        while (true) {
            char flag = 0;
            System.out.println("您今天是否看诊，输入是/否：");
            flag = sc.next().charAt(0);
            if (flag == '是') {
                this.getTodaySchedule().setIsTreat(true);
                break;
            } else if (flag == '否') {
                this.getTodaySchedule().setIsTreat(false);
                return;
            } else {
                System.out.println("输入有误，请重新输入");
            }
        }
        while (true) {
            try {
                LocalDate today = LocalDate.now();
                System.out.println("请输入今日上班时间,（格式 时:分}：");
                String startTimeStr = sc.next();
                LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
                this.getTodaySchedule().setStartTime(today.atTime(startTime));

                System.out.println("请输入今日下班时间,（格式 时:分}：");
                String endTimeStr = sc.next();
                LocalTime endTime = LocalTime.parse(endTimeStr, formatter);
                this.getTodaySchedule().setStartTime(today.atTime(endTime));

                System.out.println("请输入您今日预计有多少号，输入0到50间的整数：");
                int treatedNum = sc.nextInt();
                this.getTodaySchedule().setAllAppointNum(treatedNum);

                if (this.getTodaySchedule().checkArrange()) {
                    System.out.println("排班成功！");
                    break;
                }
                ;
                System.out.println("您的排班计划有问题，请仔细检查并重新输入");
            } catch (InputMismatchException e) {
                System.out.println("您的输入有错误，请重新输入，错误：" + sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("时间格式输入错误,请确保输入时间格式为：HH:mm");
            }
        }
    }

    public static boolean removeDoctorForDepartment(Department department) {
        if (doctorList.isEmpty()) return true;
        return doctorList.removeIf(d -> d.getDepartment().getId() == department.getId());
    }
}
