package com.searv.mobileshop.dto;

public class SendOtpRequest {

    private String identifier;
    private String otpType; // EMAIL or MOBILE

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }}
