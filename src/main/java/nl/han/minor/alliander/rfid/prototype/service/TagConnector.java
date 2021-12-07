package nl.han.minor.alliander.rfid.prototype.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.SQLiteDB;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IComponentDatabase;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

public class TagConnector implements IInfoConnector {

  private IScanner scanner;
  private IComponentDatabase database;

  public TagConnector(IScanner scanner) {
    database = new SQLiteDB();
    this.scanner = scanner;
  }

  public List<ComponentDAO> getScannedComponents(List<BigInteger> ids) {
    List<ComponentDAO> tags = new ArrayList<ComponentDAO>();
    for (BigInteger id : ids) {
      tags.add(database.getComponentFromID(id));
    }
    return tags;
  }
}
