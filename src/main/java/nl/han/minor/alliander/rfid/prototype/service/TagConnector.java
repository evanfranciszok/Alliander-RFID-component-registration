package nl.han.minor.alliander.rfid.prototype.service;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IComponentDatabase;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

public class TagConnector implements IInfoConnector {

  private IComponentDatabase database;

  public TagConnector(IScanner scanner, IComponentDatabase db) {
    database = db;
  }

  public List<ComponentDAO> getScannedComponents(List<TagDAO> ids) {
    List<ComponentDAO> components = new ArrayList<ComponentDAO>();
    for (TagDAO id : ids) {
      ComponentDAO com = database.getComponentFromRFID(id.getId());
      if (com == null)
        com = new ComponentDAO(0, id.getId(), null, null, null, null, null, null, null);
      components.add(com);
    }
    return components;
  }
}
