/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.tools.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.*;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.FileEntity;
import org.apache.http.util.EntityUtils;


public class ORRESTTool
{
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String POST = "POST";
    private static final String BASE_URL = "BASEURL";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String URLSTART = "/api/v1";
    
    private static final String RESOURCE_PERSON = "person";
    private static final String RESOURCE_SORPERSON = "sorPerson";
    private static final String RESOURCE_SORROLE = "sorRole";
    private static final String RESOURCE_PERSON_ACTIVATION = "personActivation";
    
    private static final String ACTION_ADD = "add";
    private static final String ACTION_FORCE_ADD = "forceAdd";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_DELETE_MISTAKE = "deleteMistake";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_GET = "get";
    private static final String ACTION_LINK = "link";
    private static final String ACTION_NEWKEY = "newKey";
    private static final String ACTION_VERIFY = "verify";
    private static final String ACTION_INVALIDATE = "invalidate";   
        
    private static void usage()
    {
        System.out.println("Usage:");
        System.out.println("    ortool [-quiet] person get idType idValue");
        System.out.println("    ortool [-quiet] person link idType idValue filename");
        System.out.println("");
        System.out.println("    ortool [-quiet] sorPerson add sorSourceId filename");
        System.out.println("    ortool [-quiet] sorPerson forceAdd sorSourceId filename");
        System.out.println("    ortool [-quiet] sorPerson update sorSourceId sorPersonId filename");
        System.out.println("    ortool [-quiet] sorPerson sorPerson delete sorSourceId");
        System.out.println("    ortool [-quiet] sorPerson sorPerson deleteMistake  sorSourceId");
        System.out.println("");
        System.out.println("    ortool [-quiet] sorRole add sorSourceId sorPersonId filename");
        System.out.println("    ortool [-quiet] sorRole update sorSourceId sorPersonId sorRoleId filename");
        System.out.println("    ortool [-quiet] sorRole delete sorSourceId sorPersonId sorRoleId filename");
        System.out.println("    ortool [-quiet] sorRole deleteMistake sorSourceId sorPersonId sorRoleId filename");
        System.out.println("");
        System.out.println("    ortool [-quiet] personActivation newKey idType idValue");
        System.out.println("    ortool [-quiet] personActivation verify idType idValue key");
        System.out.println("    ortool [-quiet] personActivation invalidate idType idValue key");
        System.out.println("");
        System.out.println("  -quiet will cause only the response body to be printed, otherwise");
        System.out.println("  the response headers and timing information is included.");
        System.exit(1);
    }
    
