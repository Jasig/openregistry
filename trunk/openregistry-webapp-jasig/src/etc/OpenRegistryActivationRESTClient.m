#include <CoreFoundation/CoreFoundation.h>
#include "ASIHTTPRequest.h"

/* Objective-C sample HTTP cleint for Open Registry Activation RESTful resource.
 * Uses 'ASIHTTPRequest library. for installation instructions see: http://allseeing-i.com/ASIHTTPRequest/
 */

//Function prototypes
static int verify_and_invalidate_activation_key(NSURL *);
static ASIHTTPRequest * setup_request(NSURL *);

int main (int argc, const char * argv[]) {
    NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
    
	ASIHTTPRequest *request = setup_request([NSURL URLWithString:@"https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation"]);

    //Perform an HTTP POST request
    [request setRequestMethod:@"POST"];
    [request start];
    
    NSError *error = [request error];
    if(error) {
        NSLog([error description]);
        [pool drain];
        return 1;
    }
    
    printf("Generate Activation Key call status: %d\n", [request responseStatusCode]);
    printf("New Activation Key resource Location: %s\n", [[[request responseHeaders] objectForKey:@"Location"] UTF8String]);
    printf("New Activation Key value: %s\n\n", [[[[[request responseHeaders] objectForKey:@"Location"] componentsSeparatedByString:@"/"] lastObject] UTF8String]);
	
    //Further process the activation key
    int ret = verify_and_invalidate_activation_key([NSURL URLWithString:[[request responseHeaders] objectForKey:@"Location"]]);
    
    [pool drain];
	return ret;
}

static int verify_and_invalidate_activation_key(NSURL *url) {
    printf("Processing the activation key..............\n\n");
    ASIHTTPRequest *request = setup_request(url);
    //Verify - HTTP GET
    [request setRequestMethod:@"GET"];
    [request start];
    
    NSError *error = [request error];
    if(error) {
        NSLog([error description]);
        return 1;
    }
    
    if([request responseStatusCode] == 204) {
        printf("The activation code is valid!\n");
    }
    else {
        printf([[request responseString] UTF8String]);
    }
    
    //Now invalidate - HTTP DELETE
    request = setup_request(url);
    [request setRequestMethod:@"DELETE"];
    [request start];
    
    error = [request error];
    if(error) {
        NSLog([error description]);
        return 1;
    }
    
    printf("The invalidation of the key returned HTTP status code: %d\n", [request responseStatusCode]);
    return 0;
}

static ASIHTTPRequest * setup_request(NSURL *url) {
    ASIHTTPRequest *request = [[[ASIHTTPRequest alloc] initWithURL:url] autorelease];
    [request setUsername:@"rats"];
    [request setPassword:@"RAT1234"];
    [request setShouldRedirect:NO];
    [request setValidatesSecureCertificate:NO];
    return request;
}