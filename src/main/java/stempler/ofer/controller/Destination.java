package stempler.ofer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stempler.ofer.model.*;
import stempler.ofer.model.wobi.WobiResponse;
import stempler.ofer.service.EsbService;
import stempler.ofer.utils.LoadFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


@Controller 
@Log4j
public class Destination {

	private static int tries = 1;
	
	@Autowired
	EsbService esbService;
	
	@Autowired
	LoadFile loadFile;
	
	//-----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = {"/logEntry"}, method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
	PrepaidResponse logEntry(@RequestBody LogEntry logEntry, HttpServletRequest request, HttpServletResponse response) {
        log.debug("/logEntry() - logEntry:[" + logEntry + "]");
        PrepaidResponse prepaidResponse = new PrepaidResponse();
        prepaidResponse.setErrorCode("0");
        return prepaidResponse;
    }
//-----------------------------------------------------------------------------------------------------------------
	
//	  String url = "http://localhost:8085/testPrpProxy";
// 	  To test getToken in PRP-ESB
	@RequestMapping (method = RequestMethod.POST , value="/testPrpProxy")
	public @ResponseBody
	LoginResponse testProxy(@RequestParam Map<String, String> requestMap) {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccess_token("123123");
		loginResponse.setRefresh_token("567567");
		if (tries != 3) {
			loginResponse.setExpires_in("0");
		} else {
			tries = 0;
			loginResponse.setExpires_in("25");

		}
		tries++;

		return loginResponse;
	}
	
////------------------------------------------------------------------------------------------------------	
	
	@RequestMapping (method = RequestMethod.GET , value="/esb")
	public @ResponseBody
	EsbResponse testEsb (HttpServletResponse servletResponse){
		Input esbTestInput = new Input();
		EsbResponse response = new EsbResponse();
		esbTestInput.setCardStatus(123);
		esbTestInput.setClientNumber(666);
		
		String esbString = "<Input><clientNumber>123</clientNumber><CardStatus>123</CardStatus></Input>";
		Input input = new Input();
		input.setCardStatus(5);
		input.setClientNumber(5);
		
		
		Result result = (Result) esbService.sendRequestToEsb(Input.class, input, Result.class);
		response.setResult(result);
		return response;
	}

	
////------------------------------------------------------------------------------------------------------	
	@RequestMapping (method = RequestMethod.GET , value="/testWobitest")
	public @ResponseBody
	WobiResponse testWobi(HttpServletResponse servletResponse){
		
		String fileContenet = loadFile.getData();
		String fileJsonString =loadFile.getDataJson();
		
		ObjectMapper maaper = new ObjectMapper();
		Request request = maaper.convertValue(fileJsonString, Request.class);
		System.out.println(request);
		
		WobiResponse result = (WobiResponse) esbService.sendRequestToEsb(Request.class, fileContenet, WobiResponse.class);
		
		return result;
	}
	///------------------------------------------------------------------------------------------------------	
		@RequestMapping (method = RequestMethod.POST , value="/testWobiPost")
		public @ResponseBody WobiResponse testWobiPost(@RequestBody Request request){
			
			System.out.println(request.toString());
			Request requestMapped = new Request();
			String fileContenet = loadFile.getData();
			String fileJsonString =loadFile.getDataJson();
			String fileXml2 = loadFile.getDataXml2();
			ObjectMapper maaper = new ObjectMapper();
//			try {
//				 requestMapped = maaper.readValue(fileJsonString, Request.class);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(requestMapped);
			
			WobiResponse result = (WobiResponse) esbService.sendRequestToEsb(Request.class, fileContenet, WobiResponse.class);
//			WobiResponse result2 = (WobiResponse) esbService.sendRequestToEsb(Request.class, fileJsonString, WobiResponse.class);

			return result;
		}

///------------------------------------------------------------------------------------------------------	
		
		@RequestMapping (method = RequestMethod.POST, value ="/mockDocNResponse" )
		public @ResponseBody HttpEntity<String> mockDocNResponse( String content, HttpServletRequest request, HttpServletResponse response){
//			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_XML);
			headers.add("DestinationHeader", "mockGetDocNResponse");
			String mockResponse = loadFile.getMockDocNResponse();
//			
			HttpEntity<String> entity = new HttpEntity<>(mockResponse, headers);
			return entity;
		}	
////------------------------------------------------------------------------------------------------------	
	
