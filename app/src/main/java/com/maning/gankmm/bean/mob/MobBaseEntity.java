package com.maning.gankmm.bean.mob;

import java.io.Serializable;

/**
 * Created by maning on 2017/4/11.
 */

public class MobBaseEntity<T> implements Serializable {
    private static final long serialVersionUID = -4553802208756427393L;

    private String msg;

    private String retCode;

    private T result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MobBaseEntity{" +
                "msg='" + msg + '\'' +
                ", retCode='" + retCode + '\'' +
                ", result=" + (result == null ? "" : result.toString())+
                '}';
    }
}
