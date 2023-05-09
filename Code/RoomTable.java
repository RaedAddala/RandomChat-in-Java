import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTable extends JFrame {
    private List<List<String>> lines = new ArrayList<List<String>>();
    public void PushRoom( String id1, String id2){
        try{
        List<String> toAdd = new ArrayList<String>();
        toAdd.add(id1);
        toAdd.add(id2);
        lines.add(toAdd);
        table.repaint();
        model.addRow(toAdd.toArray(new String[0]));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public DefaultTableModel model;
    public void DeleteRoom(String converstantId){
        int count =0;
        for (List<String> line: lines
        ) {
            if(line.get(0).equals(converstantId) || line.get(1).equals(converstantId)){
                lines.remove(count);
                model.removeRow(count);
                break;
            }
            count++;
        }
        table.repaint();
    }
    final String[] colonnesNom = {"user1 id", "user2 id"};
    public RoomTable() {
        model = new DefaultTableModel(colonnesNom, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.enableInputMethods(false);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(500, 300));
        this.getContentPane().add(scrollpane);
        this.pack();
        this.setVisible(true);
    }
    private JTable table;
}
