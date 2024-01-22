
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonelView implements ViewInterface {

    @Override
    public ViewData create(ModelData modelData, String functionName, String operationName) throws Exception {
        switch (operationName) {
            case "selectBasvuru":
                return selectBasvuruOperation(modelData);

            case "insertBasvuru":
                return insertBasvuruOperation(modelData);

            case "insertBasvuru.gui":
                return insertBasvuruGUI(modelData);

            case "insertSonuc":
                return insertSonucOperation(modelData);

            case "insertSonuc.gui":
                return insertSonucGUI(modelData);

            case "insertYonlendirme":
                return insertYonlendirmeOperation(modelData);

            case "insertYonlendirme.gui":
                return insertYonlendirmeGUI(modelData);

            case "insert":
                return insertOperation(modelData);

            case "insert.gui":
                return insertGUI(modelData);

            case "insertUcret":
                return insertUcretOperation(modelData);

            case "insertUcret.gui":
                return insertUcretGUI(modelData);

            case "insertSurec":
                return insertSurecOperation(modelData);

            case "insertSurec.gui":
                return insertSurecGUI(modelData);

            case "selectGiris":
                return selectGirisOperation(modelData);
            case "selectSonuclanmamis":
                return selectSonuclanmamisOperation(modelData);
            case "selectRapor":
                return selectRaporOperation(modelData);
            case "selectUcret":
                return selectUcretOperation(modelData);

            case "update":
                return updateOperation(modelData);

            case "update.gui":
                return updateGUI(modelData);
                
             case "delete":
                return deleteOperation(modelData);
                 
            case "delete.gui":
                return deleteGUI(modelData);

        }

        return new ViewData("MainMenu", "");

    }
    
     ViewData deleteOperation(ModelData modelData) throws Exception {
        System.out.println("Number of deleted rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }
     
      ViewData deleteGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParametersPersonel());

        return new ViewData("Personel", "delete", parameters);
    }

    ViewData selectUcretOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                int basvuruNo = resultSet.getInt("BasvuruNo");
                float ucret = resultSet.getFloat("Ucret");
                Date sonOdemeTarihi = resultSet.getDate("SonOdemeTarihi");
                short odendiMi = resultSet.getShort("OdendiMi");

                // Display values
                System.out.print(basvuruNo + "\t");
                System.out.print(ucret + "\t");
                System.out.print(sonOdemeTarihi + "\t");
                System.out.print(odendiMi + "\t");
                System.out.println("");

            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    //personel ekleme guisi
    ViewData insertGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "PersonelID, Ad, Soyad, Sifre, YetkiID");

        List<Object> rows = new ArrayList<>();

        int personelID;
        String ad;
        String soyad;
        String sifre;
        short yetkiID;

        System.out.println("Fields to insert:");

        personelID = getInteger("PersonelID : ", true);

        ad = getString("Ad : ", true);

        soyad = getString("Soyad:", true);
        sifre = getString("Sifre : ", true);
        sifre = toHexString(getSHA(sifre));
        yetkiID = getShort("YetkiID : ", true);
        System.out.println();

        if (personelID != -1 && yetkiID != -1
                && sifre != null && ad != null && soyad != null) {
            rows.add(new Personel(personelID, ad, soyad, sifre, yetkiID));
        }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insert", parameters);
    }

    ViewData insertUcretGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruNo, OdendiMi");

        List<Object> rows = new ArrayList<>();

        int basvuruNo;
        Date ucretTalepTarihi = null;
        Date sonOdemeTarihi = null;
        short odendiMi;

        System.out.println("Fields to insert:");

        basvuruNo = getInteger("BasvuruNo : ", true);

        odendiMi = getShort("OdendiMi : ", true);
        System.out.println();

        /*    if (basvuruNo != -1 && odendiMi != -1
         && ucretTalepTarihi != null && sonOdemeTarihi != null) {*/
        rows.add(new Basvuru(basvuruNo, ucretTalepTarihi, sonOdemeTarihi, odendiMi));
        //  }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insertUcret", parameters);
    }

    Map<String, Object> getWhereParameters() throws Exception {
        System.out.println("Filter conditions:");

        Integer basvuruNo = getInteger("BasvuruNo : ", true);

        Map<String, Object> whereParameters = new HashMap<>();
        if (basvuruNo != null) {
            whereParameters.put("Basvuru.BasvuruNo", basvuruNo);
        }

        return whereParameters;
    }
    
     Map<String, Object> getWhereParametersPersonel() throws Exception {
        System.out.println("Filter conditions:");

        Integer personelID = getInteger("PersonelID : ", true);

        Map<String, Object> whereParameters = new HashMap<>();
        if (personelID != null) {
            whereParameters.put("PersonelID", personelID);
        }

        return whereParameters;
    }

    ViewData insertOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }

    ViewData updateOperation(ModelData modelData) throws Exception {
        System.out.println("Number of updated rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }

    ViewData insertUcretOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }

    ViewData insertYonlendirmeOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }

    ViewData updateGUI(ModelData modelData) throws Exception {

        Integer basvuruNo;

        System.out.println();

        Map<String, Object> updateParameters = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("whereParameters", getWhereParameters());

        return new ViewData("Personel", "update", parameters);
    }

    ViewData selectSonuclanmamisOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        System.out.println("SONUÇLANDIRILMAMIŞ BAŞVURU NUMARALARI\n");
        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                int basvuruNo = resultSet.getInt("BasvuruNo");

                // Display values
                System.out.print(basvuruNo + "\t");
                System.out.println("");

            }

            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    public static short YetkiID;
    public static short flag = 0;

    ViewData selectGirisOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        System.out.println("GİRİŞ\n");
        int i = 0;
        if (resultSet != null) {
            int ID = getInteger("ID'nizi giriniz: ", true);
            String girisSifre = getString("Şifrenizi giriniz: ", true);
            girisSifre = toHexString(getSHA(girisSifre));
            while (resultSet.next()) {
                // Retrieve by column name
                int personelID = resultSet.getInt("PersonelID");
                String ad = resultSet.getString("Ad");
                String soyad = resultSet.getString("Soyad");
                String sifre = resultSet.getString("Sifre");
                YetkiID = resultSet.getShort("YetkiID");

                if (ID == personelID && girisSifre.equals(sifre)) {
                    i = 1;
                    flag = 1;
                    YetkiID = resultSet.getShort("YetkiID");
                    System.out.println("Hoş geldiniz. Giriş başarılı.");
                    break;
                }
            }
            if (i == 0) {
                System.out.println("Kullanıcı ID'niz ya da şifrenizi hatalı girdiniz.");

            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    ViewData selectRaporOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        int toplamBasvuru = -1;
        int olumluBasvuru = -1;
        int reddedilenBasvuru = -1;
        int gizliBasvuru = -1;
        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                toplamBasvuru = resultSet.getInt("x");
                reddedilenBasvuru = resultSet.getInt("y");
                olumluBasvuru = resultSet.getInt("z");
                gizliBasvuru = resultSet.getInt("t");

                // Display values
            }
            resultSet.close();
            System.out.println(" TOPLAM BASVURU SAYISI : " + toplamBasvuru);
            System.out.println(" REDDEDİLEN BASVURU SAYISI : " + reddedilenBasvuru);
            System.out.println(" OLUMLU BASVURU SAYISI : " + olumluBasvuru);
            System.out.println(" GİZLİ BASVURU HARİCİ SAYISI : " + gizliBasvuru);
            System.out.println("");

        }

        return new ViewData("MainMenu", "");
    }

        ViewData selectBasvuruOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                int basvuruNo = resultSet.getInt("BasvuruNo");
                int basvuruSahibiID = resultSet.getInt("BasvuruSahibiID");
             
                String basvuruKurumID = resultSet.getString("KurumAdi");
                String basvuruTipiID = resultSet.getString("BasvuruTipiAdi");
                String basvuruCevapTipiID = resultSet.getString("CevapTipiAdi");
                String basvuruNedeni = resultSet.getString("BasvuruNedeni");
                String basvuruTarihi = resultSet.getString("BasvuruTarihi");
                String basvuruSonTarihi = resultSet.getString("BasvuruSonTarihi");
                String ekBilgiTalebi = resultSet.getString("EkBilgiTalebi");

                // Display values
                System.out.print(basvuruNo + "\t");
                System.out.print(basvuruSahibiID + "\t");
         
                System.out.print(basvuruKurumID + "\t");
                System.out.print(basvuruTipiID + "\t");
                System.out.print(basvuruCevapTipiID + "\t");
                System.out.print(basvuruNedeni + "\t");
                System.out.print(basvuruTarihi + "\t");
                System.out.print(basvuruSonTarihi + "\t");
                System.out.print(ekBilgiTalebi + "\t");
                System.out.println("");

            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }
    
    /*ViewData selectBasvuruOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                int basvuruNo = resultSet.getInt("BasvuruNo");
                int basvuruSahibiID = resultSet.getInt("BasvuruSahibiID");
                int basvuruPersonelID = resultSet.getInt("BasvuruPersonelID");
                int basvuruKurumID = resultSet.getInt("BasvuruKurumID");
                short basvuruTipiID = resultSet.getShort("BasvuruTipiID");
                short basvuruCevapTipiID = resultSet.getShort("BasvuruCevapTipiID");
                String basvuruNedeni = resultSet.getString("BasvuruNedeni");
                String basvuruTarihi = resultSet.getString("BasvuruTarihi");

                String basvuruSonTarihi = resultSet.getString("BasvuruSonTarihi");
                String ekBilgiTalebi = resultSet.getString("EkBilgiTalebi");

                // Display values
                System.out.print(basvuruNo + "\t");
                System.out.print(basvuruSahibiID + "\t");
                System.out.print(basvuruPersonelID + "\t");
                System.out.print(basvuruKurumID + "\t");
                System.out.print(basvuruTipiID + "\t");
                System.out.print(basvuruCevapTipiID + "\t");
                System.out.print(basvuruNedeni + "\t");
                System.out.print(basvuruTarihi + "\t");
                System.out.print(basvuruSonTarihi + "\t");
                System.out.print(ekBilgiTalebi + "\t");
                System.out.println("");

            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }*/

    //kriptolama
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {

        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    ViewData insertBasvuruOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);
        return new ViewData("MainMenu", "");
    }

    ViewData insertSonucOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);
        return new ViewData("MainMenu", "");
    }

    ViewData insertSurecOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);
        return new ViewData("MainMenu", "");
    }

    //başvuru ekleme guisi
    ViewData insertBasvuruGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruSahibiID, BasvuruPersonelID, BasvuruKurumID, BasvuruTipiID, BasvuruCevapTipiID, BasvuruNedeni, EkBilgiTalebi");

        List<Object> rows = new ArrayList<>();

        int basvuruNo = 0;
        int basvuruSahibiID;
        int basvuruPersonelID = 1;
        int basvuruKurumID;
        short basvuruTipiID = 1;  //yazılı başvuru
        short basvuruCevapTipiID;
        String basvuruNedeni;
        Date basvuruTarihi = null;
        Date basvuruSonTarihi = null;
        String ekBilgiTalebi;
        System.out.println("Fields to insert:");
        basvuruSahibiID = getInteger("basvuruSahibiID : ", false);
        basvuruKurumID = getInteger("BasvuruKurumID : ", false);
        System.out.println("1. Web\n2. Email\n3. Fax\n4. Yazili");
        basvuruCevapTipiID = getShort("BasvuruCevapTipiID", false);
        basvuruNedeni = getString("BasvuruNedeni : ", false);
        ekBilgiTalebi = getString("EkBilgiTalebi : ", true);
        System.out.println();

        if (basvuruSahibiID != -1 && basvuruPersonelID != -1 && basvuruKurumID != -1
                && basvuruTipiID != -1 && basvuruCevapTipiID != -1
                && basvuruNedeni != null && ekBilgiTalebi != null) {
            rows.add(new Basvuru(basvuruNo, basvuruSahibiID, basvuruPersonelID, basvuruKurumID, basvuruTipiID, basvuruCevapTipiID, basvuruNedeni, basvuruTarihi, basvuruSonTarihi, ekBilgiTalebi));
        }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insertBasvuru", parameters);
    }

    //yönlendirme ekleme guisi

    ViewData insertYonlendirmeGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruNo, YonlendirilenKurumNo, YonlendirenKurumNo");

        List<Object> rows = new ArrayList<>();

        int basvuruNo = 0;

        int yonlendirilenKurumNo;
        int yonlendirenKurumNo;

        System.out.println("Fields to insert:");

        basvuruNo = getInteger("BasvuruNo : ", false);

        yonlendirilenKurumNo = getInteger("YonlendirilenKurumNo : ", false);

        yonlendirenKurumNo = getInteger("YonlendirenKurumNo : ", false);
        System.out.println();

        if (basvuruNo != -1 && yonlendirilenKurumNo != -1 && yonlendirenKurumNo != -1) {
            rows.add(new Basvuru(basvuruNo, yonlendirilenKurumNo, yonlendirenKurumNo));
        }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insertYonlendirme", parameters);
    }

    //sonuç ekleme guisi

    ViewData insertSonucGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruNo, BilgiMi, Bilgi, BelgeID, Ucret, RetID, BasvuruCevabiAciklama");

        List<Object> rows = new ArrayList<>();

        int basvuruNo = 0;
        short bilgiMi = 1;
        short belgeID;
        float ucret;
        short retID;
        String bilgi;
        String basvuruCevabiAciklama;

        System.out.println("Fields to insert:");

        basvuruNo = getInteger("BasvuruNo : ", false);
        bilgiMi = getShort("BilgiMi : ", false);

        bilgi = getString("Bilgi : ", true);
        belgeID = getShort("BelgeID: ", true);
        ucret = getFloat("Ucret: ", true);
        retID = getShort("RetID: ", true);
        basvuruCevabiAciklama = getString("BasvuruCevabiAciklama : ", false);
        System.out.println();

        if (basvuruNo != -1 && bilgiMi != -1 && belgeID != -1
                && ucret != -1 && retID != -1
                && bilgi != null && basvuruCevabiAciklama != null) {
            rows.add(new Basvuru(basvuruNo, bilgiMi, bilgi, belgeID, ucret, retID, basvuruCevabiAciklama));
        }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insertSonuc", parameters);
    }

    //süreç ekleme guisi

    ViewData insertSurecGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruNo, SurecID");

        List<Object> rows = new ArrayList<>();

        int basvuruNo = 0;
        int ID = 0;
        short surecID;
        Date surecTarih = null;

        System.out.println("Fields to insert:");

        basvuruNo = getInteger("BasvuruNo : ", false);

        surecID = getShort("SurecID: ", false);

        System.out.println();

        if (basvuruNo != -1 && ID != -1 && surecID != -1) {
            rows.add(new Basvuru(basvuruNo, ID, surecID, surecTarih));
        }

        parameters.put("rows", rows);

        return new ViewData("Personel", "insertSurec", parameters);
    }

}
