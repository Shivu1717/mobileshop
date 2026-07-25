package com.searv.mobileshop.dto;

public class VerifyOtpRequest {
    private String identifier;
    private String otp;
    private String otpType;

    public String getIdentifier() {
        return identifier;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
