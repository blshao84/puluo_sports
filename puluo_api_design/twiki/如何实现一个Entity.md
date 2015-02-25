# 如何实现一个Entity

在Puluo项目一个Entity对应着一个数据库中的一张表，也是API操作的基本对象。他的定义与实现遵循以下的guideline。

*   每个entity有一个interface和一个implementation。
    -   interface：从使用者的角度定义数据接口。
    -   implementation：定义了信息的存储结构并且通常对应着具体的数据库实现。然而entity中一般不涉及具体数据库细节（e.g. RDBMS v.s NoSQL）。
    -   例子：PuluoTimelinePost会包含Event信息
        +   接口中定了一个方法`public PuluoEvent event();`
        +   实现中定义了一个成员变量`public String event_uuid;`并且通过这个event_uuid来从数据库中获得一个具体的PuluoEvent。
*   每个entity的实现中，他的成员变量尽可能是`private final`的
*   每个entity的实现中，他的成员变量尽可能是primitive type。
*   在命名规则上，成员变量以下划线分割小写单词,e.g. event_uuid。成员函数以首字母大写的方式区分单词,e.g. eventUUID。