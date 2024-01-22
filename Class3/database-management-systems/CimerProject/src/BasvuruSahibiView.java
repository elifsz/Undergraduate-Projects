
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasvuruSahibiView implements ViewInterface {

    @Override
    public ViewData create(ModelData modelData, String functionName, String operationName) throws Exception {
        switch (operationName) {
            case "select":
                return selectOperation(modelData);

            case "insert":
                return insertOperation(modelData);

            case "select.gui":
                return selectGUI(modelData);

            case "insert.gui":
                return insertGUI(modelData);

            case "selectBasvuruSahibiID":
                return selectBasvuruSahibiID(modelData);
                
            case "selectAdres":
                return selectAdresOperation(modelData);



        }

        return new ViewData("MainMenu", "");
    }
    
    
 ViewData selectAdresOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                int adresID = resultSet.getInt("AdresID");
  
                String isim = resultSet.getString("Isım");
                
                System.out.print(adresID + "\t");
                System.out.print(isim + "\t");
                
                System.out.println("");
            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }
    

    private ViewData selectBasvuruSahibiID(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        int BasvuruSahibiID;
        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                BasvuruSahibiID = resultSet.getInt("BasvuruSahibiID");

                System.out.print(BasvuruSahibiID + "\t");;

                System.out.println("");
            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    //şifre kriptolama işlemleri
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
    
    public static short kaydolduMu = 0;
    //üye olma guisi
    public ViewData insertGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruSahibiTipID, Ad,Soyad, Sifre, TurkVatandasi, TCKN,  Telefon, IsTelefonu, Fax, EPosta, Unvan, AdresID, Adres");
        kaydolduMu = 0;
        List<Object> rows = new ArrayList<>();

        int basvuruSahibiID = 0;
        short basvuruSahibiTipID; // gercek tuzel
        String ad;
        String soyad;
        short turkVatandasi;
        String tcKimlikNo = "";
        String sifre;
        String telefonNo;
        String isTelefonu;
        String fax;
        String ePosta;
        String unvan = "";
        int adresID;
        String adres;

        System.out.println("Gercek kişi için 1, Tüzel kişi için 2'ye basınız: ");
        basvuruSahibiTipID = getShort("BasvuruSahibiTipID: ", false);
        ad = getString("Ad : ", false);
        soyad = getString("Soyad : ", false);
        sifre = getString("Sifre:", false);
        sifre = toHexString(getSHA(sifre));
        System.out.println("Turk Vatandaşı iseniz 1'e değilseniz 0'a basınız: ");
        turkVatandasi = getShort("", false);
        if (turkVatandasi == 1) {
            tcKimlikNo = getString("TCKN:", true);
        }

        telefonNo = getString("Telefon:", false);
        isTelefonu = getString("IsTelefonu:", true);
        fax = getString("Fax:", true);

        ePosta = getString("EPosta:", true);
        if (basvuruSahibiTipID == 2) {
            unvan = getString("Unvan: ", true);
        }
        adresID = getInteger("AdresID:", false);
        adres = getString("Adres:", false);

        System.out.println();

        if (basvuruSahibiID != -1 && basvuruSahibiTipID != -1 && ad != null
                && soyad != null && sifre != null
                && turkVatandasi != -1 && tcKimlikNo != null && telefonNo != null && isTelefonu != null && fax != null && ePosta != null && unvan != null && adresID != -1 && adres != null) {

            rows.add(new BasvuruSahibi(basvuruSahibiID, basvuruSahibiTipID, ad, soyad, sifre, turkVatandasi, tcKimlikNo, telefonNo, isTelefonu, fax, ePosta, unvan, adresID, adres));
            kaydolduMu = 1;
        }

        parameters.put("rows", rows);
        return new ViewData("BasvuruSahibi", "insert", parameters);
    }

    ViewData insertOperation(ModelData modelData) throws Exception {
        System.out.println("Number of inserted rows is " + modelData.recordCount);
        System.out.println("Kullanıcı ID'nizi görmek için 3'e basınız.");
        return new ViewData("MainMenu", "");
    }
    public static short kullaniciFlag = 0;
    //giriş kontrolü 
    ViewData selectOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        int i = 0;
        if (resultSet != null) {
            int ID = getInteger("ID'nizi giriniz: ", true);
            String girisSifre = getString("Şifrenizi giriniz: ", true);
            girisSifre = toHexString(getSHA(girisSifre)); //kriptolama
            while (resultSet.next()) {

                int basvuruSahibiID = resultSet.getInt("BasvuruSahibiID");
                String sifre = resultSet.getString("Sifre");

                if (ID == basvuruSahibiID && girisSifre.equals(sifre)) {
                    i = 1;
                    kullaniciFlag = 1;
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

    Map<String, Object> getWhereParameters() throws Exception {
        System.out.println("Filter conditions:");

        Integer basvuruSahibiID = getInteger("BasvuruSahibiID : ", true);

        Map<String, Object> whereParameters = new HashMap<>();
        if (basvuruSahibiID != null) {
            whereParameters.put("[Kullanici.BasvuruSahibi].BasvuruSahibiID", basvuruSahibiID);
        }

        return whereParameters;
    }

    ViewData selectGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());

        return new ViewData("BasvuruSahibi", "select", parameters);
    }

}
