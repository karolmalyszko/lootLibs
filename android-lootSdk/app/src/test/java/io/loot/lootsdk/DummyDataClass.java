package io.loot.lootsdk;

public class DummyDataClass {

    private String testString;

    public DummyDataClass() {
        this.testString = "test";
    }

    public DummyDataClass(String testString) {
        this.testString = testString;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}