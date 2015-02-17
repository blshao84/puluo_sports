# How to write a dummy API result?

Let's take creating a 'User profile' result for example. 
In the API document, the returned JSON of 'user profile' API is as below:

```

{
  "uuid": "de305d54-75b4-431b-adb2-eb6b9e546013",
  "public_info":{
    "first_name": Tracey,
    "last_name":  Boyd,
    "thumbnail": "http://upyun/puluo/userThumb.jpg!200",
    "large_image": "http://upyun/puluo/userThumb.jpg",
    "saying": "I’ve got an app for that."
    "likes": 2,
    "banned": 0, // has the viewing user banned this users. 
    "following": 1, // Is the user following this user.
    "is_coach":false
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
  "created_at": "2012-01-01 12:00:00",
  "updated_at": "2012-01-01 12:00:00"
}

```

* In this example, the JSON result contains sub-object, therefore we create a class for each JSON object.

1. UserProfileResult
2. UserPublicProfileResult
3. UserPrivateProfileResult

* For each result class, create a member for every Key-Value pair: 
member name is the name of the key and member type is type of the value. Most of time, we should just use string.
For example, 'created_at' should be of type string and in the code we should use TimeUtils.formatDate to convert
a DateTime object to string. Code of UserProfileResult looks like below:

```
public class UserProfileResult extends HasJSON{
	public String uuid;
	public UserPublicProfileResult public_info;
	public UserPrivateProfileResult private_info;
	public String created_at;
	public String updated_at;
	
	public UserProfileResult(String uuid, UserPublicProfileResult public_info,
			UserPrivateProfileResult private_info, String created_at,
			String updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
		this.private_info = private_info;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
}

``` 

* The outter-most result should have a toJson method which converts itself to a json string.
This is achieved by extending from 'HasJSON' class, which defines the following method:

```

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	
```

* Create a 'public static' dummy method that returns a dummy instance.

```
	public static UserProfileResult dummy() {
		return new UserProfileResult("", UserPublicProfileResult.dummy(),
				UserPrivateProfileResult.dummy(), "", "");
	}

```

* Write a test to prevent accidental result schema change. Take a look at PuluoAPIResultTest in core project.
We should add an @Test test case for each result type.
