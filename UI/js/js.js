var globalCurrentMessage;
var globalCurrentUsername;
var messageList = [];

function run() {
    var usernameButton = document.getElementsByClassName('changeUsername')[0];
    var messageButton = document.getElementsByClassName('enterMessage')[0];

    usernameButton.addEventListener('click', onUsernameChange);
    messageButton.addEventListener('click', onMessageEnter);

    var centerPart = document.getElementsByClassName('centralPart')[0];
    centerPart.addEventListener('click', onMassageClick);

    initMessageList();

    globalCurrentUsername = loadUsernameFromLocalStorage();
    initUsername();

    render(messageList);

    centerPart.scrollTop = centerPart.scrollHeight;
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

function initMessageList() {
    messageList = loadMessages();
    if (messageList == null) {
        messageList = [];
        saveMessages(messageList);
        messageList = loadMessages();
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
        return;
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

function saveMessages(listToSave) {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("MessagesList", JSON.stringify(listToSave));
}

function loadMessages() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("MessagesList");

    if (item == 'undefined') {
        return null;
    }

    return item && JSON.parse(item);
}

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random);
}

function newMessage(text, author) {
    var d = new Date();
    var t = d.getTime();
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

    var index = indexByElement(evtObj.target, messageList);
    messageList[index].changing = false;
    messageList[index].message = loadCurrentMessageFromLocalStorage();
    render(messageList);
    saveMessages(messageList);
}

function onDeleteButtonClick(evtObj) {
    var index = indexByElement(evtObj.target, messageList);
    messageList[index].deleted = true;
    render(messageList);
    saveMessages(messageList);
}

function onChangeButtonClick(evtObj) {
    var buttonChangeMessage = evtObj.target;
    var index = indexByElement(evtObj.target, messageList);
    var divForMessage = evtObj.target.parentElement;
    if (buttonChangeMessage.innerText == "I") {
        var divForText = divForMessage.lastElementChild;
        globalCurrentMessage = divForText.innerText;
        saveCurrentMessageInLocalStorage();
        messageList[index].changing = true;
        render(messageList);
        saveMessages(messageList);
    }
    else {
        var input = divForMessage.lastElementChild.previousElementSibling;
        if(input.value != loadCurrentMessageFromLocalStorage()) {
            messageList[index].changed = true;
        }
        messageList[index].changing = false;
        messageList[index].message = input.value;
        render(messageList);
        saveMessages(messageList);
    }
}


function onUsernameChange() {
    var centerPart = document.getElementsByClassName('centralPart')[0];
    var changeButton = document.getElementById("changeUsername");
    var editChangeName = document.getElementsByClassName("editname")[0];
    if (changeButton.innerText == "Ch-ch-change") {
        editChangeName.disabled = false;
        changeButton.innerText = "Save";
    }
    else {

        changeButton.innerText = "Ch-ch-change";
        globalCurrentUsername = editChangeName.value;
        editChangeName.disabled = true;
        centerPart.scrollTop = centerPart.scrollHeight;
        saveUsernameInLocalStorage();
        render(messageList);
    }
}

function initUsername() {
    var editChangeName = document.getElementsByClassName("editname")[0];
    editChangeName.value = globalCurrentUsername;
}

function onMessageEnter() {
    var newMessage = document.getElementById('messageArea');
    var authorName = document.getElementsByClassName('editname')[0];
    var centerPart = document.getElementsByClassName('centralPart')[0];

    var mes = addMessage(newMessage.value);
    newMessage.value = '';
    render(messageList);
    centerPart.scrollTop = centerPart.scrollHeight;
}

function addMessage(value) {
    if (!value || !loadUsernameFromLocalStorage()) {
        return;
    }
    var newMes = newMessage(value, loadUsernameFromLocalStorage());

    messageList.push(newMes);
    saveMessages(messageList);
    return newMes;
}

function indexByElement(element, messages) {
    var id = element.parentElement.getAttribute('data-mes-id');

    return messages.findIndex(function (item) {
        return item.id == id;
    });
}