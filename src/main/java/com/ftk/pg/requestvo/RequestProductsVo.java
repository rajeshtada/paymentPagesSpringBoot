package com.ftk.pg.requestvo;

import java.io.Serializable;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestProductsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671631092726983930L;

	@XmlElement(name = "product")
	private List<RequestProductVo> products;

	public List<RequestProductVo> getProducts() {
		return products;
	}

	public void setProducts(List<RequestProductVo> products) {
		this.products = products;
	}

}