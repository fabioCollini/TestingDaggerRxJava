package it.droidcon.testingdaggerrxjava;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyTestJava {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock Collaborator1 collaborator1;

    @Mock Collaborator2 collaborator2;

    @InjectMocks ObjectUnderTest objectUnderTest;

    @Test public void myTestMethod(){
        //Arrange 
        when(collaborator1.provideValue()).thenReturn(2);
        //Act 
        objectUnderTest.execute();
        //Assert 
        verify(collaborator2).printValue(10);
        assertThat(objectUnderTest.getValue()).isEqualTo(10);
    }
}