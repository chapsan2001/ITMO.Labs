package com.lab.client.gui.musicBandCanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/** Class is responsible for drawing and movement of the objects; */
public abstract class Shape {
  protected double x;
  protected double y;
  protected double w;
  protected double h;
  protected double vel;
  protected double posX;
  protected double posY;

  /**
   * Constructor takes parameters needed for drawing and movement.
   *
   * @param x position by x.
   * @param y position by y.
   * @param w width.
   * @param h height.
   * @param vel velocity.
   */
  protected Shape(double x, double y, double w, double h, double vel) {
    this.x = x;
    this.posX = x;

    this.y = y;
    this.posY = y;

    this.w = w;
    this.h = h;
    this.vel = vel;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public boolean containsRect(double x, double y) {
    return getRectangle().contains(x, y);
  }

  public boolean containsCircle(double x, double y) {
    return getCircle().contains(x, y);
  }

  protected Rectangle getRectangle() {
    return new Rectangle(x, y, w, h);
  }

  protected Ellipse getCircle() {
    return new Ellipse(x, y, w, h);
  }

  /** Responsible for movement of the object. Can be overridden. */
  public void move() {
    if (x < w || x > 500 - w) {
      vel = -vel;
    }

    x += vel;

    if (y < h || y > 500 - h) {
      vel = -vel;
    }

    y += vel;
  }

  /**
   * Responsible for drawing of the object. Must be overridden.
   *
   * @param gc graphics object.
   */
  public abstract void draw(GraphicsContext gc);

  protected final void drawRect(GraphicsContext gc, Color color) {
    gc.setFill(color);
    gc.fillRect(x, y, w, h);
    gc.fill();
  }

  protected final void drawCircle(GraphicsContext gc, Color color) {
    gc.setFill(color);
    gc.fillOval(x, y, w, h);
    gc.fill();
  }
}
