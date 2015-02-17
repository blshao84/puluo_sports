在core project中，所有puluo api根据其分类定义在com.puluo.api package下。
[puluo API 文档](https://github.com/blshao84/puluo_sports/tree/master/puluo_api_design) 中的每一个API，例如 User 下的 User Profile，我们为其定义一个Java的类来实现其功能。具体规范如下：

*   命名：与该API的Result尽可能采用相同的前缀。例如，UserProfileAPI 与 UserProfileResult。
*   继承：每个API继承于PuluoAPI<T>，其中T为该API的Result类型。例如：
    `public class UserProfileAPI extends PuluoAPI<UserProfileResult>`
*   实现：
    -   父类接口：PuluoAPI<T> 中定义了一个 `public T rawResult()`的接口。这个接口返回的是这个API对应的结果对象。每个具体的API实现都需要有API的逻辑生成这个对象。在实现具体功能之前，我们可以返回 null（并在方法中标记//TODO）。
    -   成员变量：[puluo API 文档](https://github.com/blshao84/puluo_sports/tree/master/puluo_api_design)中为每个API定义了required和optional的参数，我们将每一个参数定义为一个API类的成员变量。成员变量的类型以String和primtive type为主。例如，User Profile API 在文档中定义为 `GET /users/{user_mobile_or_uuid}` 并且没有required或者optional参数，它的意思是说该API可以根据mobile或者user uuid来查询一个用户的具体信息，那么UserProfileAPI就有2个成员变量
    ```
    String mobile;
    String uuid;

    ```
    -   构造函数：一般来说构造函数由eclipse自动根据成员变量生成即可。但有些时候可能需要自定义的构造函数来parse原始的string input到具体的每一个成员变量。或者有时还可以在构造函数中对输入做一些基本检查。例如UserProfileAPI的构造函数如下：
    
    ```
    public UserProfileAPI(String mobile, String uuid) {
        super();
        this.mobile = mobile;
        this.uuid = uuid;
    }

    public UserProfileAPI(String mobileOrUUID) {
        super();
        if (isMobile(mobileOrUUID)) {
            this.mobile = mobileOrUUID;
        }
        if (isUUID(mobileOrUUID)) {
            this.uuid = mobileOrUUID;
        }
    }

    ```

    它定义了两个API：第一个API是由成员变量自动生成的。第二个API是自定义的。这个例子中，因为传入的输入可能是mobile或者uuid，构造函数中做了parse以及检查。当然其中的isMobile和isUUID暂时可以不实现。


根据以上的规范，在core中的com.puluo.api.auth，已经定义好了Auth API下的实现，也可以作为参考。