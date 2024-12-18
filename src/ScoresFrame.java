import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ScoresFrame extends JFrame{
    ArrayList<Score> scores;
    public ScoresFrame(ArrayList<Score> scores){
        this.scores = scores;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Bestenliste");
        this.setSize(400, 400);

        String[] columns = {"Punkte","Name","Datum", "Schwierigkeit"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Score score : scores) {
            Object[] scoreFields = {score.score, score.name, score.date, score.difficulty}; 
            tableModel.addRow(scoreFields);
        }

        JTable scoreTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(scoreTable);

        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
