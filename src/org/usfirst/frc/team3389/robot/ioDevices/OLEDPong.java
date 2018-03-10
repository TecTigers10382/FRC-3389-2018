/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/
/*
 * Ported to the NI RoboRIO and extended.

 * based off of github projects:
 *   @see https://github.com/mhudoba/Arduino-OLED-Pong
 *   @see https://github.com/ruction/arduino-pong
 * 
 * @author FRC Team 3389
 * 
 */

package org.usfirst.frc.team3389.robot.ioDevices;

import java.util.Random;

import org.usfirst.frc.team3389.robot.Robot;


public class OLEDPong {
	private static final int START = 1;
	private static final int UP_RIGHT = 2;
	private static final int UP_LEFT = 3;
	private static final int DOWN_RIGHT = 4;
	private static final int DOWN_LEFT = 5;
	private static final int UP_RIGHTS = 6;
	private static final int UP_LEFTS = 7;
	private static final int DOWN_RIGHTS = 8;
	private static final int DOWN_LEFTS = 9;

	private static final int RIGHT_WALL = 1;
	private static final int LEFT_WALL = 2;
	private static final int UPPER_WALL = 3;
	private static final int BOTTOM_WALL = 4;
	private static final int PADDLE_REND = 5;
	private static final int PADDLE_RMID = 6;
	private static final int PADDLE_LEND = 7;
	private static final int PADDLE_LMID = 8;

	private static final int PADDLE_XL = 0; // paddles are 2 pixels thick
	private static final int PADDLE_XR = 126;
	private static final int PADDLE_SIZE = 10;
	
	private static final int SPD = 50; // ball speed
	
	private int playerCount = 0;
	private int paddleL, paddleR; // only the Y position of a paddle will change
	private int scoreR, scoreL;
	private int movementVal;
	private int ballX, ballY;
	private int lastballX = 64, lastballY = 32;
	private int direct = START;
	private int lastDirection, rndDirection;
	private Random rndGenerator;
	private boolean done = false;
	
	private Thread thread = null;
	private boolean stopped = true;

	
	public OLEDPong(int players) { // players can be 0, 1, or 2
		playerCount = players;     // currently no user controls are available so zero is the only smart choice
		// set smallest font
		Robot.robotScreen.setFont(OLEDFont.FONT_5X8);
		// seed the randomizer to each game is different
		rndGenerator = new Random();
	}
	
	/**
	 * Starts the thread responsible to playing a single game of Pong
	 */
	public void start() {
		if (thread == null || !thread.isAlive()) {
			stopped = false;
			thread = new Thread(()->{ play();  });
			thread.start();
		}
	}

