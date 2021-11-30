package nl.han.minor.alliander.rfid.prototype.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.SQLiteDB;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.ITagDatabase;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

public class TagConnector implements IInfoConnector {

  private IScanner scanner;
  private ITagDatabase database;

  public TagConnector(IScanner scanner) {
    database = new SQLiteDB();
    this.scanner = scanner;
  }

  public List<TagDAO> getScannedTags(List<BigInteger> ids) {
    List<TagDAO> tags = new ArrayList<TagDAO>();
    for (BigInteger id : ids) {
      tags.add(new TagDAO(id, "-", "-", "1400000000"));
      // System.out.println(id + " ###");
      // TagDAO tag = database.getTagsFromID(id);
      // if (tag != null)
      // tags.add(tag);
    }
    return tags;
  }
}
