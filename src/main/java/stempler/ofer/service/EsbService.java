package stempler.ofer.service;

import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXB;
import java.io.StringReader;
@Log4j
@Service
public class EsbService {

	
	public Object sendRequestToEsb(Class<?> requestClass , Object requestObject, Class<?> responseClass  ){
		
		
		String url = "http://devsoasrv1:7001/JerusalemBank/WobiServices/Proxy_Services/WobiGetOffer";
		//String url = config.getUrl....	     
	     try {
	    	 JSONObject o = XML.toJSONObject(requestObject.toString());
	    	 
		     RestTemplate rt = new RestTemplate();
		  
		     //create Xml String from object given and its class.
//		     JAXBContext jaxRequest = JAXBContext.newInstance(requestClass);
//	    	 StringWriter stringWriter = new StringWriter();
//	    	 Marshaller marshaller = jaxRequest.createMarshaller();	    	 
//	    	 marshaller.marshal(requestObject, stringWriter);
//	    	 String requestXmlString = stringWriter.toString();
	    	 
//		     JSONObject o = new JSONObject(requestObject);
//				String content = XML.toString(o);
//				System.out.println(content);
		     
	    	 //build a rest template with the request String 
	    	 HttpEntity<String> request = new HttpEntity<String>(requestObject.toString());
	    	 ResponseEntity<String> responseHttpEntity = rt.postForEntity(url, request, String.class);
			 String replyString = responseHttpEntity.getBody();
			System.out.println(replyString);
			 //Create a resonse object from the post response
				Object result =  JAXB.unmarshal(new StringReader(replyString), responseClass);
				
				return result;
//				response.setResult(result);
	     }catch (Exception e) {
			log.error("Could not send request to ESB. " + e);
		}
		return null;
	}
//public Object sendRequestToEsb(Class<?> requestClass , Object requestObject, Class<?> responseClass  ){
//		
//		
//		
//        String url = "http://devsoasrv1:7001/JerusalemBank/GeneralServices/proxy_services/XMLTest";
//		//String url = config.getUrl....	     
//	     try {
//	    	 
//		     RestTemplate rt = new RestTemplate();
//		  
//		     //create Xml String from object given and its class.
//		     JAXBContext jaxRequest = JAXBContext.newInstance(requestClass);
//	    	 StringWriter stringWriter = new StringWriter();
//	    	 Marshaller marshaller = jaxRequest.createMarshaller();	    	 
//	    	 marshaller.marshal(requestObject, stringWriter);
//	    	 String requestXmlString = stringWriter.toString();
//	    	 
//	    	 //build a rest template with the request String 
//	    	 HttpEntity<String> request = new HttpEntity<String>(requestXmlString);
//	    	 ResponseEntity<String> responseHttpEntity = rt.postForEntity(url, request, String.class);
//			 String replyString = responseHttpEntity.getBody();
//			
//			 //Create a resonse object from the post response
//				Object result =  JAXB.unmarshal(new StringReader(replyString), responseClass);
//				
//				return result;
////				response.setResult(result);
//	     }catch (Exception e) {
//			log.error("Could not send request to ESB. " + e);
//		}
//		return null;
//	}
}
