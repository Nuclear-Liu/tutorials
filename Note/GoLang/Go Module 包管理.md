# Go Module

> Go SDK >= `1.12` (正式支持)

## Go Env 配置

> `-w`: write （写入）

* `GOPROXY` 国内代理
  
  全局设置国内代理: `go env -w GOPROXY=https://goproxy.io,direct`

* `GO111MODULE` Go Module

  全局开启 Go Module: `go env -w GO111MODULE=on`

  > `GO111MODULE` 值：
  > * `auto` **默认值**，自动模式，项目在 `$GOPATH/src` 目录外且项目跟目录有 `go.mod` 则是开启(`GOPATH` 失效)，否则时关闭
  > * `on` 模块支持， go 忽略 `GOPATH` 和 `vendor` 文件夹，**只根据** `go.md` 下载依赖
  > * `off` 无模块支持， go 从 `GOPATH` 和 `vendor` 文件夹寻找包

* `GOROOT`: go 的安装路径
* `GOPATH`: go 的工作路径

## Go Mod 操作

`go mod <command> [arguments]`

`<command>` 命令选项：
* `download`: 将模块下载到本地缓存
* `edit`: 从工具或脚本编辑 `go.mod`
* `graph`: 打印模块依赖图
* `init`:
* `tidy`:
* `vendor`:
* `verify`:
* `why`:

## 初始化项目

`go mod init [project_name]` 

`go.mod` 文件中:

* `module` 项目的值为 `go mod init` 中填写的 `project_name`
* `go` 项目的值为 GoLang 版本
* `require` 项目的依赖

## 添加依赖

`go get [-t] [-u] [-v] [build flags] [packages]`

`-t`: 标识为构建测试所需的模块
`-u`: 标识更新提供依赖关系的模块
`-v`:
