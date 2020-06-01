package com.tester.api.beans;


import java.io.Serializable;

public class ResponseBean implements Serializable {

    private String response;
    private int statusCode;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBean(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBean(String response, int statusCode) {
        this.response = response;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "response='" + response + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
