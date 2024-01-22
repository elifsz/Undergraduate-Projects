
import java.util.*;

public class ModelViewControllerConsole {

    public static void main(String[] args) throws Exception {
        connectToDatabase();

        Map<String, Controller> router = new HashMap<>();
        router.put("MainMenu", new Controller(new MainMenuView(), new NopModel()));
       
        // basvuru
        router.put("Basvuru", new Controller(new BasvuruView(), new BasvuruModel()));
        
        // başvuru sahibi
        router.put("BasvuruSahibi", new Controller(new BasvuruSahibiView(), new BasvuruSahibiModel()));
        
        // personel
        router.put("Personel", new Controller(new PersonelView(), new PersonelModel()));

        ViewData viewData = new ViewData("MainMenu", "");
        do {
            // Model, View, and Controller
            Controller controller = router.get(viewData.functionName);
            ModelData modelData = controller.executeModel(viewData);
            viewData = controller.getView(modelData, viewData.functionName, viewData.operationName);

            System.out.println();

            System.out.println("-------------------------------------------------");
            System.out.println();
        } while (viewData.functionName != null);

        System.out.println();
        System.out.println();
        System.out.println("Program is ended...");

        // Disconnect from database
        disconnectFromDatabase();
    }

    public static void connectToDatabase() {
        DatabaseUtilities.host = "localhost:49672";
        DatabaseUtilities.databaseName = "Group19";

        try {
            DatabaseUtilities.getConnection();
        } catch (Exception e) {
            System.out.println("Exception occured : " + e);
            return;
        }
    }

    public static void disconnectFromDatabase() {
        try {
            DatabaseUtilities.disconnect();
        } catch (Exception e) {
            System.out.println("Error disconnecting from database : " + e);
            return;
        }
    }

}
