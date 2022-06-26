# 在线编译器

## 简述

一款简单的在线编译器后台, 支持``c``, ``c++``, ``java``, ``python``和``javascript``, 后续会支持更多语言



## 原理

1. 使用``JNI``时加载``.so``文件限制的执行 *编译* 和 *运行*

2. 通过 ``seccomp``限制``syscall``, 例如``clone``, ``fork``, ``write``, ``read``, ``exit_group``等



## 安装

建议在``docker``中使用, 非``docker``环境中使用中会存在很多``syscall``限制问题

1. 执行``sandbox/src/main/c``中的脚本, 生成``sandbox.so``文件到``/usr/lib/sandbox/sandbox.so``
2. 后端使用``springboot`` , 通过启动类启动



## 接口示例



### Run 提交代码

URL:  ``/just/run``

Method: ``POST``

Args:

* ``language`` ``c = 1``, ``c++ = 2``, ``java = 3``, ``python = 4``, ``javascript = 5``
* ``code`` code
* ``input`` 运行输入

Request示例:

```json
{
    "language": 2,
    "code": "#include <iostream>\nusing namespace std;\nint main() {\n    int a, b;\n    cin >> a >> b;\n    int res = 0;\n    while (a <= b) {\n        res += a;\n        a++;\n    }\n    cout << res << endl;\n    return 0;\n}",
    "input": "1 100"
}
```

Response示例:

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



### Query 查询执行结果

URL: ``/just/query``

Method:  ``GET``

Args: 

* ``id`` 任务的id

Request示例:

```
curl --location --request GET 127.0.0.1:8080/just/query?id=7ce2e89a-5a04-4810-aadb-d6e957c9aa13
```

Response示例:

* ``data.status`` 没有任务 = -1, 运行结束 = 0, 仍在运行 = 1
* ``data.result.status`` 运行成功 = 0, 编译错误 = 1, 运行出错 = 2
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

* sandbox部分来源于:  [**QingdaoU/OnlineJudge**](https://github.com/QingdaoU/Judger)
* sandbox在某些系统上可能存在syscall 权限问题, 使用``dmesg -T``查看系统调用, 在``sandbox``中的``c/src/rules``的``syscalls_whitelist``中放行该syscall
* 在某些机器或系统上``memory``和``realTime``可能过大, 可以在``src/main/java/com/lc/compiler/config/language/configs`` 调整为适合大小