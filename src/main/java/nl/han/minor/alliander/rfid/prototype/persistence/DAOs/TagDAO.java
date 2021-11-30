package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

import java.io.Serializable;

public class TagDAO implements Serializable {

  public TagDAO(int id, String sn, String sup, String nam, String prodDate, String dOI, String com) {
    this.id = id;
    this.serialNumber = sn;
    this.supplier = sup;
    this.name = nam;
    this.productionDate = prodDate;
    this.dateOfInstallment = dOI;
    this.comment = com;
  }

  private int id;
  private String serialNumber;
  private String supplier;
  private String name;
  private String productionDate;
  private String dateOfInstallment;
  private String comment;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateOfInstallment() {
    return dateOfInstallment;
  }

  public void setDateOfInstallment(String dateOfInstallment) {
    this.dateOfInstallment = dateOfInstallment;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getProductionDate() {
    return productionDate;
  }

  public void setProductionDate(String productionDate) {
    this.productionDate = productionDate;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "TagDAO [id=" + id + ", name=" + name + "]";
  }
}
