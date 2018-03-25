package stempler.ofer.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Input")
@XmlAccessorType(XmlAccessType.FIELD)
public class Input {
	
	@XmlElement(name="clientNumber")
	private int clientNumber;
	
	@XmlElement(name="CardStatus")
	private int CardStatus;
	
	public int getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	public int getCardStatus() {
		return CardStatus;
	}
	public void setCardStatus(int cardStatus) {
		CardStatus = cardStatus;
	}
	@Override
	public String toString() {
		return "Input [clientNumber=" + clientNumber + ", CardStatus=" + CardStatus + "]";
	}

	
	
//	from Dor
//	<Input>
//	<clientNumber>123</clientNumber>
//	<CardStatus>123</CardStatus>
//	</Input>

	
}
