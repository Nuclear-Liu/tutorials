

## Go SDK

* `go help <topic>` 获取更短管理 `topic` 的信息
* `go run [go_file.go]` 直接运行 `go_file.go` 
* `go build [go_file.go]` 编译 `go_file.go` 生成二进制可执行文件
* `go get [-t] [-u] [-v] [build flags] [packages]`

  Get 将其命令行参数解析为特定模块版本的包，更新 go.mod 以要求这些版本，并将源代码下载到模块缓存中。

## Go Source File

> Go 代码使用**包**（类似于其他语言中的库和模块）来组织。
> 
> 一个包由放在**一个文件夹**中**一个或多个** `.go` 文件组成；
