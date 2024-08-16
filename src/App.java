import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class App {
    public static void main(String[] args) throws Exception {
        int tileSize = 32;
        int colums = 16;
        int rows = 16;

        LocalDateTime currentDateTime = LocalDateTime.now();
        
        // Ausgabe ohne Formatierung
        System.out.println("Aktuelles Datum und Uhrzeit: " + currentDateTime);
        // Datum und Uhrzeit formatieren
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        
        // Formatierte Ausgabe
        System.out.println("Formatiertes Datum und Uhrzeit: " + formattedDateTime);
        SpaceInvadersMenu menu = new SpaceInvadersMenu();
        //MainFrame mainFrame = new MainFrame(tileSize, colums, rows, menu.userName);

    }
}
