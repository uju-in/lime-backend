// STOMP 클라이언트 변수 초기화
let stompClient = null;

// 사용자 닉네임 생성 (랜덤 문자열)
let userNickname = 'user' + generateRandomString(6);

// 사용자의 채팅방 ID
// connet() 함수에서 할당
let myRoomId;

// 랜덤 문자열을 생성하는 함수
// 사용자 닉네임 생성할 때 사용
function generateRandomString(length) {
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}

// 웹소켓 연결을 끊는 함수
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("websocket with stomp for chat is disconnected");
}

// 다른 사용자의 메시지 항목을 생성하는 함수
function getOtherMessageItem(message) {
    // 메시지 컨테이너에 avatar, messageInfo를 추가 하고 반환 한다.
    let messageContainer = document.createElement('div');
    messageContainer.classList.add('chat-message');
    messageContainer.classList.add('other-message');

    // 사용자 avatar 설정
    let avatar = document.createElement('div');
    avatar.classList.add('message-avatar');
    avatar.style.backgroundImage = "url('/images/user-avatar.png')";

    // messageInfo 생성
    let messageInfo = document.createElement('div');
    messageInfo.classList.add('message-info');

    // 메시지 작성자 표시
    let author = document.createElement('div');
    author.classList.add('message-author');
    author.textContent = userNickname;

    // 메시지 텍스트 설정
    let text = document.createElement('div');
    text.classList.add('message-text');
    text.textContent = message;

    // 메시지 시간 표시
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

// 수신된 메시지를 화면에 추가하는 함수
// 서버에서 실시간으로 메시지를 받으면 호출 된다.
function appendMessageOutput(chatGetResponse) {

    // 자신이 보낸 메시지는 추가하지 않음
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

// 웹소켓 연결 및 구독 설정 함수
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

// 자신의 메시지 항목을 생성하는 함수
function getMyMessageItem(message) {
    // 메시지 컨테이너에 messageInfo를 추가 하고 반환 한다.
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

// 메시지 아이템을 화면에 추가하는 함수
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

    // 메시지 보내기 함수
    function sendMessage() {
        let message = messageBox.value.trim();

        if (message.length > 0) {

            // 서버로 메시지 전송
            stompClient.send('/app/publish/messages', {},
                JSON.stringify({
                    'userNickname': userNickname,
                    'chatRoomId': myRoomId,
                    'message': message
                })
            );

            // 자신의 메시지를 화면에 추가
            addMessageItem(message);

            // 메시지 입력창 초기화
            messageBox.value = '';
        }
    }

    // 버튼 클릭 이벤트 리스너 설정
    sendButton.addEventListener('click', function () {
        sendMessage();
    });

    // 키보드 이벤트 리스너 설정
    messageBox.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendMessage();
        }
    });
});
