let stompClient = null;
let wsCreateHandler = null;
let userId = null;

function connect() {
    let host = window.location.host; // with port
    userId = GetQueryString("userId");

    let socket = new SockJS("http://" + host + "/websocket");

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        writeToScreen("connected: " + frame);

        stompClient.subscribe('/queue/user' + userId, function (response) {
            // let num = 1 / 0;
            // console.log(num);
            writeToScreen(response.body);
            // response.ack(); // ack 确认
        }, {ack: 'client'}); // 开启手动 ack

        stompClient.subscribe('/queue/chat', function (response) {
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
