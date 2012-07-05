package com.icsnett.hudson.publisher;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.kohsuke.stapler.DataBoundConstructor;

import com.icsnett.hudson.ICSReportPublisherUtils;

public class ICSReportPublisher extends Recorder{
	
	public final String pattern;	
	private static final Logger LOGGER = Logger.getLogger( ICSReportPublisher.class.getName() );
	
	@Extension
	public static final ICSReportPublisherDescriptor DESCRIPTOR = new ICSReportPublisherDescriptor();

	@DataBoundConstructor
	public ICSReportPublisher(String pattern){
		this.pattern = pattern;
		try {
            Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            // falls through 
        }
	}

//	@Override
//	public Action getProjectAction( AbstractProject<?, ?> project ){
//		return new ClangScanBuildProjectAction( project );
//	}

	@Override
	public ICSReportPublisherDescriptor getDescriptor() {
		return DESCRIPTOR;
	}
	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	@Override
	public boolean perform( AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener ) throws InterruptedException, IOException {

		listener.getLogger().println( "Publishing files to Reports folder" );
		
		FilePath sourceDir = ICSReportPublisherUtils.locateReportSourceFolder(build);
		FilePath destDir = new FilePath(build.getWorkspace(), ICSReportPublisherUtils.REPORT_OUTPUT_FOLDERNAME + "_" + Calendar.getInstance().getTime());
		
		copyReportsToBackup(sourceDir, destDir, listener);
		
		return true;
	}
	
	/**
	 * Copy Check Style reports to a folder
	 */
	private void copyReportsToBackup( FilePath source, FilePath dest, BuildListener listener ){
		try{
//			source.copyRecursiveTo( "**/target/checkstyle-result.xml", dest );
			source.copyRecursiveTo( pattern, dest );
		}catch( Exception e ){
			listener.fatalError( "Unable to copy reports to Reports." );
		}
	}

}
