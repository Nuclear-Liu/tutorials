let stompClient = null;
let wsCreateHandler = null;
let userId = null;

function connect() {
    userId = GetQueryString("userId");

    let socket = new SockJS("http://127.0.0.1:8880/websocketserver/websocket");

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        writeToScreen("connected: " + frame);

        stompClient.subscribe('/topic/chat', function (response) {
            writeToScreen(response.body);
        });

        stompClient.subscribe('/queue/user_' + userId, function (response) {
            writeToScreen(response.body);
        });

    }, function (error) {
        wsCreateHandler && clearTimeout(wsCreateHandler);
        wsCreateHandler = setTimeout(function () {
            console.log("reconnect...");
            connect();
            console.log("reconnect succeed.");
        }, 1000);
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    writeToScreen("disconnected");
}

function writeToScreen(message) {
    if (DEBUG_FLAG) {
        $("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
    }
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);

    let context = "";

    if (r != null) {
        context = r[2];
    }

    reg = null;
    r = null;

    return context == null || context == "" || context == "undefined" ? "" : context;
}
