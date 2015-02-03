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
|POST|used for updating existing objects|
|PUT|used for creating new objects|

* We don't support 'DELETE' method. Data can only be invalidated (through POST).
* We don't support 'PATCH' method. Incremental updates are done by POST.

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
Create a new user and returns error if the user already exists

`PUT /users/register`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|mobile|Long|user's mobile number|12346789000|
|password|String|SHA-HASH of user's plain text password|cd8460a5e0f2c2af596f170009bffc02df06b54d|

###### Curl Example

```
$ curl -n -X PUT https://api.puluo.com/users/register
-H "Content-Type: application/json" \
-d '{
  "mobile": "12346789000",
  "password": "cd8460a5e0f2c2af596f170009bffc02df06b54d"
}'
```

###### Response Example

```
HTTP/1.1 201 Created
Last-Modified: Sun, 01 Jan 2012 12:00:00 GMT
```

```
{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013",
  "mobile": "12346789000",
  "password": "cd8460a5e0f2c2af596f170009bffc02df06b54d",
  "created_at": "2012-01-01T12:00:00Z",
  "updated_at": "2012-01-01T12:00:00Z"
}
```

##### Login  

Login a user to the system by creating a session him.
Successful login (or the user has already logged in) returns the user's uuid and session's created_at

`POST /users/login`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|mobile|Long|user's login id|12346789000|
|password|String|SHA-HASH of user's plain text password|cd8460a5e0f2c2af596f170009bffc02df06b54d|

###### Curl Example
```
$ curl -n -X POST https://api.puluo.com/users/login
-H "Content-Type: application/json" \
-d '{
  "mobile": "12346789000",
  "password": "cd8460a5e0f2c2af596f170009bffc02df06b54d"
}'
```

###### Response Example

```
HTTP/1.1 201 Created
Last-Modified: Sun, 01 Jan 2012 12:00:00 GMT
```

```
{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013", //user's uuid
  "created_at": "2012-01-01T12:00:00Z" //session created time
}
```

##### Logout
Destroy current session and all states saved in the session.

`POST /users/logout`


###### Curl Example
```
$ curl -n -X POST https://api.puluo.com/users/logout
-H "Content-Type: application/json" 
```  

###### Response Example

```
HTTP/1.1 201 Created
Last-Modified: Sun, 01 Jan 2012 12:00:00 GMT
```

```
{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013", //user's uuid
  "duration_seconds": "12345" //session created time
}
```

##### Update Password  
Update a user's password

`POST /users/credential/update`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|password|String|SHA-HASH of user's current password|cd8460a5e0f2c2af596f170009bffc02df06b54d|
|new_password|String|SHA-HASH of user's new password|cd8460a5e0f2c2af596f170009bffc02df06b54d|

###### Curl Example
```
$ curl -n -X POST https://api.puluo.com/users/credential/update
-H "Content-Type: application/json" \
-d '{
  "password": "cd8460a5e0f2c2af596f170009bffc02df06b54d",
  "new_password": "cd8460a5e0f2c2af596f170009bffc02df06b54d"
}'
```
###### Response Example

```
HTTP/1.1 201 Created
Last-Modified: Sun, 01 Jan 2012 12:00:00 GMT
```

```
{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013",
  "created_at": "2012-01-01T12:00:00Z",
  "updated_at": "2012-01-01T12:00:00Z"
}
```

### User  

##### User Profile 

Get a user's complete profile

`GET /users/{user_mobile_or_uuid}`

###### Curl Example

```

$ curl -n -X GET https://api.puluo.com/users/{user_mobile_or_uuid}
```

###### Response Example

```
HTTP/1.1 200 Created
Last-Modified: Sun, 01 Jan 2012 12:00:00 GMT
```

```
{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013",
  "public_info":{
  	"firstName": Tracey,
    "lastName":  Boyd,
	"thumbnail": "http://upyun/puluo/userThumb.jpg!200",
	"largeImage": "http://upyun/puluo/userThumb.jpg",
	"saying": "I’ve got an app for that."
	"likes": 2,
	"banned": 0, // has the viewing user banned this users. 
	"following": 1 // Is the user following this user.
  },
  "private_info": { // this info is only visible for the logged in user
    "email": "tracey.boyd@kotebo.com",
	"sex": "m",
	"birthdate": 9/12/84,
	"occupation": "Internet Plumber",
	"country": "USA",
	"state": "Washington",
	"city": "Seattle",
	"zip": "234234",
  },
  "created_at": "2012-01-01T12:00:00Z",
  "updated_at": "2012-01-01T12:00:00Z"
}
```


##### User Profile Update
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
``` 
##### User Search  
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```


### Setting 
##### User Setting 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### User Setting Update
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```  


### Social Graph 
##### List Friends
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
``` 
##### Request Friend 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### Delete Friend
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```  
##### Deny Friend 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```


### Message  
##### Send Message 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
``` 
##### List Message
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```  
##### Search Message  
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```


### Event  
##### Event Registration 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### Event Detail 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### Event Memory 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### All Events 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### Event Search 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```

### Timeline 
##### User Timeline
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```  
##### Like Timeline
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```  
##### Comment Timeline
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### Delete Timeline Comment
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```


### Service 
##### Email
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
``` 
##### Image Upload 
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```
##### SMS  
Some description here
`API Type and Name`

###### Required Parameters

|Name|Type|Description|Example|
| ------------- |:-------------|:----- |:-----|
|||||

###### Curl Example
```
$ curl -n -X API_TYPE_NAME
-H "Content-Type: application/json" \
-d '{
  "email": "someone@example.org",
  "role": "admin"
}'
```

