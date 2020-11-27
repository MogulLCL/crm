package com.mogul.domian;

public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object d){
        Result result=new Result();
        result.setCode(200);
        result.setMsg("操作成功！");
        result.setData(d);
        return result;
    }
    public static Result success(String m,Object d){
        Result result=new Result();
        result.setCode(200);
        result.setMsg(m);
        result.setData(d);
        return result;
    }
    public static Result error(Object d){
        Result result=new Result();
        result.setCode(400);
        result.setMsg("操作失败！");
        result.setData(d);
        return result;
    }
    public static Result error(String m,Object d){
        Result result=new Result();
        result.setCode(400);
        result.setMsg(m);
        result.setData(d);
        return result;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
