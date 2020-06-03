package com.tester.api.beans;


import org.apache.http.Header;

import java.io.Serializable;

public class ResponseBean implements Serializable {

    private String response;
    private int statusCode;
    private Header[] allHeaders;

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

    public Header[] getAllHeaders() {
        return allHeaders;
    }

    public void setAllHeaders(Header[] allHeaders) {
        this.allHeaders = allHeaders;
    }

    public ResponseBean(String response, int statusCode, Header[] allHeaders ) {
        this.response = response;
        this.statusCode = statusCode;
        this.allHeaders = allHeaders;
    }

    public ResponseBean(int statusCode) {
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
