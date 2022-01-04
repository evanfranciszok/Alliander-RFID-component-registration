package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class TagDAO {
  private String id;
  private int RSSI;
  private String data;

  public TagDAO(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getRSSI() {
    return RSSI;
  }

  public void setRSSI(int rSSI) {
    RSSI = rSSI;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "TagDAO [RSSI=" + RSSI + ", data=" + data + ", id=" + id + "]";
  }
}
