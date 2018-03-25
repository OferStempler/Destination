package stempler.ofer.model;


import java.io.Serializable;


public class LogEntry implements Serializable{
	private String sourceSystem;   // as: "prepaid-matach",
	private String processName;    // as: "Daily Matach File",
	private String fileTemplate;   // as: "PREPAID_RATES",
	private String operationDate;  // as: "15/02/2018 11;  //21;  //00",
	private String operationType;  // as: "IN/OUT/PROCESS"
	private String operationDesc;  // as: "PREPAID-RATES - File found",
	private String status;         // as: "Success"
	private String fileName;
	private String fileType;
	private String fileSize;
	
	
	
	
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getFileTemplate() {
		return fileTemplate;
	}
	public void setFileTemplate(String fileTemplate) {
		this.fileTemplate = fileTemplate;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationDesc() {
		return operationDesc;
	}
	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// String status, String operationDesc, String operationType, String fileTemplate
	public LogEntry() {}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "LogEntry [sourceSystem=" + sourceSystem + ", processName=" + processName + ", fileTemplate="
				+ fileTemplate + ", operationDate=" + operationDate + ", operationType=" + operationType
				+ ", operationDesc=" + operationDesc + ", status=" + status + ", fileName=" + fileName + ", fileType="
				+ fileType + ", fileSize=" + fileSize + "]";
	}
	public LogEntry(String sourceSystem, String processName, String fileTemplate, String operationDate,
			String operationType, String operationDesc, String status, String fileName, String fileType,
			String fileSize) {
		super();
		this.sourceSystem = sourceSystem;
		this.processName = processName;
		this.fileTemplate = fileTemplate;
		this.operationDate = operationDate;
		this.operationType = operationType;
		this.operationDesc = operationDesc;
		this.status = status;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}
	
	
	
	 
}
