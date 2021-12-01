package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class TravoDAO extends SpecificationDAO {

  private int KVA;
  private int fase;
  private int Hz;
  private int weight;
  private String coolingType;

  public TravoDAO(int kva, int fase, int hz, int weight, String cooling) {
    super("Travo");
    this.KVA = kva;
    this.fase = fase;
    this.Hz = hz;
    this.weight = weight;
    this.coolingType = cooling;
  }

  public int getKVA() {
    return KVA;
  }

  public int getFase() {
    return fase;
  }

  public int getHz() {
    return Hz;
  }

  public int getWeight() {
    return weight;
  }

  public String getCoolingType() {
    return coolingType;
  }

}