	@RequestMapping (method = RequestMethod.POST, value ="/simpleStringResponse" )
	public @ResponseBody String simpleJsonStringResponse ( String content, HttpServletRequest request, HttpServletResponse response){
//		System.out.println(resquest.getBody());
		String systemEncoding = System.getProperty("file.encoding");
		log.debug("System Encoding is: " + systemEncoding);
		log.debug("Content Type: " + request.getContentType());
		log.debug("MessageContent: " +content);
		
		
		
//		String response = "*CLIENT RESPONSE FOR POST*";
		String jsonString = "{\"DETAILS\":{ \"NAME\" : \"WADE\", \"AGE\": 19}}";
		response.setHeader("DestinationHeader", "999");
//		String response = "<Input><clientNumber>123</clientNumber><CardStatus>123</CardStatus></Input>";

		return jsonString;
	}
////------------------------------------------------------------------------------------------------------	
	
	@RequestMapping (method = RequestMethod.POST, value ="/returnSameString" )
	public @ResponseBody HttpEntity<String> returnSameString ( String content, HttpServletRequest request, HttpServletResponse response){
		String systemEncoding = System.getProperty("file.encoding");
		log.debug("System Encoding is: " + systemEncoding);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestContent = null;
		
		//   Retrieve the input data 
		try {

			ServletInputStream sis = request.getInputStream();

			BufferedReader b = new BufferedReader(new InputStreamReader(sis));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = b.readLine()) != null) {
				builder.append(line);
			}
			requestContent = builder.toString();
//			System.out.println(requestContent);
			sis.close();
		} catch (Exception e) {
			String error = "Unable to acquire request input data" + e.getMessage();
			log.error(error);
			return new HttpEntity<>(requestContent, headers);
		}
		System.out.println(requestContent);
		HttpEntity<String> entity = new HttpEntity<>(requestContent, headers);

		return entity;
	}
////------------------------------------------------------------------------------------------------------	
	
	@RequestMapping (method = RequestMethod.POST, value ="/k300getFileGuid" )
	public @ResponseBody K300Response getFileGuid(String ip, String channelRequestName, HttpServletRequest request,Integer isLimited) {

		System.out.println(ip);
		System.out.println(channelRequestName);

		System.out.println(isLimited);
		K300Response response = new K300Response();
		response.setIsSuccess(true);
		response.setGuid("FILE-GUID");
		return response;
	}
////------------------------------------------------------------------------------------------------------	
	@RequestMapping (method = RequestMethod.POST, value ="/k300UploadFile" )
	public @ResponseBody K300Response uploadFile(MultipartFile attachmentFile, String ip, String fileGuid, String synchronous, HttpServletRequest request,Integer isLimited, String extraInfo) throws IOException {

		System.out.println(ip);
		System.out.println(fileGuid);
		System.out.println(isLimited);
		System.out.println(extraInfo);

		K300Response response = new K300Response();
//		
//		if (attachmentFile != null ){
//			String fileName = attachmentFile.getOriginalFilename();
//			InputStream inputstream = attachmentFile.getInputStream();
//			byte[] bufferFile = new byte[inputstream.available()];
//			inputstream.read(bufferFile);
//			File file = new File("C:/Users/ofers/Desktop/" + fileName );
//			file.createNewFile();
//			FileOutputStream ne = new FileOutputStream(file);
//			ne.write(bufferFile);
//			ne.close();
//			
//			
//		}

		response.setIsSuccess(true);
		response.setGuid("xxxxxxxxxxxxxxxxx");
		return response;
	}
////------------------------------------------------------------------------------------------------------	
	@RequestMapping (method = RequestMethod.POST, value ="/uploadEncode" )
	public @ResponseBody String encodeUploadedTpBase64(MultipartFile attachmentFile, String ip) throws IOException {

		String fileName = attachmentFile.getName();
		String base64 = Base64Utils.encodeToString(attachmentFile.getBytes());
		
		BufferedWriter wrtier = null;
		wrtier = new BufferedWriter(new FileWriter("C:/Users/ofers/Desktop/base64.txt" ));
		wrtier.write(base64);
		wrtier.close();
		return "ok";
	}
////------------------------------------------------------------------------------------------------------	
	@RequestMapping (method = RequestMethod.GET, value ="/resume" )
	public @ResponseBody String testResume ( String content) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = "http://accptsoasrv1:8001/JerusalemBank/GeneralServices/Proxy_Service/resumeWorkflow/WorkFlowName=KYC_SALARY_WF&InstanceId=PARAM_INSTANCE_ID&jsonData=fileName\":\"PARAM_FILE_NAME\",\"statusCode\":\"PARAM_STATUS_CODE\",\"statusDescription\":\"PARAM_STATUS_DESCRIPTION\",\"Mode\":\"EXEC\"";    
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		try {
		    System.out.println(response1.getStatusLine());
		    org.apache.http.HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity1);
		} finally {
		    response1.close();
		}
		
		return "(OK";

	}
