package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class TravoDAO extends SpecificationDAO {

  private int KVA;
  private int fase;
  private int Hz;
  private int weight;
  private String coolingType;

  public TravoDAO() {
    super("Travo");
  }

  public int getKVA() {
    return KVA;
  }

  public void setKVA(int kVA) {
    KVA = kVA;
  }

  public int getFase() {
    return fase;
  }

  public void setFase(int fase) {
    this.fase = fase;
  }

  public int getHz() {
    return Hz;
  }

  public void setHz(int hz) {
    Hz = hz;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getCoolingType() {
    return coolingType;
  }

  public void setCoolingType(String coolingType) {
    this.coolingType = coolingType;
  }

}
