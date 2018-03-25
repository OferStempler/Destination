package stempler.ofer.service;

import lombok.extern.log4j.Log4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
@Log4j
public class DynamicJsonExplorer {

	private Properties properties;
	private JSONObject jsonObject;
	private Map<Object, Object> map;

	//-----------------------------------------------------------------------------------------------------------------
	public DynamicJsonExplorer (){
		properties = new Properties();
		map = new HashMap<>();
	}
	//-----------------------------------------------------------------------------------------------------------------
	public String getValue(Object fieldToSearch){
		if (map.containsKey(fieldToSearch)){
			return String.valueOf(map.get(fieldToSearch) );
		}else{
			return null;
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	public  void parse(String jsonString) throws Exception {
		try {

			JSONParser jsonParser = new JSONParser();
			Reader r = new StringReader(jsonString);

			// Object object = jsonParser.parse(new FileReader(file));
			Object object = jsonParser.parse(r);
			jsonObject = (JSONObject) object;

			parseJson(jsonObject);

		} catch (Exception ex) {

//			log.error("DynamicJsonExplorer.parse() - Exception [" + ex.getMessage() + "]", ex);
			throw ex;
		}

	}
	//-----------------------------------------------------------------------------------------------------------------
	public  void getArray(Object object2) throws Exception {

		JSONArray jsonArr = (JSONArray) object2;

		for (int k = 0; k < jsonArr.size(); k++) {
			if (jsonArr.get(k) instanceof JSONObject) {
				parseJson((JSONObject) jsonArr.get(k));
			} else {
//				log.debug("DynamicJsonExplorer.getArray() - Key=" + k + ", value=" + jsonArr.get(k));
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
//	public  void parseJson(JSONObject jsonObject) throws ParseException {
//		log.debug("\n");
//		log.debug("DynamicJsonExplorer.parseJson() - Json Object:[" + jsonObject + "],   Number Of Elements: " + (jsonObject.size()-1) );
//
//		@SuppressWarnings("unchecked")
//		Set<Object> set = jsonObject.keySet();
//
//		Iterator iterator = set.iterator();
//
//		while (iterator.hasNext()) {
//
//			Object obj = iterator.next();
//
//			if (jsonObject.get(obj) instanceof JSONArray) {
//				getArray(jsonObject.get(obj));
//			} else {
//				Object o1 = jsonObject.get(obj);
////				log.debug("Key=" + obj + ", Value : " + o1);
//				if (o1 instanceof JSONObject) {
//					parseJson((JSONObject) o1);
//				} else {
//					map.put(obj , o1);
//					log.debug("DynamicJsonExplorer.parseJson() - LIEF Key:[" + obj + "],\t Value:[" + o1 + "]");
//				}
//			}
//
//		}
//	}
	public  void parseJson(JSONObject jsonObject) throws ParseException, Exception  {
//		log.debug("\n");
//		log.debug("DynamicJsonExplorer.parseJson() - Json Object:[" + jsonObject + "],   Number Of Elements: " + (jsonObject.size()-1) );

		@SuppressWarnings("unchecked")
		Set<Object> set = jsonObject.keySet();

		Iterator<?> iterator = set.iterator();

		while (iterator.hasNext()) {

			Object obj = iterator.next();

			if (jsonObject.get(obj) instanceof JSONArray) {
				getArray(jsonObject.get(obj));
			} else {
				Object o1 = jsonObject.get(obj);
//				log.debug("Key=" + obj + ", Value : " + o1);
				//if (o1 instanceof JSONObject) {
				if ( o1 != null && o1 instanceof JSONObject ) {
					parseJson((JSONObject) o1);
				} else {
					if ( o1 != null && String.class.isAssignableFrom(o1.getClass()) && ((String)o1).contains("{") ){
						parse((String)o1);
					}
					map.put(obj , o1);
//					log.debug("DynamicJsonExplorer.parseJson() - LIEF Key:[" + obj + "],\t Value:[" + o1 + "]");
				}
			}

		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public  String getAllKeysAsSortedString() {
		StringBuffer sb = new StringBuffer(); 
		TreeSet<Object> set = new TreeSet<Object>(this.map.keySet());
		Collections.synchronizedSortedSet(set);
		return set.toString().trim().replaceAll(" ", "");
//		for (Map.Entry<Object, Object> entry : this.map.entrySet() ){
//			sb.append(entry.getKey()).append("|");
////			log.debug(entry.getKey() + "--->" + entry.getValue());
//		}
//		return sb.toString();
	}
	//-----------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		//File file = new File("c:/t.json");
		String h = "\"idType\"              : \"1\",";
		 String json = "{"
				       +  "\"idType\"              : \"1\","
			 +  		 "\"idNumber\"            : \"038645672\","
			 +  		 "\"businessProcess\"     : \"Mortgages\","
			 +  		 "\"userProcessId\"       : \"6845519\","
			 +  		 "\"requiredDocId\"       : \"123489\","
			 +  		 "\"requiredDocTypeCode\" : \"125\","
			 +  		 "\"requiredDocTypeDesc\" : \"תלוש\""
             +  	 "";
		
		
//		FileReader fr = new FileReader(file);
//		BufferedReader br = new BufferedReader(fr);
//		String line = null;
//		StringBuilder sb = new StringBuilder();
//		while ((line = br.readLine()) != null) {
//			sb.append(line);
//		}
//		br.close();
		try {
			DynamicJsonExplorer d = new DynamicJsonExplorer();
			d.parse(json);
			String s = (String)d.getValue("idNumber");
//			log.debug(s);
			s = (String)d.getValue("userProcessId");
//			log.debug(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	public Map<Object, Object> getMap() {
		return map;
	}
}
