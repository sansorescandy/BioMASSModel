package biomass.simulator.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biomass.simulator.core.BioMASSModel;


import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BioMASSGUIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BioMASSModel model;
	private Thread runThread;
	private final Object runThreadLock=new Object();
	private long maxStepsToEnd = Long.MAX_VALUE;
	//private long maxStepsToEnd = 86400;
	private boolean runThreadShouldStop;
	private long runSleep = 0;
	public static BioMASSModel staticInstance=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//model = new BioMASSModel();
		model = new BioMASSModel();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BioMASSGUIFrame frame = new BioMASSGUIFrame();
					frame.pack();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BioMASSGUIFrame() {
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		getContentPane().add(contentPane, BorderLayout.NORTH);
		
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//t1=System.currentTimeMillis();
				model.start();
				startRunThread();
			}
		});
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(btnRun);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Tiempo= "+(System.currentTimeMillis()-t1)+" Pasos= "+model.scheduler.getSteps());
				stopRunThread();
	            //model.stop();
			}
		});
		contentPane.add(btnStop);
		
		getContentPane().add(model.getBioMASSDisplay(), BorderLayout.SOUTH);
	}
	
	 Runnable blocker = new Runnable()
     {
     public void run()
         {
         // intentionally do nothing
         }
     };
	
	private void startRunThread() {
		setShouldStopRunThread(false);
		Runnable run = new Runnable() {
			long t1,t=0;
			long step=0;
			boolean modelstop=false;
			public void run() {
				try {
					//long currentSteps = 0;
					/*if (!Thread.currentThread().isInterrupted() && !getShouldStopRunThread())
                        try  // it's possible we could be interrupted in-between here (see killPlayThread)
                            {
                            // important here that we're not synchronized on schedule -- because
                            // killPlayThread blocks on schedule before interrupting for JMF bug
                            SwingUtilities.invokeAndWait(blocker);
                            }                    
                        catch (InterruptedException e)
                            {
                            try
                                {
                                Thread.currentThread().interrupt();
                                }
                            catch (SecurityException ex) { } // some stupid browsers -- *cough* IE -- don't like interrupts
                            }                    
                        catch (java.lang.reflect.InvocationTargetException e)
                            {
                            System.err.println("This should never happen: " + e);
                            }                    
                        catch (Exception e)
                            {
                            e.printStackTrace();
                            }*/
					while(true){
						if (getShouldStopRunThread())
							break;
						//t1=System.currentTimeMillis();
						
						modelstop=model.step(step);
						step++;
						//t=t+(System.currentTimeMillis()-t1);
						//System.out.println("Tiempo= "+(System.currentTimeMillis()-t1));
						//s = model.scheduler.getSteps();
						//System.out.println("Pasos= "+s);
						/*if (!Thread.currentThread().isInterrupted() && !getShouldStopRunThread())
	                        try  // it's possible we could be interrupted in-between here (see killPlayThread)
	                            {
	                            // important here that we're not synchronized on schedule -- because
	                            // killPlayThread blocks on schedule before interrupting for JMF bug
	                            SwingUtilities.invokeAndWait(blocker);
	                            }                    
	                        catch (InterruptedException e)
	                            {
	                            try
	                                {
	                                Thread.currentThread().interrupt();
	                                }
	                            catch (SecurityException ex) { } // some stupid browsers -- *cough* IE -- don't like interrupts
	                            }                    
	                        catch (java.lang.reflect.InvocationTargetException e)
	                            {
	                            System.err.println("This should never happen: " + e);
	                            }                    
	                        catch (Exception e)
	                            {
	                            e.printStackTrace();
	                            }*/
						if (getShouldStopRunThread() || step >= getWhenShouldEnd() || modelstop) {
							model.stop();
                            break;
						}
						long sleep = getRunSleep();
                        //if (sleep==0 && getShouldYield())
                        //    sleep = 1;
                        if (sleep > 0 && !Thread.currentThread().isInterrupted() && !getShouldStopRunThread())
                            try  // it's possible we could be interrupted in-between here (see killPlayThread)
                                {
                                Thread.sleep(sleep);
                                }                        
                            catch (InterruptedException e)
                                {
                                try
                                    {
                                    Thread.currentThread().interrupt();
                                    }
                                catch (SecurityException ex) { } // some stupid browsers -- *cough* IE -- don't like interrupts
                                }                        
                            catch (Exception e)
                                {
                                e.printStackTrace();
                                }
					}
					//System.out.println("Promedio= "+t+" Pasos= "+s);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}

		};
		runThread = new Thread(run);
		runThread.start();
	}
	
        
    public void setRunSleep(final long sleep)
        {
        synchronized (runThreadLock)
            {
            runSleep = sleep;
            }
        }

    public long getRunSleep()
        {
        synchronized (runThreadLock)
            {
            return runSleep;
            }
        }
	
	 private long getWhenShouldEnd()
     {
     synchronized (runThreadLock)
         {
         return maxStepsToEnd;
         }
     }

	private boolean getShouldStopRunThread() {
		synchronized (runThreadLock)
        {
        return runThreadShouldStop;
        }
	}
        
    private void setShouldStopRunThread(final boolean stop) {
        synchronized (runThreadLock)
            {
            runThreadShouldStop = stop;
            }
    }
    
    private void stopRunThread() {
    	setShouldStopRunThread(true);
    	try {
    		if (runThread != null) {
    		do {
    			runThread.interrupt();
    			runThread.join(50);
            }
    		while(runThread.isAlive());
    		runThread = null;
            }
        } 
    	catch (InterruptedException e) {
    		System.err.println(e);
        }
    }

    public static BioMASSModel getInstance() {
    	return model;
    }
    
}

