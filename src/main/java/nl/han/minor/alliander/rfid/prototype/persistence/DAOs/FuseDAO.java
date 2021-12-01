package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class FuseDAO extends SpecificationDAO {

  private int VA;
  private int volt;

  public FuseDAO() {
    super("Fuse");
  }

  public int getVA() {
    return VA;
  }

  public void setVA(int vA) {
    VA = vA;
  }

  public int getVolt() {
    return volt;
  }

  public void setVolt(int volt) {
    this.volt = volt;
  }

}
