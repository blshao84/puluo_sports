
在我们的项目中，我们按照如下方式建立并使用一个与数据库中的表对应的对象。

*   场景：假设我们在数据库中有一个用户表，我们希望在app中能够查询、插入、更新、删除一个数据库的用户。
*   实现：
    *   接口：
        *   com.puluo.entity.User：在Interface中我们对于数据库表的每一项我们定义一个function。例如，user表包含id和name，那么我们User的Interface就定义：
            *   public long id();
            *   public String name();
            *   注意：这里面的interfaces有别于一个entity具体实现中的setter和getter。因为User这个interface并不涉及任何具体实现。
        *   com.puluo.dao.UserDao: 这个接口定义了对于User这个entity我们需要的所有数据库相关的操作。在上面的场景中我们应该定义4个function:
            *   public void save(User user);
            *   public void update(User user);
            *   public User getById(long id);
            *   public void deleteById(long id);
            *   注意：这里面只定义接口不涉及任何具体实现，并且所有的接口的定义只能使用User这个Interface，而不是它具体的实现。
    *   实现：
        *   com.puluo.entity.impl.UserImpl: 这里面我们定义具体一个User的实现，比如这个User有2个field，以及他们有setter和getter方法。重要的是这个UserImpl需要implements User。
        *   com.puluo.dao.impl.UserDaoImpl: 这个是对于User的数据库操作的具体实现。所有的’xxDaoImpl’ 都有这样的interface：XXDaoImpl extends DaoTemplate with XXDao。其中，DaoTempalte定义并扩展了一些基本的JDBC操作。XXDaoImpl的具体实现，则需要使用根据具体的功能构造SQL语句、调用JDBC的API并处理返回结果。
        *   DaoApi：这个类集合了所有XXDaoImpl (public static)，并且所有的数据库调用都要通过这个类来进行。例如：

        ```
        Class DaoApi {
            public static UserDao userDao = BeanFactory(User.class, “userDao”)
        }

        ```
        当其他code（例如用户注册api）需要从数据库查询一个用户的时候应该这样调用：
        `User user  = DaoApi.userDao.getById(id)`
        
        *   注意：DaoApi的类型也都是Interface而非具体实现，由于我们使用的是spring的IOC来管理bean，所有数据库相关的instance creation都在xml里面配置完成(puluo.xml)。

    *   例子：
        大家看一下src/test/java里面的DummyEntitySaveLoadTest。我通过上述convention写了一个minimal example。以后我们每当实现一组Entity、EntityDao的时候，都需要写一个相应的test case来protect我们的code。