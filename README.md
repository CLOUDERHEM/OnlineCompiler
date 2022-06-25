# 在线编译器

## 简述

一款简单的在线编译器后台, 支持c, cpp, java和python, 后续会更新更多语言.



## 原理

1. 使用``JNI``调用``C``代码执行程序

2. 通过 ``seccomp``限制系统调用, 例如``system("rm -rf /")``



## 安装

建议在docker中使用

1. 执行``src/main/c``中的脚本, 生成``sandbox.so``文件到``/usr/lib/sandbox``
2. 后端使用``springboot`` , 通过启动类启动



## 接口示例



### RUN 运行代码

URL:  ``/just/run``

Method: ``POST``

Args:

* ``language``  1 C, 2 Cpp, 3 Java, 4 Python
* ``code`` code
* ``input`` 程序的输入

Request:

```json
{
    "language": 2,
    "code": "#include <iostream>\nusing namespace std;\nint main() {\n    int a, b;\n    cin >> a >> b;\n    int res = 0;\n    while (a <= b) {\n        res += a;\n        a++;\n    }\n    cout << res << endl;\n    return 0;\n}",
    "input": "1 100"
}
```

Response:

```json
{
    "code": 200,
    "msg": "等待执行结果",
    "data": {
        "id": "7ce2e89a-5a04-4810-aadb-d6e957c9aa13",
        "status": null,
        "result": null,
        "timestamp": 1656135776537
    }
}
```



### Query 查询结果

URL: ``/just/query``

Method:  ``GET``

Args: ``id`` 任务的id

Request:

```
curl --location --request GET '127.0.0.1:8080/just/query?id="7ce2e89a-5a04-4810-aadb-d6e957c9aa13"'
```

Response:

* ``data.status`` -1 没有任务, 0 运行结束, 1 仍在运行中
* ``data.result.status`` 0 运行成功, 1 编译错误, 2 运行出错
* ``data.result.output`` 运行的输出

```json
{
    "code": 200,
    "msg": "运行成功 ",
    "data": {
        "id": "7ce2e89a-5a04-4810-aadb-d6e957c9aa13",
        "status": 0,
        "result": {
            "cpuTime": 0,
            "signal": 0,
            "memory": 1626112,
            "exitCode": 0,
            "result": 0,
            "error": 0,
            "realTime": 42,
            "status": 0,
            "output": "5050\n",
            "runMsg": null
        },
        "timestamp": 1656138213151
    }
}
```



---



## 附加

* Sandbox部分来源于:  [**QingdaoU/OnlineJudge**](https://github.com/QingdaoU/Judger)
* sandbox在某些系统上可能存在syscall 权限问题, 使用``dmesg -T``查看系统调用, 在``src/rules``的``syscalls_whitelist``中放行该syscall
* 在某些系统上``memory``可能过大, 可以在``src/main/java/com/lc/compiler/config/language/configs`` 调整为适合大小