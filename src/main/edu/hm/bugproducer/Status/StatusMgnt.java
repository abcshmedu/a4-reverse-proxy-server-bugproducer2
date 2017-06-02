package edu.hm.bugproducer.Status;

public class StatusMgnt {

    private MediaServiceResult result;
    private String msg;
    private int code;

    public StatusMgnt() {
        result=MediaServiceResult.MSR_INTERNAL_SERVER_ERROR;
        msg="Its over!!!";
        code=result.getCode();

    }

    public StatusMgnt(MediaServiceResult result, String msg) {
        this.result = result;
        this.msg = msg;
        this.code = result.getCode();
    }

    public MediaServiceResult getResult() {
        return result;
    }

    public void setResult(MediaServiceResult result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
