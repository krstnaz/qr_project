package com.example.qrscanner;

public class Component {
    private String title;
    private String vendor_code;

    public Component() {
    }

    public Component(String title, String vendor_code) {
        this.title = title;
        this.vendor_code = vendor_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public void setVendor_code(String vendor_code) {
        this.vendor_code = vendor_code;
    }
}
