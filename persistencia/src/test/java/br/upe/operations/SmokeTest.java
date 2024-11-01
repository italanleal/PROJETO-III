package br.upe.operations;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Smoke Test Suite")
@SelectClasses({EventCRUDTest.class})
public class SmokeTest {
}
