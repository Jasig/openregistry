#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

def perform_http_request(url, request)
    http = Net::HTTP.new(url.host, url.port)
    http.use_ssl = true
    request.basic_auth "rats", "RAT1234"
    http.verify_mode = OpenSSL::SSL::VERIFY_NONE
    http.request(request)
end

#Perform an HTTP POST request
url = URI.parse 'https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation/'
request = Net::HTTP::Post.new(url.path) 
response = perform_http_request url, request
puts "Generate Activation Key call status: #{response.code}"
puts "New Activation Key resource Location: #{response['location']}"
puts "New Activation Key value: #{response['location'][%r{[^/]+\z}]}\n\n"

#Further process the activation key  
url = URI.parse response['location']
puts "Processing the activation key..............\n\n"

#Verify - HTTP GET
request = Net::HTTP::Get.new(url.path)  
response, data = perform_http_request url, request
if response.code == '204'
  puts "The activation is valid"
else 
  puts data
end  

#Now invalidate - HTTP DELETE
request = Net::HTTP::Delete.new(url.path)    
response = perform_http_request url, request
puts "The invalidation of the key returned HTTP status code: #{response.code}"

