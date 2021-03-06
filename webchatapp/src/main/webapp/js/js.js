'use strict';

var globalCurrentMessage;
var globalCurrentUsername;

var Application = {
    mainUrl : 'http://localhost:8080/chat',
    secondUrl : 'http://localhost:8080/login',
    messageList : [],
    token : 'TN11EN',
    isConnected : null
};

function run() {
    var usernameButton = document.getElementsByClassName('changeUsername')[0];
    var messageButton = document.getElementsByClassName('enterMessage')[0];

    messageButton.addEventListener('click', onMessageEnter);
   // usernameButton.addEventListener('click', onUsernameChange);

    var centerPart = document.getElementsByClassName('centralPart')[0];
    centerPart.addEventListener('click', onMassageClick);

    initMessageList();

    initUsername();

    centerPart.scrollTop = centerPart.scrollHeight;
}

function onUsernameChange() {
    var url = Application.mainUrl + "/exit";
    ajax('GET', url, null);
}

function render(messages) {
    var items = document.getElementsByClassName('centralPart')[0];
    var children = items.children;
    while (children.length > 0) {
        items.removeChild(children[0]);
    }
    for (var i = 0; i < messages.length; i++) {
        if (messages[i].author == globalCurrentUsername) {
            renderAllyMessage(messages[i]);
        }
        else {
            renderEnemyMessage(messages[i]);
        }
    }
}

function getMessageHistory() {
    var url = Application.mainUrl + '?token=' + Application.token;

        ajax('GET', url, null, function(responseText){
            var json = JSON.parse(responseText);
            Application.messageList = Application.messageList.concat(json.messages);
            Application.token = json.token;
            render(Application.messageList);
            Connect();
            var centerPart = document.getElementsByClassName('centralPart')[0];
            centerPart.scrollTop = centerPart.scrollHeight;
        });
}

function initMessageList() {
    getMessageHistory();

    if (Application.messageList == null) {
        Application.messageList = [];
    }
}

function renderAllyMessage(messaage) {
    var element;
    var items = document.getElementsByClassName('centralPart')[0];
    if (messaage.deleted == true) {
        element = elementDeletedFromTemplate();
    }
    else if (messaage.changing == true) {
        element = elementChangingFromTemplate();
        renderMessageValues(element, messaage);
    }

    else if (messaage.changed == true) {
        element = elementFromTemplate();
        renderMessageValues(element, messaage);
    }

    else {
        element = elementFromTemplate();
        renderMessageValues(element, messaage);
    }

    element.classList.add('messageAlly');
    items.appendChild(element);
}

function renderEnemyMessage(messaage) {
    var element;
    var items = document.getElementsByClassName('centralPart')[0];
    if (messaage.deleted == true) {
        element = elementDeletedFromTemplate();
    }
    else if (messaage.changing == true) {
        element = elementChangingFromTemplate();
        element.firstElementChild.style.display = "none";
        element.firstElementChild.nextElementSibling.style.display = "none";
        renderMessageValues(element, messaage);
    }

    else if (messaage.changed == true) {
        element = elementFromTemplate();
        element.firstElementChild.style.display = "none";
        element.firstElementChild.nextElementSibling.style.display = "none";
        renderMessageValues(element, messaage);
    }

    else {
        element = elementFromTemplate();
        element.firstElementChild.style.display = "none";
        element.firstElementChild.nextElementSibling.style.display = "none";
        renderMessageValues(element, messaage);
    }

    element.classList.add('messageEnemy');
    items.appendChild(element);
}

function renderMessageValues(element, message) {
    element.setAttribute('data-mes-id', message.id);
    var d = new Date(message.time);
    var validDate = d.toLocaleDateString() + " " + d.toLocaleTimeString();
    element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.textContent = message.author + " :";
    if (message.changed == true) {
        element.firstElementChild.nextElementSibling.nextElementSibling.textContent = validDate + " (Changed)";
        element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.
            nextElementSibling.textContent = message.message;
    }
    if(message.changing == true) {
        element.firstElementChild.nextElementSibling.nextElementSibling.textContent = validDate;
        element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.
            nextElementSibling.setAttribute('value', message.message);
    }
    if(message.changing == true && message.changed == true) {
        element.firstElementChild.nextElementSibling.nextElementSibling.textContent = validDate + " (Changed)";
        element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.
        nextElementSibling.setAttribute('value', message.message);
    }
    if(message.changing == false && message.changed == false) {
        element.firstElementChild.nextElementSibling.nextElementSibling.textContent = validDate;
        element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.
            nextElementSibling.textContent = message.message;
    }
}

function elementFromTemplate() {
    var template = document.getElementById("message-template");
    return template.firstElementChild.cloneNode(true);
}

function elementDeletedFromTemplate() {
    var template = document.getElementById("message-deleted-template");
    return template.firstElementChild.cloneNode(true);
}

function elementChangingFromTemplate() {
    var template = document.getElementById("message-changing-template");
    return template.firstElementChild.cloneNode(true);
}

function saveUsernameInLocalStorage() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("CurrentUsername", JSON.stringify(globalCurrentUsername));
}

function loadUsernameFromLocalStorage() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return ;
    }

    var item = localStorage.getItem("CurrentUsername");

    return item && JSON.parse(item);
}

function saveCurrentMessageInLocalStorage() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("CurrentMessage", JSON.stringify(globalCurrentMessage));
}

