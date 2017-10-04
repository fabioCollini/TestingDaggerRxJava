package it.droidcon.testingdaggerrxjava;

public class ObjectUnderTest {

    private Collaborator1 collaborator1;

    private Collaborator2 collaborator2;

    public ObjectUnderTest(Collaborator1 collaborator1, Collaborator2 collaborator2) {
        this.collaborator1 = collaborator1;
        this.collaborator2 = collaborator2;
    }

    private Integer value;

    public void execute() {
        value = collaborator1.provideValue() * 5;
        collaborator2.printValue(value);
    }

    public Integer getValue() {
        return value;
    }
}
