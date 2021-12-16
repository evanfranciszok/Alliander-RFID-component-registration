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
    String query = "select Component.*, ComponentType.Name as TypeName from Component LEFT JOIN ComponentType on Component.Type = ComponentType.ID";
    try {
      List<ComponentDAO> coms = new ArrayList<ComponentDAO>();
      makeConnection();
      ResultSet resultSet = executeSelectQuery(query);
      while (resultSet.next()) {
        coms.add(createComponentFromResultSet(resultSet));
      }
      closeConnection();
      return coms;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  @Override
  public List<MSRDAO> getAllMSRs() {
    String query = "SELECT * FROM MSR";
    try {
      List<MSRDAO> mSRs = new ArrayList<MSRDAO>();
      makeConnection();
      ResultSet resultSet = executeSelectQuery(query);
      while (resultSet.next()) {
        int iD = resultSet.getInt("ID");
        String sn = resultSet.getString("SerialNumber");
        String addr = resultSet.getString("Address");
        String nc = resultSet.getString("NominalCurrent");
        String ow = resultSet.getString("Owner");
        String admin = resultSet.getString("Administrator");
        String st = resultSet.getString("State");
        mSRs.add(new MSRDAO(iD, sn, addr, nc, ow, admin, st));
      }
      closeConnection();
      return mSRs;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  @Override
  public List<ComponentDAO> getAllComponentFromMSR(int mSRId) {
    String query = "select c.* from Component_in_MSR as cm left join Component as c on cm.ComponentID = c.ID where cm.MSRID = '"
        + mSRId + "'";
    try {
      List<ComponentDAO> coms = new ArrayList<ComponentDAO>();
      makeConnection();
      ResultSet resultSet = executeSelectQuery(query);
      while (resultSet.next()) {
        coms.add(createComponentFromResultSet(resultSet));
      }
      closeConnection();
      return coms;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  @Override
  public boolean addOrUpdateComponent(ComponentDAO com) {
    try {
      makeConnection();
      String query;
      if (findIfComponentExists(com.getrFID())) {
        query = "UPDATE Component SET SerialNumber = '" + com.getSerialNumber() + "', Supplier = '"
            + com.getSupplier()
            + "', Name = '" + com.getName() + "', Comment = '" + com.getComment() + "' WHERE RFID = '" + com.getrFID()
            + "'";
      } else {
        query = "INSERT INTO Component (RFID, SerialNumber, Supplier, Name, Comment) VALUES ('" + com.getrFID() + "','"
            + com.getSerialNumber() + "','" + com.getSupplier() + "','" + com.getName() + "','" + com.getComment()
            + "');";
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
        component = createComponentFromResultSet(resultSet);
      }
      closeConnection();

    } catch (Exception e) {
      System.err.println(e);
    }
    return component;
  }

  private ComponentDAO createComponentFromResultSet(ResultSet resultSet) throws SQLException {
    ComponentDAO component;
    int id = resultSet.getInt("ID");
    String rfid = resultSet.getString("RFID");
    String serNr = resultSet.getString("SerialNumber");
    String sup = resultSet.getString("Supplier");
    String name = resultSet.getString("name");
    String date = checkString(resultSet.getString("DateOfInstallment"), true);
    String prodDate = checkString(resultSet.getString("ProductionDate"), true);
    String comment = checkString(resultSet.getString("Comment"), false);
    // SpecificationDAO specification = createSpecificationDAO(id,
    // checkString(resultSet.getString("TypeName"), false));

    component = new ComponentDAO(id, rfid, serNr, sup, name, prodDate, date, comment, null);
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

  private SpecificationDAO createSpecificationDAO(int id, String type) throws SQLException {
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
