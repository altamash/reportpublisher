package com.icsnett.hudson;

import hudson.FilePath;
import hudson.model.AbstractBuild;


public class ICSReportPublisherUtils{
	
	public static final String REPORT_OUTPUT_FOLDERNAME = "Reports/";
	
//	public static String getIconsPath(){
//		return "/plugin/" + PluginImpl.SHORTNAME + "/icons/";
//	}
	
//	public static String getTransparentImagePath(){
//		return "/plugin/" + PluginImpl.SHORTNAME + "/transparent.png";
//	}
	
	public static FilePath locateReportSourceFolder( AbstractBuild<?,?> build ){
		if( build == null ) return null;
		return build.getWorkspace();
	}
	
}
