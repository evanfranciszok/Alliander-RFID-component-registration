package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

public class MSRDAO {
    private int iD;
    private String serialNumber;
    private String address;
    private String nominalCurrent;
    private String owner;
    private String administrator;
    private String state;

    public MSRDAO(int iD, String sn, String addr, String nc, String ow,
            String admin, String st) {
        this.iD = iD;
        this.serialNumber = sn;
        this.address = addr;
        this.nominalCurrent = nc;
        this.owner = ow;
        this.administrator = admin;
        this.state = st;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNominalCurrent() {
        return nominalCurrent;
    }

    public void setNominalCurrent(String nominalCurrent) {
        this.nominalCurrent = nominalCurrent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
