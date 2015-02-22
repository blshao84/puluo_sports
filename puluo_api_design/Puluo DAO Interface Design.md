### Authentication 

##### Register
* 功能：Create a new user and returns error if the user already exists
* Entity: PuluoUser
* Entity DAO
  - PuluoUser
    + `public PuluoUser getByMobile(String mobile)`
    + `public boolean save(String mobile, String password)`

##### Login  

* 功能：Login a user to the system by creating a session him.
Successful login (or the user has already logged in) returns the user's uuid and session's created_at
* Entity: PuluoUser, PuluoSession
* Entity DAO:
  - PuluoUser
    + `public PuluoUser getByMobile(String mobile)`
  - PuluoSession
    + `public PuluoSession getBySessionID(String sessionID)`
    + `public boolean save(String sessionID)`

##### Logout
Destroy current session and all states saved in the session.

##### Update Password  
Update a user's password


### User  

##### User Profile 

Get a user's complete profile




##### User Profile Update

Update user profile



##### User Search  

Search user by parameters. If more parameters are specified, search conditions are 'AND' together.



### Setting 

##### User Setting 

Get user's setting


##### User Setting Update
Update user setting



### Social Graph 

##### List Friends

Get a user's own friends list


##### Request Friend 

Send a private message of type 'FriendRequest' to another user



##### Delete Friend

De-friend two users. Delete all their past messages.



##### Deny Friend 

Deny a friend request.



##### Approve Friend 

Approve a friend request.



### Message  

##### Send Message 

Current user send a message to his/her friend
Currently, we only support text messages.



##### List Message

Get messages from a specific user since a specific time



### Event  

##### Event Registration 

Create orders and returns links to alipay



##### Event Detail 

Get detail information of an event



##### Event Memory 

Get list of user image links of an event



##### Events Search 

If keyword is specified, search events from name and description and then 
return 'max_count' sorted and filtered events from the 'n'-th item.

If keyword is not specified, return all events with sorting and filter.



### Timeline 

##### User Timeline

Get a list of events user attended/to attend



##### Like Timeline

Like an event on a user's timeline



##### Remove Timeline Like

Remove a like to an event on a user's timeline



##### Comment Timeline

Add a new comment or reply to a comment from user's timeline


##### Delete Timeline Comment
Delete a comment from user's timeline



### Service 

##### Email

Send notification email to users

##### Image Upload 

Upload images to server



##### SMS  

Send notification SMS to users

