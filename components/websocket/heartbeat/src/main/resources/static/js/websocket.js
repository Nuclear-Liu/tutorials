let wsObj = null;
let wsUri = null;
let userId = -1;
let lockReconnect = false;
let wsCreateHandler = null;

function createWebSocket() {
    const host = window.location.host; // with port
    userId = GetQueryString("userId");

    wsUri = "ws://" + host + "/websocket/" + userId;

    try {
        wsObj = new WebSocket(wsUri);
        initWsEventHandler();
    } catch (e) {
        writeToScreen("执行关闭事件，开始重连");
        // reconnect
        reconnect();
    }
}

function initWsEventHandler() {
    try {
        wsObj.onopen = function (evt) {
            onWsOpen(evt);
            heartCheck.start();
        };

        wsObj.onmessage = function (evt) {
            onWsMessage(evt);
            heartCheck.start();
        };

        wsObj.onclose = function (evt) {
            writeToScreen("执行关闭事件，开始重连");
            onWsClose(evt);
            reconnect();
        };

        wsObj.onerror = function (evt) {
            writeToScreen("执行 error 事件，开始重连");
            onWsError(evt);
            reconnect();
        };
    } catch (e) {
        writeToScreen("绑定事件没有成功");
        reconnect();
    }
}

function onWsOpen(evt) {
    writeToScreen("CONNECTED");
}

function onWsClose(evt) {
    writeToScreen("DISCONNECTED");
}

function onWsError(evt) {
    writeToScreen(evt.data)
}

function writeToScreen(message) {
    if (DEBUG_FLAG) {
        $("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
    }
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg); // 获取url中"?"符后的字符串并正则匹配
    let context = "";

    if (r != null) {
        context = r[2]
    }
    reg = null;
    r = null;

    return context == null || context == "" || context == "undefined" ? "" : context;
}

function reconnect() {
    // 判断是否正在被使用
    if (lockReconnect) {
        return;
    }
    writeToScreen("1s reconnect");
    lockReconnect = true;

    // 连接重复失败，设置延迟避免请求过多
    wsCreateHandler && clearTimeout(wsCreateHandler);

    wsCreateHandler = setTimeout(function () {
        writeToScreen("reconnect..." + wsUri);

        createWebSocket();

        lockReconnect = false;
        writeToScreen("reconnect succeed.");
    }, 1000);
}

let heartCheck = {
    // 15s 之内如果没有收到后台的消息，认为连接断开，再次连接
    timeout: 5000,
    timeoutObj: null,
    serverTimeoutObj: null,

    // reboot
    reset: function () {
        clearTimeout(this.timeout);
        clearTimeout(this.serverTimeoutObj);
        this.start();
    },

    // 开启定时器
    start: function () {
        let self = this;

        this.timeoutObj && clearTimeout(this.timeoutObj);
        this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);

        this.timeoutObj = setTimeout(function () {
            writeToScreen("发送 ping 到后台");
            try {
                wsObj.send("ping");
            } catch (e) {
                writeToScreen("发送 ping 异常");
            }
            // 内嵌计时器
            self.serverTimeoutObj = setTimeout(function () {
                // 如果 onclose 会执行 reconnect 执行 ws.close() 即可
                // 如果直接执行 reconnect 会触发 onclose 导致重连两次
                writeToScreen("没有收到后台的数据，关闭连接");
                // wsObj.onclose();
                reconnect();
            }, self.timeout);
        }, self.timeout);
    },
}
