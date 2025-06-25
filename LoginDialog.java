import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private User zalogowanyUzytkownik = null;
    private LoginManager loginManager;
    
    public LoginDialog(JFrame parent) {
        super(parent, "Logowanie", true);
        this.loginManager = new LoginManager();
        initializeComponents();
        setupLayout();
        setupEvents();
        setSize(320, 180);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initializeComponents() {
        loginField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Zaloguj");
        cancelButton = new JButton("Anuluj");
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel headerLabel = new JLabel("Logowanie");
        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));
        add(headerLabel, gbc);
        
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Login:"), gbc);
        
        gbc.gridx = 1;
        add(loginField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Hasło:"), gbc);
        
        gbc.gridx = 1;
        add(passwordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);
    }
    
    private void setupEvents() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprawdzLogowanie();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        ActionListener loginAction = e -> sprawdzLogowanie();
        loginField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
    }
    
    private void sprawdzLogowanie() {
        String login = loginField.getText().trim();
        String haslo = new String(passwordField.getPassword());

        if (login.isEmpty() || haslo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Proszę wprowadzić login i hasło!",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        loginButton.setText("Sprawdzanie...");
        loginButton.setEnabled(false);

        try {
            zalogowanyUzytkownik = loginManager.zaloguj(login, haslo);

            if (zalogowanyUzytkownik != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Nieprawidłowy login lub hasło!",
                    "Błąd logowania",
                    JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                loginField.selectAll();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Błąd połączenia z bazą danych: " + e.getMessage(),
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
        }

        loginButton.setText("Zaloguj");
        loginButton.setEnabled(true);
    }
    
    public User getZalogowanyUzytkownik() {
        return zalogowanyUzytkownik;
    }
}