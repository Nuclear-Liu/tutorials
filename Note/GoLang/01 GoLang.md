# GoLang

> 国内代理配置： `https://goproxy.cn/`
> 
> * windows
>     
> `> $env:GO111MODULE = "on"` `> $env:GOPROXY = "https://goproxy.cn"`

## Go SDK

* `go help <topic>` 获取更短管理 `topic` 的信息
* `go run [go_file.go]` 直接运行 `go_file.go` 
* `go build [go_file.go]` 编译 `go_file.go` 生成二进制可执行文件
* `go get [-t] [-u] [-v] [build flags] [packages]`

    Get 将其命令行参数解析为特定模块版本的包，更新 go.mod 以要求这些版本，并将源代码下载到模块缓存中。

* `gofmt` 工具提供对代码的格式化

    **Go 对于代码的格式化要求非常严格**。

* `goimports` 按需管理导入声明的插入和移除

    获取方式： `go get golang.org/x/tools/cmd/goimports`

## Go Source File

> Go 代码使用**包**（类似于其他语言中的库和模块）来组织。
> 
> 一个包由放在**一个文件夹**中**一个或多个** `.go` 文件组成；
> 文件夹的名称描述了包的作用。

1. `package` : 源文件的开始使用 `package` 声明，指明文件所属包

    * 名称为 `main` 的包用来定义一个独立的可执行程序，而不是库。
    * `main` 包中的 `main` 函数是程序的入口。
2. `import` : 源文件在 `package` 后使用 `import` 指定导入其他包的列表

    必须精确的导入每个包，在却是或存在不需要的包情况下，编译失败。

3. 程序声明 : 最后部分为存储在文件中的程序声明

## function

函数声明： 
1. `func` 关键字
2. 函数名
3. `()` 中声明参数列表
4. 返回值列表（可以为空）
5. `{}` 中的函数体

## 变量声明

* `var <valueName> [valueType] [= <value>]` 支持自动类型推断
* 短变量声明： `<valueName>:= <value>`

## 循环

> go 中只支持 `for` 循环语句

`for <initialization>; <condition>; <post> {}`
* `<initialization>` 初始化，循环语句执行之前执行
* `<condition>` 条件（布尔）表达式
* `<post>` 每次循环后执行

> 三部分都可以省略
> 
> 支持 `break` 或 `return` 等语句

## key world

* `range`: 产生一对值，索引和这个索引处元素的值
* `_`: 空标识符；处理临时变量
