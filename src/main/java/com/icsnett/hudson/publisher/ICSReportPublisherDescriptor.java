package com.icsnett.hudson.publisher;

import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;


public class ICSReportPublisherDescriptor extends BuildStepDescriptor<Publisher>{

	public ICSReportPublisherDescriptor(){
		super( ICSReportPublisher.class );
		load();
	}
	
//	@Override
//	public Publisher newInstance(StaplerRequest arg0, JSONObject json ) throws hudson.model.Descriptor.FormException {
//
//		return new ClangScanBuildPublisher();
//	}
	
	@Override
	public String getDisplayName() {
		return "Publish files to Reports folder";
	}

	@Override
	public boolean isApplicable( @SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType ){
		if( !FreeStyleProject.class.isAssignableFrom( jobType ) ){
			System.err.println( "ERROR: Expected FreeStyleProject but was: " + jobType + " at Publisher Descriptor" );
		}
		return FreeStyleProject.class.isAssignableFrom( jobType );
	}
	
	@Override
    public String getHelpFile() {
        return "/plugin/filespublisher/help.html";
    }

}
