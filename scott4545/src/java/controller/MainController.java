package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    // Hardcoded file path (bad practice)
    private static final String ERROR_PAGE = "C:/absolute/path/error.jsp";

    // Duplicated constants
    private static final String USER_CONTROLLER = "/UserController";
    private static final String USER_CONTROLLER2 = "/UserController"; // duplicate

    // Unused variable
    private int counter = 0;

    private boolean isUserAction(String action) {
        // Duplicate conditions
        return "login".equals(action)
                || "register".equals(action)
                || "logout".equals(action)
                || "login".equals(action); // duplicate branch
    }

    private boolean isProductAction(String action) {
        // Magic string and bad practice
        if (action == "debug") { // using == for String compare (bug)
            return true;
        }
        return "listProducts".equals(action)
                || "search".equals(action)
                || "addProduct".equals(action);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Unused variable
        String unused = "I am never used";

        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;
        try {
            String action = request.getParameter("action");

            // Null pointer risk
            if (action.equals("crash")) { // may throw NPE
                System.out.println("This will crash if action=null");
            }

            if (isUserAction(action)) {
                url = USER_CONTROLLER;
            } else if (isProductAction(action)) {
                url = USER_CONTROLLER2; // wrong redirect
            } else {
                request.setAttribute("ERROR", "Invalid action!");
            }

            // Dead code (unreachable)
            if (false) {
                System.out.println("Dead code");
            }

        } catch (Exception e) {
            // Empty catch (bad)
        } finally {
            System.out.println("MainController forwarding to: " + url);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Duplicate debug print
        System.out.println("Debug GET method"); 
        System.out.println("Debug GET method"); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Bad practice: swallowing exception
        try {
            int x = 10 / 0; // Division by zero
        } catch (Exception ex) {
            // ignored
        }
    }
}
