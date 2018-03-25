package stempler.ofer.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="Result")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@XmlElement(name="Res")
	private String Res;

// @XmlElement(name="Res")
	public String getRes() {
		return Res;
	}
	public void setRes(String res) {
		Res = res;
	}

	@Override
	public String toString() {
		return "Result [Res=" + Res + "]";
	}

	
	
	
}
