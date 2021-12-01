package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public abstract class SpecificationDAO {
  public String specificationType;

  public SpecificationDAO(String typeName) {
    this.specificationType = typeName;
  }

  public String getSpecificationType() {
    return this.specificationType;
  }
}
