# Linux

内核空间；用户空间（应用程序所在空间）；

内核管理整个系统的硬件资源；

> OS 没有绝对的数据可靠性（至少会丢弃是1条数据）更多的时再速度与可靠性之前的权衡；
> 可靠性通过将单机问题转化为集群可靠性来做保障，而单机更多偏向于性能（主从复制，主备）；

## VFS


> * `VFS` 虚拟文件系统，为系统和应用提供统一的文件系统抽象（屏蔽底层实现细节）；树状结构
> 
>     目录树结构趋向于稳定；
> 
>     **一切皆文件**，文件有对应的**类型**：
>     * `-` 普通文件（例如：可执行文件、图片、文本等）
>     * `d` 目录文件
>     * `l` 连接文件（能够同时看到文件内容的变化）
>         * 软连接（删除原文件会导致软连接找不到实际文件报错）
>         * `2` 硬链接（指向同一个物理（磁盘的物理位置）文件），删除文件不会删除实际文件（文件链接数 -1 ）
>     * `b` **块设备**（支持自由偏移读，例如：磁盘）
>     * `c` **字符设备**（不支持自由偏移读，例如：键盘、网卡）
>     * `s` socket
>     * `p` pipeline(管道)
>     * `eventpoll` 指向内核空间的 EP 内存区域
>
> * `inode`/`node` 在虚拟文件系统中，每个文件在被打开的时候都有一个 inode 号
> 
> * `pagecache` (默认 4K)缓存页（内核）（**线程共享**）
> 
> * `dirty` 页缓存内容被修改标记为**脏**
> 
>     当缓存页数量达到设置的阈值此时会将脏页回写磁盘，但是系统会尽量缓存所以不会立即释放回写的缓存页，只有当内存不够用时，根据LRU算法进行淘汰；
> 
> * `flush` 将缓存内容刷新写入磁盘
> 
> * `FD` 文件描述符（指针）每个**线程独有**
> 
> * `seek` 文件偏移量，每个**线程独有**

读取文件时，首先由内核读取到内存，开辟 `pagecache` 缓存页；多个应用程序间共享 `pagecache` ，当缓存页内存被修改后标记为**脏**，通过 `flush` 将修改刷新到磁盘；
每个应用对于同一个文件有自己的偏移量 `seek` 信息，各自获取要读取的位置和内容；

> Java 语言不支持 `direct I/O` C 语言支持

任何文件都有:
* `FD:0u` 标准输入
* `FD:1u` 标准输出
* `FD:2u` 错误输出

> `/proc` 内核映射目录，进程信息
> `/proc/[PID]/fd` 文件描述符目录

> **重定向**：不是命令，是一种**机制**；
> 
> `<` 重定向输入
> `>` 重定向输出
> 
> **管道**： `|`
> 
> 管道连边为单独的子进程；

> Linux `pagecache` `dirty` 设置
> 
> * `vm.dirty_background_ratio=`
> * `vm.dirty_ratio=`

> 使用 Buffer I/O 更快：减少了写系统调用，减少内核态和用户态切换
> 
> Jvm 默认 Buffer 大小为 `8K`

## Linux 命令

> * `&&` 多个命令同时执行

* `df` 获取设备的挂载目录信息
* `umount [dir]` 卸载 `dir` 目录
* `mount [dev] [dir]` 挂载设备 `dev` 到 `dir` 目录
    * `-t` 挂在的设备文件格式化类型（`ext2`）
* `ln [file_name] [link_name]` 为 `file_name` 文件创建连接文件 `link_name` （默认**硬链接**）

    命令参数
    * `-s` 创建**软连接**
* `stat [file_name]` 查看 `file_name` 文件的元数据信息
* `dd if=[] of=[] bs=[] count=[]` 拷贝目录生成文件
    * `if` 输入文件
    * `of` 输出文件
    * `bs` 拷贝块大小
    * `count` 拷贝块数量

* `losetup [dev_dir] [file]` 回环挂载文件 `file` 到 `dev_dir` 设备
* `mke2fs [dev_dir]` 格式化 `dev_dir` 目录为 `ext2` 格式
* `ldd [app]` `app` 程序运行时需要的动态链接库信息
* `chroot [dir]` 切换根目录为 `dir`
* `echo $$` 打印当前进程 PID
* `lsof -p [PID]` 显示进程 `PID` 打开的文件
    * `-op` 查看进程文件描述符细节
* `pcstat` `pacecache`信息
    * `-pid`
* `head [file_name]` 从文件头部读取（默认10行）
    * `-num` 读取 `num` 行
* `tail [file_name]` 从文件尾部读取（默认10行）
    * `-num` 读取 `num` 行
* `pstree` 查看进程树
* `ps [options]` 查看系统进程
    
    `options`
    * `-e`
    * `-f`
* `tcpdump -nn -i eth0 port 9090` tcp 抓包
* `strace -ff -o out [cmd]` 追踪 `cmd` 对内核的系统调用，线程独立输出
* `tail -f [file_name]` 追踪文件 `file_name` 最新内容
* `man` 参考手册，一共有8类文档
    * `man bash`
    * `man man` 