    public static void main(String arguments[])
    {
        List argsList = new ArrayList(arguments.length);
        for (int i=0; i<arguments.length; i++) argsList.add(arguments[i]);
        
        Iterator args = argsList.iterator();
        
        boolean quiet = false;
        if (!args.hasNext()) usage();
        String quietFlag = (String)argsList.iterator().next();
        if ("-quiet".equalsIgnoreCase(quietFlag))
        {
            args.next();
            quiet = true;
        }
       
        try
        {
            //get configuration
            Properties p = readPropertiesFile();
            String url = p.getProperty(BASE_URL);
            url = url + URLSTART;
            System.out.println("baseUrl: "+url);
            String username = p.getProperty(USERNAME);
            String password = p.getProperty(PASSWORD);
        	
        	//get resource to act on
        	if (!args.hasNext()) usage();
            String resource = (String)args.next();
            System.out.println("resource: " +resource);
            
            //get action to perform
            if (!args.hasNext()) usage();
            String action =(String)args.next();
            System.out.println("action: " +action);

            //handle request           
            if (resource.equalsIgnoreCase(RESOURCE_PERSON)){
            	handlePersonRequest(url, action, args, quiet, username, password); 
            } else if (resource.equalsIgnoreCase(RESOURCE_SORPERSON)){
            	handleSoRPersonRequest(url, action, args, quiet, username, password);
            } else if (resource.equalsIgnoreCase(RESOURCE_SORROLE)){
            	handleSoRRoleRequest(url, action, args, quiet, username, password);
            } else if (resource.equalsIgnoreCase(RESOURCE_PERSON_ACTIVATION)){
            	handlePersonActivationRequest(url, action, args, quiet, username, password);
            } else {
            	usage();
            }
        }
        catch (Exception x)
        {
            System.err.println(x);
            System.exit(1);
        }
    }
    

    
    private static void request(boolean quiet, String action, String url, String username, String password, File file) throws IOException{
        if (!quiet)
        {
            System.out.println("[issuing request: " + action + " " + url + "]");
        }
    	DefaultHttpClient httpclient = new DefaultHttpClient();
    	
    	httpclient.getCredentialsProvider().setCredentials(
    			new AuthScope("localhost", 8080),
    			new UsernamePasswordCredentials(username, password));
    	
    	HttpResponse response = null;
    	
    	if (action.equalsIgnoreCase(POST)){ 
    		response = doPost(httpclient, url, file);
    	} else if (action.equalsIgnoreCase(GET)){
    		response = doGet(httpclient, url);
    	} else if (action.equalsIgnoreCase(PUT)){   	
    		response = doPut(httpclient, url, file);
    	} else if (action.equalsIgnoreCase(DELETE)){   	
    		response = doDelete(httpclient, url);
    	}
    	
    	System.out.println(response.getStatusLine());
 
    	HttpEntity entity = response.getEntity();
    	
    	if (entity != null) {   		
    		long len = entity.getContentLength();
    	    if (len != -1 && len < 2048) {
    	        System.out.println(EntityUtils.toString(entity));
    	    }
    	}
    }
    
    private static HttpResponse doPost(DefaultHttpClient httpclient, String url, File file) throws IOException{
    	HttpPost method = new HttpPost(url);
    	if (file != null){
    		FileEntity fileEntity = new FileEntity(file, "application/xml; charset=\"UTF-8\"");
    		method.setEntity(fileEntity);
    	}
    	  	
    	return httpclient.execute(method);	
    }
    
    private static HttpResponse doGet(DefaultHttpClient httpclient, String url) throws IOException{
    	HttpGet method = new HttpGet(url);
    	return httpclient.execute(method);	
    }
    
    private static HttpResponse doPut(DefaultHttpClient httpclient, String url, File file) throws IOException{
    	HttpPut method = new HttpPut(url);
    	if (file != null){
    		FileEntity fileEntity = new FileEntity(file, "application/xml; charset=\"UTF-8\"");
    		method.setEntity(fileEntity);
    	}
    	  	
    	return httpclient.execute(method);	
    }
    
    private static HttpResponse doDelete(DefaultHttpClient httpclient, String url) throws IOException{
    	HttpDelete method = new HttpDelete(url);
    	return httpclient.execute(method);	
    }
  
    
    private static void handlePersonRequest(String url, String action, Iterator args, Boolean quiet, String username, String password) throws IOException{
    	if (action.equalsIgnoreCase(ACTION_GET)){
    		if (!args.hasNext()) usage();
    		String idType = (String)args.next();
        	if (!args.hasNext()) usage();
        	String idValue = (String)args.next();
        	url = url + "/people/" + idType + "/" + idValue;
        	request(quiet, GET, url, username, password, null);   		
    	} else if (action.equalsIgnoreCase(ACTION_LINK)){
    		if (!args.hasNext()) usage();
    		String idType = (String)args.next();
        	if (!args.hasNext()) usage();
            String idValue = (String)args.next();
        	if (!args.hasNext()) usage();
            String file = (String)args.next();
    		url = url + "/people/" + idType + "/" + idValue;
    		request(quiet, POST, url, username, password, new File(file));    		
    	} else {
    		usage();
    	}
    }
    
