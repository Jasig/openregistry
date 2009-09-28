require 'net/https'
require 'uri'

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

