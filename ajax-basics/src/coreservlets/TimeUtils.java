package coreservlets;

public class TimeUtils {
  public static void pause(double seconds) {
    try {
      Thread.sleep((long)(seconds*1000));
    } catch(InterruptedException e) {}
  }
}
