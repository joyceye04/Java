/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BreakoutYJ extends GraphicsProgram {

/** Width and height of application window in pixels.  On some platforms 
  * these may NOT actually be the dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board.  On some platforms these may NOT actually
  * be the dimensions of the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

	private GRect paddle;
//	private double lastX;
	private double vx,vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GOval ball;
	private int count = NBRICKS_PER_ROW * NBRICK_ROWS;
	private boolean win = false;
	private int turn = 0;
	private static final int DELAY = 30;

/* Method: run() */
/** Runs the Breakout program. */
	
	public void run() {
		bricks();
		createPaddle();
		createBall();
		addMouseListeners();
		play();
	}
	private void bricks(){
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=0;j<2;j++){
				GRect rect = new GRect((BRICK_SEP+BRICK_WIDTH)*i,BRICK_Y_OFFSET+(BRICK_HEIGHT+BRICK_SEP)*j,BRICK_WIDTH,BRICK_HEIGHT);//position+size
				rect.setColor(Color.RED);
				rect.setFilled(true);
				rect.setFillColor(Color.RED);
				add(rect);
			}
		}
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=2;j<4;j++){
				GRect rect = new GRect((BRICK_SEP+BRICK_WIDTH)*i,BRICK_Y_OFFSET+(BRICK_HEIGHT+BRICK_SEP)*j,BRICK_WIDTH,BRICK_HEIGHT);//position+size
				rect.setColor(Color.ORANGE);
				rect.setFilled(true);
				rect.setFillColor(Color.ORANGE);
				add(rect);
			}
		}
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=4;j<6;j++){
				GRect rect = new GRect((BRICK_SEP+BRICK_WIDTH)*i,BRICK_Y_OFFSET+(BRICK_HEIGHT+BRICK_SEP)*j,BRICK_WIDTH,BRICK_HEIGHT);//position+size
				rect.setColor(Color.YELLOW);
				rect.setFilled(true);
				rect.setFillColor(Color.YELLOW);
				add(rect);
			}
		}
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=6;j<8;j++){
				GRect rect = new GRect((BRICK_SEP+BRICK_WIDTH)*i,BRICK_Y_OFFSET+(BRICK_HEIGHT+BRICK_SEP)*j,BRICK_WIDTH,BRICK_HEIGHT);//position+size
				rect.setColor(Color.GREEN);
				rect.setFilled(true);
				rect.setFillColor(Color.GREEN);
				add(rect);
			}
		}
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=8;j<10;j++){
				GRect rect = new GRect((BRICK_SEP+BRICK_WIDTH)*i,BRICK_Y_OFFSET+(BRICK_HEIGHT+BRICK_SEP)*j,BRICK_WIDTH,BRICK_HEIGHT);//position+size
				rect.setColor(Color.CYAN);
				rect.setFilled(true);
				rect.setFillColor(Color.CYAN);
				add(rect);
			}
		}
	}
	
	private void createPaddle() {
		paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - PADDLE_HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}
	
	public void mouseMoved(MouseEvent e){
		if(e.getX()> WIDTH - paddle.getWidth()){
			paddle.move(WIDTH-paddle.getX()-paddle.getWidth(), 0);
		} 
		else{
			paddle.move(e.getX()-paddle.getX(),0);
		}
	}

	private void createBall() {
		ball = new GOval(WIDTH/2-BALL_RADIUS, HEIGHT/2-BALL_RADIUS, BALL_RADIUS*2, BALL_RADIUS*2);
		ball.setFilled(true);
		add(ball);
	}
	
	private void initiateVel(){
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) 
			vx = -vx;
		vy = 3;
	}
	
	private void play() {
		
		initiateVel();
		
		while (!gameOver()) {
			ball.move(vx, vy);
			if ((ball.getX() <= 0) || (ball.getX() >= WIDTH - BALL_RADIUS*2))
				vx = -vx;
			if(ball.getY() <= 0) 
				vy = -vy;
			GObject collider = getCollidingObject(ball.getX(), ball.getY(), BALL_RADIUS);
			if(collider!=null){
				vy=-vy;
				if(collider!=paddle){
					remove(collider);
					count--;
				}
			}
			pause(DELAY);
		}
		turn++;
	}
	
	private boolean gameOver() {	
		if (count == 0) {
			win = true;
			JOptionPane.showMessageDialog(null,"BIG CONG U WIN");
			return true;
		} 
		else if (ball.getY() >= HEIGHT - BALL_RADIUS*2) {
			JOptionPane.showMessageDialog(null,"GAME OVER");
			return true;
		} 
		else {
			return false;
		}				
	}
	
	private GObject getCollidingObject(double x, double y, double r) {
		if (getElementAt(x, y) != null) {
			return getElementAt(x, y);
		} 
		else if (getElementAt(x+2*r, y) != null) {
			return getElementAt(x+2*r, y);
		} 
		else if (getElementAt(x, y+2*r) != null) {
			return getElementAt(x, y+2*r);
		} 
		else if (getElementAt(x+2*r, y+2*r) != null) {
			return getElementAt(x+2*r, y+2*r);
		} 
		else {
			return null;
		}
	}
}
