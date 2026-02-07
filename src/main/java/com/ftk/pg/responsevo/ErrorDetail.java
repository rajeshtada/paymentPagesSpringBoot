package com.ftk.pg.responsevo;


public class ErrorDetail {

	private String fieldName;
    private String validationKey;
    private String message;
    private int fieldId;
    private int validationId;
    private String attemptId;
    private String level;

    // Getters and Setters
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValidationKey() {
        return validationKey;
    }

    public void setValidationKey(String validationKey) {
        this.validationKey = validationKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

