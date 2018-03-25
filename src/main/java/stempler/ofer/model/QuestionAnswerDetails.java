package stempler.ofer.model;


import lombok.Data;

@Data
public class QuestionAnswerDetails {
	private Integer questionOrder;
	private String  questionCode;
	private String  questionDescription;
	private String  answerCode;
	private String  answerDescription;
	private Integer themeCode;
	private String  themeDescription;
}
