import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainFrame extends JFrame {
    private SystemKurierski system;
    private PaczkaTableModel tableModel;
    private JTable paczkaTable;
    private JLabel statusLabel;
    private int lastId = 1;
    private User aktualnyUzytkownik;
    public MainFrame() {

        if (!pokazOknoLogowania()) {
            System.exit(0);
        }

        system = new SystemKurierski();
        lastId = system.getPaczkaService().pobierzNajwiekszeId() + 1;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshTable();
        updateStatusBar();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                zapiszDane();
            }
        });        
    }

    private boolean pokazOknoLogowania() {
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);
        
        aktualnyUzytkownik = loginDialog.getZalogowanyUzytkownik();
        return aktualnyUzytkownik != null;
    }

    private boolean czyMaUprawnienia(String akcja) {
        switch (akcja) {
            case "USUN":
                return aktualnyUzytkownik == User.ADMIN;
            default:
                return false;
        }
    }

    
    
    private void initializeComponents() {
        setTitle("Aplikacja kurierska");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        tableModel = new PaczkaTableModel();
        paczkaTable = new JTable(tableModel);
        paczkaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paczkaTable.setRowHeight(25);
        paczkaTable.getTableHeader().setReorderingAllowed(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        paczkaTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        paczkaTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        paczkaTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        paczkaTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        paczkaTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        
        paczkaTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        paczkaTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        paczkaTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        paczkaTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        paczkaTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        paczkaTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        paczkaTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        paczkaTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        
        statusLabel = new JLabel("Gotowy");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("System kurierski", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(paczkaTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Dodaj paczkę");
        JButton editButton = new JButton("Edytuj paczkę");
        JButton deleteButton = new JButton("Usuń paczkę");
        JButton statusButton = new JButton("Zmień status");
        JButton exportButton = new JButton("Eksportuj do pliku");
        JButton statsButton = new JButton("Statystyki");
        
        Dimension buttonSize = new Dimension(140, 35);
        addButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        statusButton.setPreferredSize(buttonSize);
        exportButton.setPreferredSize(buttonSize);
        statsButton.setPreferredSize(buttonSize);
            
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(statusButton);
        panel.add(exportButton);
        panel.add(statsButton);

        if (aktualnyUzytkownik != User.ADMIN) {
            deleteButton.setVisible(false);
        }
        
        return panel;
    }
    
    private void setupEventHandlers() {
        JPanel buttonPanel = (JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(1);
        
        ((JButton) buttonPanel.getComponent(0)).addActionListener(e -> dodajPaczke());
        ((JButton) buttonPanel.getComponent(1)).addActionListener(e -> edytujPaczke());
        ((JButton) buttonPanel.getComponent(2)).addActionListener(e -> usunPaczke());
        ((JButton) buttonPanel.getComponent(3)).addActionListener(e -> zmienStatus());
        ((JButton) buttonPanel.getComponent(4)).addActionListener(e -> eksportujDane());
        ((JButton) buttonPanel.getComponent(5)).addActionListener(e -> pokazStatystyki());
        
        paczkaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    edytujPaczke();
                }
            }
        });
    }
    
    private void dodajPaczke() {
        PaczkaDialog dialog = new PaczkaDialog(this, "Dodaj nową paczkę", null, lastId);
        dialog.setVisible(true);
        
        if (dialog.isApproved()) {
            try {
                system.getPaczkaService().dodajPaczke(dialog.getPaczka());
                refreshTable();
                updateStatusBar();
                showSuccess("Paczka została dodana pomyślnie");
                lastId++;
            } catch (PaczkaException e) {
                showError("Błąd dodawania paczki: " + e.getMessage());
            }
        }
    }
    
    private void edytujPaczke() {
        int selectedRow = paczkaTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Wybierz paczkę do edycji");
            return;
        }
        
        Paczka paczka = tableModel.getPaczkaAt(selectedRow);
        String originalId = paczka.getId();
        
        PaczkaDialog dialog = new PaczkaDialog(this, "Edytuj paczkę", paczka, 0);
        dialog.setVisible(true);
        
        if (dialog.isApproved()) {
            try {
                system.getPaczkaService().edytujPaczke(originalId, dialog.getPaczka());
                refreshTable();
                updateStatusBar();
                showSuccess("Paczka została zaktualizowana pomyślnie");
            } catch (PaczkaException e) {
                showError("Błąd edycji paczki: " + e.getMessage());
            }
        }
    }
    
    private void usunPaczke() {
        if (!czyMaUprawnienia("USUN")){
            showWarning("Brak uprawnień");
            return;
        }


        int selectedRow = paczkaTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Wybierz paczkę do usunięcia");
            return;
        }
        
        Paczka paczka = tableModel.getPaczkaAt(selectedRow);
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Czy na pewno chcesz usunąć paczkę: " + paczka.getId() + "?",
            "Potwierdzenie usunięcia", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                system.getPaczkaService().usunPaczke(paczka.getId());
                refreshTable();
                updateStatusBar();
                showSuccess("Paczka została usunięta pomyślnie");
            } catch (PaczkaNotFoundException e) {
                showError("Błąd usuwania paczki: " + e.getMessage());
            }
        }
    }
    
    private void zmienStatus() {
        int selectedRow = paczkaTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Wybierz paczkę do zmiany statusu");
            return;
        }
        
        Paczka paczka = tableModel.getPaczkaAt(selectedRow);
        
        StatusChangeDialog dialog = new StatusChangeDialog(this, paczka.getStatus());
        dialog.setVisible(true);
        
        if (dialog.isApproved()) {
            try {
                system.getPaczkaService().zmienStatus(paczka.getId(), dialog.getSelectedStatus());
                refreshTable();
                updateStatusBar();
                showSuccess("Status paczki został zmieniony pomyślnie");
            } catch (PaczkaNotFoundException e) {
                showError("Błąd zmiany statusu: " + e.getMessage());
            }
        }
    }
    
    private void eksportujDane() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("raport_paczek.txt"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Pliki tekstowe", "txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    fileName += ".txt";
                }
                
                List<Paczka> paczki = system.getPaczkaService().pobierzWszystkiePaczki();
                system.getExportService().exportuj(paczki, fileName);
                showSuccess("Dane zostały wyeksportowane do pliku: " + fileName);
            } catch (ExportException e) {
                showError("Błąd eksportu: " + e.getMessage());
            }
        }
    }

   private void zapiszDane() {
        try {
            system.getPaczkaService().zapiszDane();
        } catch (Exception e) {
            System.err.println("Błąd podczas zapisywania danych: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void pokazStatystyki() {
        StatystykiRaport raport = system.getStatystykaService().generujRaport();
        StatystykiDialog dialog = new StatystykiDialog(this, raport);
        dialog.setVisible(true);
    }
    
    private void refreshTable() {
        List<Paczka> paczki = system.getPaczkaService().pobierzWszystkiePaczki();
        tableModel.setPaczki(paczki);
    }
    
    private void updateStatusBar() {
        StatystykiRaport raport = system.getStatystykaService().generujRaport();
        statusLabel.setText(String.format("Paczki: %d | Doręczone: %d | Do doręczenia: %d | Przychód: %.2f zł", 
            raport.getWszystkiePaczki(), 
            raport.getPaczkiDoręczone(), 
            raport.getPaczkiDoDoręczenia(), 
            raport.getCalkowityPrzychod()));
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Uwaga", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Błąd", JOptionPane.ERROR_MESSAGE);
    }


}