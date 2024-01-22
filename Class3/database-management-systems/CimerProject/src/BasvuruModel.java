
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasvuruModel implements ModelInterface {

    @Override
    public ResultSet selectBasvuruNo(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sqlNo = new StringBuilder();
        sqlNo.append("SELECT MAX(BasvuruNo) AS BasvuruNumarasi FROM Basvuru");
        Connection connectionNo = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connectionNo.prepareStatement(sqlNo.toString());
        ResultSet result = preparedStatement.executeQuery();

        return result;
    }

    // Süreç tablosundaki verileri alır.
    @Override
    public ResultSet selectSurec(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" Basvuru.BasvuruNo,  [Basvuru.BasvuruSurecTip].SurecIDAdi, [Basvuru.BasvuruSurec].SurecTarih");
        sql.append(" FROM Basvuru inner join [Basvuru.BasvuruSurec]\n"
                + "on Basvuru.BasvuruNo = [Basvuru.BasvuruSurec].BasvuruNo \n"
                + "inner join [Basvuru.BasvuruSurecTip]\n"
                + "on [Basvuru.BasvuruSurecTip].SurecID = [Basvuru.BasvuruSurec].SurecID");

        System.out.println("Süreç Ekranı");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }

    //Sonuç tablosundaki verileri alır

    @Override
    public ResultSet selectSonuc(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" Basvuru.BasvuruNo, [Basvuru.BasvuruSonuc].Bilgi, [Basvuru.BasvuruSonuc].Ucret,  [Basvuru.RetSebepleri].RetSebebi, [Basvuru.BasvuruSonuc].BasvuruCevabiAciklama");
        sql.append(" FROM Basvuru inner join [Basvuru.BasvuruSonuc]\n"
                + "on Basvuru.BasvuruNo = [Basvuru.BasvuruSonuc].BasvuruNo \n"
                + "inner join [Basvuru.RetSebepleri]\n"
                + "on [Basvuru.RetSebepleri].RetSebepID = [Basvuru.BasvuruSonuc].RetID");

        System.out.println("Sonuç Ekranı");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }

    // başvuruları gösterir.
    
    @Override
    public ResultSet select(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("  BasvuruNo, BasvuruSahibiID, [Kurum].KurumAdi, [Basvuru.BasvuruTipi].BasvuruTipiAdi,[Basvuru.CevapTipleri].CevapTipiAdi , BasvuruNedeni, BasvuruTarihi, BasvuruSonTarihi, EkBilgiTalebi");
        sql.append(" FROM Basvuru inner join [Basvuru.CevapTipleri]\n"+
                 "on Basvuru.BasvuruCevapTipiID = [Basvuru.CevapTipleri].CevapTipiID\n"+
                 "inner join [Kurum]\n"+
                 "on [Kurum].KurumID = Basvuru.BasvuruKurumID\n"+
                 "inner join [Basvuru.BasvuruTipi]\n"+
                 "on Basvuru.BasvuruTipiID = [Basvuru.BasvuruTipi].BasvuruTipiID");
  
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));
        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
    }
    
 
    
    @Override
      public ResultSet selectID(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("  BasvuruNo, BasvuruNedeni");
        sql.append(" FROM Basvuru");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }
      
     @Override
    public ResultSet selectUcret(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" [Basvuru].BasvuruNo, [Basvuru.BasvuruSonuc].Ucret, [BasvuruUcreti].SonOdemeTarihi, [BasvuruUcreti].OdendiMi ");
        sql.append(" FROM Basvuru inner join [Basvuru.BasvuruSonuc]\n"+
                 "on [Basvuru].BasvuruNo = [Basvuru.BasvuruSonuc].BasvuruNo\n"+
                 "inner join [BasvuruUcreti]\n"+
                 "on [Basvuru].BasvuruNo = [BasvuruUcreti].BasvuruNo\n");
            
        
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));
        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
    }

    //Tüm kurumları gösterir
    @Override
    public ResultSet selectKurum(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("  KurumID, KurumAdi");
        sql.append(" FROM Kurum");
        System.out.println("Başvurabileceğiniz Kurumlar");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }

    //Başvuru ekler.
    @Override
    public int insert(String fieldNames, List<Object> rows) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO Basvuru (" + fieldNames + ") ");
        sql.append(" VALUES ");

        String[] fieldList = fieldNames.split(",");

        int rowCount = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i) instanceof Basvuru) {
                rowCount++;

                Basvuru basvuru = (Basvuru) rows.get(i);

                sql.append("(");
                for (int j = 0; j < fieldList.length; j++) {
                    String fieldName = fieldList[j].trim();
                    sql.append(DatabaseUtilities.formatField(basvuru.getByName(fieldName)));
                    if (j < fieldList.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");

                if (i < rows.size() - 1) {
                    sql.append(", ");
                }
            }
        }

        if (rowCount > 0) {
            Connection connection = DatabaseUtilities.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        return rowCount;

    }

    @Override
    public int delete(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM Basvuru ");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        int rowCount = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowCount;

    }
    

    @Override
    public String toString() {
        return "Basvuru Model";
    }

    @Override
    public ResultSet selectBasvuru(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertBasvuru(String fieldNames, List<Object> rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertSonuc(String fieldNames, List<Object> rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertSurec(String fieldNames, List<Object> rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectSonuclanmamis(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectGiris(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectRapor(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertYonlendirme(String fieldNames, List<Object> rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectBasvuruSahibiID(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertUcret(String fieldNames, List<Object> rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Map<String, Object> updateParameters, Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectAdres(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
