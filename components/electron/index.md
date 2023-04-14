# Electron

Electron 使用 JavaScript HTML CSS 构建桌面应用程序的框架。
Electron 将 Chromium 和 Node.js 嵌入到一个二进制文件中，允许仅需一个代码仓库就可以实现支持 Windows MacOS Linux 的跨平台应用。

> 开发环境安装 Node.js 才能编写 Electron 项目，但是 Electron 使用它内置的 Node.js 环境来运行。

## 初始化

1. 创建项目目录并初始化：

    `mkdir <electron-app> && cd <electron-app>`
    
    `npm init`
    
        * `entry point` 一般为： `main.js`
        * `author` `description` 应用打包是必填项

2. 安装 `electron` 包依赖：

    `npm install --save-dev electron`

3. 在 `package.json` 配置文件中的 `scripts` 添加 `start` 命令，在开发模式下使用 `npm start` 打开应用：

    `{ "scripts": { "start": "electron ." } }`

    > **运行主进程**
    > 
    > 任何 Electron 应用程序入口都是 `main` 文件。这个文件控制**主进程**。
    > 
    > 执行期间 Electron 依据应用中 `package.json` 配置下 `main` 字段中配置的值，查找此文件。
    > 
    > `main` 文件运行在 Node.js 环境中，负责控制应用程序声明周期、显示原生界面、执行特殊操作并管理渲染器进程。

4. 创建 `main.js`
5. 创建页面

    在应用程序创建窗口前，需要先创建加载窗口内容，在 Electron 中各个窗口显示的内容可以是**本地 HTML 文件** / **远程 URL**。

    本地模式： 创建 `index.html`

6. `main.js` 文件头部导入 Electron 模块

    `const { app, BrowserWindow } = require('electron')`

    将创建的页面 (`index.html`) 关联 `BrowserWindow` 实例：

    ```js
    const createWindow = () => {
        const win = new BrowserWindow({
            width: 800,
            height: 600
        })
    
        win.loadFile('index.html')
    }
    app.whenReady().then(() => {
        createWindow()
    })
    ```

    > 页面加载进应用窗口中，需要两个 Electron 模块：
    > 
    > * `app` 控制应用程序的**事件声明周期**
    > * `BrowserWindow` 创建和管理**应用程序窗口**
    > 
    > 在 Electron 中，只有在 `app` 模块的 `ready` 事件被激发后才能创建浏览器窗口；
    > 通过使用 `app.whenReady()` API 来监听事件， 在 `whenReady()` 成功后调用**创建浏览器窗口实例(createWindow)**`
    > 
    > 
    > Electron 遵循 JavaScript 传统约定：
    > * 以 **PascalCase** 命名可实例化的类（如： `BrowserWindow` `Tray` `Notification` ）
    > * 以 **camelCase** 命名不可实例化的函数、变量等（如： `app` `ipcRenderer` `webContents` ）

7. 打包分发应用

    最快捷的打包方式是使用 Electron Forge：

    * 添加 Electron Forge 到应用开发依赖： `npm install --save-dev @electron-forge/cli`
    * 使用 `import` 命令设置 Forge 脚手架： `npx electron-forge import`
    * 使用 `npm run package` 打包应用程序或 `npm run make` 创建可分发应用程序

## 窗口的声明周期

应用程序窗口在每个 OS 下有不同的行为，需要一些额外的模板代码使其看起来更像平台原生。

一般使用 **`process`** 全局的 `platform` 属性来专门为某些操作系统运行代码

### 关闭所有窗口时退出应用 (Windows & Linux)

关闭所有窗口通常会完全退出一个应用程序，需要监听 `app` 模块的 `window-all-closed` 事件，（如果不是在 macOS(`darwin`) 上运行)调用 `app.quit()`。

```js
app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
})
```

### 没有窗口则打开一个窗口 (macOS)

macOS 应用程序通常即时没有打开任何窗口的情况下也继续运行，通过监听 `app` 模块的 `activate` 事件，如果没有任何浏览器窗口是打开的，则调用 `createWindow()` 方法。

```js
app.whenReady().then(() => {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) {
            createWindow()
        }
    })
})
```

### 通过**预加载**脚本从渲染器访问 Node.js

在主进程中不能直接编辑 DOM ，主进程无法访问渲染器 `document` 上下文，它们存在与完全不同的进程（参阅： [进程模型]()）。

**预加载脚本**在渲染器进程加载之前加载，并且有权访问两个全局渲染器(`window` 和 `document`)和 Node.js 环境。
预加载脚本传入 `BrowserWindow` 构造器中的 `webPreferences.preload` 选项：

```js
const createWindow = () => {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
        preload: path.join(__dirname, 'preload.js')
    }
  })

  win.loadFile('index.html')
}
```

* `__dirname` 字符串指向当前正在执行脚本的路径（本例中，指向项目的根文件夹）
* `path.join` 将多个路径联结在一起，创建一个跨平台的路径字符串

Node.js 的 `process.versions` 可以获取到版本信息。