	/**
	 * Stops the thread responsible for playing a single game of Pong
	 * 
	 * @throws InterruptedException
	 *             if any thread has interrupted the current thread. The interrupted
	 *             status of the current thread is cleared when this exception is
	 *             thrown.
	 */
	public void stop() {
		done = true;
		stopped = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// not much to do since this is just an easter egg
		}
		thread = null;
		Robot.robotScreen.updateBitmap(OLEDBitmap.READY.getData(), OLEDBitmap.READY.getWidth(), OLEDBitmap.READY.getHeight(), 0, 0);
	}
	
	
	
	public void play() {
		// initialize new game
		paddleR = paddleL = (64 - PADDLE_SIZE) - 2;
		scoreR = scoreL = 0;
		direct = START;
		done = false;
		
		do {
			drawGame();
			updatePlayers();
			done = computeGame();
			// don't run the game a max speed
			/*
			try {
				Thread.sleep(1000/50); // approximately 50 frames per second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		} while (!done);
		drawGame(); // draw the final win
	}


	public boolean isRunning() {
		return !stopped;
	}
	

	public void quit() {
		stop();
	}
	
	private void assignDirection(int a, int b, int c, int d, int e, int f, int g, int h) {
		// the parameters represent the the various combinations of up/down left/right, 
		movementVal = 0;
		lastDirection = direct;
		lastballX = ballX;
		lastballY = ballY;
		if (lastDirection == a) {direct = b;}
		else if (lastDirection == c) {direct = d;}
		else if (lastDirection == e) {direct = f;}
		else if (lastDirection == g) {direct = h;}

		if (direct == START) {
			if (rndDirection == UP_RIGHT) {direct = DOWN_RIGHT;}
			else if (rndDirection == UP_LEFT) {direct = DOWN_LEFT;}
			else if (rndDirection == DOWN_RIGHT) {direct = UP_RIGHT;}
			else if (rndDirection == DOWN_LEFT) {direct = UP_LEFT;}
		}
	}

	private int map (int val, int fromMin, int fromMax, int toMin, int toMax) {
		return (int)((double)(val - fromMin) * (double)(toMax - toMin) / (double)(fromMax - fromMin) + toMin);
	}
	
	private void movement(int i, int num) {
		if (direct == START) {
			lastballX = 64;
			lastballY = 32;
		}
		if (i == UP_RIGHT) {
			ballX = lastballX + map(movementVal, 0, SPD, 0, 128);
			ballY = lastballY - map(movementVal, 0, SPD, 0, num);
		} else if (i == UP_LEFT) {
			ballX = lastballX - map(movementVal, 0, SPD, 0, 128);
			ballY = lastballY - map(movementVal, 0, SPD, 0, num);
		} else if (i == DOWN_RIGHT) {
			ballX = lastballX + map(movementVal, 0, SPD, 0, 128);
			ballY = lastballY + map(movementVal, 0, SPD, 0, num);
		} else if (i == DOWN_LEFT) {
			ballX = lastballX - map(movementVal, 0, SPD, 0, 128);
			ballY = lastballY + map(movementVal, 0, SPD, 0, num);
		}
	}

	
	private void checkCollision() {
		// paddles are ? pixels tall 
		// if the ball hits the top or bottom 30% of the paddle, it bounces more aggressively than it it hits in the middle
		int top = 0;
		int upper = (int)Math.round(PADDLE_SIZE * 0.30);
		int lower = (int)Math.round(PADDLE_SIZE * 0.70);
		int bottom = PADDLE_SIZE;


		if ((ballX >= PADDLE_XR-2) && ((ballY >= paddleR + top) && (ballY < paddleR + upper)))                             //Right Paddle TOP
		    assignDirection(UP_RIGHT, UP_LEFT, UP_RIGHTS, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, DOWN_RIGHTS, DOWN_LEFT);
		else if ((ballX >= PADDLE_XR-2) && ((ballY >= paddleR + (upper) && (ballY <= paddleR + lower))))                   //Right Paddle MIDDLE
		    assignDirection(UP_RIGHT, UP_LEFTS, UP_RIGHTS, UP_LEFTS, DOWN_RIGHT, DOWN_LEFTS, DOWN_RIGHTS, DOWN_LEFTS);
		else if ((ballX >= PADDLE_XR-2) && ((ballY > lower) && (ballY <= bottom)))                                         //Right Paddle BOTTOM
		    assignDirection(UP_RIGHT, UP_LEFT, UP_RIGHTS, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, DOWN_RIGHTS, DOWN_LEFT);
		else if ((ballX <= PADDLE_XL+2) && ((ballY >= paddleL + top) && (ballY < paddleL + upper)))                        //Left Paddle TOP
		    assignDirection(UP_LEFT, UP_RIGHT, UP_LEFTS, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, DOWN_LEFTS, DOWN_RIGHT);
		else if ((ballX <= PADDLE_XL+2) && ((ballY >= paddleL + upper) && (ballY <= paddleL + lower)))                     //Left Paddle TOP-MIDDLE
		    assignDirection(UP_LEFT, UP_RIGHTS, UP_LEFTS, UP_RIGHTS, DOWN_LEFT, DOWN_RIGHTS, DOWN_LEFTS, DOWN_RIGHTS);
		else if ((ballX <= PADDLE_XL+2) && ((ballY > paddleL + lower) && (ballY <= paddleL + bottom)))                     //Left Paddle BOTTOM
			assignDirection(UP_LEFT, UP_RIGHT, UP_LEFTS, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, DOWN_LEFTS, DOWN_RIGHT);
		else if (ballX >= 127) {
			rndDirection = rndGenerator.nextInt(4) + 2; // generate a number 2,3,4,5
			direct = START;
			movementVal = 0;
			lastballX = ballX;
			lastballY = ballY;
			ballX = 64;
			ballY = 32;
			scoreL++;
		} else if (ballX <= 2) { //beyond Left wall
			rndDirection = rndGenerator.nextInt(4) + 2; // generate a number between 2,3,4,5
			direct = START;
			movementVal = 0;
			lastballX = ballX;
			lastballY = ballY;
			ballX = 64;
			ballY = 32;
			scoreR++;
		} else if (ballY <= 2) { //Upper wall
			assignDirection(UP_RIGHT, DOWN_RIGHT, UP_LEFT, DOWN_LEFT, UP_RIGHTS, DOWN_RIGHTS, UP_LEFTS, DOWN_LEFTS);
		} else if (ballY >= 62) { //Bottom wall
			assignDirection(DOWN_RIGHT, UP_RIGHT, DOWN_LEFT, UP_LEFT, DOWN_RIGHTS, UP_RIGHTS, DOWN_LEFTS, UP_LEFTS);
		}
	}

	
	private void drawGame() {
		Robot.robotScreen.clear();
		Robot.robotScreen.drawLine(64, 0, 64, 64, 4);         // draw court net in middle
		Robot.robotScreen.drawRect(PADDLE_XL, paddleL, 1, PADDLE_SIZE); // player1 paddle on left
		Robot.robotScreen.drawRect(PADDLE_XR, paddleR, 1, PADDLE_SIZE); // player2 paddle on right
		Robot.robotScreen.drawString(String.format("%02d", scoreL), 30, 0);
		Robot.robotScreen.drawString(String.format("%02d", scoreR), 94, 0);
		if (scoreL >= 10)
			Robot.robotScreen.drawString("WIN", 30, 0);
		if (scoreR >= 10)
			Robot.robotScreen.drawString("WIN", 94, 0);

		if (!done) {
			// Robot.robotScreen.setPixel(ballX, ballY, true);    // single pixel ball
			Robot.robotScreen.fillRect(ballX, ballY, 2, 2);       // 2x2 ball
		}

		Robot.robotScreen.refresh();
	}


	private void updatePlayers() {
		// an autonomous paddle will move toward the ball
		// autonomous paddles need two speeds (but that would make them too smart)
		if (playerCount < 2) {
			// we only update the paddle position if the ball is coming toward the left
			if (lastballX >= ballX) {
				if(paddleL + (PADDLE_SIZE/2) > ballY)
					paddleL -= 1;
				if(paddleL + (PADDLE_SIZE/2) < ballY)
					paddleL += 1;
			}
		}
		else {
			paddleL += (int)(5 * Robot.operatorControllers.getOperatorJoystick().getRawAxis(1));
		}
		if(paddleL < 1)
			paddleL = 1;
		if(paddleL > (64 - PADDLE_SIZE))
			paddleL = (64 - PADDLE_SIZE);

		if (playerCount < 1) {
			// we only update the paddle position if the ball is coming toward the right
			if (lastballX <= ballX) {
				// assume the paddle is 6 pixels tall
				if(paddleR + (PADDLE_SIZE/2) > ballY)
					paddleR -= 1;
				if(paddleR + (PADDLE_SIZE/2) < ballY)
					paddleR += 1;
			}
		}
		else {
			paddleR += (int)(5 * Robot.operatorControllers.getDriverJoystick().getRawAxis(1));
		}
		if(paddleR < 1)
			paddleR = 1;
		if(paddleR > (64 - PADDLE_SIZE))
			paddleR = (64 - PADDLE_SIZE);
	}
	
	
	private boolean computeGame() {
		if (direct == START) {
			if (rndDirection == UP_RIGHT) {movement(UP_RIGHT, 64);}
			else if (rndDirection == UP_LEFT) {movement(UP_LEFT, 64);}
			else if (rndDirection == DOWN_RIGHT) {movement(DOWN_RIGHT, 64);}
			else if (rndDirection == DOWN_LEFT) {movement(DOWN_LEFT, 64);
			}    
		} 
		else if (direct == UP_RIGHT) {movement(UP_RIGHT, 96);}
		else if (direct == UP_LEFT) {movement(UP_LEFT, 96);}
		else if (direct == DOWN_RIGHT) {movement(DOWN_RIGHT, 96);}
		else if (direct == DOWN_LEFT) {movement(DOWN_LEFT, 96);}
		else if (direct == UP_RIGHTS) {movement(UP_RIGHT, 32);}
		else if (direct == UP_LEFTS) {movement(UP_LEFT, 32);}
		else if (direct == DOWN_RIGHTS) {movement(DOWN_RIGHT, 32);}
		else if (direct == DOWN_LEFTS) {movement(DOWN_LEFT, 32);
		}

		checkCollision();
		movementVal++;
		
		// has anyone won the game yet ?
		if ((scoreL >= 10) || (scoreR >= 10))
			return true;
		return done; // was false but we may have been quit externally
	}


	
	public void showTeam() {
		String messages[] = { "ﾫﾚﾞﾒ￟ￌￌￇￆ", "ﾼﾗﾚﾆﾞﾑﾑﾚ￟ﾵￓ￟ﾼﾐﾓﾚ￟ﾨￓ", "ﾻﾞﾉﾖﾛ￟ﾼￓ￟ﾻﾚﾌﾋﾖﾑ￟ﾾￓ", "ﾺﾍﾖﾜ￟ﾼￓ￟ﾵﾐﾌﾗ￟ﾲￓ", "ﾱﾞﾋﾗﾞﾑ￟ﾬￓ￟ﾯﾍﾞﾋﾖﾔﾞ￟ﾯￓ", "ﾬﾗﾞﾑﾚ￟ﾷￓ￟ﾬﾗﾞﾆﾑﾚ￟ﾳ" }; // colon ￅ

		Robot.robotScreen.clear();

		for (int i = 0; i < messages.length; i++) {
			String e = messages[i];
			String d = "";;
			for (int j = 0; j < e.length(); j++)
				d = d + (char)(~(e.charAt(j)));
			if (i == 0)
				Robot.robotScreen.drawStringCentered(d, ((Robot.robotScreen.getFontOuterHeight() + 1) * i));
			else
				Robot.robotScreen.drawStringCentered(d, ((Robot.robotScreen.getFontOuterHeight() + 1) * i) + 2);
		}

		Robot.robotScreen.refresh();
	}
}
