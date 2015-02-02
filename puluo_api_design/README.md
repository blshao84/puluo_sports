# HTTP API Design Guide

## Introduction

This guide describes the design of Puluo APIs: common practices among all APIs and each API's input/output format. 
The primary purpose of this set of APIs is to support front-end mobile applications. 

### Overview  
This section describes how Puluo API works in general and practices we use among all APIs.
##### Authentication  
We use token based authorization for all APIs. Each request must be associated with a valid (not expired) token,
and the server is always able to match a token to a user in the system. 
A token can only be acquired by 'login' api. 
##### Caching
ETag is not supported in this version.  
##### Clients 
Clients **MUST** address requests using HTTPS.
##### cURL Examples
cURL examples are not available in this version.
##### Errors  
Failing responses will have an appropriate status and a JSON body containing more details about a particular error.
See error responses for more example ids.

|Name		| Type	Description	| Example |
| ------------- |:-------------:| :-----|
|id	    	| string 			| id of error raised	| "rate_limit" |
|message	| string			| end user message of error raised	| "Your account reached the API limit. Please wait a few minutes before making new requests" |
|url		| string			| reference url with more information about the error	| https://devcenter.heroku.com/articles/platform-api-reference#rate-limits |

Note that the url is included only when relevant and may not be present in the response.

##### Error Response 
```
{
  "id":       "rate_limit",
  "message":  "Your account reached the API rate limit.",
  "url":      "https://api.puluo.com/articles/rate_limit"
}
```
##### Methods  
|Method|Usage|
| ------------- |:-------------:| 
|GET|used for retrieving lists and individual objects|
|PATCH|used for updating existing objects|
|POST|used for creating new objects|

We don't support 'DELETE' method. Data can only be invalidated (through PATCH).
##### Parameters  
Values that can be provided for an action are divided between optional and required values. 
Parameters should be JSON encoded and passed in the request body.
##### Ranges 
**We don't support Ranges in this version.**

List requests will return a Content-Range header indicating the range of values returned. 
Large lists may require additional requests to retrieve. 
If a list response has been truncated you will receive a 206 Partial Content status and the Next-Range header set. 
To retrieve the next range, repeat the request with the Range header set 
to the value of the previous request’s Next-Range header.
##### Request-ID  
Each API response contains a unique request id in the Request-Id header to facilitate tracking. 
When reporting issues, providing this value makes it easier to pinpoint problems 
and provide solutions more quickly.
##### Status 
The result of responses can be determined by returned status.
###### Successful Responses

|Status	| Description|
| ------------- |:-------------| 
|200 OK	|request succeeded |
|201 Created|resource created, for example a new app was created or an add-on was provisioned|
|202 Accepted|request accepted, but the processing has not been completed|
|206 Partial Content|request succeeded, but this is only a partial response, see ranges|
###### Error Responses
Error responses can be divided in to two classes. 
Client errors result from malformed requests and should be addressed by the client. 
Puluo errors result from problems on the server side and must be addressed internally.

**Client Error Responses**

|Status	|Error ID|Description|
| ------------- |:-------------:| :-----|
|400 Bad Request|bad_request|request invalid, validate usage and try again|
|401 Unauthorized|unauthorized|	request not authenticated, session token is missing, invalid or expired|
|403 Forbidden|	forbidden|request not authorized, provided credentials do not provide access to specified resource|
|403 Forbidden|	suspended|request not authorized, account or application was suspended.|
|404 Not Found	|not_found|request failed, the specified resource does not exist|
|406 Not Acceptable|not_acceptable|	request failed, set Accept: application/vnd.heroku+json; version=3 header and try again|
|416 Requested Range Not Satisfiable|requested_range_not_satisfiable|request failed, validate Content-Range header and try again|
|422 Unprocessable Entity|invalid_params|	request failed, validate parameters try again|

**Puluo Error Responses**

|Status|Description|
| ------------- |:-------------| 
|500 Internal Server Error|error occurred, we are notified, but contact support if the issue persists|
|503 Service Unavailable|API is unavailable, check response body or Puluo status for details|

### Authentication 

##### Register  
##### Login  
##### Logout  
##### Update Password  


### User  

##### User Profile  
##### User Memory 
##### User Profile Update 
##### User Search  


### Setting 
##### User Setting 
##### User Setting Update  


### Social Graph 
##### List Friends 
##### Request Friend 
##### Delete Friend  
##### Deny Friend 


### Message  
##### Send Message  
##### List Message  
##### Search Message  


### Event  
##### Event Registration 
##### Event Detail 
##### Event Memory 
##### All Events 
##### Event Search 


### Timeline 
##### User Timeline  
##### Like Timeline  
##### Comment Timeline
##### Delete Timeline Comment


### Service 
##### Email 
##### Image Upload 
##### SMS  


