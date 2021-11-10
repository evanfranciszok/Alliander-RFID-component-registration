package nl.han.minor.alliander.rfid.prototype.persistence;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
// import java.util.Date;

public class TagRepository {
  private static Connection connection = null;
  private static Statement stmt = null;

  public List<TagDAO> getTags() {
    List<TagDAO> tags = new ArrayList<TagDAO>();
    makeConnection();
    try {
      ResultSet rs = stmt.executeQuery("SELECT * FROM TAGS;");
      while (rs.next()) {
        String date = null;
        if (rs.getString("DATE_OF_INSTALLMENT") != null) {
          Date d = new Date((long) Integer.parseInt(rs.getString("DATE_OF_INSTALLMENT")) * 1000);
          date = d.toString();
        }
        tags.add(new TagDAO(rs.getInt("ID"), rs.getString("SUPPLIER"), rs.getString("NAME"), date));
      }
      rs.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    closeConnection();
    return tags;
  }

  private void makeConnection() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/tags.db");
      connection.setAutoCommit(false);
      stmt = connection.createStatement();
      System.out.println("Opened database successfully");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void closeConnection() {
    try {
      stmt.close();
      connection.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  // public static void connect() {
  // try {
  // ResultSet rs = stmt.executeQuery("SELECT * FROM TAGS;");

  // while (rs.next()) {
  // // int id = rs.getInt("ID");
  // // String name = rs.getString("NAME");
  // // int supplier = rs.getInt("SUPPLIER");
  // // String dateOfInstallment = rs.getString("DATE_OF_INSTALLMENT");

  // // System.out.println("ID = " + id);
  // // System.out.println("NAME = " + name);
  // // System.out.println("SUPPLIER = " + supplier);
  // // if (dateOfInstallment != null) {
  // // Date date = new Date((long) Integer.parseInt(dateOfInstallment) * 1000);
  // // System.out.println("DATE OF INSTALLMENT = " + date);
  // // }
  // // System.out.println();
  // }
  // rs.close();

  // } catch (Exception e) {
  // System.err.println(e.getClass().getName() + ": " + e.getMessage());
  // System.exit(0);
  // }
  // System.out.println("Operation done successfully");
  // }
}
