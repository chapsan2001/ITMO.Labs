package com.lab.client.gui.musicBandCanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MusicBandCircle extends Shape {
  public MusicBandCircle() {
    super(200, 200, 200, 200, 1);
  }

  @Override
  public void move() {
    if (x > 400) {
      x = -200;
    }

    x += vel;
  }

  @Override
  public void draw(GraphicsContext gc) {
    drawCircle(gc, Color.GREEN);
  }
}
