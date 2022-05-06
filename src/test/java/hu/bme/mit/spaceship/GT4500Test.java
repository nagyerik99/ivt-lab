package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTorpedoStore;
  private TorpedoStore mockTorpedoStore2;

  @BeforeEach
  public void init(){
    mockTorpedoStore = mock(TorpedoStore.class);
    mockTorpedoStore2 = mock(TorpedoStore.class);

    this.ship = new GT4500(mockTorpedoStore,mockTorpedoStore2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    verify(mockTorpedoStore).isEmpty();
    verify(mockTorpedoStore).fire(1);
    verify(mockTorpedoStore2,times(0)).isEmpty();
    verify(mockTorpedoStore2,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockTorpedoStore2.getTorpedoCount()).thenReturn(10);
    when(mockTorpedoStore.fire(1)).thenReturn(true);
    when(mockTorpedoStore2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTorpedoStore,times(1)).fire(1);
    verify(mockTorpedoStore2,times(1)).fire(1);
  }

 @Test
 public void fireTorpedo2Times_Success(){
  when(mockTorpedoStore.getTorpedoCount()).thenReturn(10);
  when(mockTorpedoStore2.getTorpedoCount()).thenReturn(10);
  when(mockTorpedoStore.fire(1)).thenReturn(true);
  when(mockTorpedoStore2.fire(1)).thenReturn(true);

  var res1 = ship.fireTorpedo(FiringMode.SINGLE);
  var res2 = ship.fireTorpedo(FiringMode.SINGLE);

  //Assert
  verify(mockTorpedoStore,times(1)).isEmpty();
  verify(mockTorpedoStore2,times(1)).isEmpty();
  verify(mockTorpedoStore,times(1)).fire(1);
  verify(mockTorpedoStore2,times(1)).fire(1);
 } 

@Test
public void fireFirstFailSecondSuccess(){
  when(mockTorpedoStore.isEmpty()).thenReturn(true);
  when(mockTorpedoStore2.isEmpty()).thenReturn(false);
  when(mockTorpedoStore2.fire(1)).thenReturn(true);

  ship.fireTorpedo(FiringMode.SINGLE);

  verify(mockTorpedoStore, times(1)).isEmpty();
  verify(mockTorpedoStore2,times(1)).isEmpty();
  verify(mockTorpedoStore2,times(1)).fire(1);

}

@Test
public void fireFirstFailSecondFail(){
  when(mockTorpedoStore.isEmpty()).thenReturn(true);
  when(mockTorpedoStore2.isEmpty()).thenReturn(true);
  when(mockTorpedoStore.fire(1)).thenReturn(false);
  when(mockTorpedoStore2.fire(1)).thenReturn(false);

  ship.fireTorpedo(FiringMode.SINGLE);

  verify(mockTorpedoStore, times(1)).isEmpty();
  verify(mockTorpedoStore2,times(1)).isEmpty();
  verify(mockTorpedoStore,times(0)).fire(1);
  verify(mockTorpedoStore2,times(0)).fire(1);

}

@Test
public void fireFirstFailSecondFail_All(){
  when(mockTorpedoStore.isEmpty()).thenReturn(true);
  when(mockTorpedoStore2.isEmpty()).thenReturn(true);
  when(mockTorpedoStore.fire(1)).thenReturn(false);
  when(mockTorpedoStore2.fire(1)).thenReturn(false);

  var result = ship.fireTorpedo(FiringMode.SINGLE);

  verify(mockTorpedoStore, times(1)).isEmpty();
  verify(mockTorpedoStore2,times(1)).isEmpty();
  verify(mockTorpedoStore,times(0)).fire(1);
  verify(mockTorpedoStore2,times(0)).fire(1);
  assertEquals(false, result);
}

@Test
public void fireFirstFail(){
  when(mockTorpedoStore.isEmpty()).thenReturn(false);
  when(mockTorpedoStore.fire(1)).thenReturn(false);

  var result = ship.fireTorpedo(FiringMode.SINGLE);

  verify(mockTorpedoStore, times(1)).isEmpty();
  verify(mockTorpedoStore2,times(0)).isEmpty();
  verify(mockTorpedoStore,times(1)).fire(1);
  verify(mockTorpedoStore2,times(0)).fire(1);
  assertEquals(false, result);
}

@Test
public void fireFirstThenSecondEmptyPrimaryAgain(){
  when(mockTorpedoStore.isEmpty()).thenReturn(false);
  when(mockTorpedoStore2.isEmpty()).thenReturn(true);
  when(mockTorpedoStore.isEmpty()).thenReturn(false);
  when(mockTorpedoStore.fire(1)).thenReturn(true);
  when(mockTorpedoStore2.fire(1)).thenReturn(false);

  var res1 = ship.fireTorpedo(FiringMode.SINGLE);
  var res2 = ship.fireTorpedo(FiringMode.SINGLE);

  //Assert
  verify(mockTorpedoStore,times(2)).isEmpty();
  verify(mockTorpedoStore2,times(1)).isEmpty();
  verify(mockTorpedoStore,times(2)).fire(1);
  verify(mockTorpedoStore2,times(0)).fire(1);
}



}
