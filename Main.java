import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Błąd uruchomienia aplikacji: " + e.getMessage(), 
                    "Błąd krytyczny", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}