package com.hospital.demo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Patient extends User {
    private ArrayList<AppointRecord> appointRecords;
    public static Scanner sc;

    public static ArrayList<Patient> patientList;

    static {
        patientList = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public Patient() {
        appointRecords = new ArrayList<>();
    }

    public Patient(String name, int age, char gender, String accountID, String password, ArrayList<AppointRecord> appointRecords) {
        this.appointRecords = appointRecords;
    }

    public ArrayList<AppointRecord> getAppointRecords() {
        return appointRecords;
    }

    public void setAppointRecords(ArrayList<AppointRecord> appointRecords) {
        this.appointRecords = appointRecords;
    }

    public void patientOperator(ArrayList<Doctor> doctorList) {
        OUT:
        while (true) {
            System.out.println("-------------");
            System.out.println("1、我的信息");
            System.out.println("2、挂号");
            System.out.println("3、挂号记录");
            System.out.println("4、退出");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    printUserInfo();
                    break;
                case 2:
                    appoint(doctorList);
                    break;
                case 3:
                    showAppointRecord();
                    break;
                case 4:
                    break OUT;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }

    public void appoint(ArrayList<Doctor> doctorList) {
        ONCEAPPOINT:
        while (true) {
            if (Department.getDepartments().isEmpty()) {
                System.out.println("当前没有科室信息，请联系管理员处理");
                return;
            }
            Department.printDepartments();
            System.out.println("请选择您想挂的科室，输入0退出：");
            try {
                int departmentId = sc.nextInt();
                if (departmentId == 0) {
                    break;
                }
                if (departmentId < 0 || departmentId > Department.getDepartments().size()) {
                    System.out.println("您的输入有误，请重新输入：");
                    continue;
                }
                ArrayList<Doctor> specialDoctors = Doctor.getDoctorInfoByDepartment(
                        Department.getDepartments().get(departmentId - 1));
                if (specialDoctors.isEmpty()) {
                    System.out.println("当前科室没有医生看诊。");
                    continue;
                }
                //展示该科室的所有医生
                System.out.println(Department.getDepartments().get(departmentId - 1).getName() + "科室下的医生有：");
                for (int i = 0; i < specialDoctors.size(); i++) {
                    System.out.println(i + 1 + "、" + specialDoctors.get(i).getName());
                }
                //选择医生并查看该医生的基本信息及排班挂号信息
                while (true) {
                    System.out.println("请输入您想挂号的医生序号：");
                    int doctorId = sc.nextInt() - 1;
                    if (doctorId < 0 || doctorId >= specialDoctors.size()) {
                        System.out.println("输入有误");
                        continue;
                    }
                    Doctor doctor = specialDoctors.get(doctorId);
                    if (!doctor.getTodaySchedule().IsTreat()) {
                        System.out.println(doctor.getName().charAt(0) + "医生今日不上班哦，请选择其他医生");
                        break;
                    }
                    doctor.printDoctorInfo();
                    while (true) {
                        System.out.println("--------");
                        System.out.println("是否挂号，输入是或者否：");
                        char flag = sc.next().charAt(0);
                        if (flag == '否') {
                            continue ONCEAPPOINT;
                        }
                        if (flag != '是') {
                            System.out.println("您的输入有误，请重新输入:");
                            continue;
                        }
                        Schedule s = doctor.getTodaySchedule();
                        if (s.getCurAppointNum() > s.getAllAppointNum()) {
                            System.out.println("当前医生挂号已满，请选择其他医生");
                            continue ONCEAPPOINT;
                        }
                        System.out.println("---------------");
                        System.out.println("请输入您的病情描述：");
                        String appointDesc = sc.next();

                        s.setCurAppointNum(s.getCurAppointNum() + 1);
                        System.out.println("挂号成功");
                        //给病人增加挂号记录
                        AppointRecord a = new AppointRecord(getAccountID(), getName(), doctor.getAccountID(),
                                doctor.getName(), Department.getDepartments().get(departmentId - 1), appointDesc);
                        ArrayList<AppointRecord> tmpRecords = getAppointRecords();
                        tmpRecords.add(a);
                        setAppointRecords(tmpRecords);
                        break ONCEAPPOINT;
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("您的输入:" + sc.nextLine() + "有误，请重新输入：");
            }
        }
    }

    public void showAppointRecord() {
        System.out.println("------------------");
        if (appointRecords.isEmpty()) {
            System.out.println("您当前无挂号记录");
            return;
        }
        System.out.println("当前账户挂号记录如下：");
        System.out.println("病人\t科室\t医生\t病情描述");
        for (AppointRecord record : appointRecords) {
            System.out.println(record.getPatientName() + "\t" + record.getDepartment().getName() + "\t" + record.getDoctorName() + "\t" + record.getBriefDescription());
        }
    }

    public static Patient registerPatient(){
        Patient patient = new Patient();
        patient.inputAttributes();
        return patient;
    }

}
