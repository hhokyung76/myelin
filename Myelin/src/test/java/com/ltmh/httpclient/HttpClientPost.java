package com.ltmh.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientPost {
	
	public static void main(String[] args) {
//		String id = "11";
//		String name  = "test_name";
//		String origin = "test_origin";

		String id = "idtest";
		String custId= "hhokyung";
		String custEmail = "hhokyung@gmail.com";
		String ltmhFlag = "ltmhFlagTest";
		String ltmhSubject = "BP 통합/자동화, BPEL(Business Process Execution Language)";
		String ltmhContent = "o 정의 : 프로세스 엔진에 의해 해석, 자동 실행하는 BP 정의를 위한 표준 언어(XML기반)\r\n" + 
				"    → 비즈니스 프로세스인 컴포지트 서비스를 쉽고, 직관적으로 구현(WSFL과 XLANG 발전)\r\n" + 
				"  o 특징\r\n" + 
				"    ▲플랫폼 독립적 : XML 표준을 사용, 비즈니스 프로세스 모델링 제공\r\n" + 
				"    ▲서비스 동적/병렬 호출, Human IF Component 지원\r\n" + 
				"  o 위치\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"     - WSDL 위에 존재, QoS 보장을 위해 WS-Security, WS-Reliable Messaging, WS-Transaction 사용\r\n" + 
				"  o 구성요소\r\n" + 
				"\r\n" + 
				"Primary\r\n" + 
				"Activity\r\n" + 
				"invoke\r\n" + 
				"서비스를 동기/비동기적으로 호출하고 값을 받음\r\n" + 
				"Assign\r\n" + 
				"결과 값을 받아서 임시 저장소에 저장, 변수와 동일한 역할\r\n" + 
				"Receive\r\n" + 
				"비동기적 호출에 의한 처리된 값 리턴\r\n" + 
				"Reply\r\n" + 
				"동기화 작업의 응답 생성\r\n" + 
				"Structure\r\n" + 
				"Activity\r\n" + 
				"Sequence\r\n" + 
				"일정한 순서로 호출되는 Activity 집합 정의\r\n" + 
				"Switch\r\n" + 
				"프로세스 판단에 의한 분기 처리\r\n" + 
				"While\r\n" + 
				"루프 정의\r\n" + 
				"Process\r\n" + 
				"Partners\r\n" + 
				"프로세스에 참여하는 웹서비스를 정의\r\n" + 
				"Containers\r\n" + 
				"비즈니스 프로세스에서 사용되는 변수 선언\r\n" + 
				"\r\n" + 
				"  o 활용 : ERP, CRM 등 기존 시스템의 유연한 통합, 비즈니스 프로세스 자동화 실현\r\n" + 
				"\r\n" + 
				"  ※ 비즈니스프로세스 정의언어\r\n" + 
				"    ▲WSFL(IBM) : Direct Graph 기반, SOAP/UDDI/WSDL과 호환\r\n" + 
				"    ▲XLANG(MS) : 블록구조형, BizTalk 비즈니스 모델링 언어\r\n" + 
				"    ▲BPMN : 비즈니스 프로세스 모델링 표기법, BPEL로 변환 후 웹서비스와 연계 실행\r\n" + 
				"    ▲BPSS : ebXML에서 워크플로우 통신, 오케스트레이션 정의";
		
		Map<String,String> paramMap = new HashMap<String, String>();
		//paramMap.put("id", id);
		paramMap.put("id", id);
		paramMap.put("custId", custId);
		paramMap.put("custEmail", custEmail);
		paramMap.put("ltmhFlag", ltmhFlag);
		paramMap.put("ltmhSubject", ltmhSubject);
		byte utf8Content[] = ltmhContent.getBytes();
		try {
			paramMap.put("ltmhContent",  new String(utf8Content, "UTF-8") );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//paramMap.put("origin", origin);
		String url = "http://192.168.0.44:9448/summit";
		
		HttpClientPost app = new HttpClientPost();
		app.getHttpPostData(url, paramMap);
	}
	
	private String getHttpPostData(String url, Map<String,String> paramMap) {
	    String responseBody = "";
	    try {
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        Builder builder = RequestConfig.custom();
	        builder.setConnectTimeout(4000);
	        builder.setSocketTimeout(4000);
	        builder.setStaleConnectionCheckEnabled(false);
	        RequestConfig config = builder.build();
	        
	        UrlEncodedFormEntity form;
	        try {
	            
	            HttpPost httpPost = new HttpPost(url);
	            ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
	            for (Map.Entry<String, String> entry: paramMap.entrySet()) {
	                postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            }
	            form = new UrlEncodedFormEntity(postParams,"UTF-8");
//	            form = new UrlEncodedFormEntity(postParams);
//                form.setContentEncoding(HTTP.UTF_8);
                httpPost.setEntity(form);
	            //httpPost.setEntity(new UrlEncodedFormEntity(postParams));
	            httpPost.setConfig(config);
	            
	            System.out.println("url : " + url);
	            System.out.println("Executing request " + httpPost.getRequestLine());
	 
	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	                
	                @Override
	                public String handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        String responseStr = "";
	                        if( entity != null ) {
	                             responseStr = EntityUtils.toString(entity);
	                        }
	                        return responseStr;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }
	 
	            };
	            responseBody = httpclient.execute(httpPost, responseHandler);
	            
	            System.out.println("----------------------------------------");
	            System.out.println(responseBody);
	            
	        } finally {
	            httpclient.close();
	        }
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    return responseBody;
	}
}
 
