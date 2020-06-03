package com.tester.api.beans;


public class CaseBean {
    private int id;
    private String desc;
    private String url;
    private String token;
    private String parameterType;
    private String method;
    private String params;
    private String expected;
    private Boolean isRun;


    public CaseBean(){

    }

    public CaseBean(int id, String desc, String url, String token, String parameterType, String method, String params, String expected, Boolean isRun) {
        this.id = id;
        this.desc = desc;
        this.url = url;
        this.token = token;
        this.parameterType = parameterType;
        this.method = method;
        this.params = params;
        this.expected = expected;
        this.isRun = isRun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public Boolean isRun() {
        return isRun;
    }

    public void setRun(Boolean run) {
        isRun = run;
    }

    @Override
    public String toString() {
        return "CaseBean{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                ", formData='" + parameterType + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", expected='" + expected + '\'' +
                ", isRun=" + isRun +
                '}';
    }
}
