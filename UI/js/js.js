function run(){
    var usernameButton = document.getElementsByClassName('changeUsername')[0];
    var messageButton = document.getElementsByClassName('enterMessage')[0];


    usernameButton.addEventListener('click', onUsernameChange);
    messageButton.addEventListener('click', onMessageEnter);

    var centerPart = document.getElementsByClassName('centralPart')[0];
    centerPart.addEventListener('click', onMassegeClick);

    window.scrollTo(0, document.body.scrollHeight);

}

function onMassegeClick(evtObj) {
    if(evtObj.type === 'click' && evtObj.target.classList.contains('utilDelete')){
        onDeleteButtonClick(evtObj);
    }
    if(evtObj.type === 'click' && evtObj.target.classList.contains('utilChange')){
        onChangeButtonClick(evtObj);
    }
}

function onDeleteButtonClick(evtObj){
    evtObj.target.parentElement.remove();
}

function onChangeButtonClick(evtObj){
    var divForButtons = evtObj.target.parentElement.parentElement;
    var divForMessage = divForButtons.parentElement.parentElement;
    var pForMessage = divForButtons.parentElement;
    var divForText = document.createElement('div');
    var text = divForButtons.previousElementSibling;
    var messageTextarea = document.getElementById('messageTextarea');
    pForMessage.removeChild(text);
    pForMessage.removeChild(divForButtons);
    pForMessage.appendChild(divForText);
    divForText.appendChild(document.createTextNode(messageTextarea.value));
    pForMessage.appendChild(divForButtons);
    divForMessage.classList.remove('yourMessage');
    divForMessage.classList.add('yourMessageChanged');
}



function onUsernameChange(){
    var changeButton = document.getElementById("changeUsername");
    var editChangeName = document.getElementsByClassName("editname")[0];
    if(changeButton.innerText == "Ch-ch-change") {
        editChangeName.disabled = false;
        changeButton.innerText = "Save";
    }
    else {
        changeButton.innerText = "Ch-ch-change";
        editChangeName.disabled = true;
    }
}

function onMessageEnter(){
    var newMessage = document.getElementById('messageArea');
    var authorName = document.getElementsByClassName('editname')[0];

    addMessage(newMessage.value, authorName.value);
    newMessage.value = '';
    window.scrollTo(0, document.body.scrollHeight);

}

function addMessage(value, author) {
    if(!value || !author){
        return;
    }
    var item = createItem(value, author);
    var items = document.getElementsByClassName('centralPart')[0];

    items.appendChild(item);
}

function createItem(text, author){
    var divForMessage = document.createElement('div');
    var buttonForDel = document.createElement('button');
    var buttonForChange = document.createElement('button');
    var divForTime = document.createElement('div');
    var divForAuthor = document.createElement('div');
    var divForText = document.createElement('div');
    var d = new Date();
    var t= d.getTime();
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