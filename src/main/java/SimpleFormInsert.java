
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
	
   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ////String userName = request.getParameter("userName");
     // String email = request.getParameter("email");
      //String phone = request.getParameter("phone");
      String StudentID = request.getParameter("StudentID");
      String name = request.getParameter("name");
      String email = request.getParameter("email");
      String GPA = request.getParameter("GPA");
      //String StudentID 
      // Name
      // Year
      // GPA
      // 

      Connection connection = null;
      String insertSql = " INSERT INTO myStudent (id, STUDENTID, NAME, EMAIL, GPA) values (default, ?, ?, ?,?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         //preparedStmt.setString(1, userName);
         preparedStmt.setString(1, StudentID);
         preparedStmt.setString(2, name);
         preparedStmt.setString(3, email);
         preparedStmt.setString(4, GPA);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b> Name</b>: " + StudentID + "\n" + //
            "  <li><b>Email</b>: " + name + "\n" + //
            "  <li><b>Phone</b>: " + email + "\n" + //
            "  <li><b>GPA</b>: " + GPA + "\n" + //

            "</ul>\n");

      out.println("<a href=/StudentRecords/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
