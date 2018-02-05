/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/
/*
 * Based on material from Florian Frankenberger and made available
 * under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */

package org.usfirst.frc.team3389.robot.subsystems.ioDevices;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.utils.Logger;


/**
 * A raspberry pi driver for the 128x64 pixel OLED display (i2c bus).
 * The supported kind of display uses the SSD1306 driver chip and
 * is connected to the raspberry's i2c bus (bus 1).
 * <p/>
 * Note that you need to enable i2c (using for example raspi-config).
 * Also note that you need to load the following kernel modules:
 * <pre>i2c-bcm2708</pre> and <pre>i2c_dev</pre>
 * <p/>
 * Also note that it is possible to speed up the refresh rate of the
 * display up to ~60fps by adding the following to the config.txt of
 * your raspberry: dtparam=i2c1_baudrate=1000000
 * <p/>
 * Sample usage:
 * <pre>
 * OLEDDisplay display = new OLEDDisplay();
 * display.drawStringCentered("Hello World!", 25, true);
 * display.update();
 * Thread.sleep(10000); //sleep some time, because the display
 *                      //is automatically cleared the moment
 *                      //the application terminates
 * </pre>
 * <p/>
 * This class is basically a rough port of Adafruit's BSD licensed
 * SSD1306 library (https://github.com/adafruit/Adafruit_SSD1306)
 * 
 * This implementation is quick port for the NI RoboRIO
 *
 * @author Florian Frankenberger
 * @author FRC Team 3389
 */

// TODO - needs javadocs commenting

public class OLEDDisplay extends I2CUpdatableAddress {

	private static final int defaultAddress = 0x3C;

    private static final int DISPLAY_WIDTH = 128;
    private static final int DISPLAY_HEIGHT = 64;
    private static final int MAX_INDEX = (DISPLAY_HEIGHT / 8) * DISPLAY_WIDTH;

    private static final byte SSD1306_SETCONTRAST = (byte) 0x81;
    private static final byte SSD1306_DISPLAY_WRITE = (byte) 0xA4;
    private static final byte SSD1306_DISPLAYALLON_RESUME = (byte) 0xA4;
    private static final byte SSD1306_DISPLAYALLON = (byte) 0xA5;
    private static final byte SSD1306_NORMALDISPLAY = (byte) 0xA6;
    private static final byte SSD1306_INVERTDISPLAY = (byte) 0xA7;
    private static final byte SSD1306_DISPLAYOFF = (byte) 0xAE;
    private static final byte SSD1306_DISPLAYON = (byte) 0xAF;

    private static final byte SSD1306_SETDISPLAYOFFSET = (byte) 0xD3;
    private static final byte SSD1306_SETCOMPINS = (byte) 0xDA;

    private static final byte SSD1306_SETVCOMDETECT = (byte) 0xDB;

    private static final byte SSD1306_SETDISPLAYCLOCKDIV = (byte) 0xD5;
    private static final byte SSD1306_SETPRECHARGE = (byte) 0xD9;

    private static final byte SSD1306_SETMULTIPLEX = (byte) 0xA8;

    private static final byte SSD1306_SETLOWCOLUMN = (byte) 0x00;
    private static final byte SSD1306_SETHIGHCOLUMN = (byte) 0x10;

    private static final byte SSD1306_SETSTARTLINE = (byte) 0x40;

    private static final byte SSD1306_MEMORYMODE = (byte) 0x20;
    private static final byte SSD1306_COLUMNADDR = (byte) 0x21;
    private static final byte SSD1306_PAGEADDR = (byte) 0x22;

    private static final byte SSD1306_COMSCANINC = (byte) 0xC0;
    private static final byte SSD1306_COMSCANDEC = (byte) 0xC8;

    private static final byte SSD1306_SEGREMAP = (byte) 0xA0;

    private static final byte SSD1306_CHARGEPUMP = (byte) 0x8D;

    private static final byte SSD1306_EXTERNALVCC = (byte) 0x1;
    private static final byte SSD1306_SWITCHCAPVCC = (byte) 0x2;

