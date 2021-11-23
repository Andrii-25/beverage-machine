package com.andrii.beveragemachine.utils;

public class Status {

    private String status;
    private String msg;

    public Status(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
