package com.gemini.plugin;

import com.gemini.reporting.GemEcoUpload;
import com.gemini.utils.GemJarConstants;
import com.gemini.utils.GemJarGlobalVar;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.IOException;

import static com.gemini.utils.GemJarConstants.*;
import static com.gemini.utils.GemJarUtils.*;

public class SerenityPluginUtils {

    public static void initalizeGemJarGlobalVariablesForSerenityPlugin() throws IOException{
        EnvironmentVariables properties = SystemEnvironmentVariables.createEnvironmentVariables();
        GemJarGlobalVar.projectName = properties.getProperty(PROJECT_NAME)!=null ? properties.getProperty(PROJECT_NAME) : properties.getProperty("Jewel."+PROJECT_NAME);
        GemJarGlobalVar.environment = properties.getProperty(ENVIRONMENT)!=null ? properties.getProperty(ENVIRONMENT) : properties.getProperty("Jewel."+ENVIRONMENT);
        GemJarGlobalVar.s_run_id = getS_Run_id();
        GemJarGlobalVar.reportName = properties.getProperty(REPORT_NAME)!=null ? properties.getProperty(REPORT_NAME) : properties.getProperty("Jewel." + REPORT_LOCATION);
        System.setProperty(GemJarConstants.GEMJAR_USER_NAME,properties.getProperty(GEMJAR_USER_NAME)!=null ?properties.getProperty(GEMJAR_USER_NAME):properties.getProperty("Jewel."+GEMJAR_USER_NAME));
        System.setProperty(GemJarConstants.GEMJAR_REPORTING_TOKEN,properties.getProperty(GEMJAR_REPORTING_TOKEN)!=null ?properties.getProperty(GEMJAR_REPORTING_TOKEN) : properties.getProperty("Jewel."+GEMJAR_REPORTING_TOKEN));
        System.setProperty(GemJarConstants.JEWEL_ENTRY_URL,properties.getProperty(JEWEL_ENTRY_URL)!=null ?  properties.getProperty(JEWEL_ENTRY_URL) : properties.getProperty("Jewel."+JEWEL_ENTRY_URL));
        System.setProperty(GemJarConstants.GEMJAR_SUBSCRIPTION_ID,properties.getProperty(GEMJAR_SUBSCRIPTION_ID)!=null ? properties.getProperty(GEMJAR_SUBSCRIPTION_ID) : properties.getProperty("Jewel."+GEMJAR_SUBSCRIPTION_ID));
        GemEcoUpload.setUpJewelURLs();
        GemJarGlobalVar.reportName = properties.getProperty(REPORT_NAME)!=null ? properties.getProperty(REPORT_NAME) : properties.getProperty("Jewel." + REPORT_LOCATION);
    }
}
