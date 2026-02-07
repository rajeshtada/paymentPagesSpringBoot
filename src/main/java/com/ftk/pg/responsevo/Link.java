package com.ftk.pg.responsevo;

import java.util.Map;

public  class Link {
    private String href;
    private String rel;
    private String method;
    private Parameters parameters;

    // Getters and setters
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Link [href=" + href + ", rel=" + rel + ", method=" + method + ", parameters=" + parameters + "]";
	}
    
    

    // Other getters and setters for all fields
}