function loadCurrentMessageFromLocalStorage() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("CurrentMessage");

    return item && JSON.parse(item);
}

function saveMessage(newMessage) {
    ajax('POST', Application.mainUrl, JSON.stringify(newMessage), function(responseText){
        var json = JSON.parse(responseText);
        Application.token = json.token;
        render(Application.messageList);
    });
}

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random);
}

function newMessage(text, author) {
    var d = new Date();
    d = d.getTime();
    return {
        message: text,
        author: author,
        time: d,
        deleted: false,
        changed: false,
        changing: false,
        id: '' + uniqueId()
    };
}

function onMassageClick(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('utilDelete')) {
        onDeleteButtonClick(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('utilChange')) {
        onChangeButtonClick(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('utilDontSave')) {
        onDontChangeButtonClick(evtObj);
    }
}

function onDontChangeButtonClick(evtObj) {

    var index = indexByElement(evtObj.target, Application.messageList);
    Application.messageList[index].changing = false;
    Application.messageList[index].message = loadCurrentMessageFromLocalStorage();
    render(Application.messageList);
    saveMessages(Application.messageList);
}

function onDeleteButtonClick(evtObj) {
    var index = indexByElement(evtObj.target, Application.messageList);
    var mes = Application.messageList[index];

    var mesToDelete = {
        id: mes.id
    };

    ajax('DELETE', Application.mainUrl, JSON.stringify(mesToDelete), function(){
        mes.deleted = true;
        render(Application.messageList);
    });
}

function onChangeButtonClick(evtObj) {
    var buttonChangeMessage = evtObj.target;
    var index = indexByElement(evtObj.target, Application.messageList);
    var divForMessage = evtObj.target.parentElement;
    if (buttonChangeMessage.innerText == "I") {
        var divForText = divForMessage.lastElementChild;
        globalCurrentMessage = divForText.innerText;
        saveCurrentMessageInLocalStorage();
        Application.messageList[index].changing = true;
        render(Application.messageList);
    }
    else {
        var input = divForMessage.lastElementChild.previousElementSibling;
        var m = Application.messageList[index];
        if(input.value != loadCurrentMessageFromLocalStorage()) {
            m.changed = true;
        }
        m.changing = false;
        m.message = input.value;
        sendPutRequestToServer(m);
    }
}

function sendPutRequestToServer(m) {
    ajax('PUT', Application.mainUrl, JSON.stringify(m), function(){
        render(Application.messageList);
    });
}

function initUsername() {
    var editChangeName = document.getElementsByClassName("editname")[0];
    globalCurrentUsername = editChangeName.value;
    if(globalCurrentUsername == '') {
        var uid = getCookie('uid');
        ajax('PUT', Application.mainUrl + "/getUsername", JSON.stringify(uid), function(responseText){
            var json = responseText;
            globalCurrentUsername =  json;
            editChangeName.value = json;
        });

    }
}

function onMessageEnter() {
    var newMessage = document.getElementById('messageArea');
    var centerPart = document.getElementsByClassName('centralPart')[0];

    addMessage(newMessage.value);
    newMessage.value = '';
    centerPart.scrollTop = centerPart.scrollHeight;
}

function addMessage(value) {
    if (!value || !globalCurrentUsername) {
        return;
    }
    var newMes = newMessage(value, globalCurrentUsername);

    Application.messageList.push(newMes);
    saveMessage(newMes);
    return newMes;
}

function indexByElement(element, messages) {
    var id = element.parentElement.getAttribute('data-mes-id');

    return messages.findIndex(function (item) {
        return item.id == id;
    });
}

function ajax(method, url, data, continueWith, continueWithError) {
    var xhr = new XMLHttpRequest();

    continueWithError = continueWithError || defaultErrorHandler;
    xhr.open(method || 'GET', url, true);

    xhr.onload = function () {
        if (xhr.readyState !== 4)
            return;

        if(xhr.status != 200) {
            continueWithError('Error on the server side, response ' + xhr.status);
            return;
        }

        if(isError(xhr.responseText)) {
            continueWithError('Error on the server side, response ' + xhr.responseText);
            return;
        }

        continueWith(xhr.responseText);
        Application.isConnected = true;
    };

    xhr.ontimeout = function () {
        ServerError();
    }

    xhr.onerror = function (e) {
        ServerError();
    };

    xhr.send(data);
}

function defaultErrorHandler(message) {
    console.error(message);
}

function isError(text) {
    if(text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch(ex) {
        return text.error;
    }

    return !!obj.error;
}

function ServerError(){
    var errorServer = document.getElementsByClassName('ServerError')[0];
    errorServer.innerHTML = '<img class="alarm" align="right" src="img/alarm.png" alt="Connection problems">';
}

function Connect() {
    if(Application.isConnected)
        return;

    function whileConnected() {
        Application.isConnected = setTimeout(function () {
            ajax('GET', Application.mainUrl + '?token=' + Application.token, null,function (serverResponse) {
                if (Application.isConnected) {
                    var json = JSON.parse(serverResponse);
                    Application.messageList = Application.messageList.concat(json.messages);
                    Application.token = json.token;
                    render(Application.messageList);
                    var centerPart = document.getElementsByClassName('centralPart')[0];
                    centerPart.scrollTop = centerPart.scrollHeight;
                    whileConnected();
                }
            });
        }, Math.round(1000));
    }

    whileConnected();
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^]")/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function setCookie (name, value) {
    document.cookie = name + "=" + escape(value);
}