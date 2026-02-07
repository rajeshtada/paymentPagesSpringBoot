package com.ftk.pg.responsevo;


import java.util.List;

public class FrmRiskResponse {
	

	 private boolean valid;
	    private List<ErrorDetail> errors;
	    private int riskScore;

	    // Getters and Setters
	    public boolean isValid() {
	        return valid;
	    }

	    public void setValid(boolean valid) {
	        this.valid = valid;
	    }

	    public List<ErrorDetail> getErrors() {
	        return errors;
	    }

	    public void setErrors(List<ErrorDetail> errors) {
	        this.errors = errors;
	    }

	    public int getRiskScore() {
	        return riskScore;
	    }

	    public void setRiskScore(int riskScore) {
	        this.riskScore = riskScore;
	    }
	    
	    
	    
	    
}
