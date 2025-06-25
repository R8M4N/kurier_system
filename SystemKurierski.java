public class SystemKurierski {
    private IPaczkaService paczkaService;
    private IExportService exportService;
    private IStatystykaService statystykaService;
    
    public SystemKurierski() {
        this.paczkaService = new PaczkaService();
        this.exportService = new ExportService();
        this.statystykaService = new StatystykaService(paczkaService);
    }
    
    public IPaczkaService getPaczkaService() {
        return paczkaService;
    }
    
    public IExportService getExportService() {
        return exportService;
    }
    
    public IStatystykaService getStatystykaService() {
        return statystykaService;
    }
}