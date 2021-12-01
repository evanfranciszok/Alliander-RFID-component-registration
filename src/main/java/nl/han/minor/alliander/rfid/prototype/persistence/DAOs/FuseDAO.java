package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class FuseDAO extends SpecificationDAO {

  private int VA;
  private int volt;

  public FuseDAO(int va, int volt) {
    super("Fuse");
    this.VA = va;
    this.volt = volt;
  }

  public int getVA() {
    return VA;
  }

  public int getVolt() {
    return volt;
  }
}
