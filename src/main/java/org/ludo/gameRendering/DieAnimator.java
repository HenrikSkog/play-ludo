package org.ludo.gameRendering;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.ludo.gameLogic.Die;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class DieAnimator {
  private Node dieBtn;
  private Text dieOutput;
  private int lastRoll;
  private int finalRoll;
  //TODO: dette er egentlig ikke bra nok ass

  public DieAnimator(Text dieOutput, Button dieBtn, int lastRoll, int finalRoll) {
    this.dieBtn = dieBtn;
    this.dieOutput = dieOutput;
    this.lastRoll = lastRoll;
    this.finalRoll = finalRoll;
  }

  public void animate(int time, int rotations, int numberFlashes, int finalResult) {
    RotateTransition rt = new RotateTransition(Duration.millis(time), dieBtn);
    rt.setByAngle(360 * rotations);
    rt.play();

    var timer = new Timer();

    var timerTask = new TimerTask() {
      private int count = 0;

      @Override
      public void run() {
        if (count == numberFlashes - 1) {
          dieOutput.setText(Integer.toString(finalResult));
          timer.cancel();
          timer.purge();
        } else {
          count++;

          int roll = newUniqueDieRoll(lastRoll);

          if (count == numberFlashes - 1)
//        then this is the last flash before the actual roll, get unique roll based on not equal to final roll
            dieOutput.setText(Integer.toString(newUniqueDieRoll(lastRoll, finalRoll)));
          else
            dieOutput.setText(Integer.toString(roll));
            lastRoll = roll;

        }
      }
    };
    timer.scheduleAtFixedRate(timerTask, 0, time / (numberFlashes - 1));
  }

  private int newUniqueDieRoll(int... rolls) {
      int roll = (int) (Math.random() * 6 + 1);
    for (int i:rolls) {
      if(i == roll)
        return newUniqueDieRoll(rolls);
    }
    return roll;
  }
}
