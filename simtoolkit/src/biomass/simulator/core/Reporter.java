/**
 * 
 */
package biomass.simulator.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import multiagent.model.agent.SteppableAgent;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.model.taxonomy.Population;

/**
 * @author flavioreyes, candysansores
 *
 */
public class Reporter implements SteppableAgent {
	BioMASSModel model;
	Bag bufferedWriters;
	BufferedWriter refWriter;
	
	public Reporter(){
		model=BioMASSGUIFrame.getInstance();
		bufferedWriters = new Bag();
	}

	/* (non-Javadoc)
	 * @see biomass.simulator.core.SteppableObject#step(long)
	 */
	@Override
	public boolean step(long step) {
		// Registramos estad’sticas de utilizaci—n de los refugios
		try{
			refWriter.write(String.format("%30d", step));
			for(int sizeClass=0; sizeClass<model.refSizeClasses; sizeClass++)
				refWriter.write(String.format(" %12d", model.getRefUseCounter(sizeClass)));
			refWriter.write(String.format("\n"));
			
		}
		catch (Exception e){
			System.err.println("Write error: " + e.getMessage()+" on file refuges...txt");				
		}
		// Registramos estad’sticas de las poblaciones
		BufferedWriter bfwriter;
		Population p;
		int ageClass, ip;
		for(ip=0;ip<model.populations.numObjs;ip++){
			p= (Population) model.populations.objs[ip];
			bfwriter=(BufferedWriter) bufferedWriters.objs[ip];
			try{
				bfwriter.write(String.format("%30d", step));
				for(ageClass=0; ageClass<p.ageClasses; ageClass++){
					bfwriter.write(String.format(" %12d", p.getOrgs(ageClass)));
					bfwriter.write(String.format(" %12d", p.getHid(ageClass)));
//					p.resetHid(ageClass);
					bfwriter.write(String.format(" %12d", p.getKilled(ageClass)));
					bfwriter.write(String.format(" %12d", p.getStarved(ageClass)));
					bfwriter.write(String.format(" %12.4f", p.getLeanMass(ageClass)));
					bfwriter.write(String.format(" %12.4f", p.getFatMass(ageClass)));
					bfwriter.write(String.format(" %12.4f", p.getKilledMass(ageClass)));
					bfwriter.write(String.format(" %12.4f", p.getStarvedMass(ageClass)));
				}
				bfwriter.write(String.format("\n"));
			}
			catch (Exception e){
				System.err.println("Write error: " + e.getMessage()+" on file "+p.getName()+"...txt");				
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see biomass.simulator.core.SteppableObject#start()
	 */
	@Override
	public void start() {
		FileWriter fstream;
		// Preparamos las cadenas con fecha y hora del registro
		Locale.setDefault(Locale.US);
		Date date= new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String dateString1 = formatter.format(date);
		formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateString2 = formatter.format(date);		
		formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString3 = formatter.format(date);	
		// Abrimos el archivo con el registro de los refugios
		try{
			int sizeClass;
		    fstream = new FileWriter("refuges-"+dateString1+".txt");
		    refWriter = new BufferedWriter(fstream);
		    refWriter.write(String.format("%-30s %12.2f\n", "BioMASSver", model.getVersion()));
		    refWriter.write(String.format("%-30s %12s\n", "date", dateString2));
		    refWriter.write(String.format("%-30s %12s\n", "hour", dateString3));
		    refWriter.write(String.format("%-30s %12d\n", "refuges", model.refuges.size()));
		    refWriter.write(String.format("%-30s %12.4f\n", "refs_minrad", model.getRefMinRad()));
		    refWriter.write(String.format("%-30s %12.4f\n", "refs_maxrad", model.getRefMaxRad()));
		    refWriter.write(String.format("%-30s %12.4f\n", "refs_areafrac", model.getRefAreaFrac()));
		    refWriter.write(String.format("%-30s %12.4f\n", "refs_disp", model.getRefSpatialStdDev()));
		    refWriter.write(String.format("%-30s %12d\n", "refs_sizeclasses", model.refSizeClasses));
			refWriter.write(String.format("%-30s", "refs_sizeclass"));	
			for(sizeClass=0; sizeClass<model.refSizeClasses; sizeClass++)
				refWriter.write(String.format(" %12s",String.format("%s%02d", "class", sizeClass)));
			refWriter.write(String.format("\n"));
			refWriter.write(String.format("%-30s", "refs_maxsize"));	
			for(sizeClass=0; sizeClass<model.refSizeClasses; sizeClass++)
				refWriter.write(String.format(" %12.4f", model.refMinRad+(sizeClass+1)*(model.refMaxRad-model.refMinRad)/model.refSizeClasses));
			refWriter.write(String.format("\n"));
			refWriter.write(String.format("%-30s", "refs_count"));	
			for(sizeClass=0; sizeClass<model.refSizeClasses; sizeClass++)
				refWriter.write(String.format(" %12d", model.getRefCounter(sizeClass)));
			refWriter.write(String.format("\n"));
			refWriter.write(String.format("%-20s%10s\n", "refs_use","step"));				
		}
		catch (Exception e){
			System.err.println("Open error: " + e.getMessage()+" on file refuges...txt");
		}
		BufferedWriter bfwriter;
		Population p;
		
		int i, ip;
		for(ip=0;ip<model.populations.numObjs;ip++){
			p= (Population) model.populations.objs[ip];
			try{
			    fstream = new FileWriter("pop"+p.getName()+"-"+dateString1+".txt");
			    bfwriter = new BufferedWriter(fstream);
			    bufferedWriters.add(bfwriter);
			    bfwriter.write(String.format("%-30s %12.2f\n", "BioMASSver", model.getVersion()));
			    bfwriter.write(String.format("%-30s %12s\n", "date", dateString2));
			    bfwriter.write(String.format("%-30s %12s\n", "hour", dateString3));
			    bfwriter.write(String.format("%-30s\n", p.getName()));
			    bfwriter.write(String.format("%-30s\n", p.fg.spec));
			    bfwriter.write(String.format("%-30s\n", p.fg.commonName));
			    bfwriter.write(String.format("%-30s %12d\n", "longevity", p.fg.lifeSpan));
			    bfwriter.write(String.format("%-30s %12.4f\n", "linf", p.fg.linf));
			    bfwriter.write(String.format("%-30s %12.4f\n", "t0", p.fg.t0));
			    bfwriter.write(String.format("%-30s %12.4f\n", "k", p.fg.k));
			    bfwriter.write(String.format("%-30s %12.4f\n", "a", p.fg.a));
			    bfwriter.write(String.format("%-30s %12.4f\n", "b", p.fg.b));
			    bfwriter.write(String.format("%-30s %12d\n", "newborn_orgs", p.getNewbornOrgs()));
			    bfwriter.write(String.format("%-30s %12.4f\n", "pop_deathrate", p.getDeathRate()));
			    bfwriter.write(String.format("%-30s %12d\n", "rep_cycle", p.fg.ageClassLapse));
			    bfwriter.write(String.format("%-30s %12d\n", "age_classes", p.ageClasses));
				bfwriter.write(String.format("%30s", "steps"));	
				for(i=0; i<p.ageClasses; i++){
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "orgs", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "hid", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "killed", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "starved", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "leanmass", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "fatmass", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "killmass", i)));
					bfwriter.write(String.format(" %12s",String.format("%s%02d", "starvemas", i)));
				}
				bfwriter.write(String.format("\n"));
			}				
			catch (Exception e){
				System.err.println("Open error: " + e.getMessage()+" on file "+p.getName()+"...txt");
			}
		}
	}

	/* (non-Javadoc)
	 * @see biomass.simulator.core.SteppableObject#stop()
	 */
	@Override
	public void stop() {
		try{
			refWriter.close();
		}
		catch (Exception e){
			System.err.println("Open error: " + e.getMessage()+" on file refuges...txt");
		}
		Population p;
		for(int ip=0;ip<model.populations.numObjs;ip++){
			p= (Population) model.populations.objs[ip];
			try{
				((BufferedWriter) bufferedWriters.objs[ip]).close();			
			}
			catch (Exception e){
					System.err.println("Close error: " + e.getMessage()+" on file "+p.getName()+"...txt");
			}
		}
	}

}
