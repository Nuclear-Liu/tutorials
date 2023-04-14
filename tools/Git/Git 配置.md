# Git 配置

## Windows 下配置长文件路径支持

```shell
git config --global core.longpaths true
```

## 配置用户名与邮箱

```shell
git config user.name "<user-name>"
git config user.email "<user-email>"
```

## 合并提交请求

```shell
# 合并到 HEAD 的最近 <number> 个请求
git rebase -i HEAD~<number>
```

## 从 tag 位置创建分支

`git checkout tags/<tag> -b <branch>`

## 从 CSV 克隆

`git svn clone https://svn.apache.org/repos/asf/tomcat/trunk/ ./tomcat -s --prefix=svn/`

## 常用分支前缀

* `feat` 新功能
* `fix` 修复 bug
* `docs` 文档
* `style` 格式
* `refactor` 重构
* `revert` 撤销，版本回退

