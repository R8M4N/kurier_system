import javax.swing.*;
import java.awt.*;

public class StatusChangeDialog extends JDialog {
    private JComboBox<StatusPaczki> statusComboBox;
    private boolean approved = false;
    
    public StatusChangeDialog(JFrame parent, StatusPaczki currentStatus) {
        super(parent, "Zmiana statusu paczki", true);
        
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(new JLabel("Nowy status:"));
        statusComboBox = new JComboBox<>(StatusPaczki.values());
        statusComboBox.setSelectedItem(currentStatus);
        mainPanel.add(statusComboBox);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Zapisz");
        JButton cancelButton = new JButton("Anuluj");
        
        saveButton.addActionListener(e -> {
            approved = true;
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public StatusPaczki getSelectedStatus() {
        return (StatusPaczki) statusComboBox.getSelectedItem();
    }
}