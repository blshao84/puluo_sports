
    1.  /users/login (Done!)
      * mobile存在并且password匹配
        * 结果：登录成功，返回一个有效的token，并且/users/status 返回成功
      * mobile存在但password不匹配
      * mobile不存在
      * 参数缺少mobile
      * 参数缺少password
    2.  /users/register( Done!)
      * mobile已存在
      * 参数缺少mobile
      * 参数缺少password
      * 参数缺少auth_code
      * mobile不存在，但auth_code错误
      * mobile不存在，且auth_code正确
    3.  /users/logout (Done!)
      * token正确
      * token错误
    4.  /users/credential/update (Done!)
      * mobile不存在 (仅修改token用户的密码，mobile不存在也就不能登录)
      * mobile存在但password不匹配
      * mobile存在，password匹配，但缺少new_password
      * mobile存在，password匹配，new_password正确更新
    5.  /events/payment/event_uuid
    6.  /events/detail/event_uuid (Done!)
      * event_uuid不存在
      * event_uuid存在
    7.  /events/memory/event_uuid (Done!)
      * event_uuid存在，并且有memory image
      * event_uuid存在，没有memory image
      * event_uuid不存在
    8.  /events/search (Done!)
      * 按日期搜索
      * 按日期搜索，但包含已closed的event
      * 按关键字搜索，按价格默认（升序）排序
      * 按关键字搜索，按价格降序排序
      * 按Description的关键字搜索
      * 按Name的关键字搜索
      * 按Name和Description的关键字搜索
    9.  /users/friends/mobile_or_uuid (Done!)
      * 一个存在的mobile，并且这个用户有若干好友
      * 一个存在的mobile，并且这个用户没有好友
      * 一个存在的uuid，并且这个用户有若干好友
      * 一个存在的uuid，并且这个用户没有好友
      * 一个不存在的mobile
      * 一个不存在的uuid
    10. /users/friends/request
      * token代表的用户向一个存在的user_uuid请求好友，并且user_uuid不是token用户的好友，而且token用户没有向user_uuid发送过好友请求
      * token代表的用户向一个存在的user_uuid请求好友，并且user_uuid已经是token用户的好友 
      * token代表的用户向一个存在的user_uuid请求好友，并且user_uuid不是token用户的好友，而且token用户向user_uuid发送过好友请求，请求当前状态为Requested
      * token代表的用户向一个存在的user_uuid请求好友，并且user_uuid不是token用户的好友，而且token用户向user_uuid发送过好友请求，请求当前状态为Denied
      * token代表的用户向一个不存在的user_uuid请求好友
    11. /users/friends/delete
      * user_uuid用户已经在token用户的好友列表中
      * user_uuid用户不在token用户的好友列表中
    12. /users/friends/deny
      * user_uuid没有向token用户请求过好友(FriendRequest不存在)
      * user_uuid向token请求过好友，但request状态为Approved
      * user_uuid向token请求过好友，但request状态为Denied
    13. /users/friends/approve
      * user_uuid没有向token用户请求过好友(FriendRequest不存在)
      * user_uuid向token请求过好友，但request状态为Approved
      * user_uuid向token请求过好友，但request状态为Denied
    14. /users/message/send (Done!)
      * to_uuid用户不存在
      * to_uuid用户存在，并且content_type为TextMessage，content为空
      * to_uuid用户存在，并且content_type为TextMessage，content不为空
      * to_uuid用户存在，并且content_type为其他，content不为空
    15. /users/messages (Done!)
      * user_uuid用户不存在
      * user_uuid用户存在，since为空，token用户和user_uuid用户存在历史对话
      * user_uuid用户存在，since为空，token用户和user_uuid用户不存在历史对话
      * user_uuid用户存在，since为不空，token用户和user_uuid用户存在历史对话
    16. /services/images
    17. /services/sms
    18. /services/sms/register (Done!)
      * mobile为空
      * mobile格式非法，发送验证码失败
      * mobile合法，发送验证码成功
    19. /users/timeline
    20. /users/timeline/like
    21. /users/timeline/delike
    22. /users/timeline/comment
    23. /users/timeline/comment
    24. /users/status (Done!)
      * token已登录
      * token未登录 (未登录状态不能访问)
    25. /users/mobile_or_uuid (Done!)
      * mobile 存在
      * mobile 不存在
      * uuid 存在
      * uuid 不存在
    26. /users/update (Done!)
      * token 用户update first_name
      * token 用户update last_name
      * token 用户update thumbnail
      * token 用户update large_image
      * token 用户update  saying
      * token 用户update email
      * token 用户update sex
      * token 用户update birthday
      * token 用户update country
      * token 用户update state
      * token 用户update city
      * token 用户update zip
    27. /usrs/search (Done!)
    28. /users/privacy/mobile_or_uuid (Done!)
      * mobile 存在
      * mobile 不存在 (无论mobile_or_uuid为何值，API中仅返回token用户的setting)
      * uuid 存在
      * uuid 不存在 (无论mobile_or_uuid为何值，API中仅返回token用户的setting)
    29. /users/setting/update (Done!)
      * token 用户update auto_add_friend
      * token 用户update allow_stranger_view_timeline
      * token 用户update allow_searched

