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
        *   注册
            *   命令: curl -n -X PUT http://puluodev1/users/register  -H "Content-Type: application/json" -d '{"mobile":"18521564305","password":"abcde","auth_code":"327431"}'
            *   结果:
            ```
            {
              "user_uuid":"58008627-5d40-4cf7-aa11-7421287a863e",
              "mobile":"18521564305",
              "password":"abcde"
            }
            ```
    2. 登陆：
        *   请求登陆API:
            -   命令：curl -v -n -X POST http://puluodev1/users/login -H "Content-Type: application/json" -d '{"password":"abcde","mobile":"18521564305"}'
            -   结果：
            ```
            ......
            < Set-Cookie: JSESSIONID=11izies86lx0j;Path=/
            .....
            {
              "uuid":"18521564305",
              "created_at":1427011231529,
              "last_login":1427011231529
            }
            ```
        *   获得session id: 上面的命令中，-v flag 显示命令的详细信息，session id是在‘Set-Coookie’中获得，i.e. 11izies86lx0j
    3. 测试其他API：其他API在使用是都需要传session id，例如：
        *   GET 类型：
            -   命令：curl -cookie "JSESSIONID=11izies86lx0j" -n -X GET http://localhost:8080/users/18521564305
            -   结果：
            ```
            {
              "uuid":"58008627-5d40-4cf7-aa11-7421287a863e",
              "public_info":{
                "likes":0,
                "banned":true,
                "following":0,
                "is_coach":false
              },
              "private_info":{
                "sex":" ",
                "birthday":""
              },
              "created_at":1427010784000,
              "updated_at":1427036744744
            }
            ```
        *   PUT/POST 类型:
            *   命令：curl --cookie "JSESSIONID=1a5okxy6e3euwaxiyly8ixu86" -n -X POST http://localhost:8080/users/credential/update -H "Content-Type: application/json" -d '{"password":"abcde","new_password":"uvwxyz"}'
            *   结果：
            {
              "password":"abcde",
              "new_password":"uvwxyz"
            }