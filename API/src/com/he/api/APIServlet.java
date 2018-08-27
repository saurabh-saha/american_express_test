package com.he.api;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.he.api.XMLReader;


public class APIServlet extends HttpServlet {
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = "";
        try{
            action = request.getParameter("action");
            if(action.equals("searchById")) {
                response.getWriter().append(XMLReader.searchById(Integer.parseInt(request.getParameter("id"))));  
            } 
            else if (action.equals("searchByFirstName")) {
                response.getWriter().append(XMLReader.searchByFN(request.getParameter("firstName")));
            }
            else if (action.equals("searchByLastName")) {
                response.getWriter().append(XMLReader.searchByLN(request.getParameter("lastName")));
            }
            else if(action.equals("searchByIdRange")) {
                response.getWriter().println(XMLReader.searchByIdR(Integer.parseInt(request.getParameter("low")),Integer.parseInt(request.getParameter("high"))));
            }
            else if(action.equals("updateUser")) {
                XMLReader.update(request.getParameter("firstName"),request.getParameter("lastName"),Integer.parseInt(request.getParameter("id")));
            }
            else if(action.equals("insertUser")) {
                XMLReader.insert(request.getParameter("firstName"),request.getParameter("lastName"));
            }
            
        }catch (Exception e) {

        }
    }


}
