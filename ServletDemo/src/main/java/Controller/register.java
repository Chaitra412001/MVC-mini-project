package Controller;
import model.Registration;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "register", urlPatterns = {"/register"})
    public class register extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // type of the response sent to the client or browser
        PrintWriter out = response.getWriter();//response.getWriter retreives a printWriter objct,which is used to send text based data to  the clients web browser as a part of httpm response
                                                  // this allows servlet to dynamically generate html,xml,or any text based content to be displayed in the browser.
        HttpSession session = request.getSession();
        Registration reg = new Registration(session);
        try {
            if (request.getParameter("register") != null) { // fetching all the below records

                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String pw = request.getParameter("pw");
                String cp = request.getParameter("cp");

                if (pw.equals(cp)) {
                    String status = reg.Registration(name, phone, email, pw);
                    
                    
                    if (status.equals("existed")) {

                        request.setAttribute("status", "Existed record");
                        RequestDispatcher rd1 = request.getRequestDispatcher("Registration.jsp");
                        rd1.forward(request, response);

                    } else if (status.equals("success")) {
                        request.setAttribute("status", "Successfully Registered");
                        RequestDispatcher rd1 = request.getRequestDispatcher("Login.jsp");// to move the controller from one page to another
                        rd1.forward(request, response);

                    } else if (status.equals("failure")) {
                        request.setAttribute("status", "Registration failed");
                        RequestDispatcher rd1 = request.getRequestDispatcher("Registration.jsp");
                        rd1.forward(request, response);
                        
                        

                    }
                }
            } else if (request.getParameter("login") != null) {
                String email = request.getParameter("email");
                String pass = request.getParameter("pw");
                String status = reg.login(email, pass);
                if (status.equals("success")) {

                    RequestDispatcher rd1 = request.getRequestDispatcher("Index.jsp");

                    rd1.forward(request, response);

                } else if (status.equals("failure")) {
                    request.setAttribute("status", "Login failed");
                    RequestDispatcher rd1 = request.getRequestDispatcher("Login.jsp");
                    rd1.forward(request, response);
                }
            } else if (request.getParameter("logout") != null) {
                session.invalidate();
                RequestDispatcher rd1 = request.getRequestDispatcher("Index.jsp");
                rd1.forward(request, response);
            } 
            
            else if (session.getAttribute("uname") != null && request.getParameter("submit") != null) {
                String name = request.getParameter("name");
                String pno = request.getParameter("pno");
                String email = request.getParameter("email");
                Registration u = new Registration(session);
                String status = u.update(name, pno, email);
                if (status.equals("success")) {
                    request.setAttribute("status", "Profile successfully Updated");
                    RequestDispatcher rd1 = request.getRequestDispatcher("Index.jsp");
                    rd1.forward(request, response);
                } else {
                    request.setAttribute("status", "Updation failure");
                    RequestDispatcher rd1 = request.getRequestDispatcher("Index.jsp");
                    rd1.forward(request, response);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)// internally calls the processRequest  method
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


    


