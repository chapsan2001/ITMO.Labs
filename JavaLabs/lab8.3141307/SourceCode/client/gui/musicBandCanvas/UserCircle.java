package com.lab.client.gui.musicBandCanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UserCircle extends Shape {
  public UserCircle() {
    super(225, 10, 50, 50, 5);
  }

  @Override
  public void move() {
    if (y > 400) {
      y = -50;
    }

    y += vel;
  }

  @Override
  public void draw(GraphicsContext gc) {
    drawCircle(gc, Color.CYAN);
  }
}
