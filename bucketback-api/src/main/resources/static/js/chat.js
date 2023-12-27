let stompClient = null;

let userNickname = 'user' + generateRandomString(6);

let myRoomId;

function generateRandomString(length) {
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("websocket with stomp for chat is disconnected");
}

function getOtherMessageItem(message) {
    let messageContainer = document.createElement('div');
    messageContainer.classList.add('chat-message');
    messageContainer.classList.add('other-message');

    let avatar = document.createElement('div');
    avatar.classList.add('message-avatar');
    avatar.style.backgroundImage = "url('/images/user-avatar.png')";

    let messageInfo = document.createElement('div');
    messageInfo.classList.add('message-info');

    let author = document.createElement('div');
    author.classList.add('message-author');
    author.textContent = userNickname;

    let text = document.createElement('div');
    text.classList.add('message-text');
    text.textContent = message;

    let time = document.createElement('div');
    time.classList.add('message-time');
    time.textContent = new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});

    messageInfo.appendChild(author);
    messageInfo.appendChild(text);
    messageInfo.appendChild(time);

    messageContainer.appendChild(avatar);
    messageContainer.appendChild(messageInfo);

    return messageContainer;
}

function appendMessageOutput(chatGetResponse) {

    if (chatGetResponse.sendUserName === userNickname) {
        return;
    }

    let messageBox = document.getElementById('msg');
    let messageItems = document.getElementById('chat-messages');
    let message = chatGetResponse.message;
    let messageItem = getOtherMessageItem(message);

    messageItems.appendChild(messageItem);
    messageItems.scrollTop = messageItems.scrollHeight;
    messageBox.value = '';
}

function connect(roomId) {
    let socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    myRoomId = roomId;
    stompClient.connect({}, function (frame) {
        console.log('websocket with stomp for chat is connected');
        console.log('connected info: ' + frame);
        stompClient.subscribe('/subscribe/rooms/' + myRoomId, function (messageOutput) {
            appendMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function getMyMessageItem(message) {
    let messageContainer = document.createElement('div');
    messageContainer.classList.add('chat-message');
    messageContainer.classList.add('my-message');

    let messageInfo = document.createElement('div');
    messageInfo.classList.add('message-info');

    let text = document.createElement('div');
    text.classList.add('message-text');
    text.textContent = message;

    let time = document.createElement('div');
    time.classList.add('message-time');
    time.textContent = new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});

    messageInfo.appendChild(text);
    messageInfo.appendChild(time);
    messageContainer.appendChild(messageInfo);

    return messageContainer;
}

function addMessageItem(message) {
    let messageItems = document.getElementById('chat-messages');
    if (message.length > 0) {
        let messageItem = getMyMessageItem(message);

        messageItems.appendChild(messageItem);
        messageItems.scrollTop = messageItems.scrollHeight;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    let messageBox = document.getElementById('msg');
    let sendButton = document.getElementById('sendBtn');

    function sendMessage() {
        let message = messageBox.value.trim();
        if (message) {
            stompClient.send('/app/publish/messages', {},
                JSON.stringify({
                    'userNickname': userNickname,
                    'chatRoomId': myRoomId,
                    'message': message
                })
            );
            addMessageItem(message);
            messageBox.value = '';
        }
    }

    sendButton.addEventListener('click', function () {
        sendMessage();
    });

    messageBox.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendMessage();
        }
    });
});
