package bomoncntt.svk62.msv2051067230.model;

public class ChamCong {
    private String cmnd_fk;
    private String tennvcham;
    private String ngchamcong;
    private String sngaycong;

    public ChamCong() {

    }


    public ChamCong(String cmnd_fk, String tennvcham, String ngchamcong, String sngaycong) {
        this.cmnd_fk = cmnd_fk;
        this.tennvcham = tennvcham;
        this.ngchamcong = ngchamcong;
        this.sngaycong = sngaycong;
    }

    public String getCmnd_fk() {
        return cmnd_fk;
    }

    public void setCmnd_fk(String cmnd_fk) {
        this.cmnd_fk = cmnd_fk;
    }

    public String getTennvcham() {
        return tennvcham;
    }

    public void setTennvcham(String tennvcham) {
        this.tennvcham = tennvcham;
    }

    public String getNgchamcong() {
        return ngchamcong;
    }

    public void setNgchamcong(String ngchamcong) {
        this.ngchamcong = ngchamcong;
    }

    public String getSngaycong() {
        return sngaycong;
    }

    public void setSngaycong(String sngaycong) {
        this.sngaycong = sngaycong;
    }


    @Override
    public String toString() {
        return cmnd_fk+"-"+tennvcham+"-"+ngchamcong+"-"+sngaycong;
    }

}
