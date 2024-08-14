package com.hospital.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//医生今日的时间安排
public class Schedule {
    //是否看诊、开始工作时间、结束工作时间，能看多少人,已经约了多少人
    private boolean isTreat;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int curAppointNum;
    private int allAppointNum;

    //给一个默认时间安排
    public Schedule() {
        isTreat = false;
        startTime = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        LocalTime time8am = LocalTime.of(8, 0);
        LocalTime time5pm = LocalTime.of(17, 0);
        startTime = today.atTime(time8am);
        endTime = today.atTime(time5pm);

        curAppointNum = 0;
        allAppointNum = 30;

    }

    public Schedule(boolean mIsTreat, LocalDateTime startTime, LocalDateTime mEndTime,
                    int mCurAppointNum, int allAppointNum, boolean aIsTreat, LocalDateTime aStartTime,
                    LocalDateTime aEndTime, int aCurAppointNum, int aAllAppointNum) {
        this.isTreat = mIsTreat;
        this.startTime = startTime;
        this.endTime = mEndTime;
        this.curAppointNum = mCurAppointNum;
        this.allAppointNum = allAppointNum;
    }


    public boolean IsTreat() {
        return isTreat;
    }

    public void setIsTreat(boolean mIsTreat) {
        this.isTreat = mIsTreat;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCurAppointNum() {
        return curAppointNum;
    }

    public void setCurAppointNum(int curAppointNum) {
        this.curAppointNum = curAppointNum;
    }

    public int getAllAppointNum() {
        return allAppointNum;
    }

    public void setAllAppointNum(int allAppointNum) {
        this.allAppointNum = allAppointNum;
    }

    public boolean checkArrange() {
        if(this.startTime.isAfter(this.getEndTime()))return false;
        return this.allAppointNum <= Doctor.maxAppointNum2Doctor && this.allAppointNum >= 0;
    }
}
