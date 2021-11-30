package nl.han.minor.alliander.rfid.prototype.persistence;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.ITagDatabase;

import java.math.BigInteger;
import java.sql.*;

public class SQLiteDB implements ITagDatabase {
  private static Connection connection = null;
  private static Statement stmt = null;

  @Override
  public TagDAO getTagsFromID(BigInteger id) {
    TagDAO tag = null;
    try {
      makeConnection();
      System.out.println("query = " + "select * from Component where RFID is '" + id + "'");
      ResultSet resultSet = executeQuery("select * from Component where RFID is '" + id + "'");
      if (resultSet.next()) {
        String date = null;
        if (resultSet.getString("DateOfInstallment") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("DateOfInstallment")) * 1000);
          date = d.toString();
        }
        String prodDate = null;
        if (resultSet.getString("ProductionDate") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("ProductionDate")) * 1000);
          prodDate = d.toString();
        }
        String comment = "";
        if (resultSet.getString("Comment") != null) {
          comment = resultSet.getString("Comment");
        }

        tag = new TagDAO(resultSet.getInt("ID"), resultSet.getString("SerialNumber"),
            resultSet.getString("Supplier"), resultSet.getString("Name"), prodDate, date,
            comment);
      }

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return tag;
  }

  @Override
  public List<TagDAO> getAllTags() {
    List<TagDAO> tags = new ArrayList<TagDAO>();
    try {
      makeConnection();
      ResultSet resultSet = executeQuery("SELECT * FROM Component;");
      while (resultSet.next()) {
        String date = null;
        if (resultSet.getString("DateOfInstallment") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("DateOfInstallment")) * 1000);
          date = d.toString();
        }
        String prodDate = null;
        if (resultSet.getString("ProductionDate") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("ProductionDate")) * 1000);
          prodDate = d.toString();
        }
        String comment = null;
        if (resultSet.getString("Comment") != null) {
          comment = resultSet.getString("Comment");
        }
        tags.add(new TagDAO(resultSet.getInt("ID"), resultSet.getString("SerialNumber"),
            resultSet.getString("Supplier"), resultSet.getString("Name"), prodDate, date,
            comment));
      }
      resultSet.close();
      closeConnection();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return tags;
  }

  private void makeConnection() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/tags.db");
    connection.setAutoCommit(false);
    stmt = connection.createStatement();
  }

  private ResultSet executeQuery(String query) throws SQLException {
    return stmt.executeQuery(query);
  }

  private void closeConnection() throws SQLException {
    stmt.close();
    connection.close();
  }
}
