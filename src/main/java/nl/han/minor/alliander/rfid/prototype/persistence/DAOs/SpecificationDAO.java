package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public abstract class SpecificationDAO {
  public String type;

  public SpecificationDAO(String typeName) {
    this.type = typeName;
  }

  public String getType() {
    return type;
  }
}
