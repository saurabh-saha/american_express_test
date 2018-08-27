package com.he.api;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class XMLReader {

    public static List<User> userList = new LinkedList<>();
    public static Document doc;
    public static int auto_inc;
    public static void main(String[] args){
        List<User> read = read();
        String user = searchById(1);
        String userList = searchByFN("Hacker");
        String userList1 = searchByLN("Earth");
        String userList2 = searchByIdR(2, 4);
        update("","Poo",1);
        insert("Saurabh", "Saha");
        write();
    }

    public static String searchById(int id) {
        read();
        List<User> ul = new LinkedList<>();
        for(User u : userList) {
            if(u.id == id) {
                ul.add(u);
            }
        }
        return JsonUtility.convertArray(ul).toString();
    }

    public static String searchByIdR(int idl, int idh) {
        read();
        List<User> ul = new LinkedList<>();
        for(User u : userList) {
            if(u.id >= idl && u.id <= idh) {
                ul.add(u);
            }
        }
        return JsonUtility.convertArray(ul).toString();
    }

    public static void update(String fn, String ln, int id) {
        read();
        for(User u : userList) {
            if(u.id == id) {
                if(!fn.equals(""))
                    u.firstName =  fn;
                if(!ln.equals(""))
                    u.lastName =  ln;
                return;
            }
        }
    }

    public static void insert(String fn, String ln) {
        read();
        User u = new User();
        u.firstName = Objects.isNull(fn) ? "" : fn;
        u.lastName = Objects.isNull(ln) ? "" : ln;
        u.id = auto_inc++;
        userList.add(u);
    }

    public static String searchByFN(String fn) {
        read();
        List<User> ul = new LinkedList<>();
        for(User u : userList) {
            if(u.firstName.equals(fn)) {
                ul.add(u);
            }
        }
        return JsonUtility.convertArray(ul).toString();
    }

    public static String searchByLN(String ln) {
        read();
        List<User> ul = new LinkedList<>();
        for(User u : userList) {
            if(u.lastName.equals(ln)) {
                ul.add(u);
            }
        }
        return JsonUtility.convertArray(ul).toString();
    }

    public static List<User> read() {
        if(userList.size() != 0) {
            return userList;
        }

        try {
            File fXmlFile = new File("src/com/he/api/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            auto_inc = Integer.parseInt(doc.getDocumentElement().getAttribute("id_auto_increment"));
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("user");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    User user = new User();
                    user.id = Integer.parseInt(eElement.getAttribute("id"));
                    user.firstName = eElement.getAttribute("firstName");
                    user.lastName = eElement.getAttribute("lastName");
                    userList.add(user);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    private static String convertToXML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        builder.append("\n");
        builder.append(MessageFormat.format("<dataset id_auto_increment=\"{0}\">",auto_inc));
        for (User u : userList) {
            builder.append(MessageFormat.format("<user firstName=\"{0}\" id=\"{1}\" lastName=\"{2}\"/>",
                    u.firstName, u.id, u.lastName));
        }
        builder.append("</dataset>");
        return builder.toString();
    }

    public static void write() {
        try {
            FileWriter f = new FileWriter("src/data.xml",false); // important part
            f.write(convertToXML());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}