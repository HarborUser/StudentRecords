import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   
   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#839192\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM myStudent";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM myStudent WHERE STUDENTID LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();
         System.out.println("Error Check");
         while (rs.next()) {
            int id = rs.getInt("id");
            System.out.println("Error Check");
            //String userName = rs.getString("myuser").trim();
            //String email = rs.getString("email").trim();
            //String phone = rs.getString("phone").trim();
            
            String StudentID = rs.getString("StudentID").trim();
            String name = rs.getString("name").trim();
            String gmail = rs.getString("email").trim();
            String GPA = rs.getString("GPA").trim();
            System.out.println("Error Check");
            //String StudentID = request.getParameter("Student ID: ");
            //String name = request.getParameter("Name: ");
            //String email = request.getParameter("email: ");
           // String GPA = request.getParameter("GPA: ");
            if (keyword.isEmpty() || StudentID.contains(keyword)) {
            	System.out.println("Error Check");
               out.println("ID: " + id + ", ");
               out.println("StudentID: " + StudentID + ", ");
               out.println("Name: " + name + ", ");
               out.println("Email: " + gmail + ", ");
               out.println("GPA: " + GPA + "<br>");
            }
         }
         out.println("<a href=/StudentRecords/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
