package stempler.ofer.model;

import lombok.Data;

import java.util.List;

@Data
public class SetMortgageDecisionsRequest {

 private String  leadNum;
 private Integer IdType;
 private String  IdNumber;
 private List<QuestionAnswerDetails> decisionTreeArray;
}
