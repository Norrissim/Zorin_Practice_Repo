package Servlets;

import Classes.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import storage.InMemoryMessageStorage;
import storage.MessageStorage;
import storage.Portion;
import utils.Constants;
import utils.InvalidTokenException;
import utils.MessageHelper;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value= "/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageStorage messageStorage = new InMemoryMessageStorage();
        String query = req.getQueryString();
        if (query == null) {

        }
        Map<String, String> map = queryToMap(query);
        String token = map.get(Constants.REQUEST_PARAM_TOKEN);
        if (StringUtils.isEmpty(token)) {

        }
        try {
            int index = MessageHelper.parseToken(token);
            if (index > messageStorage.size()) {

            }
            Portion portion = new Portion(index);
            List<Message> messages = messageStorage.getPortion(portion);
            PrintWriter wr = resp.getWriter();
            wr.write(MessageHelper.buildServerResponseBody(messages, messages.size() + portion.getFromIndex()));
            wr.close();
        } catch (InvalidTokenException e) {
            resp.sendError(400, "Incorrect token in request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageStorage messageStorage = new InMemoryMessageStorage();
        try {
            BufferedReader br = req.getReader();
            Message message = MessageHelper.getClientMessage(br.readLine());
            messageStorage.addMessage(message);
            String responseBody = MessageHelper.buildServerResponseBody(null, messageStorage.size());
            PrintWriter pw = resp.getWriter();
            pw.write(responseBody);
            pw.close();
        } catch (ParseException e) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Incorrect request body");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageStorage messageStorage = new InMemoryMessageStorage();
        try {
            Message message = MessageHelper.getClientMessage(req.getReader().readLine());
            messageStorage.updateMessage(message);
        } catch (ParseException e) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Incorrect request body");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageStorage messageStorage = new InMemoryMessageStorage();
        String body = req.getReader().readLine();
        if (body == null) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Absent query in request");
        }
        try {
            JSONObject jsonObj = MessageHelper.stringToJsonObject(body);
            String id = jsonObj.get("id").toString();
            messageStorage.removeMessage(jsonObj.get("id").toString());
            jsonObj = null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();

        for (String queryParam : query.split(Constants.REQUEST_PARAMS_DELIMITER)) {
            String paramKeyValuePair[] = queryParam.split("=");
            if (paramKeyValuePair.length > 1) {
                result.put(paramKeyValuePair[0], paramKeyValuePair[1]);
            } else {
                result.put(paramKeyValuePair[0], "");
            }
        }
        return result;
    }
}
