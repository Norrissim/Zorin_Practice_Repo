var gloablTempSt;
function run() {
    var usernameButton = document.getElementsByClassName('changeUsername')[0];
    var messageButton = document.getElementsByClassName('enterMessage')[0];

    usernameButton.addEventListener('click', onUsernameChange);
    messageButton.addEventListener('click', onMessageEnter);

    var centerPart = document.getElementsByClassName('centralPart')[0];
    centerPart.addEventListener('click', onMassegeClick);

    centerPart.scrollTop = centerPart.scrollHeight;
}

function onMassegeClick(evtObj) {
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
    divForText.innerText = gloablTempSt;
    divForMessage.removeChild(buttonDontSave);
    divForMessage.removeChild(inputForNewMessage);
    divForMessage.appendChild(divForText);
}

function onDeleteButtonClick(evtObj) {
    evtObj.target.parentElement.remove();
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
        gloablTempSt = divForText.innerText;
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
        editChangeName.disabled = true;
    }
}

function onMessageEnter() {
    var newMessage = document.getElementById('messageArea');
    var authorName = document.getElementsByClassName('editname')[0];
    var centerPart = document.getElementsByClassName('centralPart')[0];

    addMessage(newMessage.value, authorName.value);
    newMessage.value = '';
    centerPart.scrollTop = centerPart.scrollHeight;
}

function addMessage(value, author) {
    if (!value || !author) {
        return;
    }
    var item = createItem(value, author);
    var items = document.getElementsByClassName('centralPart')[0];

    items.appendChild(item);
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