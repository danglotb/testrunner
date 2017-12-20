package fr.inria.stamp.runner.coverage;

import fr.inria.stamp.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 19/12/17
 */
public class JacocoRunnerTest extends AbstractTest {

    @Test
    public void testWithoutNewJvmOnTestClass() throws Exception {

        /*
            Using the api to compute the coverage on a test class
         */

        JacocoRunner.main(new String[]{
                        TEST_PROJECT_CLASSES, "example.TestSuiteExample"
                }
        );
        final CoverageListener load = CoverageListener.load();
        assertEquals(141, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionCovered)
                .reduce(0, (integer, instructionCovered) -> integer + instructionCovered).intValue());
        assertEquals(204, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionTotal)
                .reduce(0, (integer, instructionTotal) -> integer + instructionTotal).intValue());
    }

    @Test
    public void testWithoutNewJvmOnTestCases() throws Exception {

        /*
            Using the api to compute the coverage on test cases
         */

        JacocoRunner.main(new String[]{
                        TEST_PROJECT_CLASSES,
                        "example.TestSuiteExample",
                        "test8:test2"
                }
        );
        final CoverageListener load = CoverageListener.load();
        assertEquals(46, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionCovered)
                .reduce(0, (integer, instructionCovered) -> integer + instructionCovered).intValue());
        assertEquals(68, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionTotal)
                .reduce(0, (integer, instructionTotal) -> integer + instructionTotal).intValue());
    }

    @Test
    public void testExecutionOnTestClass() {

        /*
            Launch a new process to compute the coverage on the test class
         */

        Process p;
        try {
            p = Runtime.getRuntime().exec(commandLine);
            p.waitFor();
            assertEquals(0, p.exitValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final CoverageListener load = CoverageListener.load();
        assertEquals(141, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionCovered)
                .reduce(0, (integer, instructionCovered) -> integer + instructionCovered).intValue());
        assertEquals(204, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionTotal)
                .reduce(0, (integer, instructionTotal) -> integer + instructionTotal).intValue());
    }

    @Test
    public void testExecutionTestCases() throws Exception {

         /*
            Launch a new process to compute the coverage on the test class
         */

        Process p;
        try {
            p = Runtime.getRuntime().exec(commandLine + " test8:test3");
            p.waitFor();
            assertEquals(0, p.exitValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final CoverageListener load = CoverageListener.load();
        assertEquals(46, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionCovered)
                .reduce(0, (integer, instructionCovered) -> integer + instructionCovered).intValue());
        assertEquals(68, load.getInstructionsCoveragePerLinePerTestCasesName().keySet()
                .stream()
                .flatMap(methodName -> load.getInstructionsCoveragePerLinePerTestCasesName().get(methodName).stream())
                .map(coverage -> coverage.instructionTotal)
                .reduce(0, (integer, instructionTotal) -> integer + instructionTotal).intValue());
    }

    private final String classpath = MAVEN_HOME + "org/jacoco/org.jacoco.core/0.7.9/org.jacoco.core-0.7.9.jar:" +
            MAVEN_HOME + "org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar:" +
            MAVEN_HOME + "commons-io/commons-io/2.5/commons-io-2.5.jar:" +
            JUNIT_CP;

    private final String commandLine = "java -cp " +
            classpath + ":" + TEST_PROJECT_CLASSES + ":" + PATH_TO_RUNNER_CLASSES +
            " fr.inria.stamp.runner.coverage.JacocoRunner " +
            TEST_PROJECT_CLASSES +
            " example.TestSuiteExample";
}
