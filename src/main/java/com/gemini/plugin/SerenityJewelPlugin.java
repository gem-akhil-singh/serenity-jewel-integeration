package com.gemini.plugin;

import com.gemini.reporting.GemTestReporter;
import com.gemini.reporting.STATUS;
import com.gemini.utils.GemJarGlobalVar;
import com.gemini.utils.GemJarUtils;
import net.thucydides.core.model.*;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

//Serenity Jewel Plugin method implementation
public class SerenityJewelPlugin implements StepListener  {

    private static final Logger logger = LogManager.getLogger(SerenityJewelPlugin.class);
    public static final String SRUNID = UUID.randomUUID().toString();
    public static boolean suiteRunFlag = true;
    public static boolean initialiseGemJarGlobalVar = true;


    @Override
    public void testSuiteStarted(Class<?> aClass) {

    }

    //Method responsible for getting configuration data , and setting up suite run
    public void testSuiteStarted(Story story) {
        {
            if(initialiseGemJarGlobalVar){
                try{
                    SerenityPluginUtils.initalizeGemJarGlobalVariablesForSerenityPlugin();

                }
                catch (Exception e){
                    logger.info("Exception In Loading Configuration Values ");
                    logger.debug(e.getMessage());
                }
                finally {
                    if(initialiseGemJarGlobalVar)
                        initialiseGemJarGlobalVar=false;
                }
            }}

        GemJarGlobalVar.s_run_id=SRUNID;
        if(suiteRunFlag){
            //initialise Suite
            GemTestReporter.startSuite(GemJarGlobalVar.projectName,GemJarGlobalVar.environment,GemJarGlobalVar.s_run_id);
            suiteRunFlag=false;
        }

    }

    //method for ending suite
    public void testSuiteFinished() {
        try {
            GemTestReporter.endSuite();
        }
        catch (Exception e) {
            logger.info("Failed to finish test Suite");
            logger.debug(e.getMessage());
        }
    }

    @Override
    public void testStarted(String s) {

    }


    //method for test started and pushing in Jewel
    public void testStarted(String description, String id) {
        try{
            int separatorIdx = id.indexOf(";");
            String category = id.substring(0,separatorIdx);
            GemTestReporter.startTestCase(description,category,false);}
        catch (Exception e){
            logger.info("Error In Test Started");
            logger.debug(e.getMessage());
        }

    }



    //method for test finished , adding test steps and test case ended
    public void testFinished(TestOutcome result) {
        try{
            //for each test step get the steps
            List<? extends TestStep> step;
            if(result.getTestSteps().size() == 1){
                step = result.getTestSteps().get(0).getFlattenedSteps();
            }
            else{
                step = result.getTestSteps();
            }

            for (TestStep testStep : step) {

                String stepDescription;
                if(testStep.getException()!=null || !Objects.equals(testStep.getErrorMessage(), "")) //check for error message
                    stepDescription = testStep.getErrorMessage();
                else {
                    stepDescription = testStep.getChildren().toString().substring(1, testStep.getChildren().toString().length() - 1); //fetching  stepDescription
                    if(testStep.isFailure() || testStep.isError()){
                        stepDescription = stepDescription + " || Error : "+ testStep.getNestedException().getOriginalCause();
                    }
                }
                if(testStep.getLastScreenshot()!=null){
                    GemTestReporter.addTestStep(testStep.getDescription(),stepDescription ,getStatus(testStep),testStep.getLastScreenshot().getScreenshot().toString());
                }
                else {
                    GemTestReporter.addTestStep(testStep.getDescription(),stepDescription ,getStatus(testStep));
                }
            }
        }
        catch (Exception e){
            logger.info("Error in add Test Steps");
            logger.debug(e.getMessage());
        }finally {
            GemTestReporter.endTestCase();
        }

        //before Add test Step Codefor Multip Screenshots will be handled
    }

    @Override
    public void testRetried() {

    }

    @Override
    public void stepStarted(ExecutedStepDescription executedStepDescription) {


    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {

    }

    @Override
    public void stepFailed(StepFailure stepFailure) {

    }

    @Override
    public void lastStepFailed(StepFailure stepFailure) {

    }

    @Override
    public void stepIgnored() {

    }

    @Override
    public void stepPending() {

    }

    @Override
    public void stepPending(String s) {

    }

    @Override
    public void stepFinished() {

    }

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable throwable) {

    }

    @Override
    public void testIgnored() {

    }

    @Override
    public void testSkipped() {

    }

    @Override
    public void testPending() {

    }

    @Override
    public void testIsManual() {

    }

    @Override
    public void notifyScreenChange() {

    }

    @Override
    public void useExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void addNewExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void exampleStarted(Map<String, String> map) {

    }

    @Override
    public void exampleFinished() {

    }

    @Override
    public void assumptionViolated(String s) {

    }

    @Override
    public void testRunFinished() {

    }

    //method returning STATUS Jewel Supported
    private STATUS getStatus(TestStep testStep) {
        try{
            TestResult result = testStep.getResult();

            switch (result){
                case SUCCESS:return STATUS.PASS;
                case FAILURE:return STATUS.FAIL;
                case ERROR:return STATUS.ERR;
                case PENDING:return STATUS.EXE;
                case COMPROMISED:return STATUS.WARN;
                default:return STATUS.INFO;
            }
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
        return STATUS.INFO;


    }}
