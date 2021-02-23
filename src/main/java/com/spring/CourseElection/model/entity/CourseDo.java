package com.spring.CourseElection.model.entity;

public class CourseDo {
    private Integer id;

    private String name;

    private String teacherId;

    private String teacherName;

    private Integer credit;

    private String address;

    private String courseTime;

    private Integer capacity;

    private Integer electionNum;

    private Integer proportion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime == null ? null : courseTime.trim();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getElectionNum() {
        return electionNum == null ? 0 : electionNum;
    }

    public void setElectionNum(Integer electionNum) {
        this.electionNum = electionNum;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }
}