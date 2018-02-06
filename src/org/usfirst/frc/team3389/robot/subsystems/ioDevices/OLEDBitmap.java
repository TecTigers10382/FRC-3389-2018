package org.usfirst.frc.team3389.robot.subsystems.ioDevices;

public enum OLEDBitmap {
	LOGO (128, 64, new byte[] {
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000010,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000010,(byte)0b00001000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000100,(byte)0b00001000,(byte)0b00000010,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00001000,(byte)0b00010000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00010000,(byte)0b00110000,(byte)0b00001000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00010000,(byte)0b00100000,(byte)0b00011000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b01100000,(byte)0b01000000,(byte)0b00100000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b01000001,(byte)0b10000000,(byte)0b01100000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b10000011,(byte)0b00000000,(byte)0b11000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00001011,(byte)0b01010000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000001,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b01000000,(byte)0b00111011,(byte)0b11111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00001110,(byte)0b00000000,(byte)0b00000010,(byte)0b00000010,(byte)0b10000001,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11100000,(byte)0b11111111,(byte)0b01101100,(byte)0b00000000,(byte)0b00000000,(byte)0b01111110,(byte)0b00000000,(byte)0b01111111,(byte)0b00000000,(byte)0b00000100,(byte)0b00000001,(byte)0b00000001,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11111101,(byte)0b11111011,(byte)0b11111100,(byte)0b11000000,(byte)0b00000110,(byte)0b01111111,(byte)0b11110000,(byte)0b01111111,(byte)0b10000000,(byte)0b00001000,(byte)0b00001011,(byte)0b00000010,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b11111101,(byte)0b10111111,(byte)0b01101100,(byte)0b11110000,(byte)0b01111110,(byte)0b00111111,(byte)0b11110000,(byte)0b01110001,(byte)0b00000000,(byte)0b00010000,(byte)0b00001110,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11101111,(byte)0b11110111,(byte)0b11111100,(byte)0b11111111,(byte)0b11111110,(byte)0b00110000,(byte)0b00000000,(byte)0b11100000,(byte)0b00000000,(byte)0b00110000,(byte)0b00011000,(byte)0b00001100,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01111101,(byte)0b10111101,(byte)0b10110100,(byte)0b11111111,(byte)0b11111110,(byte)0b00110000,(byte)0b00000001,(byte)0b11000000,(byte)0b00000000,(byte)0b00100000,(byte)0b00110000,(byte)0b00010000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11111111,(byte)0b11111111,(byte)0b10101100,(byte)0b11111111,(byte)0b11111110,(byte)0b00110000,(byte)0b00000001,(byte)0b11000000,(byte)0b00000000,(byte)0b01000000,(byte)0b01100000,(byte)0b00100000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11011011,(byte)0b01100000,(byte)0b00000000,(byte)0b11111111,(byte)0b11100110,(byte)0b00111111,(byte)0b10000000,(byte)0b11000000,(byte)0b00000000,(byte)0b00000000,(byte)0b10000000,(byte)0b01100000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b11111111,(byte)0b10000000,(byte)0b00000000,(byte)0b01001111,(byte)0b11000010,(byte)0b00111111,(byte)0b11000000,(byte)0b11100000,(byte)0b00000000,(byte)0b00000001,(byte)0b00000000,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11011100,(byte)0b00000000,(byte)0b00000100,(byte)0b01000111,(byte)0b11000010,(byte)0b01110000,(byte)0b00000001,(byte)0b11100000,(byte)0b00000000,(byte)0b00000001,(byte)0b00000000,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b11111000,(byte)0b00000000,(byte)0b00000100,(byte)0b01000111,(byte)0b11000010,(byte)0b01110000,(byte)0b00000001,(byte)0b11110001,(byte)0b10000000,(byte)0b00000011,(byte)0b00000000,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b11010000,(byte)0b00001010,(byte)0b00001100,(byte)0b00000011,(byte)0b11000000,(byte)0b01111111,(byte)0b11000000,(byte)0b01111011,(byte)0b11000000,(byte)0b00000000,(byte)0b00000001,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b01110000,(byte)0b01111111,(byte)0b00001100,(byte)0b00000011,(byte)0b11000000,(byte)0b01111111,(byte)0b11100000,(byte)0b00111111,(byte)0b10000000,(byte)0b00000000,(byte)0b00000001,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11110011,(byte)0b11101001,(byte)0b00001000,(byte)0b00000011,(byte)0b11000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00111111,(byte)0b10000000,(byte)0b00000000,(byte)0b00000010,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b01111011,(byte)0b11111100,(byte)0b00011100,(byte)0b00000001,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000011,(byte)0b00000000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11011111,(byte)0b10000100,(byte)0b00011000,(byte)0b00000001,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00001010,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11100101,(byte)0b11000010,(byte)0b00111100,(byte)0b00000001,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01110011,(byte)0b11000001,(byte)0b00110100,(byte)0b00000001,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11011000,(byte)0b11111111,(byte)0b11111000,(byte)0b00000000,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b11101110,(byte)0b00111111,(byte)0b11011100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000010,(byte)0b11110011,(byte)0b11011011,(byte)0b11111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01001000,(byte)0b11111111,(byte)0b11011000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01000100,(byte)0b00111101,(byte)0b11111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b01111110,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01100011,(byte)0b00000111,(byte)0b10101000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b11001111,(byte)0b10000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01110001,(byte)0b11111111,(byte)0b11111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b01100000,(byte)0b00111110,(byte)0b00000111,(byte)0b11100000,(byte)0b11000011,(byte)0b10000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11110000,(byte)0b01110111,(byte)0b11101000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000001,(byte)0b11000000,(byte)0b11111000,(byte)0b00111111,(byte)0b11000011,(byte)0b00110000,(byte)0b11000001,(byte)0b10000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000010,(byte)0b10111000,(byte)0b00011011,(byte)0b01111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00100001,(byte)0b11111001,(byte)0b11111000,(byte)0b00111111,(byte)0b11100011,(byte)0b00111000,(byte)0b01110000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11111110,(byte)0b00000111,(byte)0b11110100,(byte)0b00000011,(byte)0b00000000,(byte)0b01100001,(byte)0b11111001,(byte)0b11111100,(byte)0b00111000,(byte)0b00000001,(byte)0b10011000,(byte)0b00111000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000010,(byte)0b10101111,(byte)0b11111111,(byte)0b10111000,(byte)0b00000111,(byte)0b10000001,(byte)0b11110000,(byte)0b01100001,(byte)0b10001110,(byte)0b00111000,(byte)0b00000001,(byte)0b10111000,(byte)0b00001100,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b01111011,(byte)0b11011111,(byte)0b11111000,(byte)0b00000111,(byte)0b11111111,(byte)0b11110000,(byte)0b01100001,(byte)0b10000100,(byte)0b00011100,(byte)0b00000001,(byte)0b10110001,(byte)0b00001110,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000011,(byte)0b01101110,(byte)0b11111011,(byte)0b10111100,(byte)0b00000111,(byte)0b11111111,(byte)0b11110000,(byte)0b01100001,(byte)0b10000000,(byte)0b00011100,(byte)0b00000001,(byte)0b11110001,(byte)0b10000110,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b01101111,(byte)0b11011111,(byte)0b11111000,(byte)0b00000111,(byte)0b11111111,(byte)0b11110000,(byte)0b00110001,(byte)0b10000000,(byte)0b00011111,(byte)0b11000001,(byte)0b11110001,(byte)0b10000111,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b01101110,(byte)0b11011011,(byte)0b11111000,(byte)0b00000111,(byte)0b10111111,(byte)0b01110000,(byte)0b00110001,(byte)0b10011111,(byte)0b10001111,(byte)0b11100001,(byte)0b11111000,(byte)0b11000111,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000001,(byte)0b11101110,(byte)0b11011111,(byte)0b11111100,(byte)0b00000111,(byte)0b00011110,(byte)0b00110000,(byte)0b00110001,(byte)0b10000111,(byte)0b00001100,(byte)0b00000001,(byte)0b11011000,(byte)0b11100111,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b11101110,(byte)0b11011111,(byte)0b11111100,(byte)0b00000011,(byte)0b00011110,(byte)0b00110000,(byte)0b00110001,(byte)0b11000110,(byte)0b00001100,(byte)0b00000001,(byte)0b11011100,(byte)0b01111111,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00100110,(byte)0b11111111,(byte)0b10011100,(byte)0b00000010,(byte)0b00011110,(byte)0b00010000,(byte)0b00110000,(byte)0b11100110,(byte)0b00111111,(byte)0b11110001,(byte)0b11001100,(byte)0b00111110,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00100111,(byte)0b01111111,(byte)0b10001100,(byte)0b00000000,(byte)0b00001110,(byte)0b00010001,(byte)0b11111100,(byte)0b11111110,(byte)0b00111111,(byte)0b11110001,(byte)0b10000110,(byte)0b00011110,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000110,(byte)0b01111111,(byte)0b11001100,(byte)0b00000000,(byte)0b00001110,(byte)0b00000001,(byte)0b11111100,(byte)0b11111110,(byte)0b00111111,(byte)0b10000011,(byte)0b10000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000110,(byte)0b00111111,(byte)0b11111100,(byte)0b00000000,(byte)0b00001110,(byte)0b00000001,(byte)0b11111100,(byte)0b00111100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000010,(byte)0b01111111,(byte)0b11111100,(byte)0b00000000,(byte)0b00001100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000010,(byte)0b00011111,(byte)0b11111100,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00011111,(byte)0b11111000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00001111,(byte)0b11111000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000011,(byte)0b11111000,(byte)0b00000000,(byte)0b00000100,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000011,(byte)0b11111000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000001,(byte)0b11100000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,
			(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000,(byte)0b00000000

	});
	
	private int width, height;
    private final byte[] data;

    private OLEDBitmap(int width, int height, byte[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }
    
    public byte[] getData() {
        return this.data;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}