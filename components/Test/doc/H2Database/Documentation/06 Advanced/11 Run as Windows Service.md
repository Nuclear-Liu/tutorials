## Run as Windows Service *作为 Windows 服务运行*

Using a native wrapper / adapter, Java applications can be run as a Windows Service.
There are various tools available to do that.
The Java Service Wrapper from [Tanuki Software, Inc.]() is included in the installation.
Batch files are provided to install, start, stop and uninstall the H2 Database Engine Service.
This service contains the TCP Server and the H2 Console web application.
The batch files are located in the directory `h2/service`.


使用本机包装器/适配器，Java 应用程序可以作为 Windows 服务运行。
有各种工具可以做到这一点。
来自 [Tanuki Software, Inc.]() 的 Java Service Wrapper 包含在安装中。
批处理文件用于安装、启动、停止和卸载 H2 数据库引擎服务。
该服务包含 TCP 服务器和 H2 控制台 Web 应用程序。
批处理文件位于目录 `h2service` 中。

---

The service wrapper bundled with H2 is a 32-bit version.
To use a 64-bit version of Windows (x64), you need to use a 64-bit version of the wrapper, for example the one from [Simon Krenger]().


与 H2 捆绑的服务包装器是 32 位版本。
要使用 64 位版本的 Windows (x64)，您需要使用 64 位版本的包装器，例如来自 [Simon Krenger]() 的包装器。

---

When running the database as a service, absolute path should be used.
Using `~` in the database URL is problematic in this case, because it means to use the home directory of the current user.
The service might run without or with the wrong user, so that the database files might end up in an unexpected place.


将数据库作为服务运行时，应使用绝对路径。
在这种情况下，在数据库 URL 中使用 `~` 是有问题的，因为它意味着使用当前用户的主目录。
该服务可能会在没有或有错误用户的情况下运行，因此数据库文件可能会出现在意外的位置。

---

### Install the Service *安装服务*

The service needs to be registered as a Windows Service first.
To do that, double click on `1_install_service.bat`.
If successful, a command prompt window will pop up and disappear immediately. If not, a message will appear.


该服务需要先注册为 Windows 服务。
为此，请双击“1_install_service.bat”。
如果成功，将弹出命令提示符窗口并立即消失。如果没有，则会出现一条消息。

---

### Start the Service *启动服务*

You can start the H2 Database Engine Service using the service manager of Windows, or by double clicking on `2_start_service.bat`.
Please note that the batch file does not print an error message if the service is not installed.


您可以使用 Windows 的服务管理器或双击 `2_start_service.bat` 来启动 H2 数据库引擎服务。
请注意，如果未安装该服务，批处理文件不会打印错误消息。

---

### Connect to the H2 Console *连接到 H2 控制台*

After installing and starting the service, you can connect to the H2 Console application using a browser.
Double clicking on `3_start_browser.bat` to do that.
The default port (8082) is hard coded in the batch file.


安装并启动服务后，您可以使用浏览器连接到 H2 Console 应用程序。
双击 `3_start_browser.bat` 即可。
默认端口 (8082) 在批处理文件中进行了硬编码。

---

### Stop the Service *停止服务*

To stop the service, double click on `4_stop_service.bat`.
Please note that the batch file does not print an error message if the service is not installed or started.


要停止服务，双击 `4_stop_service.bat` 。
请注意，如果服务未安装或启动，批处理文件不会打印错误消息。

---

### Uninstall the Service *卸载服务*

To uninstall the service, double click on `5_uninstall_service.bat`.
If successful, a command prompt window will pop up and disappear immediately.
If not, a message will appear.


要卸载该服务，请双击 `5_uninstall_service.bat` 。
如果成功，将弹出命令提示符窗口并立即消失。
如果没有，则会出现一条消息。

---

### Additional JDBC drivers *其他 JDBC 驱动程序*

To use other databases (for example MySQL), the location of the JDBC drivers of those databases need to be added to the environment variables `H2DRIVERS` or `CLASSPATH` before installing the service.
Multiple drivers can be set; each entry needs to be separated with a `;` (Windows) or `:` (other operating systems).
Spaces in the path names are supported.
The settings must not be quoted.


要使用其他数据库（例如 MySQL），需要在安装服务之前将这些数据库的 JDBC 驱动程序的位置添加到环境变量 `H2DRIVERS` 或 `CLASSPATH` 中。
可设置多个驱动程序；每个条目都需要用 `;` （Windows）或 `:` （其他操作系统）分隔。
支持路径名中的空格。
不得引用设置。

---
