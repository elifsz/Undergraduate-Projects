
import java.util.*;

class MainMenuView implements ViewInterface {

    @Override
    public ViewData create(ModelData modelData, String functionName, String operationName) throws Exception {
        Integer menu;
        Integer choice = 1;
        short YetkiID = PersonelView.YetkiID;
        short flagPersonel = PersonelView.flag;//Personel Ekranlarını Kontrol Eder.
        short flagKullanici = BasvuruSahibiView.kullaniciFlag;
        short flagBasvuru = BasvuruView.basvurduMu;
        short flagKayit = BasvuruSahibiView.kaydolduMu;
        do {
            if(flagKullanici == 0) {
                 System.out.println("1. Kullanıcı Ekranı");
            }
            if(flagKullanici == 1) {
                System.out.println("2. Başvuru Ekranı");
            }
            if(flagKullanici != 1) {
               System.out.println("3. Personel Ekranı");
            }
            System.out.println("4. Çıkış");
            System.out.println();

            menu = getInteger("Seciminizi Giriniz : ", false);

            if (menu == 1) {
                do {
                    System.out.println("1. Üye olma");
                    System.out.println("2. Giriş yapma");
                    
                    if(flagKayit == 1) {
                        System.out.println("3. Kullanıcı ID görüntüle");
                    }
                    System.out.println("4. Adresleri görüntüleyin");
                    System.out.println("5. Çıkış");
                    System.out.println();
                    choice = getInteger("Seçiminizi giriniz : ", false);
                } while (choice == null || choice < 1 || choice > 5);
            }

            if (menu == 2) {

                do {
                    
                    System.out.println("1. Basvurunuzu Görüntüleyin");
                    System.out.println("2. Basvuru Yapın");
                    System.out.println("3. Sonucunuzu Görüntüleyin");
                   
                    System.out.println("4. Basvurunuzun Sureclerini Görüntüle ");
                    System.out.println("5. Kurumları Görüntüle ");
                    
                    if(flagBasvuru == 1) {
                        System.out.println("6. Başvuru Numarası Göster");
                    }
                    
                    System.out.println("7. ID ile Başvuruyu Göster");
                    System.out.println("8. Başvuru Ücretini Göster");

                    System.out.println("9. CIKIS");
                    System.out.println();

                    choice = getInteger("Secimizi Giriniz : ", false);
                } while (choice == null || choice < 1 || choice > 9);
            }

            if (menu == 3) {

                do {
                    if (flagPersonel == 0) {
                        System.out.println("8. GİRİŞ");
                    }

                    if (flagPersonel == 1 && YetkiID == 0) {
                        System.out.println("1. Tüm başvuruları görüntüle");
                    } else if (flagPersonel == 1 && YetkiID == 1) {
                        System.out.println("2. Personel ekle");
                        System.out.println("3. Başvuru ekle");
                        System.out.println("4. Başvuru sonucu ekle");
                        System.out.println("5. Başvuru süreci ekle");
                    } else if (flagPersonel == 1 && YetkiID == 2) {
                        System.out.println("1. Tüm başvuruları görüntüle");
                        System.out.println("2. Personel ekle");
                        System.out.println("3. Başvuru ekle");
                        System.out.println("4. Başvuru sonucu ekle");
                        System.out.println("5. Başvuru süreci ekle");
                        System.out.println("6. Raporlama");
                        System.out.println("7. Sonuçlandırılmamış Başvuruları Görüntüle");
                        System.out.println("9. Yönlendirilen başvuru ekle");
                        System.out.println("10. Başvuru ücreti ekle");
                        System.out.println("11. Ucretleri Görüntüle");
                        System.out.println("12. Yönlendirme ya da ücret talebi için tarih güncelleme");
                        System.out.println("13. Personel Silme");

                    }
                    System.out.println("14. ÇIKIŞ");
                    System.out.println();

                    choice = getInteger("Secimizi Giriniz : ", false);
                } while (choice == null || choice < 1 || choice > 14);
            }

        } while (menu == null || menu < 1 || menu > 4);

        Map<String, Object> userInput = new HashMap<>();
        userInput.put("mainMenuChoice", choice);

        if (menu == 2) {

            switch (choice.intValue()) {
                
                case 1:
                    operationName = "select.gui";
                    break;
                case 2:
                    operationName = "insert.gui";
                    break;
                case 3:
                    operationName = "selectSonuc.gui";
                    break;               
                case 4:
                    operationName = "selectSurec.gui";
                    break;
                case 5:
                    operationName = "selectKurum";
                    break;
                case 6:
                    operationName = "selectBasvuruNo";
                    break;
                case 7:
                    operationName = "selectID.gui";
                    break;
                case 8:
                    operationName = "selectUcret.gui";
                    break;   
                 
               
                default:
                    return new ViewData(null, null);
            }

            return new ViewData("Basvuru", operationName, new HashMap<>());

        } else if (menu == 1) {
            switch (choice.intValue()) {
                case 1:
                    operationName = "insert.gui";
                    break;
                case 2:
                    operationName = "select";
                    break;
                case 3:
                    operationName = "selectBasvuruSahibiID";
                    break;
                case 4:
                    operationName = "selectAdres";
                    break;
                default:
                    return new ViewData(null, null);

            }

            return new ViewData("BasvuruSahibi", operationName, new HashMap<>());
        } else if (menu == 3) {
            switch (choice.intValue()) {
                case 1:
                    operationName = "selectBasvuru";
                    break;
                case 2:
                    operationName = "insert.gui";
                    break;
                case 3:
                    operationName = "insertBasvuru.gui";
                    break;
                case 4:
                    operationName = "insertSonuc.gui";
                    break;
                case 5:
                    operationName = "insertSurec.gui";
                    break;
                case 6:
                    operationName = "selectRapor";
                    break;
                case 7:
                    operationName = "selectSonuclanmamis";
                    break;
                case 8:
                    operationName = "selectGiris";
                    break;
                case 9:
                    operationName = "insertYonlendirme.gui";
                    break;
                case 10:
                    operationName = "insertUcret.gui";
                    break;   
                case 11:
                    operationName = "selectUcret";
                    break; 
                case 12:
                    operationName = "update.gui";
                    break; 
                 case 13:
                    operationName = "delete.gui";
                    break;
                 
                default:
                    return new ViewData(null, null);
            }

            return new ViewData("Personel", operationName, new HashMap<>());

        }
        return new ViewData(null, null);

    }

    @Override
    public String toString() {
        return "Main Menu View";
    }
}
