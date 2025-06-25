import javax.swing.*;
import java.awt.*;

public class StatystykiDialog extends JDialog {
    
    public StatystykiDialog(JFrame parent, StatystykiRaport raport) {
        super(parent, "Statystyki systemu", true);
        
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel label1 = new JLabel("Wszystkie paczki:");
        label1.setFont(labelFont);
        mainPanel.add(label1, gbc);
        gbc.gridx = 1;
        JLabel value1 = new JLabel(String.valueOf(raport.getWszystkiePaczki()));
        value1.setFont(valueFont);
        mainPanel.add(value1, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel label2 = new JLabel("Paczki doręczone:");
        label2.setFont(labelFont);
        mainPanel.add(label2, gbc);
        gbc.gridx = 1;
        JLabel value2 = new JLabel(String.valueOf(raport.getPaczkiDoręczone()));
        value2.setFont(valueFont);
        value2.setForeground(Color.GREEN.darker());
        mainPanel.add(value2, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel label3 = new JLabel("Paczki do doręczenia:");
        label3.setFont(labelFont);
        mainPanel.add(label3, gbc);
        gbc.gridx = 1;
        JLabel value3 = new JLabel(String.valueOf(raport.getPaczkiDoDoręczenia()));
        value3.setFont(valueFont);
        value3.setForeground(Color.ORANGE.darker());
        mainPanel.add(value3, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel label4 = new JLabel("Całkowity przychód:");
        label4.setFont(labelFont);
        mainPanel.add(label4, gbc);
        gbc.gridx = 1;
        JLabel value4 = new JLabel(String.format("%.2f zł", raport.getCalkowityPrzychod()));
        value4.setFont(valueFont);
        value4.setForeground(Color.BLUE.darker());
        mainPanel.add(value4, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Zamknij");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }
}