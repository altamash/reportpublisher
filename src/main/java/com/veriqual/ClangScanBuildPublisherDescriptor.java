package com.veriqual;

import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;


public class ClangScanBuildPublisherDescriptor extends BuildStepDescriptor<Publisher>{

	public ClangScanBuildPublisherDescriptor(){
		super( ClangScanBuildPublisher.class );
		load();
	}
	
	@Override
	public Publisher newInstance(StaplerRequest arg0, JSONObject json ) throws hudson.model.Descriptor.FormException {

		return new ClangScanBuildPublisher();
	}
	
	@Override
	public String getDisplayName() {
		return "Publish Checkstyle reports to CheckStyleReports folder";
	}

	@Override
	public boolean isApplicable( @SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType ){
		if( !FreeStyleProject.class.isAssignableFrom( jobType ) ){
			System.err.println( "Clang scan-build ERROR: Expected FreeStyleProject but was: " + jobType + " at Publisher Descriptor" );
		}
		return FreeStyleProject.class.isAssignableFrom( jobType );
	}

}