    private static final byte  SSD1306_ACTIVATE_SCROLL = (byte) 0x2F;
    private static final byte  SSD1306_DEACTIVATE_SCROLL = (byte) 0x2E;
    private static final byte  SSD1306_SET_VERTICAL_SCROLL_AREA = (byte) 0xA3;
    private static final byte  SSD1306_RIGHT_HORIZONTAL_SCROLL = (byte) 0x26;
    private static final byte  SSD1306_LEFT_HORIZONTAL_SCROLL = (byte) 0x27;
    private static final byte  SSD1306_VERTICAL_AND_RIGHT_HORIZONTAL_SCROLL = (byte) 0x29;
    private static final byte  SSD1306_VERTICAL_AND_LEFT_HORIZONTAL_SCROLL = (byte) 0x2A;

    private boolean inited = false;
    private OLEDFont currentFont;
    private int maxChars;
    private int maxLines;
    private boolean invert_display;
    
    // we add a space at the head of the buffer to hold the I2C command
    private final byte[] imageBuffer = new byte[((DISPLAY_WIDTH * DISPLAY_HEIGHT) / 8)];

    /**
     * creates an oled display object with default
     * i2c bus 1 and default display address of 0x3C
     *
     * @throws NACKException
     */
    public OLEDDisplay() {
		super(Port.kMXP, defaultAddress);
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

        clear();  // erase screen buffer

        //add shutdown hook that clears the display
        //and closes the bus correctly when the software
        //if terminated.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });
        Robot.robotLogger.log(Logger.DEBUG, this, "exit");
    }
    
    public void finalize() {
        shutdown();
    }

    public final boolean init() {
    	Robot.robotLogger.log(Logger.DEBUG, this, "enter");
    	invert_display = false;
    	setFont(OLEDFont.FONT_5X8);
            
    	writeByte(0x00,SSD1306_DISPLAYOFF);                    // 0xAE
    	writeByte(0x00,SSD1306_SETDISPLAYCLOCKDIV);            // 0xD5
    	writeByte(0x00,(byte) 0x80);                           // the suggested ratio 0x80
    	writeByte(0x00,SSD1306_SETMULTIPLEX);                  // 0xA8
    	writeByte(0x00,(byte) 0x3F);
    	writeByte(0x00,SSD1306_SETDISPLAYOFFSET);              // 0xD3
    	writeByte(0x00,(byte) 0x0);                            // no offset
    	writeByte(0x00,(byte) (SSD1306_SETSTARTLINE | 0x0));   // line #0
    	writeByte(0x00,SSD1306_CHARGEPUMP);                    // 0x8D
    	writeByte(0x00,(byte) 0x14);
    	writeByte(0x00,SSD1306_MEMORYMODE);                    // 0x20
    	writeByte(0x00,(byte) 0x00);                           // 0x0 act like ks0108
    	writeByte(0x00,(byte) (SSD1306_SEGREMAP | 0x1));
    	writeByte(0x00,SSD1306_COMSCANDEC);
    	writeByte(0x00,SSD1306_SETCOMPINS);                    // 0xDA
    	writeByte(0x00,(byte) 0x12);
    	writeByte(0x00,SSD1306_SETCONTRAST);                   // 0x81
    	writeByte(0x00,(byte) 0xCF);
    	writeByte(0x00,SSD1306_SETPRECHARGE);                  // 0xd9
    	writeByte(0x00,(byte) 0xF1);
    	writeByte(0x00,SSD1306_SETVCOMDETECT);                 // 0xDB
    	writeByte(0x00,(byte) 0x40);
    	writeByte(0x00,SSD1306_DISPLAYALLON_RESUME);           // 0xA4
    	writeByte(0x00,SSD1306_NORMALDISPLAY);
    	writeByte(0x00,SSD1306_DISPLAYON);//--turn on oled panel
            
    	this.inited = true;;
 
		refresh();
    	Robot.robotLogger.log(Logger.DEBUG, this, "exit");
    	return this.inited;
    }

