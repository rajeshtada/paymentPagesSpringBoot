package com.ftk.pg.vo.axis;


import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSet {
	@XmlElement(name = "Table1")
	private List<Table1> table1;

	public List<Table1> getTable1() {
		return table1;
	}

	public void setTable1(List<Table1> table1) {
		this.table1 = table1;
	}

	public static DataSet fromXML(String xml) throws JAXBException {
		// Remove checksum if present
		int endIndex = xml.indexOf("</DataSet>") + "</DataSet>".length();
		if (endIndex > 0 && endIndex <= xml.length()) {
			xml = xml.substring(0, endIndex);
		}

		JAXBContext context = JAXBContext.newInstance(DataSet.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (DataSet) unmarshaller.unmarshal(new StringReader(xml));
	}

	@Override
	public String toString() {
		return "DataSet{" + "table1="
				+ (table1 != null ? table1.stream().map(Table1::toString).collect(Collectors.joining(", ")) : "null")
				+ '}';
	}
}
