package com.hospital.demo;

import java.util.ArrayList;

class Department {
    private int id;//科室id
    private String name;//科室名
    private String desc;//科室简介

    private static int totalDepartmentID;//唯一自增id属性
    private static final ArrayList<Department> DEPARTMENTS;

    static {
        totalDepartmentID = 0;
        DEPARTMENTS = new ArrayList<>();
    }

    //初始化DEPARTMENTS
    public static void init() {
        //默认存在两个科室
        addDepartment("外科", "处理各种外科疑难杂病");
        addDepartment("内科", "全国最厉害的内科科室");
    }

    public Department() {
    }

    public Department(int id, String name, String desc) {
        this();
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static int getTotalDepartmentID() {
        return totalDepartmentID;
    }

    public static void setTotalDepartmentID(int totalDepartmentID) {
        Department.totalDepartmentID = totalDepartmentID;
    }

    public static ArrayList<Department> getDepartments() {
        return DEPARTMENTS;
    }

    public static void setDepartments(ArrayList<Department> departments) {
        Department d = new Department();
    }

    //按顺序输出已有科室信息
    public static void printDepartments() {
        System.out.println("-----科室信息-----");
        for (int i = 0; i < Department.getDepartments().size(); i++) {
            System.out.println(i + 1 + "、 " + Department.getDepartments().get(i).getName()
                    + "     " + Department.getDepartments().get(i).getDesc());
        }
    }


    //新增科室
    public static boolean addDepartment(String departmentName, String departmentDesc) {
        //检查是否已经存在，或者与已有科室冲突
        for (int i = 0; i < Department.getDepartments().size(); i++) {
            if (Department.getDepartments().get(i).getName().equals(departmentName)) {
                return false;
            }
        }
        Department d = new Department();
        d.setId(totalDepartmentID++);
        d.setName(departmentName);
        d.setDesc(departmentDesc);
        DEPARTMENTS.add(d);
        return true;
    }

    //按照index删除科室
    public static void removeDepartment(int index) {
        DEPARTMENTS.remove(index);
    }

    //按照对象删除
    public static void removeDepartment(Department department) {
        DEPARTMENTS.remove(department);
    }

}
