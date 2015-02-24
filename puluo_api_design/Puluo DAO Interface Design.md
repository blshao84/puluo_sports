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
* 功能：Destroy current session and all states saved in the session.
* Entity: PuluoUser, PuluoSession
* Entity DAO:
  - PuluoUser
    + `public PuluoUser getByUUID(String uuid)`
  - PuluoSession
    + `public boolean getBySessionID(String sessionID)`
    + `public boolean deleteSession(String sessionID)`


##### Update Password  
* 功能：Update a user's password
* Entity: PuluoUser, PuluoSession
* Entity DAO:
  - PuluoUser
    + `public PuluoUser getByUUID(String uuid)`
    + `public boolean updatePassword(PuluoUser user, String newPassword)`
  - PuluoSession
    + `public PuluoSession getBySessionID(String sessionID)`



### User  

##### User Profile 

* 功能：Get a user's complete profile
* Entity: PuluoUser
* Entity DAO:
  - PuluoUser
    + `public PuluoUser getByUUID(String uuid)`


##### User Profile Update

* 功能：Update user profile
* Entity: PuluoUser
* Entity DAO:
  - PuluoUser
    + `public PuluoUser getByUUID(String uuid)`
    + `public boolean updateUserProfile(PuluoUser user, PuluoUser newuser)`


##### User Search  

* 功能：Search user by parameters. If more parameters are specified, search conditions are 'AND' together.
* Entity: PuluoUser
* Entity DAO:
  - PuluoUser
    + `public PuluoUser findUser(String[] criteria)`



### Setting 

##### User Setting 

* 功能：Get user's setting


##### User Setting Update
* 功能：Update user setting



### Social Graph 

##### List Friends

* 功能：Get a user's own friends list
* Entity: PuluoUserFriendship
* EntityDAO:
  - PuluoUserFriendship
    + `public List<PuluoUserFriendship> getFriendListByUUID(String uuid)`


##### Request Friend 

* 功能：Send a private message of type 'FriendRequest' to another user
* Entity: PuluoPrivateMessage
* EntityDAO: 
  - PuluoPrivateMessage
    + `public boolean saveMessage(PuluoPrivateMessage message)`


##### Delete Friend

* 功能：De-friend two users. Delete all their past messages.
* Entity: PuluoUserFriendship
* EntityDAO:
  - PuluoUserFriendship
    + `public List<PuluoUserFriendship> deleteOneFriend(String userUUID, String frendUUID)`


##### Deny Friend 

* 功能：Deny a friend request.
* Entity: PuluoPrivateMessage
* EntityDAO: 
  - PuluoPrivateMessage
    + `public updateMessage(PuluoPrivateMessage message)`
    + `public PuluoPrivateMessage getFriendRequestMessage(String userUUID)`
    + `public boolean saveMessage(PuluoPrivateMessage message)`


##### Approve Friend 

* 功能：Approve a friend request.
* Entity: PuluoPrivateMessage
* EntityDAO: 
  - PuluoPrivateMessage
    + `public updateMessage(PuluoPrivateMessage message)`
    + `public PuluoPrivateMessage getFriendRequestMessage(String userUUID)`
    + `public boolean saveMessage(PuluoPrivateMessage message)`
  - PuluoUserFriendship
    + `public List<PuluoUserFriendship> addOneFriend(String userUUID, String frendUUID)`



### Message  

##### Send Message 

* 功能：Current user send a message to his/her friend
Currently, we only support text messages.
* Entity: PuluoPrivateMessage
* Entity DAO:
  - PuluoPrivateMessage
    + `public String sendMessage(String userUUID, PuluoPrivateMessage message)`


##### List Message

* 功能：Get messages from a specific user since a specific time
* Entity: PuluoPrivateMessage
* Entity DAO:
  - PuluoPrivateMessage
    + `public PuluoPrivateMessage[] findMessagesByUser(String userUUID)`



### Event  

##### Event Registration 

* 功能：Create orders and returns links to alipay
* Entity: PuluoEvent, PuluoUser
* Entity DAO:
  - PuluoEvent
    + `public PuluoPaymentAPI registerEvent(String userUUID, PuluoEvent event)`


##### Event Detail 

* 功能：Get detail information of an event


##### Event Memory 

* 功能：Get list of user image links of an event


##### Events Search 

* 功能：If keyword is specified, search events from name and description and then return 'max_count' sorted and filtered events from the 'n'-th item.
If keyword is not specified, return all events with sorting and filter.



### Timeline 

##### User Timeline

* 功能：Get a list of events user attended/to attend


##### Like Timeline

* 功能：Like an event on a user's timeline


##### Remove Timeline Like

* 功能：Remove a like to an event on a user's timeline


##### Comment Timeline

* 功能：Add a new comment or reply to a comment from user's timeline


##### Delete Timeline Comment
* 功能：Delete a comment from user's timeline



### Service 

##### Email

* 功能：Send notification email to users


##### Image Upload 

* 功能：Upload images to server
* Entity: PuluoEventPhoto
* EntityDAO:
  - PuluoEventPhoto
    + `public boolean saveEventPhoto(PuluoEventPhoto photo)`


##### SMS  

* 功能：Send notification SMS to users

