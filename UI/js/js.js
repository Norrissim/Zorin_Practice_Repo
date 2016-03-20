var globalTempSt;
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
    while(children.length > 0) {
        items.removeChild(children[0]);
    }
    for(var i = 0; i < messages.length; i++) {
        if(messages[i].author == globalCurrentUsername) {
            renderAllyMessage(messages[i]);
        }
        else {
            renderEnemyMessage(messages[i]);
        }
    }
}

function initMessageList() {
    messageList = loadMessages();
    if(messageList == null) {
        messageList = [];
        saveMessages(messageList);
        messageList = loadMessages();
    }
}

function renderAllyMessage(messaage){
    var items = document.getElementsByClassName('centralPart')[0];
    var element = elementFromTemplate();
    element.classList.add('messageAlly');
    renderMessageValues(element, messaage);

    items.appendChild(element);
}

function renderEnemyMessage(messaage){
    var items = document.getElementsByClassName('centralPart')[0];
    var element = elementFromTemplate();
    element.classList.add('messageEnemy');
    renderMessageValues(element, messaage);

    items.appendChild(element);
}

function renderMessageValues(element, message) {
    element.setAttribute('data-mes-id', message.id);
    var d = new Date(message.time);
    var validDate = d.toLocaleDateString() + " " + d.toLocaleTimeString();
    element.firstElementChild.nextElementSibling.nextElementSibling.textContent = validDate;
    element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.textContent = message.author + " :";
    element.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.textContent = message.message;
}

function elementFromTemplate() {
    var template = document.getElementById("message-template");

    return template.firstElementChild.cloneNode(true);
}

function saveUsernameInLocalStorage() {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("CurrentUsername", JSON.stringify(globalCurrentUsername));
}

function loadUsernameFromLocalStorage() {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("CurrentUsername");

    return item && JSON.parse(item);
}

function saveMessages(listToSave) {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("MessagesList", JSON.stringify(listToSave));
}

function loadMessages() {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("MessagesList");

    if(item == 'undefined')
    {
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
        message:text,
        author: author,
        time: d,
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
}

function onDontChangeButtonClick(evtObj) {

    var buttonChangeMessage = evtObj.target.parentElement.firstElementChild.nextElementSibling;
    var divForMessage = evtObj.target.parentElement;
    buttonChangeMessage.innerText = "I";
    var inputForNewMessage = divForMessage.lastElementChild.previousSibling;
    var buttonDontSave = divForMessage.lastElementChild;
    var divForText = document.createElement('div');
    divForText.innerText = globalTempSt;
    divForMessage.removeChild(buttonDontSave);
    divForMessage.removeChild(inputForNewMessage);
    divForMessage.appendChild(divForText);
}

function onDeleteButtonClick(evtObj) {
    var index = indexByElement(evtObj.target, messageList);
    messageList.splice(index, 1);
    render(messageList);
    saveMessages(messageList);
}

function onChangeButtonClick(evtObj) {
    var buttonChangeMessage = evtObj.target;
    var divForMessage = evtObj.target.parentElement;
    if (buttonChangeMessage.innerText == "I") {
        buttonChangeMessage.innerText = "K";
        var buttonDontSave = document.createElement('button');
        buttonDontSave.classList.add('utilDontSave');
        buttonDontSave.appendChild(document.createTextNode("Don't save"));
        var divForText = divForMessage.lastElementChild;
        var tempMes = divForText.innerText;
        globalTempSt = divForText.innerText;
        var inputForNewMessage = document.createElement('input');
        inputForNewMessage.classList.add('editname');
        inputForNewMessage.value = tempMes;
        divForMessage.removeChild(divForText);
        divForMessage.appendChild(inputForNewMessage);
        divForMessage.appendChild(buttonDontSave);
        buttonDontSave.addEventListener('click', onDontChangeButtonClick);
    }
    else {
        buttonChangeMessage.innerText = "I";
        var inputForNewMessage = divForMessage.lastElementChild.previousElementSibling;
        var buttonDontSave = divForMessage.lastElementChild;
        var tempMes = inputForNewMessage.value;
        var divForText = document.createElement('div');
        divForText.innerText = tempMes;
        divForMessage.removeChild(buttonDontSave);
        divForMessage.removeChild(inputForNewMessage);
        divForMessage.appendChild(divForText);
    }
}


function onUsernameChange() {
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

function indexByElement(element, messages){
    var id = element.parentElement.getAttribute('data-mes-id');

    return messages.findIndex(function(item) {
        return item.id == id;
    });
}

function createItem(text, author) {
    var divForMessage = document.createElement('div');
    var buttonForDel = document.createElement('button');
    var buttonForChange = document.createElement('button');
    var divForTime = document.createElement('div');
    var divForAuthor = document.createElement('div');
    var divForText = document.createElement('div');
    var d = new Date();
    var t = d.getTime();
    buttonForDel.classList.add('utilDelete');
    buttonForChange.classList.add('utilChange');
    divForMessage.appendChild(buttonForDel);
    buttonForDel.appendChild(document.createTextNode('X'));
    divForMessage.appendChild(buttonForChange);
    buttonForChange.appendChild(document.createTextNode('I'));
    divForMessage.appendChild(divForTime);
    divForTime.appendChild(document.createTextNode(d.toTimeString()));
    divForMessage.appendChild(divForAuthor);
    divForAuthor.appendChild(document.createTextNode(author + " :"));
    divForMessage.appendChild(divForText);
    divForText.appendChild(document.createTextNode(text));
    divForMessage.classList.add('messageAlly');


    return divForMessage;
}