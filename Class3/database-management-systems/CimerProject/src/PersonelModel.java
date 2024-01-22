
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonelModel implements ModelInterface {
    
    
    
     @Override
    public int update(Map<String, Object> updateParameters, Map<String, Object> whereParameters) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE Basvuru SET BasvuruSonTarihi = DATEADD(DAY,15,Basvuru.BasvuruSonTarihi)\n" +
                    "FROM  Basvuru INNER JOIN [Basvuru.BasvuruSurec] ON Basvuru.BasvuruNo = [Basvuru.BasvuruSurec].BasvuruNo\n" );
        int appendCount = 0;
     /*   for (Map.Entry<String, Object> entry : updateParameters.entrySet()) {
            sql.append(entry.getKey() + " = " + DatabaseUtilities.formatField(entry.getValue()));
            if (++appendCount < updateParameters.size()) {
                sql.append(", ");
            }
        }*/
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));
        sql.append(" AND ([Basvuru.BasvuruSurec].SurecID = 3 OR [Basvuru.BasvuruSurec].SurecID = 6)");
        //System.out.println(sql.toString());

        // execute constructed SQL statement
        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        int rowCount = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowCount;
    
    
    
    
    
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
    
    
    // raporlama yapar   
     @Override
    public ResultSet selectRapor(Map<String, Object> whereParameters) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append(" WITH A AS (SELECT\n"
                + "COUNT(BasvuruNo) as x\n"
                + "FROM [Basvuru]),\n"
                + "\n"
                + "B AS (SELECT\n"
                + "COUNT(BasvuruNo) as y\n"
                + "FROM [Basvuru.BasvuruSurec]\n"
                + "WHERE SurecID=4),\n"
                + "\n"
                + "C AS (SELECT\n"
                + "COUNT(BasvuruNo) as z\n"
                + "FROM [Basvuru.BasvuruSurec]\n"
                + "WHERE SurecID=5),\n"
                + "\n"
                + "D AS (SELECT\n"
                + "COUNT(BasvuruNo) as t\n"
                + "FROM [Basvuru.BasvuruSonuc]\n"
                + "WHERE RetID = 2 OR RetID = 4 OR RetID = 5 OR RetID = 9 OR RetID = 10 OR RetID = 11 OR RetID = 12) ");
        sql.append(" SELECT x,y,z,t FROM A,B,C,D ");

        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
    }
    
    //  cevaplanmamış başvuruları personelin görmesini sağlar.
     @Override
    public ResultSet selectSonuclanmamis(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" WITH S1 as(SELECT\n"
                + "(Basvuru.BasvuruNo)\n"
                + "\n"
                + "FROM Basvuru INNER JOIN [Basvuru.BasvuruSonuc]\n"
                + "ON Basvuru.BasvuruNo = [Basvuru.BasvuruSonuc].BasvuruNo),\n"
                + "\n"
                + "s2 as (\n"
                + "select BasvuruNo\n"
                + "FROM basvuru)\n"
                + "\n"
                + "select *  from s2 Except select  * from s1 ");


        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }
    
    // personelin giriş işlemi
    @Override
    public ResultSet selectGiris(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
         sql.append(" SELECT ");
        sql.append("  PersonelID, Ad, Soyad, Sifre, YetkiID");
        sql.append(" FROM [Kullanici.Personel] ");


        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;

    }
    
    // başvuru süreçlerini personele gösterir
    @Override
    public ResultSet selectSurec(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" [Kullanici.Personel].PersonelID,[Kullanici.Personel].Ad, [Kullanici.Personel].Soyad, [Kullanici.Personel].Sifre, [Kullanici.Personel].YetkiID");
        sql.append(" FROM [dbo].[Kullanici.Personel] INNER JOIN [dbo].[Kullanici]");
      
        System.out.println("This is yarkinsargin technology!");
        
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));
      

        // execute constructed SQL statement
        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
       
    
    }
    //başvuruları personele gösterir.
     @Override
   /* public ResultSet selectBasvuru(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("  BasvuruNo, BasvuruSahibiID, BasvuruPersonelID, BasvuruKurumID, BasvuruTipiID, BasvuruCevapTipiID, BasvuruNedeni, BasvuruTarihi, BasvuruSonTarihi, EkBilgiTalebi");
        sql.append(" FROM Basvuru");
 
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));

        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
       
    
    }*/
     
  
    public ResultSet selectBasvuru(Map<String, Object> whereParameters) throws Exception {
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
     
    // internet üzerinden olmayan başvuruları personelin eklemesini sağlar. 
      @Override
    public int insertBasvuru(String fieldNames, List<Object> rows) throws Exception {
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
    public int insertUcret(String fieldNames, List<Object> rows) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO BasvuruUcreti (" + fieldNames + ") ");
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
    
    
    
    // Yönlendirilen başvurunun eklenmesini sağlar
         @Override
    public int insertYonlendirme(String fieldNames, List<Object> rows) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO [BasvuruYonlendirme] (" + fieldNames + ") ");
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
    
    // Başvuru sonucunun eklenmesini sağlar
       @Override
    public int insertSonuc(String fieldNames, List<Object> rows) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO [Basvuru.BasvuruSonuc] (" + fieldNames + ") ");
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
    
    //Başvurunun sürecini ekler.
    @Override
    public int insertSurec(String fieldNames, List<Object> rows) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO [Basvuru.BasvuruSurec] (" + fieldNames + ") ");
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
    public ResultSet select(Map<String, Object> whereParameters) throws Exception {
        // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" PersonelID, Ad, Soyad, Sifre, YetkiID");
        sql.append(" FROM Kullanici.Personel");
        
     


        
        List<Map.Entry<String, Object>> whereParameterList = DatabaseUtilities.createWhereParameterList(whereParameters);
        sql.append(DatabaseUtilities.prepareWhereStatement(whereParameterList));


        Connection connection = DatabaseUtilities.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        DatabaseUtilities.setWhereStatementParameters(preparedStatement, whereParameterList);
        ResultSet result = preparedStatement.executeQuery();

        return result;
       
    
    }
    
    //personel ekler
    @Override
    public int insert(String fieldNames, List<Object> rows) throws Exception {
         // construct SQL statement
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO [Kullanici.Personel] (" + fieldNames + ") ");
        sql.append(" VALUES ");

        String[] fieldList = fieldNames.split(",");

        int rowCount = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i) instanceof Personel) {
                rowCount++;

                Personel personel = (Personel) rows.get(i);

                sql.append("(");
                for (int j = 0; j < fieldList.length; j++) {
                    String fieldName = fieldList[j].trim();
                    sql.append(DatabaseUtilities.formatField(personel.getByName(fieldName)));
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
        sql.append(" DELETE FROM [Kullanici.Personel] ");

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
        return "Personel Model";
    }    

    @Override
    public ResultSet selectKurum(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectSonuc(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public ResultSet selectBasvuruNo(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectBasvuruSahibiID(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectID(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet selectAdres(Map<String, Object> whereParameters) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   


}
