    package bomoncntt.svk62.msv2051067230.helper;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import androidx.annotation.Nullable;

    public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String Database_name = "Nhanvien.db";

        public static final String Table_name = "Nhanvien_table";
        public static final String col_cmnd = "cmnd";
        public static final String col_tennv = "tennv";
        public static final String col_gt = "gt";
        public static final String col_diachi = "diachi";
        public static final String col_sdt = "sdt";
        public static final String col_email = "email";
        public static final String col_chucvu = "chucvu";
        public static final String col_image = "anhnv";

        public static final String Table_name_chamcong = "Chamcong";
        public static final String col_cmnd_fk = "cmnd_fk";
        public static final String col_tennvcham = "tennvcham";
        public static final String col_ngchamcong = "ngchamcong";
        public static final String col_sngaycong = "sngaycong";


        public static final String Table_name_tamung = "Tamung";
        public static final String col_cmndfk = "cmndfk";
        public static final String col_tennvung = "tennvung";
        public static final String col_ngayung = "ngayung";
        public static final String col_sotienung = "songayung";

        public static final String Table_login = "login_table";
        public static final String col_username = "username";
        public static final String col_password = "password";


        public DatabaseHelper(@Nullable Context context) {
            super(context, Database_name, null, 4);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Table_name +" (" +
                    col_cmnd + " INTEGER PRIMARY KEY, " +
                    col_tennv + " VARCHAR(50), " +
                    col_gt + " INTEGER, " +
                    col_diachi + " INTEGER, " +
                    col_sdt + " NUMBER, " +
                    col_email + " INTEGER, " +
                    col_chucvu + " INTEGER, " +
                    col_image + " LONGTEXT)");

            db.execSQL("CREATE TABLE " + Table_login + "(" +
                    col_username + " CHAR(10) PRIMARY KEY, " +
                    col_password + " CHAR(10))");

            db.execSQL("CREATE TABLE " + Table_name_chamcong + "(" +
                    col_ngchamcong + " DATE PRIMARY KEY ," +
                    col_tennvcham + " INTEGER ," +
                    col_cmnd_fk + " INTEGER NOT NULL," +
                    col_sngaycong + " NUMBER," +
                    "FOREIGN KEY (" + col_cmnd_fk + ") REFERENCES " + Table_name +
                    "(" + col_cmnd + ") ON DELETE RESTRICT ON UPDATE RESTRICT" +
                    ")");

            db.execSQL("CREATE TABLE " + Table_name_tamung + "(" +
                    col_ngayung + " DATE PRIMARY KEY ," +
                    col_tennvung + " INTEGER ," +
                    col_cmndfk + " INTEGER NOT NULL," +
                    col_sotienung + " NUMBER," +
                    "FOREIGN KEY (" + col_cmndfk + ") REFERENCES " + Table_name +
                    "(" + col_cmnd + ") ON DELETE RESTRICT ON UPDATE RESTRICT" +
                    ")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Table_name);
            db.execSQL("DROP TABLE IF EXISTS " + Table_login);
            db.execSQL("DROP TABLE IF EXISTS " + Table_name_chamcong);
            db.execSQL("DROP TABLE IF EXISTS " + Table_name_tamung);
            onCreate(db);
        }
        public boolean insertLogin(String u,String p){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_username,u);
            cv.put(col_password,p);
            Long result = db.insert(Table_login,null,cv);
            if(result == -1 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        public boolean insertData(String cmnd,String tennv,String gt,String diachi,String sdt,String email,String chucvu,String anhnv)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_cmnd, cmnd);
            cv.put(col_tennv, tennv);
            cv.put(col_gt, gt);
            cv.put(col_diachi, diachi);
            cv.put(col_sdt, sdt);
            cv.put(col_email, email);
            cv.put(col_chucvu, chucvu);
            cv.put(col_image, anhnv);
            Long result = db.insert(Table_name,null,cv);
            if(result == -1 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        public boolean insertDataChamcong(String cmnd_fk, String tennvcham, String ngchamcong, String sngaycong)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(col_cmnd_fk, cmnd_fk);
            cv.put(col_tennvcham, tennvcham);
            cv.put(col_ngchamcong, ngchamcong);
            cv.put(col_sngaycong, sngaycong);

            Long result = db.insert(Table_name_chamcong,null,cv);
            if(result == -1 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        public boolean insertDataTamung(String cmndfk, String tennvung, String ngayung, String sotienung)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(col_cmndfk, cmndfk);
            cv.put(col_tennvung, tennvung);
            cv.put(col_ngayung, ngayung);
            cv.put(col_sotienung, sotienung);

            Long result = db.insert(Table_name_tamung,null,cv);
            if(result == -1 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        public Cursor queryLogin(String u, String p) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from " + Table_login + " where username = ? and password = ? ", new String[] {u,p});
            return cursor;
        }

        public Cursor Showdata() {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from "+Table_name,null);
            return cursor;
        }

        public Cursor ShowdataChamcong() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("select * from "+Table_name_chamcong,null);
        }

        public Cursor ShowdataTamung() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("select * from "+Table_name_tamung,null);
        }
        public boolean update(String cmnd,String tennv,String gt,String diachi,String sdt,String email,String chucvu,String anhnv)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_cmnd, cmnd);
            cv.put(col_tennv, tennv);
            cv.put(col_gt, gt);
            cv.put(col_diachi, diachi);
            cv.put(col_sdt, sdt);
            cv.put(col_email, email);
            cv.put(col_chucvu, chucvu);
            cv.put(col_image, anhnv);
            db.update(Table_name,cv,"cmnd = ?",new String[] { cmnd });
            return true;
        }
        public boolean updateChamcong(String cmnd_fk,String tennvcham,String ngchamcong,String sngaycong)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_cmnd_fk, cmnd_fk);
            cv.put(col_tennvcham, tennvcham);
            cv.put(col_ngchamcong, ngchamcong);
            cv.put(col_sngaycong, sngaycong);
            db.update(Table_name_chamcong,cv,"ngchamcong = ?",new String[] { ngchamcong });
            return true;
        }

        public boolean updateTamung(String cmndfk,String tennvung,String ngayung,String sotienung)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_cmndfk, cmndfk);
            cv.put(col_tennvung, tennvung);
            cv.put(col_ngayung, ngayung);
            cv.put(col_sotienung, sotienung);
            db.update(Table_name_tamung,cv,"ngayung = ?",new String[] { ngayung });
            return true;
        }

        public Integer delete(String cmnd)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(Table_name,"cmnd = ?",new String[] {cmnd});
        }

        public Integer deleteChamcong(String ngchamcong)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(Table_name_chamcong,"ngchamcong = ?",new String[] {ngchamcong});
        }

        public Integer deleteTamung(String ngayung)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(Table_name_tamung,"ngayung = ?",new String[] {ngayung});
        }

    }
