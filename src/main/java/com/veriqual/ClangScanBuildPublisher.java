package com.veriqual;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.kohsuke.stapler.DataBoundConstructor;

public class ClangScanBuildPublisher extends Recorder{
	
	public final String pattern;	
	private static final Logger LOGGER = Logger.getLogger( ClangScanBuildPublisher.class.getName() );
	
	@Extension
	public static final ClangScanBuildPublisherDescriptor DESCRIPTOR = new ClangScanBuildPublisherDescriptor();

	@DataBoundConstructor
	public ClangScanBuildPublisher(String pattern){
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
	public ClangScanBuildPublisherDescriptor getDescriptor() {
		return DESCRIPTOR;
	}
	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	@Override
	public boolean perform( AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener ) throws InterruptedException, IOException {

		listener.getLogger().println( "Publishing files to Reports folder" );
		
		FilePath sourceDir = ClangScanBuildUtils.locateClangScanBuildReportFolder(build);
		FilePath destDir = new FilePath(build.getWorkspace(), ClangScanBuildUtils.REPORT_OUTPUT_FOLDERNAME + "_" + Calendar.getInstance().getTime());
		
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
			listener.fatalError( "Unable to copy Checkstyle reports to CheckStyleReports." );
		}
	}

}
