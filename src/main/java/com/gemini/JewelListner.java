package com.gemini;


import com.gemini.reporting.GemTestReporter;
import com.gemini.reporting.STATUS;
import com.gemini.utils.GemJarConstants;
import com.gemini.utils.GemJarGlobalVar;
import net.thucydides.core.model.*;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;

import java.security.PublicKey;
import java.util.*;

import static com.gemini.utils.GemJarUtils.initializeGemJARGlobalVariables;

public class JewelListner implements StepListener {

//    @Inject
 // private LogStorage logStorage;
//    private SuiteStorage suiteStorage;

    public JewelListner() {
      //  System.out.println("Inisde Jewel Listner  ");
        //IntegrationInjector.getInjector().injectMembers(this);
    }

    public void testSuiteStarted(Class<?> storyClass) {

     //   System.out.println("This only comes once ");
        //System.out.println("THis runs first Tst Suite started with Story class ");
        //System.out.println("In the test suite started ");
        // Not used by listener
    }
    //Map<String,Integer> featureStore = new HashMap<String,Integer>();
    //int counter = 0;

    public static String sRunID = UUID.randomUUID().toString();
    public static boolean suiteRunFlag = true;
    public void testSuiteStarted(Story story) {

      //  System.out.println("This runs first testSuite started with only story");
        try{
           initializeGemJARGlobalVariables();
        }
        catch (Exception e){
            System.out.println("Exception in loading values ");
        }
        GemJarGlobalVar.s_run_id=sRunID;
        if(suiteRunFlag==true){
          //  System.out.println("Inside Flag Check");
            GemTestReporter.startSuite(GemJarGlobalVar.projectName,GemJarGlobalVar.environment,GemJarGlobalVar.s_run_id);
            suiteRunFlag=false;
        }
      //  GemJarGlobalVar.s_run_id = GemJarGlobalVar.projectName+GemJarGlobalVar.environment+ UUID.randomUUID().toString();
       // GemJarGlobalVar.s_run_id = GemJarGlobalVar.projectName+"_"+GemJarGlobalVar.environment+"_"+sRunID;
     //   System.out.println("Count Occurance of Test Suite Started ");
       // System.out.println("Test Suite Started ");
        // Not used by listener
    }

    public void testSuiteFinished() {
        GemTestReporter.endSuite();
        System.out.println("Report Published to Jewel");
    }

    public void testStarted(String description) {

      //  System.out.println("Test Started " + description );
        // Not used by listener
    }

    public void testStarted(String description, String id) {

      GemTestReporter.startTestCase(description.toString(),"On Demand",false);
      //  System.out.println("Test Started + ID  " + description );
        // Not used by listener
    }

    public void testFinished(TestOutcome result) {

        List<TestStep> testSteps = new ArrayList<>();
        testSteps = result.getTestSteps().get(0).getChildren();
        TestStep test  = new TestStep();
      //for each test step get the steps
        for (TestStep testStep : testSteps) {
           List<TestStep> serenitySteps =  testStep.getChildren();
           STATUS status = getStatus(testStep);
            String stepDescription;
           if(testStep.getException()!=null || !Objects.equals(testStep.getErrorMessage(), ""))
               stepDescription = testStep.getErrorMessage();
           else
             stepDescription = testStep.getChildren().toString().substring(1,testStep.getChildren().toString().length()-1);
          //add code for screenshot count and push the screenshot
           GemTestReporter.addTestStep(testStep.getDescription(),stepDescription ,getStatus(testStep),testStep.getLastScreenshot().getScreenshot().toString());
        }
        GemTestReporter.endTestCase();
       // System.out.println(result.getResult());
        //System.out.println("test finished");
        //System.out.println(result.toString());
       // TestRecorder recorder = TestRecorder.forTest(result);
        //recorder.record(result);
       // logStorage.clean();
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

    public void testRetried() {
        // Not used by listener
    }

    public void stepStarted(ExecutedStepDescription description) {
    //    System.out.println("Inside Step Started : " + description.getName());
        // Not used by listener
    }

    public void skippedStepStarted(ExecutedStepDescription description) {
        // Not used by listener
    }

    public void stepFailed(StepFailure failure) {
      //  System.out.println("Step Failed ");
    //    System.out.println(failure.getDescription().toString());
       // harvestDriverLogs();
    }

    public void lastStepFailed(StepFailure failure) {
     //   System.out.println("Inside last Step Failure ");
    //    System.out.println(failure.getDescription().toString());
        // Not used by listener
    }

    public void stepIgnored() {
        // Not used by listener
    }

    public void stepPending() {
        // Not used by listener
    }

    public void stepPending(String message) {
        // Not used by listener
    }

    public void stepFinished() {

   //     System.out.println("Inside Step Finsihed");
       // harvestDriverLogs();
    }

    public void testFailed(TestOutcome testOutcome, Throwable cause) {
   //     System.out.println("Inside Test failed");
     //   System.out.println(testOutcome.toString());
        // Not used by listener
    }

    public void testIgnored() {
        // Not used by listener
    }

    public void testSkipped() {
        // Not used by listener
    }

    public void testPending() {
        // Not used by listener
    }

    public void testIsManual() {
        // Not used by listener
    }

    public void notifyScreenChange() {
        // Not used by listener
    }

    public void useExamplesFrom(DataTable table) {
        // Not used by listener
    }

    public void addNewExamplesFrom(DataTable table) {
        // Not used by listener
    }

    public void exampleStarted(Map<String, String> data) {
        // Not used by listener
    }

    public void exampleFinished() {
        // Not used by listener
    }

    public void assumptionViolated(String message) {
        // Not used by listener
    }

    public void testRunFinished() {
     //   System.out.println("Test Run Ended");
        // Not used by listener
    }

//    private void harvestDriverLogs() {
//        boolean harvestLogs = ReportIntegrationConfig.get().harvestSeleniumLogs;
//        if (harvestLogs && ThucydidesWebDriverSupport.isDriverInstantiated()) {
//            Logs logs = ThucydidesWebDriverSupport.getDriver().manage().logs();
//            logStorage.collect(logs);
//        }
//    }
}
