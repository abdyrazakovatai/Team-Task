window.onload = function () {

    console.log("StompJs: ", window); // Исправлено на StompJs
    if (!window.Stomp) { // Исправлено на StompJs
        console.error("StompJs failed to load!"); // Исправлено сообщение
        return;
    }
    const socket = new SockJS('https://my-websocket-chat-545a0987aa03.herokuapp.com/chat'); // Исправлен эндпоинт на /chat
    const stompClient = window.Stomp.over(socket); // Исправлено на StompJs

    console.log("stompClient: ", stompClient); // Исправлено имя лога

    let unreadCount = 0;
    const currentUserId = 1;

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            const msg = JSON.parse(message.body);
            const isUserMessage = msg.user && msg.user.id === currentUserId;
            showMessage(msg.content, isUserMessage);
        });
    }, function (error) {
        console.error('Connection error: ' + error);
    });

    document.getElementById('sendMessageButton').addEventListener('click', function () {
        const content = document.getElementById('messageInput').value;
        if (content.trim()) {
            stompClient.send('/app/send', {}, JSON.stringify({
                content: content,
                chatId: 1,
                userId: currentUserId,
                sender: 'User'
            }));
            document.getElementById('messageInput').value = '';
        }
    });

    function showMessage(content, isUserMessage) {
        const messagesDiv = document.getElementById('messages');
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message ' + (isUserMessage ? 'user-message' : 'friend-message');
        messageDiv.innerHTML = '<span class="sender">' + (isUserMessage ? 'Вы' : 'Друг') + '</span>' + content;
        messagesDiv.appendChild(messageDiv);
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
        unreadCount++;
        document.getElementById('unreadCounter').textContent = '(' + unreadCount + ')';
    }
};