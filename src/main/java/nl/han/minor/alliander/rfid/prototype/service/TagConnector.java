package nl.han.minor.alliander.rfid.prototype.service;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.SQLiteDB;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IComponentDatabase;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

public class TagConnector implements IInfoConnector {

  private IScanner scanner;
  private IComponentDatabase database;
  private int tagnr;

  public TagConnector(IScanner scanner, IComponentDatabase db) {
    database = db;
    this.scanner = scanner;
  }

  public List<ComponentDAO> getScannedComponents(List<TagDAO> ids) {
    List<ComponentDAO> components = new ArrayList<ComponentDAO>();
    for (TagDAO id : ids) {
      // ComponentDAO com = database.getComponentFromID(id.getId());
      // com.setRSSI(id.getRSSI());
      ComponentDAO com = new ComponentDAO(tagnr, id.getId(), id.getId(), "String sup", "" + id.getId(),
          "String prodDate",
          "String dOI", "String com",
          null);
      tagnr++;
      com.setRSSI(id.getRSSI());
      components.add(com);
    }
    return components;
  }
}
