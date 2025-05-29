package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mime")
@MultipartConfig
public class MimeTypes extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String body=new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(Collectors.joining());
//        resp.setContentType("text/plain");
//        resp.getWriter().write(body);
//    }



    /*---read x www-form-urlencoded data from a http request---*/
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//
//        resp.setContentType("text/plain");
//        resp.getWriter().write(name + " " + address);
//    }


    /*---read multipart/form data from a http request---*/
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name=req.getParameter("name");
//        Part part = req.getPart("file");
//        String filename=part.getSubmittedFileName();
//
//        resp.setContentType("text/plain");
//        resp.getWriter().write(name+"-"+filename);
//    }


    /*---read JSON data from httpRequest body---*/
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(req.getReader());
//
//            String name = jsonNode.get("name").asText();
//            String address = jsonNode.get("address").asText();
//
//            resp.setContentType("text/plain");
//            resp.getWriter().write(name+" "+address);
//
//    }


    /*---read JSON array from httpRequest body---*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> users=mapper.readValue(req.getReader(), new TypeReference<List<JsonNode>>() {});

        for (JsonNode user : users){
            String name = user.get("name").asText();
            String address = user.get("address").asText();
            System.out.println(name + " " + address);
        }
        resp.setContentType("text/plain");
        resp.getWriter().println("OK");
    }
}
