package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.DatabaseMetaData;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dbcp")
public class DBCPServlet extends HttpServlet {
     BasicDataSource ds;

    @Override
    public void init() throws ServletException {
        ds  = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/eventdb");
        ds.setUsername("root");
        ds.setPassword("Ijse@1234");
        ds.setInitialSize(50);
        ds.setMaxTotal(100);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Connection connection=ds.getConnection();
            ResultSet resultSet=connection.prepareStatement("select * from event").executeQuery();
            List<Map<String,String>> elist=new ArrayList<>();
            while (resultSet.next()) {
                Map<String,String> event=new HashMap<String,String>();
                event.put("eid",resultSet.getString("eid"));
                event.put("ename",resultSet.getString("ename"));
                event.put("edescription",resultSet.getString("edescription"));
                event.put("edate",resultSet.getString("edate"));
                event.put("eplace",resultSet.getString("eplace"));
                elist.add(event);
            }
            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(),elist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getInputStream(), Map.class);

        try {
            Connection connection = ds.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO event (eid,ename, edescription, edate, eplace) VALUES (?, ?, ?, ?,?)"
            );
            stmt.setString(1, event.get("eid"));
            stmt.setString(2, event.get("ename"));
            stmt.setString(3, event.get("edescription"));
            stmt.setString(4, event.get("edate"));
            stmt.setString(5, event.get("eplace"));

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
