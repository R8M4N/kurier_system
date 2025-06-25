import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class PaczkaTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Adres nadawcy", "Adres odbiorcy", "Waga (kg)", "Status", "Data", "Typ", "Koszt (zł)"};
    private List<Paczka> paczki;
    
    public PaczkaTableModel() {
        this.paczki = new ArrayList<>();
    }
    
    public void setPaczki(List<Paczka> paczki) {
        this.paczki = paczki != null ? paczki : new ArrayList<>();
        fireTableDataChanged();
    }
    
    public Paczka getPaczkaAt(int row) {
        if (row >= 0 && row < paczki.size()) {
            return paczki.get(row);
        }
        return null;
    }
    
    @Override
    public int getRowCount() {
        return paczki.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= paczki.size()) return null;
        
        Paczka paczka = paczki.get(rowIndex);
        switch (columnIndex) {
            case 0: return paczka.getId();
            case 1: return paczka.getAdresNadawcy();
            case 2: return paczka.getAdresOdbiorcy();
            case 3: return String.format("%.2f", paczka.getWaga());
            case 4: return paczka.getStatus().toString();
            case 5: return paczka.getDataNadania();
            case 6: return paczka.getTypPaczki();
            case 7: return String.format("%.2f", paczka.obliczKoszt());
            default: return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
