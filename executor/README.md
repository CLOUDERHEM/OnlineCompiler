# sandbox

> 沙箱源码来自于 [Online Judge](https://github.com/QingdaoU/OnlineJudge)

## 主要流程

1. `main`方法中进行参数解析和校验
2. 执行`fork()`方法
    * 父进程 :
        1. 开启一个`timeout_killer`线程, 该线程`sleep(timeout)`之后检查子进程状态, 如果子进程未结束, 则杀死子进程
        2. 执行`wait4`, 等待**子进程**结束, 获取子进程的运行信息(cpu时间, 最大使用内存等)
        3. 将子进程的信息封装到`_result`返回
    * 子进程 :
        1. 解析`_config`, 通过`setrlimit`设置`cpu`, `memory`, `process number`等的限制,
        2. 文件流和io的重定向, `file -> stdin`,  `stdout -> file`, `stderr -> file`
        3. 设置子进程的`gid`和`uid`,
        4. 加载`seccomp_rules`, 限制某些`syscall`的调用
        5. `execve`执行目标程序(可包含编译器程序)
