# 在线编译器


一款简单的在线编译器后台, 支持`c`, `c++`, `java`, `python`, `golang`和`javascript`, 后续会支持更多语言


## 原理
1. 使用`JNI`调用`C`代码
2. 通过Linux内核安全机制 `seccomp`限制`syscall`, 例如禁止 `clone`, `fork`, `kill`, `socket`, `vfork` 等系统调用



## 安装

> 建议在`docker`中使用, 非`docker`环境中使用会存在`syscall`被限制导致程序不能正常被执行(此时需要将被限制的`syscall`添加到`seccomp_rules`白名单中)

1. 执行`sandbox/src/main/c`中的脚本, 生成`sandbox.so`文件到`/usr/lib/sandbox/sandbox.so`
2. 后端使用`springboot` , 通过启动类启动



## 接口示例



### Run 提交代码

URL:  `/just/run`

Method: `POST`

Args:

* `language` : `c = 1`, `c++ = 2`, `java = 3`, `python = 4`, `javascript = 5`, `golang = 6`
* `code` : your code
* `input` : 运行输入

Request示例:

> 求 1 ~ 100 的和

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

URL: `/just/query`

Method:  `GET`

Args: 

* `id` 任务的id

Request示例:

```bash
curl --location --request GET 127.0.0.1:8080/just/query?id=7ce2e89a-5a04-4810-aadb-d6e957c9aa13
```

Response示例:

* `data.status` : 没有任务 = -1, 运行结束 = 0, 仍在运行 = 1
* `data.result.status` : 运行成功 = 0, 编译错误 = 1, 运行出错 = 2
* `data.result.output` : 运行的输出

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

* sandbox源于:  [**QingdaoU/OnlineJudge**](https://github.com/QingdaoU/Judger)
* sandbox在某些系统上可能存在syscall 权限问题, 使用`dmesg -T`查看系统调用, 在`sandbox`中的`c/src/rules`的`syscalls_whitelist`中放行该syscall
* 在某些机器或系统上`memory`和`realTime`需求可能很大, 可以在`compiler/config/language/configs` 调整为适合大小
