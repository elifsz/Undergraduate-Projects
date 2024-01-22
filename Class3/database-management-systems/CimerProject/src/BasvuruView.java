
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasvuruView implements ViewInterface {

    @Override
    public ViewData create(ModelData modelData, String functionName, String operationName) throws Exception {
        switch (operationName) {
            case "select":
                return selectOperation(modelData);
            case "insert":
                return insertOperation(modelData);

            case "delete":
                return deleteOperation(modelData);
            
           
             
            case "selectSurec":
                return selectSurecOperation(modelData);
            case "selectKurum":
                return selectKurumOperation(modelData);

            case "select.gui":
                return selectGUI(modelData);
            case "selectSonuc":
                return selectSonucOperation(modelData);
            case "selectSonuc.gui":
                return selectSonucGUI(modelData);

            case "insert.gui":
                return insertGUI(modelData);

            case "selectSurec.gui":
                return selectSurecGUI(modelData);

            case "delete.gui":
                return deleteGUI(modelData);

            case "selectBasvuruNo":
                return selectBasvuruNo(modelData);
                
            case "selectID":
                return selectIDOperation(modelData);    
             
            case "selectID.gui":
                return selectIDGUI(modelData);
            
           case "selectUcret":
                return selectUcretOperation(modelData);    
             
            case "selectUcret.gui":
                return selectUcretGUI(modelData);
             
            case "update.gui": return updateGUI(modelData);
           
                
        }

        return new ViewData("MainMenu", "");

    }

    private ViewData selectBasvuruNo(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;
        int BasvuruNo;
        if (resultSet != null) {
            while (resultSet.next()) {
                // Retrieve by column name
                BasvuruNo = resultSet.getInt("BasvuruNumarasi");
                System.out.print(BasvuruNo + "\t");;

                System.out.println("");
            }
            resultSet.close();
        } 

        return new ViewData("MainMenu", "");
    }

    //başvuru
    ViewData selectOperation(ModelData modelData) throws Exception {
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
  

    
    ViewData selectIDOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {

                int basvuruNo = resultSet.getInt("BasvuruNo");
                String basvuruNedeni = resultSet.getString("BasvuruNedeni");
               
                System.out.print(basvuruNo + "\t");
                System.out.print(basvuruNedeni + "\t");
               
                System.out.println("");

            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    //başvuru süreç
    ViewData selectSurecOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {

                int basvuruNo = resultSet.getInt("BasvuruNo");

                String surecIDAdi = resultSet.getString("SurecIDAdi");
                Date surecTarih = resultSet.getDate("SurecTarih");

                System.out.print(basvuruNo + "\t");

                System.out.print(surecIDAdi + "\t");
                System.out.print(surecTarih + "\t");
                System.out.println("");
            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    //başvuru sonuç
    ViewData selectSonucOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {
                int basvuruNo = resultSet.getInt("BasvuruNo");
                String retsebebi = resultSet.getString("RetSebebi");
                String bilgi = resultSet.getString("Bilgi");
                float ucret = resultSet.getFloat("Ucret");
                String basvuruaciklama = resultSet.getString("BasvuruCevabiAciklama");

                System.out.print(basvuruNo + "\t");
                System.out.print(retsebebi + "\t");
                System.out.print(bilgi + "\t");
                System.out.print(ucret + "\t");
                System.out.print(basvuruaciklama + "\t");
                System.out.println("");
            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }

    //başvuru sonuç guisi
    ViewData selectSonucGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());
        return new ViewData("Basvuru", "selectSonuc", parameters);
    }

    //başvuru kurum
    ViewData selectKurumOperation(ModelData modelData) throws Exception {
        ResultSet resultSet = modelData.resultSet;

        if (resultSet != null) {
            while (resultSet.next()) {

                int kurumID = resultSet.getInt("KurumID");

                String kurumAdi = resultSet.getString("KurumAdi");

                System.out.print(kurumID + "\t");
                System.out.print(kurumAdi + "\t");

                System.out.println("");
            }
            resultSet.close();
        }

        return new ViewData("MainMenu", "");
    }
    
    ViewData insertOperation(ModelData modelData) throws Exception {
        
        System.out.println("Number of inserted rows is " + modelData.recordCount);
        System.out.println("Başvuru numaranızı görmek için 6'ya basınız");
        return new ViewData("MainMenu", "");
    }

    ViewData updateOperation(ModelData modelData) throws Exception {
        System.out.println("Number of updated rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
    }

    ViewData deleteOperation(ModelData modelData) throws Exception {
        System.out.println("Number of deleted rows is " + modelData.recordCount);

        return new ViewData("MainMenu", "");
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
    
    Map<String, Object> getWhereParametersID() throws Exception {
        System.out.println("Filter conditions:");

        Integer basvuruSahibiID = getInteger("BasvuruSahibiID : ", true);

        Map<String, Object> whereParameters = new HashMap<>();
        if (basvuruSahibiID != null) {
            whereParameters.put("Basvuru.BasvuruSahibiID", basvuruSahibiID);
        }

        return whereParameters;
    }
    
     ViewData selectIDGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParametersID());

        return new ViewData("Basvuru", "selectID", parameters);
    }
    
    ViewData selectGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());

        return new ViewData("Basvuru", "select", parameters);
    }
    
    public static short basvurduMu = 0;
    //başvuru ekleme guisi
    ViewData insertGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fieldNames", "BasvuruSahibiID, BasvuruPersonelID, BasvuruKurumID, BasvuruTipiID, BasvuruCevapTipiID, BasvuruNedeni, EkBilgiTalebi");
        basvurduMu = 0;
        List<Object> rows = new ArrayList<>();

        int basvuruNo = 0;
        int basvuruSahibiID;
        int basvuruPersonelID = 1;
        int basvuruKurumID;
        short basvuruTipiID = 2; //online başvuru
        short basvuruCevapTipiID;
        String basvuruNedeni;

        Date basvuruTarihi = null;

        Date basvuruSonTarihi = null;
        String ekBilgiTalebi;

        System.out.println(
                "Fields to insert:");

        basvuruSahibiID = getInteger("basvuruSahibiID : ", false);
        basvuruKurumID = getInteger("BasvuruKurumID : ", false);

        System.out.println(
                "1. Web\n2. Email\n3. Fax\n4. Yazili");
        basvuruCevapTipiID = getShort("BasvuruCevapTipiID : ", false);
        basvuruNedeni = getString("BasvuruNedeni : ", false);

        ekBilgiTalebi = getString("EkBilgiTalebi : ", true);

        System.out.println();

        if (basvuruSahibiID != -1 && basvuruPersonelID != -1 && basvuruKurumID != -1
                && basvuruTipiID != -1 && basvuruCevapTipiID != -1
                && basvuruNedeni != null && ekBilgiTalebi
                != null) {
            rows.add(new Basvuru(basvuruNo, basvuruSahibiID, basvuruPersonelID, basvuruKurumID, basvuruTipiID, basvuruCevapTipiID, basvuruNedeni, basvuruTarihi, basvuruSonTarihi, ekBilgiTalebi));
            basvurduMu = 1;
        }

        parameters.put(
                "rows", rows);

        return new ViewData(
                "Basvuru", "insert", parameters);
    }

    ViewData selectSurecGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());
        return new ViewData("Basvuru", "selectSurec", parameters);
    }
    
     ViewData selectUcretGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());
        return new ViewData("Basvuru", "selectUcret", parameters);
    }

     
     ViewData updateGUI(ModelData modelData) throws Exception {
		System.out.println("Fields to update:");
		Integer PersonelID = getInteger("PersonelID : ", true);
		
		System.out.println();
		
		Map<String, Object> updateParameters = new HashMap<>();
		if (PersonelID != null) updateParameters.put("PersonelID", PersonelID);
		
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("updateParameters", updateParameters);
		parameters.put("whereParameters", getWhereParameters());
		
		return new ViewData("Personel", "update", parameters);
	}

     
    ViewData deleteGUI(ModelData modelData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("whereParameters", getWhereParameters());

        return new ViewData("Basvuru", "delete", parameters);
    }

    @Override
    public String toString() {
        return "Basvuru View";
    }

}
