# Node.js

## Installation

```shell
VERSION=v10.15.0
DISTRO=linux-x64
sudo mkdir -p /usr/local/lib/nodejs
sudo tar -xJvf node-$VERSION-$DISTRO.tar.xz -C /usr/local/lib/nodejs
```

```shell
vim ~/.profile
```

```shell
# Nodejs
VERSION=v10.15.0
DISTRO=linux-x64
export PATH="/usr/local/lib/nodejs/node-$VERSION-$DISTRO/bin:$PATH"
```

## NPM 包管理器配置

* 修改为国内源：
```shell
npm config set registry https://registry.npm.taobao.org
npm config set registry https://registry.npmmirror.com
```
* 验证修改：
```shell
npm config get registry
```

## CommonJS

* `require()` 导入模块
* `exports` 导出
* `module` 对象
  * `module.paths.push('')` 添加新路径到 module 中

`package.json` 文件与模块搜索

`package.json` 来组织模块
