package com.gemini;


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

import java.util.*;

import static com.gemini.utils.GemJarUtils.initializeGemJARGlobalVariables;

public  class JewelListner implements StepListener {

    public JewelListner() {}

    public static  String SRUNID = UUID.randomUUID().toString();
    private static final Logger logger = LogManager.getLogger(JewelListner.class);
    public static boolean suiteRunFlag = true;
  public static boolean initialiseGemJarGlobalVar = true;

    @Override
    public void testSuiteStarted(Class<?> aClass) {

    }

    public void testSuiteStarted(Story story) {
        {
            if(initialiseGemJarGlobalVar){
                try{
                    GemJarUtils.initializeGemJARGlobalVariables();
                }
                catch (Exception e){
                    logger.info("Exception In Loading Values");
                    logger.debug(e.getMessage());
                }
                finally {
                    if(initialiseGemJarGlobalVar)
                        initialiseGemJarGlobalVar=false;
                }
            }}

        GemJarGlobalVar.s_run_id=SRUNID;
        if(suiteRunFlag){
            GemTestReporter.startSuite(GemJarGlobalVar.projectName,GemJarGlobalVar.environment,GemJarGlobalVar.s_run_id);
            suiteRunFlag=false;
        }

    }

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

    public void testFinished(TestOutcome result) {

        try{
            //for each test step get the steps
            for (TestStep testStep : result.getTestSteps()) {

                String stepDescription;
                if(testStep.getException()!=null || !Objects.equals(testStep.getErrorMessage(), ""))
                    stepDescription = testStep.getErrorMessage();
                else {
                    stepDescription = testStep.getChildren().toString().substring(1, testStep.getChildren().toString().length() - 1);
                }
                System.out.println("");

                GemTestReporter.addTestStep(testStep.getDescription(),stepDescription ,getStatus(testStep),testStep.getLastScreenshot().getScreenshot().toString());

            }
            GemTestReporter.endTestCase();}
        catch (Exception e){
            logger.info("Error in End Test");

            logger.debug(e.getMessage());
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


    private STATUS getStatus(TestStep testStep) {
        TestResult result = testStep.getResult();

       String status =  testStep.getResult().toString();
       switch (result){
           case SUCCESS:return STATUS.PASS;
           case FAILURE:return STATUS.FAIL;
           case ERROR:return STATUS.ERR;
           case PENDING:return STATUS.EXE;
           case COMPROMISED:return STATUS.WARN;
           case SKIPPED:
           case UNDEFINED:
               return STATUS.INFO;
       }
       return STATUS.INFO;

    }
}
