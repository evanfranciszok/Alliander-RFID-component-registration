package nl.han.minor.alliander.rfid.prototype.service;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.PFScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.SQLiteDB;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.ITagDatabase;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

public class TagConnector implements IInfoConnector {

  private IScanner scanner;
  private ITagDatabase database;

  public TagConnector() {
    database = new SQLiteDB();
    scanner = new PFScanner();
  }

  public List<TagDAO> getScannedTags() {
    List<TagDAO> tags = new ArrayList<TagDAO>();
    for (long id : scanner.scanTags()) {
      TagDAO tag = database.getTagsFromID(id);
      if (tag != null)
        tags.add(tag);
    }
    return tags;
  }
}