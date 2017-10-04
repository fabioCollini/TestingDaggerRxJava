package it.droidcon.testingdaggerrxjava

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

class MyTest {
  @Mock val collaborator1: Collaborator1 = mock()

  @Mock val collaborator2: Collaborator2 = mock()

  @InjectMocks val objectUnderTest = ObjectUnderTest(collaborator1, collaborator2)

  @Test fun myTestMethod() {
    //Arrange
    whenever(collaborator1.provideValue()).thenReturn(2)
    //Act
    objectUnderTest.execute()
    //Assert
    verify(collaborator2).printValue(10)
    assertThat(objectUnderTest.value).isEqualTo(10)
  }
}