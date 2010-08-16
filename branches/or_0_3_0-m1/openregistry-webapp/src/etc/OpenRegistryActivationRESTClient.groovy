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

import groovyx.net.http.RESTClient

@Grab(group='org.codehaus.groovy', module='http-builder', version='0.5.0-SNAPSHOT')
def withActivationResource(closure) {
    openRegistryActivationAPI = new RESTClient('https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation')
    openRegistryActivationAPI.auth.basic 'rats', 'RAT1234'
    
    //Perform an HTTP POST request        
    def response = openRegistryActivationAPI.post([:])
    println "Generate Activation Key call status: $response.status"
    println "New Activation Key resource ${response.getFirstHeader('Location')}"
    //Now parse the actual activation key value - in Groovy easy as 1,2,3 :-)
    println "New Activation Key value: ${response.getFirstHeader('Location').value[-1..-8].reverse()}"
    
    //Now pass the activation resource URI RESTClient to the passed in closure for further processing
    openRegistryActivationAPI.uri = response.getFirstHeader('Location').value
    closure(openRegistryActivationAPI)
}

//Verify and invalidate                    
withActivationResource {
   //Verify - HTTP GET
   def response = it.get([:])   
   switch (response.status) {
       case 204:
           println 'The activation key is valid!'
           break;
       default:
           println "$response.data"
   }
   
   //Now invalidate (and print the status, right inline using powerful Groovy GString) - HTTP DELETE
   println "The invalidation of the key returned HTTP status code: ${it.delete([:]).status}"    
}   
    
     