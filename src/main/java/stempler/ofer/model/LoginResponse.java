package stempler.ofer.model;

import lombok.Data;

@Data
public class LoginResponse {

		String access_token;
	    String refresh_token;
	    String expires_in;
}
