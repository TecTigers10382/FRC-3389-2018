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
     * using the I2C port on the RoboRIO MXP
     */
    public OLEDDisplay() {
		super(Port.kMXP, defaultAddress);
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");

        clear();  // erase screen buffer

        Robot.robotLogger.log(Logger.DEBUG, this, "exit");
    }
    
    
    /**
     * initialize the OLED display using its I2C register commands
     * 
     * @see https://cdn-shop.adafruit.com/datasheets/SSD1306.pdf
     * 
     * @return boolean success
     */
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

    
    /**
     * test to see if the OLED display was successfully initialized
     * 
     * @return boolean is initialized
     */
    public boolean isReady() {
    	return this.inited;
    }
    
    
    /**
     * method to invert the meaning of black vs white in future pixel and text write operations
     * also effects clear() - eg, calling clear() when invert is true results in a completly white display
     * @param invert boolean to set display operations to invert meaning of black and white
     */
    public void invertColors(boolean invert) {
    	invert_display = invert;
    }
    
    
    /**
     * erase all content of the video buffer and set every pixel is black (or the inverse of black)
     * the pixel colors are affected by the current setting from invertColors()
     */
    public synchronized void clear() {
    	if (this.invert_display)
            Arrays.fill(imageBuffer, (byte) 0xFF);
    	else
    		Arrays.fill(imageBuffer, (byte) 0x00);
    }


    /**
     * get the OLED display available width
     * 
     * @return int the width of the display in pixels
     */    
    public int getWidth() {
        return DISPLAY_WIDTH;
    }

    
    /**
     * get the OLED display available height
     * 
     * @return int the height of the display in pixels
     */
    public int getHeight() {
        return DISPLAY_HEIGHT;
    }

    
    /**
     * get the OLED display available character width
     * 
     * @return int the width of the display in characters using the current font's fixed width size
     */
    public int getMaxChars() {
        return maxChars;
    }

    
    /**
     * get the OLED display available height
     * 
     * @return int the height of the display in characters using the current font's fixed height size
     */
    public int getMaxLines() {
        return maxLines;
    }

    
    /**
     * set an individual pixel black or white
     * ignore's the current invertColors() setting
     * positional values are zero-based
     * 
     * @param x int horizontal pixel position on the display starting from left
     * @param y int vertical pixel position on the display starting from the top
     * @param color boolean true for white and false for black
     */
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
    
    
    /**
     * set an individual pixel on
     * uses the current invertColors() setting to determin if 'on' is white or black
     * positional values are zero-based
     * 
     * @param x int horizontal pixel position on the display starting from left
     * @param y int vertical pixel position on the display starting from the top
     */
    public synchronized void setPixel(int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	this.setPixelColor(x, y, !(this.invert_display));
    }


    /**
     * set the font to use for subsequent text operations
     * also updates the character size used for subsequent text operations
     * 
     * @param font OLEDFont one of the provided fonts
     */
    public void setFont(OLEDFont font) {
    	currentFont = font;
    	this.maxChars = (this.getWidth()  + (currentFont.getOuterWidth()  - 1)) / currentFont.getOuterWidth();
        this.maxLines = (this.getHeight() + (currentFont.getOuterHeight() - 1)) / currentFont.getOuterHeight();
    }
    

    /**
     * draw a string into the video buffer using the current font
     * this method does not clear the pixels of the video buffer prior to rendering
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param x int the left pixel position for the text
     * @param y int the upper pixel position for the text
     */
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
                    currentFont.drawChar(this, c, posX, posY);
                }
                posX += currentFont.getOuterWidth();
            }
        }
    }

    
    /**
     * draw a string into the video buffer using the current font
     * the string with be center horizontally on the display based on the fixed character width of the current font
     * this method does not clear the pixels of the video buffer prior to rendering
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param y int the upper pixel position for the text
     */
    public synchronized void drawStringCentered(String string, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        final int strSizeX = string.length() * currentFont.getOuterWidth();
        final int x = (this.getWidth() - strSizeX) / 2;
        drawString(string, x, y);
    }

    
    /**
     * erase a portion of the video buffer setting those pixels to black
     * this methods observes the most recent call to inverteColors() to determine
     * the definition of black
     * positional values are zero-based
     * 
     * @param x int the left most pixel is the rectangular area
     * @param y int the top most pixel is the rectangular area
     * @param width int the width of the rectangular area
     * @param height int the height of the retangular area
     */
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
    
    /*
     * the methods containing the word 'Text' operate on a XY layout of the display
     * based on the fixed width and fixed height size of the current font
     * 
     * currently, all text operations assume a fixed pitch font
     */

    
    /**
     * draw a string into the video buffer using the current font
     * if the string contains newlines, then line breaks are added
     * and the rendered text will proceed on the next line using the start column 
     * this method does not clear the pixels of the video buffer prior to rendering
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param col int the left character position for the text on the screen
     * @param line int the line position for the text on the screen
     */
    public synchronized void drawTextString(String string, int col, int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        int currentCol = col;
        for (char c : string.toCharArray()) {
            if (c == '\n') {
                line += 1;
                currentCol = col;
            } else {
            	currentFont.drawChar(this, c, currentCol * currentFont.getOuterWidth(), line * currentFont.getOuterHeight());
                currentCol += 1;
            }
        }
    }


    /**
     * draw a string into the video buffer using the current font
     * the string with be center horizontally on the display based on the fixed character width of the current font
     * if the string contains newlines, then unexpected results will occur
     * this method does not clear the pixels of the video buffer prior to rendering
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param line int the line position for the text on the screen
     */
    public synchronized void drawTextStringCentered(String string, int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
        final int col = (this.maxChars - string.length()) / 2;
        drawTextString(string, col, line);
    }

    
    /**
     * draw a string into the video buffer using the current font
     * the entire line is replaces with the new string
     * this method clears the pixels of the video buffer corresponding to the line of text prior to rendering
     * if the string contains newlines, then unexpected results will occur
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param line int the line position for the text on the screen
     */
    public synchronized void drawTextLine(String string, int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearTextLine(line);
    	drawTextString(string, 0, line);
    }

    
    /**
     * draw a string into the video buffer using the current font and refresh the display
     * the entire line is replaces with the new string
     * if the string contains newlines, then unexpected results will occur
     * this method clears the pixels of the video buffer corresponding to the line of text prior to rendering
     * positional values are zero-based
     * 
     * @param string java String object with the text to render
     * @param line int the line position for the text on the screen
     */
    public synchronized void updateTextLine(String string, int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearTextLine(line);
    	drawTextString(string, 0, line);
    	refreshLine(line);
    }

    /**
     * erase a portion of the video buffer setting those character locations to black
     * this methods observes the most recent call to inverteColors() to determine
     * the definition of black
     * positional values are zero-based
     * 
     * @param col int the left most character column to erase 
     * @param line int the first line to erase
     * @param chars int the number of characters across to erase
     * @param lines int the number of lines to erase
     */
    public synchronized void clearTextArea(int col, int line, int chars, int lines) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearRect(col * currentFont.getOuterWidth(), line * currentFont.getOuterHeight(), chars * currentFont.getOuterWidth(), lines * currentFont.getOuterHeight());
    }

    
    /**
     * erase a single line of the video buffer setting those character locations to black
     * this methods observes the most recent call to inverteColors() to determine
     * the definition of black
     * positional values are zero-based
     * 
     * @param line int the character line to erase
     */
    public synchronized void clearTextLine(int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	clearRect(0, line * currentFont.getOuterHeight(), this.getWidth(), currentFont.getOuterHeight());
    }

    /**
     * draws the given bitmap over the current image buffer.
     * this methods observes the most recent invertColors() setting
     * to determine if a set pixel is black or white and non-set pixels are the other

     * @param bitmap byte[] binary bitmap image data 
     * @param width the pixel width of the bitmap image
     * @param height the pixel height of the bitmap image
     * @param x int the left most pixel position of the image
     * @param y int the top most pixel position of the image 
     */
    public synchronized void drawBitmap(byte[] bitmap, int width, int height, int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}

        int index = 0;
        int pixelval;

        for (int posY = 0; posY < height; posY++) {
            for (int posX = 0; posX < (width / 8); posX++) {
                for (int bit = 0; bit < 8; bit++) {
                    pixelval = (byte) ((bitmap[index/8] >>  (8 - bit)) & 0x01);
                    setPixelColor(x + ((posX * 8) + bit), y + posY, pixelval > 0);
                    index++;
                }
            }
        }
    }

    
    /**
     * draws the given image over the current image buffer. The image
     * is automatically converted to a binary image (one bit per pixel)
     * this methods observes the most recent invertColors() setting
     * to determine if a set pixel is black or white and non-set pixels are the other
     * positional values are zero-based
     * 
     * @param image BufferedImage 
     * @param x int the left most pixel position of the image
     * @param y int the top most pixel position of the image 
     */
    public synchronized void drawImage(BufferedImage image, int x, int y) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}
    	int width = Math.max(image.getWidth(), this.getWidth());
    	int height = Math.max(image.getHeight(), this.getHeight());
        BufferedImage tmpImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        tmpImage.getGraphics().drawImage(image, 0, 0, null);

        drawBitmap (((DataBufferByte) tmpImage.getRaster().getDataBuffer()).getData(), width, height, x, y);
    }

    
    /**
     * update actual display with contents of video buffer
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

    
    /**
     * update a portion of the actual display with the contents of the video buffer
     * since the video buffer is broken into pages with each page representing 8 pixel lines
     * a single character row may refresh additional buffer lines before/after the text line
     * positional values are zero-based
     * 
     * @param line int a single line of text on the display based on the current font
     */
    public synchronized void refreshLine(int line) {
    	if (!inited) {
    		Robot.robotLogger.log(Logger.WARNING, this, "OLED Display not initialized");
    		return;
    	}

    	// line level refreshes had a bug so we just do a full refresh until we fix it
    	refresh();

    	// TODO test if this new code actually works
    	/*
    	int firstX = line * currentFont.getOuterHeight();
    	int lastX = Math.max((line+1) * currentFont.getOuterHeight(), getHeight());
    	// the display buffer is broken into 'pages' of 8 lines each; the display is made up of page0 .. page7
    	int firstPage = firstX / 8;
    	int lastPage = (lastX + 7) / 8;
    	
        byte[] buffer = new byte[16 + 1];
        buffer[0] = SSD1306_SETSTARTLINE;

        // refresh entire line
        writeByte(0x00, SSD1306_COLUMNADDR);
        writeByte(0x00, (byte) 0);   // Column start address (0 = reset)
        writeByte(0x00, (byte) (DISPLAY_WIDTH - 1)); // Column end address (127 = reset)

        // refresh all affected pages
        writeByte(0x00, SSD1306_PAGEADDR);
        writeByte(0x00, (byte) firstPage); // Page start address (0 = reset)
        writeByte(0x00, (byte) lastPage); // Page end address

        // the display buffer is loaded in 16 byte chucks
        // for a monochrome display, this is 64 pixels
        // on a 128x64 display, this is one pixel line

        for (int i = (firstPage * 8); i < (lastPage * 8); i++) {
        	// send a bunch of data in one transmission
        	System.arraycopy(imageBuffer, i*16, buffer, 1, 16);
        	writeBulk(buffer);
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

}
