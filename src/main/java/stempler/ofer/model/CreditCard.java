package stempler.ofer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard {

	private String nameOnCard;
	private String number;
	private String expiryMonth;
	private String expiryYear;
	private String ccv;
	

}
