///================================================
//  Kyle Russell
//  AUT University 2015
//  https://github.com/denkers/graphi-suic-plugin
//================================================

package com.graphi.suicideintent;

import com.graphi.app.Consts;
import java.io.File;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;


public class SuicideIntentConfig 
{
    public static final String CONFIG_FILE  =   "data/config.xml";
    private double directedWeight;
    private double undirectedWeight;
    private double selfWeight;
    private double deadWeight;
    private double killProb;

    public SuicideIntentConfig()
    {
        this(1.0, 0.5, 2.0, 0.1, 0.5);
    }
    
    public SuicideIntentConfig(double directedWeight, double undirectedWeight, double selfWeight, double deadWeight, double killProb)
    {
        this.directedWeight     =   directedWeight;
        this.undirectedWeight   =   undirectedWeight;
        this.selfWeight         =   selfWeight;
        this.deadWeight         =   deadWeight;
        this.killProb           =   killProb;
    }
    
    public static SuicideIntentConfig getConfig()
    {
        try
        {
            final String DIRECTORY      =   new File(SuicideIntentConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            File configFile             =   new File(DIRECTORY + "/" + Consts.GLOBAL_CONF_FILE);
            DocumentBuilder docBuilder  =   DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document configDoc          =   docBuilder.parse(configFile);
            double undirectedW          =   Double.parseDouble(configDoc.getElementsByTagName("directedWeight").item(0).getTextContent());
            double directedW            =   Double.parseDouble(configDoc.getElementsByTagName("undirectedWeight").item(0).getTextContent());
            double selfW                =   Double.parseDouble(configDoc.getElementsByTagName("selfWeight").item(0).getTextContent());
            double deadWeight           =   Double.parseDouble(configDoc.getElementsByTagName("deadWeight").item(0).getTextContent());
            double killProb             =   Double.parseDouble(configDoc.getElementsByTagName("killProbability").item(0).getTextContent());
            
            return new SuicideIntentConfig(directedW, undirectedW, selfW, deadWeight, killProb);
        }
        
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "[Error] Failed to read " + CONFIG_FILE);
            return new SuicideIntentConfig();
        }
    }
    
    public double getDirectedWeight() 
    {
        return directedWeight;
    }

    public double getUndirectedWeight() 
    {
        return undirectedWeight;
    }

    public double getSelfWeight() 
    {
        return selfWeight;
    }

    public double getDeadWeight()
    {
        return deadWeight;
    }

    public double getKillProb() 
    {
        return killProb;
    }
}
