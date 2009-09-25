# httplib2 library - needs to be installed: http://code.google.com/p/httplib2/
import httplib2

def verifyAndInvalidateActivationKey(activation_key_resource_client, activation_key_resource_uri):
	print 'Processing the activation key..............\n'
	
	#Verify - HTTP GET
	resp, response_content = activation_key_resource_client.request(activation_key_resource_uri, "GET")
	if resp['status'] == '204':
		print 'The activation is valid'
	else:
		print response_content
	
	#Now invalidate - HTTP DELETE
	response_tuple = activation_key_resource_client.request(activation_key_resource_uri, "DELETE")
	print "The invalidation of the key returned HTTP status code: %s" % response_tuple[0]['status']		

#Main script body
open_registry_activation_api = httplib2.Http()
open_registry_activation_api.add_credentials('rats', 'RAT1234')

#Perform an HTTP POST request
response_tuple = open_registry_activation_api.request("https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation", "POST")
print "Generate Activation Key call status: %s" % response_tuple[0]['status']
print "New Activation Key resource Location: %s" % response_tuple[0]['location']
print "New Activation Key value: %s\n" % response_tuple[0]['location'].rpartition('/')[2]

#Further process the activation key
verifyAndInvalidateActivationKey(open_registry_activation_api, response_tuple[0]['location'])