    private static void handleSoRPersonRequest(String url, String action, Iterator args, Boolean quiet, String username, String password) throws IOException{
    	if (action.equalsIgnoreCase(ACTION_ADD)){
        	if (!args.hasNext()) usage();
            String sorSourceId = (String)args.next();
        	if (!args.hasNext()) usage();
            String file = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people";
    		request(quiet, POST, url, username, password, new File(file));   		
    	} else if (action.equalsIgnoreCase(ACTION_FORCE_ADD)){
        	if (!args.hasNext()) usage();
            String sorSourceId = (String)args.next();
        	if (!args.hasNext()) usage();
            String file = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people?force=y";
    		request(quiet, POST, url, username, password, new File(file));    		
    	} else if (action.equalsIgnoreCase(ACTION_UPDATE)){
    		if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
        	if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
        	if (!args.hasNext()) usage();
    		String file = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId;
			request(quiet, PUT, url, username, password, new File(file));    		
		} else if (action.equalsIgnoreCase(ACTION_DELETE)){
			if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
        	if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId;
			request(quiet, DELETE, url, username, password, null);    		
		} else if (action.equalsIgnoreCase(ACTION_DELETE_MISTAKE)){
			if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
        	if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId + "?mistake=true";
			request(quiet, DELETE, url, username, password, null);    		
		}  else {
    		usage();
    	}
    }
    
    private static void handleSoRRoleRequest(String url, String action, Iterator args, Boolean quiet, String username, String password) throws IOException{

    	if (action.equalsIgnoreCase(ACTION_ADD)){
    		if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String file = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId + "/roles";
    		request(quiet, POST, url, username, password, new File(file));   		  		
    	} else if (action.equalsIgnoreCase(ACTION_UPDATE)){
    		if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorRoleId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String file = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId + "/roles/" + sorRoleId;
    		request(quiet, PUT, url, username, password, new File(file));    		
    	} else if (action.equalsIgnoreCase(ACTION_DELETE)){
    		if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorRoleId = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId + "/roles/" + sorRoleId;
			request(quiet, DELETE, url, username, password, null);    		
    	} else if (action.equalsIgnoreCase(ACTION_DELETE_MISTAKE)){
    		if (!args.hasNext()) usage();
    		String sorSourceId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorPersonId = (String)args.next();
    		if (!args.hasNext()) usage();
    		String sorRoleId = (String)args.next();
    		url = url + "/sor/" + sorSourceId + "/people/" + sorPersonId + "/roles/" + sorRoleId + "?mistake=true";
    		request(quiet, DELETE, url, username, password, null);    		
    	} else {
    		usage();
    	}

    }
    
    private static void handlePersonActivationRequest(String url, String action, Iterator args, Boolean quiet, String username, String password) throws IOException{
    	if (action.equalsIgnoreCase(ACTION_NEWKEY)){
    		if (!args.hasNext()) usage();
    		String idType = (String)args.next();
        	if (!args.hasNext()) usage();
        	String idValue = (String)args.next();
        	url = url + "/people/" + idType + "/" + idValue + "/activation";
        	request(quiet, POST, url, username, password, null);   		
    	} else if (action.equalsIgnoreCase(ACTION_VERIFY)){
    		if (!args.hasNext()) usage();
    		String idType = (String)args.next();
        	if (!args.hasNext()) usage();
            String idValue = (String)args.next();
        	if (!args.hasNext()) usage();
            String key = (String)args.next();
    		url = url + "/people/" + idType + "/" + idValue + "/activation/" + key;
    		request(quiet, GET, url, username, password, null);    		
    	} else if (action.equalsIgnoreCase(ACTION_INVALIDATE)){
    		if (!args.hasNext()) usage();
    		String idType = (String)args.next();
        	if (!args.hasNext()) usage();
            String idValue = (String)args.next();
        	if (!args.hasNext()) usage();
            String key = (String)args.next();
            url = url + "/people/" + idType + "/" + idValue + "/activation/" + key;
    		request(quiet, DELETE, url, username, password, null);    		
    	} else {
    		usage();
    	}
    }
    
    private static Properties readPropertiesFile(){
    	Properties properties = new Properties(); 
    	try { properties.load(new FileInputStream("ortool.properties")); 
    	} catch (IOException e) {
    		System.out.println("Could not find ortools.properties");
    	} 

    	return properties;
	}
}
