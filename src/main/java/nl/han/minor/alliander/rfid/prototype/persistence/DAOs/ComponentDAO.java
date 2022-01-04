package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

import java.io.Serializable;

public class ComponentDAO implements Serializable {

  private int id;
  private String rFID;
  private String serialNumber;
  private String supplier;
  private String name;
  private String productionDate;
  private String dateOfInstallment;
  private String comment;
  private SpecificationDAO specification;
  private int rSSI;

  public ComponentDAO(int id, String rFID, String sn, String sup, String nam, String prodDate, String dOI, String com,
      SpecificationDAO spec) {
    this.id = id;
    this.rFID = rFID;
    this.serialNumber = sn;
    this.supplier = sup;
    this.name = nam;
    this.productionDate = prodDate;
    this.dateOfInstallment = dOI;
    this.comment = com;
    this.specification = spec;
  }

  // public ComponentDAO(String rFID) {
  // this.rFID = rFID;
  // }

  public int getRSSI() {
    return rSSI;
  }

  public void setRSSI(int rSSI) {
    this.rSSI = rSSI;
  }

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

  public SpecificationDAO getSpecification() {
    return specification;
  }

  public void setSpecification(SpecificationDAO specification) {
    this.specification = specification;
  }

  @Override
  public String toString() {
    return "ComponentDAO [comment=" + comment + ", dateOfInstallment=" + dateOfInstallment + ", id=" + id + ", rfid="
        + rFID + ", name="
        + name + ", productionDate=" + productionDate + ", rSSI=" + rSSI + ", serialNumber=" + serialNumber
        + ", specification=" + specification + ", supplier=" + supplier + "]";
  }

  public String getrFID() {
    return rFID;
  }

  public void setrFID(String rFID) {
    this.rFID = rFID;
  }
}
