import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaczkaDialog extends JDialog {
    private JTextField idField;
    private JTextField adresNadawcyField;
    private JTextField adresOdbiorcyField;
    private JTextField wagaField;
    private JTextField dataField;
    private JComboBox<String> typComboBox;
    private JComboBox<StatusPaczki> statusComboBox;
    
    private boolean approved = false;
    private Paczka paczka;
    
    public PaczkaDialog(JFrame parent, String title, Paczka editPaczka, int lastId) {
        super(parent, title, true);
        this.paczka = editPaczka;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        if (editPaczka != null) {
            populateFields();
        } else {
            idfieldcomplete(lastId);
            dataField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        idField = new JTextField(15);
        idField.setEditable(false);
        adresNadawcyField = new JTextField(25);
        adresOdbiorcyField = new JTextField(25);
        wagaField = new JTextField(10);
        dataField = new JTextField(12);
        
        String[] typy = {"Standardowa", "Ekspresowa", "Krucha"};
        typComboBox = new JComboBox<>(typy);
        
        statusComboBox = new JComboBox<>(StatusPaczki.values());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("ID paczki:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Adres nadawcy:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(adresNadawcyField, gbc);
        SwingUtilities.invokeLater(() -> adresNadawcyField.requestFocusInWindow());
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Adres odbiorcy:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(adresOdbiorcyField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Waga (kg):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(wagaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Data nadania:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(dataField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Typ paczki:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(typComboBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(statusComboBox, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Zapisz");
        JButton cancelButton = new JButton("Anuluj");
        
        saveButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        saveButton.addActionListener(e -> saveAction());
        cancelButton.addActionListener(e -> dispose());
        
    }
    
    private void setupEventHandlers() {
        getRootPane().setDefaultButton((JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(0));
    }
    
    private void populateFields() {
        idField.setText(paczka.getId());
        adresNadawcyField.setText(paczka.getAdresNadawcy());
        adresOdbiorcyField.setText(paczka.getAdresOdbiorcy());
        wagaField.setText(String.valueOf(paczka.getWaga()));
        dataField.setText(paczka.getDataNadania());
        typComboBox.setSelectedItem(paczka.getTypPaczki());
        statusComboBox.setSelectedItem(paczka.getStatus());
    }

    private void idfieldcomplete(int lastId) {
        idField.setText(Integer.toString(lastId));
    }
    
    private void saveAction() {
        try {
            String id = idField.getText().trim();
            String adresNadawcy = adresNadawcyField.getText().trim();
            String adresOdbiorcy = adresOdbiorcyField.getText().trim();
            String wagaText = wagaField.getText().trim();
            String data = dataField.getText().trim();
            String typ = (String) typComboBox.getSelectedItem();
            StatusPaczki status = (StatusPaczki) statusComboBox.getSelectedItem();
            
            if (id.isEmpty() || adresNadawcy.isEmpty() || adresOdbiorcy.isEmpty() || 
                wagaText.isEmpty() || data.isEmpty()) {
                throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
            }
            
            double waga = Double.parseDouble(wagaText);
            if (waga <= 0) {
                throw new IllegalArgumentException("Waga musi być większa od 0");
            }

            try {
                LocalDate.parse(data);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data nie jest poprawną datą.");
            }
            
            switch (typ) {
                case "Standardowa":
                    paczka = new PaczkaStandardowa(id, adresNadawcy, adresOdbiorcy, waga, data);
                    break;
                case "Ekspresowa":
                    paczka = new PaczkaEkspresowa(id, adresNadawcy, adresOdbiorcy, waga, data);
                    break;
                case "Krucha":
                    paczka = new PaczkaKrucha(id, adresNadawcy, adresOdbiorcy, waga, data);
                    break;
            }
            
            paczka.setStatus(status);
            approved = true;
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Waga musi być liczbą", "Błąd danych", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd danych", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public Paczka getPaczka() {
        return paczka;
    }
}