package stempler.ofer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "EsbResponse")
public class EsbResponse {

	@XmlElement(name="Result")
	private Result Result;

	public Result getResult() {
		return Result;
	}

	public void setResult(Result result) {
		Result = result;
	}

	@Override
	public String toString() {
		return "EsbResponse [Result=" + Result + "]";
	}
	
	
}
