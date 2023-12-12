package bomoncntt.svk62.msv2051067230.model;

public class TamUng  {
    private String cmndfk;
    private String tennvung;
    private String ngayung;
    private String sotienung;

    public TamUng() {

    }


    public TamUng(String cmndfk, String tennvung, String ngayung, String sotienung) {
        this.cmndfk = cmndfk;
        this.tennvung = tennvung;
        this.ngayung = ngayung;
        this.sotienung = sotienung;
    }

    public String getCmndfk() {
        return cmndfk;
    }

    public void setCmndfk(String cmndfk) {
        this.cmndfk = cmndfk;
    }

    public String getTennvung() {
        return tennvung;
    }

    public void setTennvung(String tennvung) {
        this.tennvung = tennvung;
    }

    public String getNgayung() {
        return ngayung;
    }

    public void setNgayung(String ngayung) {
        this.ngayung = ngayung;
    }

    public String getSotienung() {
        return sotienung;
    }

    public void setSotienung(String sotienung) {
        this.sotienung = sotienung;
    }


    @Override
    public String toString() {
        return cmndfk+"-"+tennvung+"-"+ngayung+"-"+sotienung;
    }
}

