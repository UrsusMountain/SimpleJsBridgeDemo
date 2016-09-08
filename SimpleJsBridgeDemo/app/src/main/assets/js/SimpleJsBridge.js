(function () {
    if (window.SimpleJsBridge) {
        return;
    }

    var simpleIframe;
    var sendMessageQueue = {};
    var successCallbackQueue = {};
    var failureCallbackQueue = {};

    var PROTOCOL_HEAD = 'yy://';
    var TAG_NEW_MESSAGE = 'new_message/';
    var TAG_MESSAGE_CONTENT = "message_content/";

    var ID_MESSAGE = "message_id_";
    var ID_SUCCESS = "success_id_";
    var ID_FAILURE = "failure_id_";

    var messageCount = 0;
    var successCount = 0;
    var failureCount = 0;


    //发送消息(您有一条新消息)
    function sendMessage(message, successCallback, failureCallback) {
        var date = new Date().getTime();
        var messageId = message.id = ID_MESSAGE + (messageCount++) + "_" + date;
        var successId = message.successCallbackId = ID_SUCCESS + (successCount++) + "_" + date;
        var failureId = message.failureCallbackId = ID_FAILURE + (failureCount++) + "_" + date;

        sendMessageQueue[messageId] = message;
        successCallbackQueue[successId] = successCallback;
        failureCallbackQueue[failureId] = failureCallback;

        simpleIframe.src = PROTOCOL_HEAD + TAG_NEW_MESSAGE + encodeURIComponent(messageId);
    }

    // 本地收到消息提醒后,调用该方法,从消息队列中取出消息
    function popMessage(messageId) {
        var message = sendMessageQueue[messageId];
        if (!message) {
            return;
        }
        delete sendMessageQueue.messageId;

        var messageString = JSON.stringify(message);
        simpleIframe.src = PROTOCOL_HEAD + TAG_MESSAGE_CONTENT + encodeURIComponent(messageString);
    }

    // 本地执行完操作之后的回调
    function nativeCallback(responseData) {

        var message = JSON.parse(responseData);
        var successCallbackId = message.successCallbackId;
        var failureCallbackId = message.failureCallbackId;

        if (message.status === "success") {
            var successCallback = successCallbackQueue[successCallbackId];
            successCallback(message.data);
        } else if (message.status === "failure") {
            var failureCallback = failureCallbackQueue[failureCallbackId];
            failureCallback(message.err);
        }

        delete successCallbackQueue.successCallbackId;
        delete failureCallbackQueue.failureCallbackId;
    }

    //初始化
    function init() {
        //创建一个 iframe
        var doc = document;
        simpleIframe = doc.createElement('iframe');
        simpleIframe.style.display = 'none';
        doc.documentElement.appendChild(simpleIframe);

        //创建并触发事件
        var readyEvent = doc.createEvent('Events');
        readyEvent.initEvent('SimpleJsBridgeReady');
        readyEvent.bridge = SimpleJsBridge;
        doc.dispatchEvent(readyEvent);
    }

    var SimpleJsBridge = window.SimpleJsBridge = {
        _init: init,
        _sendMessage: sendMessage,
        _popMessage: popMessage,
        _nativeCallback: nativeCallback,

        _showToast:showToast,
        _getLocalImage:getLocalImage,
        _alert:alert
    };

    function send(handler,content, successCallback, failureCallback){
        var message = {};
        message.function = handler.func;
        message.data = JSON.stringify(content);
        window.SimpleJsBridge._sendMessage(message, successCallback, failureCallback);
    }

    function showToast(content, successCallback, failureCallback) {
        send({
            func : "showToast",
        },content, successCallback, failureCallback);
    }

    function getLocalImage(content, successCallback, failureCallback) {
        send({
            func : "getLocalImage",
        },content, successCallback, failureCallback);
    }

    function alert(content, successCallback, failureCallback){
        send({
            func : "alert",
        },content, successCallback, failureCallback);
    }

    window.SimpleJsBridge._init();
    // init();
})();