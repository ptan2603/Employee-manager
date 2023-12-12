package bomoncntt.svk62.msv2051067230.model;

public class NhanVien {
    private String cmnd;
    private String tennv;
    private String gt;
    private String diachi;
    private String sdt;
    private String email;
    private String chucvu;
    private String dataTitle;
    public NhanVien() {

    }

    public String getAnhnv() {
        return anhnv;
    }

    public void setAnhnv(String anhnv) {
        this.anhnv = anhnv;
    }

    private String anhnv;


    public NhanVien(String cmnd, String tennv, String gt, String diachi, String sdt, String email, String chucvu, String anh) {
        this.cmnd = cmnd;
        this.tennv = tennv;
        this.gt = gt;
        this.diachi = diachi;
        this.sdt = sdt;
        this.email = email;
        this.chucvu = chucvu;
        this.anhnv = anh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    @Override
    public String toString() {
        return cmnd+"-"+tennv+"-"+gt+"-"+diachi+"-"+sdt+"-"+email+"-"+chucvu;
    }

    public String getDataTitle() {
        return dataTitle;
    }
}

