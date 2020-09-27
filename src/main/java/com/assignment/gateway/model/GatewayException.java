package com.assignment.gateway.model;

public class GatewayException extends RuntimeException {
    private Integer statusCode;

    public GatewayException(){
        super("Internal Server Error");
        this.statusCode = 500;
    }

    public GatewayException(Integer statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
