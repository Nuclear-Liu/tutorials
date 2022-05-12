# Network

### TCP

TCP 是面向连接的可靠传输协议；
通过三次握手确认链接（`syn` `syn+ack` `ack`）；
三次握手之后双方在内核开辟资源（资源代表了链接）；

#### Socket

Socket 是四元组(`client ip` `client port` `server ip` `server port`)；握手完成后内核创建和维护四元组信息（包括缓冲区 Buffer ）；

Socket 的四元组分配给对应的程序时，映射为程序内的 `FD` （表示流）；FD作用范围时线程，线程内 `FD` 唯一；
在程序中通过文件描述符 `FD` 来映射到对应的 Socket 四元组；

`BACK_LOG` 备用链接数量（默认50个）；

> Socket 是四元组，所以端口号可以被多个 Socket 共用只要四元组保证唯一就可以；
> 
> 服务端Socket：因为是监听状态，如果再启动一个同一个端口，不能保证四元组唯一；
