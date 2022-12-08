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
        reconnect();
    }
}

function initWsEventHandler() {
    try {
        wsObj.onopen = function (evt) {
            onWsOpen(evt);
        };

        wsObj.onmessage = function (evt) {
            onWsMessage(evt);
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
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg); // 获取url中"?"符后的字符串并正则匹配
    var context = "";

    if (r != null) {
        context = r[2]
    }
    reg = null;
    r = null;

    return context == null || context == "" || context == "undefined" ? "" : context;
}

function reconnect() {
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
