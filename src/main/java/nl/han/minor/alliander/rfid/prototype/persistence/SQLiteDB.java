package nl.han.minor.alliander.rfid.prototype.persistence;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.*;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IComponentDatabase;

import java.sql.*;

public class SQLiteDB implements IComponentDatabase {
  private static Connection connection = null;
  private static Statement stmt = null;

  @Override
  public ComponentDAO getComponentFromRFID(String rfid) {
    String query = "select Component.*, ComponentType.Name as TypeName from Component LEFT JOIN ComponentType on Component.Type = ComponentType.ID where Component.RFID is '"
        + rfid + "'";
    return getSingleComponent(query);
  }

  @Override
  public ComponentDAO getComponentFromID(String id) {
    String query = "select Component.*, ComponentType.Name as TypeName from Component LEFT JOIN ComponentType on Component.Type = ComponentType.ID where Component.ID is '"
        + id + "'";
    return getSingleComponent(query);
  }

  @Override
  public List<ComponentDAO> getAllComponents() {
    List<ComponentDAO> components = new ArrayList<ComponentDAO>();
    try {
      makeConnection();
      ResultSet resultSet = executeSelectQuery("SELECT * FROM Component;");
      while (resultSet.next()) {

        String date = checkString(resultSet.getString("DateOfInstallment"), true);
        String prodDate = checkString(resultSet.getString("ProductionDate"), true);
        String comment = checkString(resultSet.getString("Comment"), false);

        components.add(
            new ComponentDAO(resultSet.getInt("ID"), resultSet.getString("RFID"), resultSet.getString("SerialNumber"),
                resultSet.getString("Supplier"), resultSet.getString("Name"), prodDate, date,
                comment, null));
      }
      resultSet.close();
      closeConnection();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return components;
  }

  @Override
  public boolean addOrUpdateComponent(ComponentDAO com) {
    try {
      makeConnection();
      String query;
      if (findIfComponentExists(com.getrFID())) {
        System.out.println("updating component: " + com);
        query = "UPDATE Component SET SerialNumber = '" + com.getSerialNumber() + "', Supplier = '"
            + com.getSupplier()
            + "', Name = '" + com.getName() + "', Comment = '" + com.getComment() + "' WHERE RFID = '" + com.getrFID()
            + "'";
        System.out.println(query);
      } else {
        System.out.println("adding component: " + com);
        query = "INSERT INTO Component (RFID, SerialNumber, Supplier, Name, Comment) VALUES ('" + com.getrFID() + "','"
            + com.getSerialNumber() + "','" + com.getSupplier() + "','" + com.getName() + "','" + com.getComment()
            + "');";
        System.out.println(query);
      }
      // ", ProductionDate = " + com.getProductionDate() + ", DateOfInstallment = " +
      // com.getDateOfInstallment()
      System.out.println(executeUpdateQuery(query));
      closeConnection();
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }
    return true;
  }

  private ComponentDAO getSingleComponent(String query) {
    ComponentDAO component = null;
    try {
      makeConnection();
      ResultSet resultSet = executeSelectQuery(query);
      if (resultSet.next()) {
        int id = resultSet.getInt("ID");
        String rfid = resultSet.getString("RFID");
        String serNr = resultSet.getString("SerialNumber");
        String sup = resultSet.getString("Supplier");
        String name = resultSet.getString("name");
        String date = checkString(resultSet.getString("DateOfInstallment"), true);
        String prodDate = checkString(resultSet.getString("ProductionDate"), true);
        String comment = checkString(resultSet.getString("Comment"), false);
        SpecificationDAO specification = creatSpecificationDAO(id, checkString(resultSet.getString("TypeName"), false));

        component = new ComponentDAO(id, rfid, serNr, sup, name, prodDate, date, comment, specification);
      }
      closeConnection();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return component;
  }

  private void makeConnection() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/tags.db");
    connection.setAutoCommit(true);
    stmt = connection.createStatement();
  }

  private ResultSet executeSelectQuery(String query) throws SQLException {
    return stmt.executeQuery(query);
  }

  private int executeUpdateQuery(String query) throws SQLException {
    return stmt.executeUpdate(query);
  }

  private void closeConnection() throws SQLException {
    stmt.close();
    connection.close();
  }

  private boolean findIfComponentExists(String rfid) throws SQLException {
    String query = "SELECT count() as doesexist FROM Component where RFID = '" + rfid + "'";
    ResultSet rs = executeSelectQuery(query);
    boolean exists = false;
    if (rs.next())
      if (rs.getInt(1) > 0)
        exists = true;
    return exists;
  }

  private String checkString(String result, boolean isDate) {
    if (result != null) {
      if (isDate) {
        Date d = new Date((long) Integer.parseInt(result) * 1000);
        return d.toString();
      } else
        return result;
    } else
      return null;
  }

  private SpecificationDAO creatSpecificationDAO(int id, String type) throws SQLException {
    if (type == null || type.isEmpty())
      return null;
    else {
      String query = "select * from " + type + " where id is '" + id + "'";
      ResultSet resultSet = executeSelectQuery(query);
      if (resultSet.next()) {
        switch (type) {
          case "Travo":
            return createTravoDAO(resultSet);
          case "Fuse":
            return createFuseDAO(resultSet);
          default:
            break;
        }
      }
      return null;
    }
  }

  private TravoDAO createTravoDAO(ResultSet result) throws SQLException {
    int kva = result.getInt("KVA");
    int fase = result.getInt("Fase");
    int hz = result.getInt("Hz");
    int weight = result.getInt("Weight");
    String cooling = result.getString("CoolingType");
    return new TravoDAO(kva, fase, hz, weight, cooling);
  }

  private FuseDAO createFuseDAO(ResultSet result) throws SQLException {
    int va = result.getInt("VA");
    int volt = result.getInt("Volt");
    return new FuseDAO(va, volt);
  }
}
