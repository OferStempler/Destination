package stempler.ofer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShippingAddress {

	
	private String steert;
	private String houseNumber;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	

}
