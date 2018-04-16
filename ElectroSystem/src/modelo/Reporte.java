package modelo;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	
	//Recibe la lista de electrodomesticos para armar el reporte
    public void ReporteRankElectrodomesticos() throws Exception
    {
    	Modelo modelo = new Modelo();
    	Map<String, Object> parametersMap = new HashMap<String, Object>();
    	parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		String reportSource = "/reports/Rank_Electrodomesticos.jasper";
		InputStream is = null;
    	try		{
    		is = getClass().getResourceAsStream(reportSource);
			this.reporte = (JasperReport) JRLoader.loadObject(is);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(modelo.getRankElectrodomesticos()));
		}
		catch( JRException ex ) 
		{
			ex.printStackTrace();
		}
    	catch ( NullPointerException ex )
    	{
    		try		{
	    		this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reports//Rank_Electrodomesticos.jasper" );
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
						new JRBeanCollectionDataSource(modelo.getRankElectrodomesticos()));
    		}catch( JRException ex2 ) 
    		{
    			ex2.printStackTrace();
    		}
    	}
    	finally
    	{
    		try 
    		{
    			if (is!=null){
    				is.close();
    			}
    		}
    		catch(Exception exp)
    		{
    			exp.printStackTrace();
    		}
    	}
    }  
    
    public void ReporteRankPiezas() throws Exception
    {
    	Modelo modelo = new Modelo();
    	Map<String, Object> parametersMap = new HashMap<String, Object>();
    	parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		String reportSource = "/reports/Rank_Piezas.jasper";
		InputStream is = null;
    	try		{
    		is = getClass().getResourceAsStream(reportSource);
			this.reporte = (JasperReport) JRLoader.loadObject(is);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(modelo.getRankPiezas()));
		}
		catch( JRException ex ) 
		{
			ex.printStackTrace();
		}
    	catch ( NullPointerException ex )
    	{
    		try		{
	    		this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reports//Rank_Piezas.jasper" );
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
						new JRBeanCollectionDataSource(modelo.getRankPiezas()));
    		}catch( JRException ex2 ) 
    		{
    			ex2.printStackTrace();
    		}
    	}
    	finally
    	{
    		try 
    		{
    			if (is!=null){
    				is.close();
    			}
    		}
    		catch(Exception exp)
    		{
    			exp.printStackTrace();
    		}
    	}
    }
    
    public void mostrar()
	{
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}
}
