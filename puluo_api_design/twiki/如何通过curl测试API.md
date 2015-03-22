1. curl是Linux或者Windows下一个命令，可以模拟发送http request。包括指定Header,添加参数等等。
2. 为了测试我们的API一般来说需要3个步骤：
    1. 注册：如果还没有通过API注册，需要首先注册手机号
        *   请求验证码：
            *   命令：curl -n -X PUT http://puluodev1/services/sms/register -H "Content-Type: application/json" -d '{"mobile":"18521564305"}'
            *   结果：
            ```
            {
                "mobile":"18521564305",
                "status":"success"
            }
            ```
        *   注册：
    2. 登陆：
        *   请求登陆API:
        *   获得session id
    3. 测试其他API：
        *   GET 类型：
        *   PUT/POST 类型：