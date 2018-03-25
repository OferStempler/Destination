package stempler.ofer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Request {
		
	 private String requestId;
	 
	    private String identification;
	 
	    private String firstName;
	 
	    private String lastName;
	 
	    private String number;
	 
	    private String institutionId;
	 
	    private String branch;
	 
	    private String accountNum;
	 
	    private String gender;
	 	 
	    private String birthDate;
	 
	    private String email;
	 
	    private String ownershipType;
	 
	    private String accountSeniority;
	 
	    private City city;
	 
	    private String street;
	 
	    private String streetNumber;
	 
	    private String postalCode;
	 
	    private String hasCreditCard;
	 
	    private String monthlyReturnDay;
	 
	    private String education;
	 
	    private String profession;
	 
	    private String role;
	 
	    private String programType;
	 
	    private String jobSeniority;
	 
	    private EmployerCity employerCity;
	 
	    private String employerName;
	 
	    private String maritalStatus;
	 
	    private String numOfChildren;
	 
	    private String personalIncome;
	 
	    private String familyIncome;
	 
	    private String numOfSupporters;
	 
	    private String mortgageAmount;
	 
	    private String isApartmentOwner;
	 
	    private String[] rentAmount;
	    
	    private String loanAmount;
	 
	    private String numOfPayments;
	 
	    private String loanPurpose;
	 
	 
	    private String isChequeDenied;
	 
	    private String extraLoan;
	 
	    private String loanBalance;
	 
	    private String savings;
	 
	    private String extraLoanMonthlyAmount;
	 
	    private String detailsOfLoanPurpose;
	 
	    private String bankRefusalToCredit;
	    
	    private String bankRefusalToCreditDetails;
}
