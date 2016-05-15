<%@ page  contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>B-b-bumble Chat</title>
    <link rel="stylesheet" href="../css/Chat_css.css">
    <script src="../js/js.js"></script>
</head>
<body onload="run()">
<section class="titlePart">
    <header>
        B-b-bumble Chat
    </header>
    <div class="username">Us-us-username :</div>
    <input disabled class="editname" type="text" value="${pageContext.request.getParameter('username')}">
    <button class="changeUsername" id="changeUsername" type="button">Logout</button>
</section>
<section class="centralPart">
</section>
<section class="writePart">
    <textarea id="messageArea" placeholder="Write your m-m-message and click Enter"></textarea>
    <button class="enterMessage" type="button">Enter</button>
    <span class="ServerError"></span>
</section>
<footer>Z-z-zorin Nikolay Inc-inc-incorporated®, February 2016</footer>
<div style="display: none" id="message-template">
    <div data-mes-id="id">
        <button class="utilDelete">X</button>
        <button class="utilChange">I</button>
        <div>Time</div>
        <div>Author</div>
        <div>Message</div>
    </div>
</div>

<div style="display: none" id="message-changing-template">
    <div data-mes-id="id">
        <button class="utilDelete">X</button>
        <button class="utilChange">K</button>
        <div>Time</div>
        <div>Author</div>
        <input type="text">
        <button class="utilDontSave">Don't save</button>
    </div>
</div>

<div style="display: none" id="message-deleted-template">
    <div data-mes-id="id">
        DELETED
    </div>
</div>

</body>
</html>