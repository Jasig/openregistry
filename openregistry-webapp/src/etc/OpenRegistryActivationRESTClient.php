<?php
/*
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

//PEAR package - needs to be installed: http://pear.php.net/package/HTTP_Request2/
require_once('HTTP/Request2.php');

$openRegistryActivationAPI = new HTTP_Request2('https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation');
$openRegistryActivationAPI->setAuth('rats', 'RAT1234', HTTP_Request2::AUTH_BASIC);
$openRegistryActivationAPI->setConfig(array('ssl_verify_peer' => false, 'ssl_verify_host' => false));

//Perform an HTTP POST request
$openRegistryActivationAPI->setMethod(HTTP_Request2::METHOD_POST);
$response = $openRegistryActivationAPI->send();
printf("Generate Activation Key call status: %d\n", $response->getStatus());
printf("New Activation Key resource Location: %s\n", $response->getHeader('Location'));
printf("New Activation Key value: %s\n\n", array_pop(explode("/", $response->getHeader('Location'))) );

//Further process the activation key
$openRegistryActivationAPI->setUrl($response->getHeader('Location'));
verifyAndInvalidateActivationKey($openRegistryActivationAPI);

function verifyAndInvalidateActivationKey($activationKeyResourceClient) {
	printf("Processing the activation key..............\n\n");
	//Verify - HTTP GET
	$activationKeyResourceClient->setMethod(HTTP_Request2::METHOD_GET);
	$response = $activationKeyResourceClient->send();
	switch($response->getStatus()) {
		case 204:
			printf("The activation is valid!\n");
			break;
		default:
			printf("%s\n", $response->getBody());
	}
	
	//Now invalidate - HTTP DELETE
	$activationKeyResourceClient->setMethod(HTTP_Request2::METHOD_DELETE);
	$response = $activationKeyResourceClient->send();
	printf("The invalidation of the key returned HTTP status code: %s\n", $response->getStatus());
}

?>