package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.math.BigInteger;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;

public interface ITagDatabase {
  public List<TagDAO> getAllTags();

  public TagDAO getTagsFromID(BigInteger id);
}