////------------------------------------------------------------------------------------------------------	
	
	
	
	@RequestMapping (method = RequestMethod.GET, value="/ldpMockClient" )
	public ResponseEntity<String> doGET ( HttpServletResponse servletResponse){
//		System.out.println(resquest.getBody());
		servletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		servletResponse.setContentType("CLIENT CONTENT TYPE");
		String systemEncoding = System.getProperty("file.encoding");
		log.debug("System Encoding is: " + systemEncoding);
//		log.debug(content);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.ACCEPTED);
//		String response = "{\"RESPONSE\":\"Hello from client\"}";
		String response = "<Input><clientNumber>123</clientNumber><CardStatus>123</CardStatus></Input>";

		responseEntity.ok(response);
		return responseEntity;
	}
	
	
	    @RequestMapping(value = {"/book"}, method = RequestMethod.POST, produces = {"application/json"})
	    public @ResponseBody String buyBook( @RequestParam CreditCard creditCard,  @RequestParam ShippingAddress shippingAddress) {
	    	System.out.println(creditCard);
	    	System.out.println(shippingAddress);
	        return "ok";
	    }
	

	
//------------------------------------------------------------------------------------------------------	
	
//	@RequestMapping (method = RequestMethod.POST )
//	@ResponseStatus(code =HttpStatus.FORBIDDEN)
//	public @ResponseBody PrpResponse prpResponseForbidden (@RequestBody String content){
////		System.out.println(resquest.getBody());
//		String systemEncoding = System.getProperty("file.encoding");
//		log.debug("System Encoding is: " + systemEncoding);
//		log.debug(content);
//		
//		PrpResponse response = new PrpResponse();
//		response.setResponseDescription("My Error Description");
//		response.setResponseCode("907");
////		ResponseEntity<PrpResponse> response =  new ResponseEntity<response>();
////		response.se
////		
//		return response;
//
//	}
//------------------------------------------------------------------------------------------------------
	
//	@RequestMapping (method = RequestMethod.POST )
//	@ResponseStatus(code =HttpStatus.OK)
//	public @ResponseBody PrpResponse prpResponse (@RequestBody String content){
////		System.out.println(resquest.getBody());
//		String systemEncoding = System.getProperty("file.encoding");
//		log.debug("System Encoding is: " + systemEncoding);
//		log.debug(content);
//		
//		PrpResponse response = new PrpResponse();
//		response.setResponseDescription("My Error Description");
//		response.setResponseCode("907");
////		ResponseEntity<PrpResponse> response =  new ResponseEntity<response>();
////		response.se
////		
//		return response;
//
//	}
//------------------------------------------------------------------------------------------------------


//	@RequestMapping (value = "/parse", method = RequestMethod.POST )
//	public Map<Object, Object> parseJson(@RequestBody String extraInfo) {
//		DynamicJsonExplorer dynExp = null;
//		if (extraInfo != null && extraInfo.length() > 0 && !"".equals(extraInfo)) {
//			dynExp = jsonToObject( extraInfo );
//			if ( dynExp == null) {
//				log.debug("DynamicFileServiceImpl.getExtraAsMap() - extraInfo cannot be parsed - or is Null");
//				return null;
//			}else{
//				log.debug("DynamicFileServiceImpl.getExtraAsMap() - Successfully parsed a map from extraInfo json string:[" + dynExp.getMap() + "]");
//			}//else
//		}else{
//			log.debug("DynamicFileServiceImpl.getExtraAsMap() - extraInfo is null or Empty!");
//			return null;
//		}//else
//		log.debug("DynamicFileServiceImpl.getExtraAsMap() - map:[" + dynExp.getMap() + "]");
//		return dynExp.getMap();
//	}
//	
//	private DynamicJsonExplorer jsonToObject( String extraInfoJson ) {
//		DynamicJsonExplorer d = null;
//		try {
//			d = new DynamicJsonExplorer();
//			d.parse( extraInfoJson );
//		} catch (Exception e) {
//    		log.error("DynamicFileServiceImpl.jsonToObject() - got Exception:[" + e.getMessage() + "]", e);
//		}
//		return d;
//	}
//	
}
