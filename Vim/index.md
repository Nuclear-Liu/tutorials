# Vim

Vim(Vi improved)

> `vim --version` : 查看 Vim **版本信息**和支持的**功能特性**；
> 
> * `+` : 启用的功能；
> * `-` : 未启用功能；

> `vim -help` : Vim 命令帮助
> 
> Usage: 
> * `vim [arguments] [file ..]` : edit specified file(s)
> * `vim [arguments] -` : read text from stdin
> * `vim [arguments] -t tag` : edit file where tag is defined
> * `vim [arguments] -q [errorfile]` : edit file with first error
> 
> Arguments:
> * `--` :  
> * `-v` :
> * `-e` :
> * `-E` :
> * `-s` :
> * `-d` :
> * `-y` :
> * `-R` :
> * `-Z` :
> * `-m` :
> * `-M` :
> * `-b` :
> * `-l` :
> * `-C` :
> * `-N` :
> * `-V[N][fname]` :
> * `-D` :
> * `-n` :
> * `-r` :
> * `-r (with file name)` :
> * `-L` :
> * `-A` :
> * `-H` :
> * `-T <terminal>` :
> * `--not-a-term` :
> * `--ttyfail` :
> * `-u <vimrc>` :
> * `--noplugin` :
> * `-p[N]` :
> * `-o[N]` :
> * `-O[N]` :
> * `+` :
> * `+<lnum>` :
> * `--cmd <command>` :
> * `-c <command>` :
> * `-S <session>` :
> * `-s <scriptin>` :
> * `-w <scriptout>` :
> * `-W <scriptout>` :
> * `-x` :
> * `--startuptime <file>` :
> * `-i <viminfo>` :
> * `--clean` :
> * `-h` or `--help` :
> * `--version` :

## 配置

配置文件的可用位置可以从 `vim --version` 命令看到；

当前用户的配置文件位置为： `$HOME/.vimrc`

```text
syntax on                   " 支持语法高亮显示
filetype plugin indent on   " 启用根据文件类型自动缩进

set autoindent              " 开始新行时处理缩进
set expandtab               " 将制表符 Tab 展开为空格
set tabstop=4               " 要计算的空格数
set shiftwidth=4            " 用于自动缩进的空格数
```

## mode _工作模式_

### normal mode _正常模式_

### insert mode _插入模式_

### 命令模式


`:help feature-list`(cmd module) : Vim 可以支持的所有功能列表


