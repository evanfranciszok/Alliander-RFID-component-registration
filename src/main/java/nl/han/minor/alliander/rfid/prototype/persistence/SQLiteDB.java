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
      ResultSet resultSet = executeQuery("select * from TAGS where id is " + id);
      if (resultSet.next()) {
        String date = null;
        if (resultSet.getString("DATE_OF_INSTALLMENT") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("DATE_OF_INSTALLMENT")) * 1000);
          date = d.toString();
        }
        tag = new TagDAO(resultSet.getBigDecimal("ID").toBigInteger(), resultSet.getString("SUPPLIER"),
            resultSet.getString("NAME"), date);
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
      ResultSet resultSet = executeQuery("SELECT * FROM TAGS;");
      while (resultSet.next()) {
        String date = null;
        if (resultSet.getString("DATE_OF_INSTALLMENT") != null) {
          Date d = new Date((long) Integer.parseInt(resultSet.getString("DATE_OF_INSTALLMENT")) * 1000);
          date = d.toString();
        }
        tags.add(new TagDAO(resultSet.getBigDecimal("ID").toBigInteger(), resultSet.getString("SUPPLIER"),
            resultSet.getString("NAME"), date));
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