    public boolean isReady() {
    	return this.inited;
    }
    
    public void invertColors(boolean invert) {
    	invert_display = invert;
    }
    public synchronized void clear() {
    	if (this.invert_display)
            Arrays.fill(imageBuffer, (byte) 0xFF);
    	else
    		Arrays.fill(imageBuffer, (byte) 0x00);
    }

    public int getWidth() {
        return DISPLAY_WIDTH;
    }

    public int getHeight() {
        return DISPLAY_HEIGHT;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public synchronized void setPixelColor(int x, int y, boolean color) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}

    	final int pos = x + (y / 8) * DISPLAY_WIDTH;
        if (pos >= 0 && pos < MAX_INDEX) {
            if (color) {
                this.imageBuffer[pos] |= (1 << (y & 0x07));
            } else {
                this.imageBuffer[pos] &= ~(1 << (y & 0x07));
            }
        }
    }
    
    public synchronized void setPixel(int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	this.setPixelColor(x, y, !(this.invert_display));
    }

    public void setFont(OLEDFont font) {
    	currentFont = font;
    	this.maxChars = this.getWidth() / currentFont.getOuterWidth();
        this.maxLines = this.getHeight() / currentFont.getOuterHeight();
    }
    
    private synchronized void drawChar(char c, int x, int y) {
        currentFont.drawChar(this, c, x, y);
    }

    public synchronized void drawString(String string, int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        int posX = x;
        int posY = y;
        for (char c : string.toCharArray()) {
            if (c == '\n') {
                posY += currentFont.getOuterHeight();
                posX = x;
            } else {
                if (posX >= 0 && posX + currentFont.getWidth() < this.getWidth()
                        && posY >= 0 && posY + currentFont.getHeight() < this.getHeight())
                {
                    drawChar(c, posX, posY);
                }
                posX += currentFont.getOuterWidth();
            }
        }
    }

    public synchronized void drawStringCentered(String string, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        final int strSizeX = string.length() * currentFont.getOuterWidth();
        final int x = (this.getWidth() - strSizeX) / 2;
        drawString(string, x, y);
    }

    public synchronized void clearRect(int x, int y, int width, int height) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        for (int posX = x; posX < x + width; ++posX) {
            for (int posY = y; posY < y + height; ++posY) {
                setPixelColor(posX, posY, this.invert_display);
            }
        }
    }
    
    // added fixed width font based operations for easy of use
    // these methods use the current font dimensions as rows and columns
    // rather than pixel positioning in x and y in the previous methods
    
    private synchronized void drawTextChar(char c, int row, int col) {
        drawChar(c, col * currentFont.getOuterWidth(), row * currentFont.getOuterHeight());
    }

    public synchronized void drawTextString(String string, int row, int col) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        int posR = row;
        int posC = col;
        for (char c : string.toCharArray()) {
            if (c == '\n') {
                posR += 1;
                posC = col;
            } else {
                if (posC >= 0 && posC+1 < this.maxChars && posR >= 0 && posR+1 < this.maxLines) {
                    drawTextChar(c, posR, posC);
                }
                posC += 1;
            }
        }
    }

    public synchronized void drawTextStringCentered(String string, int row) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearTextLine(row);
        final int col = (this.maxChars - string.length()) / 2;
        drawTextString(string, row, col);
    }

    public synchronized void drawTextLine(String string, int row) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearTextLine(row);
    	drawTextString(string, row, 0);
    }

    public synchronized void updateTextLine(String string, int row) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearTextLine(row);
    	drawTextString(string, row, 0);
    	refreshLine(row);
    }

    public synchronized void clearTextArea(int row, int col, int chars, int lines) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearRect(col * currentFont.getOuterWidth(), row * currentFont.getOuterHeight(), chars * currentFont.getOuterWidth(), lines * currentFont.getOuterHeight());
    }
    
    public synchronized void clearTextLine(int row) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearRect(0, row * currentFont.getOuterHeight(), this.getWidth(), currentFont.getOuterHeight());
    }

    /**
     * draws the given image over the current image buffer. The image
     * is automatically converted to a binary image (if it not already
     * is).
     * <p/>
     * Note that the current buffer is not cleared before, so if you
     * want the image to completely overwrite the current display
     * content you need to call clear() before.
     *
     * @param image
     * @param x
     * @param y
     */
    public synchronized void drawImage(BufferedImage image, int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        BufferedImage tmpImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        tmpImage.getGraphics().drawImage(image, x, y, null);

        int index = 0;
        int pixelval;
        final byte[] pixels = ((DataBufferByte) tmpImage.getRaster().getDataBuffer()).getData();
        for (int posY = 0; posY < DISPLAY_HEIGHT; posY++) {
            for (int posX = 0; posX < DISPLAY_WIDTH / 8; posX++) {
                for (int bit = 0; bit < 8; bit++) {
                    pixelval = (byte) ((pixels[index/8] >>  (8 - bit)) & 0x01);
                    setPixelColor(posX * 8 + bit, posY, pixelval > 0);
                    index++;
                }
            }
        }
    }

    /**
     * sends the current buffer to the display
     * @throws IOException
     */
    public synchronized void refresh() {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        writeByte(0x00,SSD1306_COLUMNADDR);
        writeByte(0x00,(byte) 0);   // Column start address (0 = reset)
        writeByte(0x00,(byte) (DISPLAY_WIDTH - 1)); // Column end address (127 = reset)

        writeByte(0x00,SSD1306_PAGEADDR);
        writeByte(0x00,(byte) 0); // Page start address (0 = reset)
        writeByte(0x00,(byte) 7); // Page end address

        byte[] buffer = new byte[16 + 1];
        buffer[0] = SSD1306_SETSTARTLINE;
        
        for (int i = 0; i < ((DISPLAY_WIDTH * DISPLAY_HEIGHT / 8) / 16); i++) {
            // send a bunch of data in one transmission
    		System.arraycopy(imageBuffer, i*16, buffer, 1, 16);
        	writeBulk(buffer);
        }
    }

    public synchronized void refreshLine(int row) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	// line level refreshes have a bug so we just do a full refresh until we fix it
    	refresh();
    	// TODO fix line level refresh
    	/*
        writeByte(0x00, SSD1306_COLUMNADDR);
        writeByte(0x00, (byte) 0);   // Column start address (0 = reset)
        writeByte(0x00, (byte) (DISPLAY_WIDTH - 1)); // Column end address (127 = reset)

        writeByte(0x00, SSD1306_PAGEADDR);
        writeByte(0x00, (byte) 0); // Page start address (0 = reset)
        writeByte(0x00, (byte) 7); // Page end address

        byte[] buffer = new byte[16 + 1];
        buffer[0] = SSD1306_SETSTARTLINE;

        // the display buffer is loaded in 16 byte chucks
        // for a monochrome display, this is 64 pixels
        // on a 128x64 display, this is one pixel line

        if ((row >= 0) && (row < this.maxLines)) {
        	int pixelHeight= currentFont.getOuterHeight();
            for (int i = (row * pixelHeight); i < ((row+1) * pixelHeight); i++) {
                // send a bunch of data in one transmission
        		System.arraycopy(imageBuffer, i*16, buffer, 1, 16);
            	writeBulk(buffer);
            }
        }
    	 */
    }

    // the I2C OLED Display supports hardware horizontal scrolling
    // vertical scrolling must be implemented in software
    
    public synchronized void scrollVerticalLine(int lines) {
    	// TODO need to implement buffer shift and redisplay
    }
    public synchronized void scrollVerticalCharacter(OLEDFont font, int chars) {
    	int lines = font.getOuterWidth() * chars;
    	scrollVerticalLine (lines);
    }
    
    
    private synchronized void shutdown() {
    	//before we shut down we clear the display
    	clear();
    	refresh();
    }
}
