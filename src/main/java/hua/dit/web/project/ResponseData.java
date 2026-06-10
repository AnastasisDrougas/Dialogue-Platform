package hua.dit.web.project;

public class ResponseData {

    private boolean success;
    private String message;

    public ResponseData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}