package com.lab.client.gui.musicBandCanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CoordinatesRect extends Shape {
  public CoordinatesRect(double x, double y) {
    super(x, y, 50, 50, 0.5);
  }

  @Override
  public void move() {
    // Do not move.
  }

  @Override
  public void draw(GraphicsContext gc) {
    drawRect(gc, Color.YELLOW);
  }
}
