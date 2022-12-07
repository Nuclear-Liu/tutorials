let stompClient = null;
let wsCreateHandler = null;

function connect() {
    const host = window.location.host; // with port
    let socket = new SockJS("http://" + host + "/websocket");

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        writeToScreen("connected: " + frame);

        stompClient.subscribe('/topic', function (response) {
            writeToScreen(response.body);
        });

        stompClient.subscribe('/sendToAll', function (response) {
            writeToScreen("sendToAll:" + response.body)
        });

        stompClient.subscribe('/sendToAllByTemplate', function (response) {
            writeToScreen("sendToAllByTemplate:" + response.body)
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
