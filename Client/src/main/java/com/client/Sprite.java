package com.client;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.client.sign.Signlink;

public class Sprite extends DrawingArea {

	public static String location = null;

	public static void init() {
//		if (Configuration.developerMode && new File("./sprites/").exists()) {
//			location = "./sprites/";
//		} else {
			location = Signlink.getCacheDirectory() + "sprites/";
		//}
	}

	public int xPosition, yPosition;
	private String spriteLocation;
	public static int loadedSprites;

	private static final Map<String, Sprite> sprites = new HashMap<>();


	public Sprite(String img) {
		try {
			img = img.toLowerCase();
			if (sprites.containsKey(img)) {
				Sprite sprite = sprites.get(img);
				myWidth = sprite.myWidth;
				myHeight = sprite.myHeight;
				maxWidth = myWidth;
				maxHeight = myHeight;
				anInt1442 = 0;
				anInt1443 = 0;
				myPixels = sprite.myPixels;
				setTransparency(255, 0, 255);
				return;
			}

			File file = new File(location + img + ".png");
			//Preconditions.checkState(file.getPath().equals(file.getPath().toLowerCase()), "Sprite path isn't lowercase: " + file.getPath());
			if (!file.exists()) {
				if (Configuration.developerMode) {
					//System.err.println("No sprite: " + file);
				}
				return;
			}

			loadedSprites++;
			Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth,
					myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			setTransparency(255, 0, 255);
			sprites.put(img, this);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}

	public Sprite(StreamLoader streamLoader, String s, int i) {
		Stream stream = new Stream(streamLoader.getArchiveData(s + ".dat"));
		Stream stream_1 = new Stream(streamLoader.getArchiveData("index.dat"));
		stream_1.currentOffset = stream.readUShort();
		maxWidth = stream_1.readUShort();
		maxHeight = stream_1.readUShort();
		int j = stream_1.readUnsignedByte();
		int ai[] = new int[j];
		for (int k = 0; k < j - 1; k++) {
			ai[k + 1] = stream_1.read3Bytes();
			if (ai[k + 1] == 0)
				ai[k + 1] = 1;
		}

		for (int l = 0; l < i; l++) {
			stream_1.currentOffset += 2;
			stream.currentOffset += stream_1.readUShort()
					* stream_1.readUShort();
			stream_1.currentOffset++;
		}

		anInt1442 = stream_1.readUnsignedByte();
		anInt1443 = stream_1.readUnsignedByte();
		myWidth = stream_1.readUShort();
		myHeight = stream_1.readUShort();
		int i1 = stream_1.readUnsignedByte();
		int j1 = myWidth * myHeight;
//		if (j1 > maxWidth * maxHeight) {
//			j1 = myWidth * myHeight;
//			System.out.println("Image number: "+ i1 +" - j1 is higher than maxWidth * maxHeight.");
//		}
		myPixels = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++)
				myPixels[k1] = ai[stream.readUnsignedByte()];
			setTransparency(255, 0, 255);
			createImageFromPixels();
			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < myWidth; l1++) {
				for (int i2 = 0; i2 < myHeight; i2++)
					myPixels[l1 + i2 * myWidth] = ai[stream.readUnsignedByte()];
			}

		}
		createImageFromPixels();
	}

	public Sprite(int i, int j) {
		myPixels = new int[i * j];
		myWidth = maxWidth = i;
		myHeight = maxHeight = j;
		anInt1442 = anInt1443 = 0;
	}
	
	public static Sprite getEmptySprite(int width, int height) {
		Sprite sprite = new Sprite(width, height);
		sprite.setTransparency(255, 0, 255);
		return sprite;
	}

	public Sprite(byte abyte0[], Component component) {
		try {
			loadedSprites++;
			Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
			MediaTracker mediatracker = new MediaTracker(component);
			mediatracker.addImage(image, 0);
			mediatracker.waitForAll();
			myWidth = image.getWidth(component);
			myHeight = image.getHeight(component);
			maxWidth = myWidth;
			maxHeight = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth,
					myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
		} catch (Exception _ex) {
			System.out.println("Error converting jpg");
		}
	}
	
	public Sprite(byte spriteData[]) {
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(spriteData);
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth,
					myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			setTransparency(255, 0, 255);
		} catch (Exception _ex) {
			System.out.println(_ex);
		}
	}

	public Sprite(Image image) {
		ImageIcon imageicon = new ImageIcon(image);
		imageicon.getIconHeight();
		imageicon.getIconWidth();
		try {
			myWidth = imageicon.getIconWidth();
			myHeight = imageicon.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth,
					myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
		} catch (Exception _ex) {
			System.out.println(_ex);
		}
	}
	
	public void drawARGBSprite2(int xPos, int yPos) {
		drawARGBSprite2(xPos, yPos, 256);
	}

	public void drawARGBSprite2(int xPos, int yPos, int alpha) {
		int alphaValue = alpha;
		xPos += anInt1442;
		yPos += anInt1443;
		int i1 = xPos + yPos * DrawingArea.width;
		int j1 = 0;
		int spriteHeight = myHeight;
		int spriteWidth = myWidth;
		int i2 = DrawingArea.width - spriteWidth;
		int j2 = 0;
		if (yPos < DrawingArea.topY) {
			int k2 = DrawingArea.topY - yPos;
			spriteHeight -= k2;
			yPos = DrawingArea.topY;
			j1 += k2 * spriteWidth;
			i1 += k2 * DrawingArea.width;
		}
		if (yPos + spriteHeight > DrawingArea.bottomY)
			spriteHeight -= (yPos + spriteHeight) - DrawingArea.bottomY;
			if (xPos < DrawingArea.topX) {
			int l2 = DrawingArea.topX - xPos;
			spriteWidth -= l2;
			xPos = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (xPos + spriteWidth > DrawingArea.bottomX) {
			int i3 = (xPos + spriteWidth) - DrawingArea.bottomX;
			spriteWidth -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(spriteWidth <= 0 || spriteHeight <= 0)) {
			renderARGBPixels2(spriteWidth, spriteHeight, myPixels, DrawingArea.pixels, i1, alphaValue, j1, j2, i2);
		}
	}

    private void renderARGBPixels2(int spriteWidth, int spriteHeight, int spritePixels[], int renderAreaPixels[], int pixel, int alphaValue, int i, int l, int j1) {
    	int pixelColor;
    	int alphaLevel;
    	int alpha = alphaValue;
    	for (int height = -spriteHeight; height < 0; height++) {
    		for (int width = -spriteWidth; width < 0; width++) {
    			alphaValue = ((myPixels[i] >> 24) & (alpha - 1));
    			alphaLevel = 256 - alphaValue;
    			if (alphaLevel > 256) {
    				alphaValue = 0;
    			}
    			if (alpha == 0) {
    				alphaLevel = 256;
    				alphaValue = 0;
    			} 
    			pixelColor = spritePixels[i++];
    			if (pixelColor != 0) {
    				int pixelValue = renderAreaPixels[pixel];
    				renderAreaPixels[pixel++] = ((pixelColor & 0xff00ff) * alphaValue + (pixelValue & 0xff00ff) * alphaLevel & 0xff00ff00) + ((pixelColor & 0xff00) * alphaValue + (pixelValue & 0xff00) * alphaLevel & 0xff0000) >> 8;
    			} else {
    				pixel++;
    			}
    		}
    		pixel += j1;
    		i += l;
    	}
    }

	public void setAlphaTransparency(int a) {
		for (int pixel = 0; pixel < myPixels.length; pixel++) {
			if (((myPixels[pixel] >> 24) & 255) == a)
				myPixels[pixel] = 0;
		}
	}
	
	public void drawAdvancedTransparentSprite(int i, int j, int opacity) {
		int k =  (int) (opacity * 2.56D);
		if (k > 256 || k < 0) {
			k = 256;
		}
		i += anInt1442;
		j += anInt1443;
		xPosition = i;
		yPosition = k;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			drawAlphaSprite(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawAdvancedSpriteCentered(int i, int j) {
		drawAdvancedSprite(i - (myWidth / 2), j - (myHeight / 2));
	}

	public void drawAdvancedSprite(int i, int j) {
	    autoUpdate();
		int k = 256;
		i += anInt1442;
		j += anInt1443;
		xPosition = i;
		yPosition = k;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			drawAlphaSprite(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	private void drawAlphaSprite(int i, int j, int ai[], int ai1[], int l,
			int i1, int j1, int k1, int l1) {
		int k;
		int j2;
		int opacity = k1;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				k1 = ((myPixels[i] >> 24) & 255);
				if (k1 > opacity) {
					k1 = opacity;
				}
				j2 = 256 - k1;
				k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00) + ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public void setTransparency(int transRed, int transGreen, int transBlue) {
		for (int index = 0; index < myPixels.length; index++)
			if (((myPixels[index] >> 16) & 255) == transRed
					&& ((myPixels[index] >> 8) & 255) == transGreen
					&& (myPixels[index] & 255) == transBlue)
				myPixels[index] = 0;
	}

	
	private void createImageFromPixels() {
		MemoryImageSource mis = new MemoryImageSource(myWidth, myHeight, ColorModel.getRGBdefault(), myPixels, 0, myWidth);
		mis.setAnimated(true);
		mis.setFullBufferUpdates(true);
		setTransparency(255, 0, 255);
	}

	public void method343() {
		DrawingArea.initDrawingArea(myHeight, myWidth, myPixels, new float[1024]);
	}

	public void method344(int i, int j, int k) {
		autoUpdate();
		for (int i1 = 0; i1 < myPixels.length; i1++) {
			int j1 = myPixels[i1];
			if (j1 != 0) {
				int k1 = j1 >> 16 & 0xff;
				k1 += i;
				if (k1 < 1)
					k1 = 1;
				else if (k1 > 255)
					k1 = 255;
				int l1 = j1 >> 8 & 0xff;
				l1 += j;
				if (l1 < 1)
					l1 = 1;
				else if (l1 > 255)
					l1 = 255;
				int i2 = j1 & 0xff;
				i2 += k;
				if (i2 < 1)
					i2 = 1;
				else if (i2 > 255)
					i2 = 255;
				myPixels[i1] = (k1 << 16) + (l1 << 8) + i2;
			}
		}

	}

	public void method345() {
		autoUpdate();
		int ai[] = new int[maxWidth * maxHeight];
		for (int j = 0; j < myHeight; j++) {
			System.arraycopy(myPixels, j * myWidth, ai, j + anInt1443
					* maxWidth + anInt1442, myWidth);
		}

		myPixels = ai;
		myWidth = maxWidth;
		myHeight = maxHeight;
		anInt1442 = 0;
		anInt1443 = 0;
	}

	public void method346(int i, int j) {
		autoUpdate();
		i += anInt1442;
		j += anInt1443;
		int l = i + j * DrawingArea.width;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = DrawingArea.width - k1;
		int i2 = 0;
		if (j < DrawingArea.topY) {
			int j2 = DrawingArea.topY - j;
			j1 -= j2;
			j = DrawingArea.topY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.width;
		}
		if (j + j1 > DrawingArea.bottomY)
			j1 -= (j + j1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int k2 = DrawingArea.topX - i;
			k1 -= k2;
			i = DrawingArea.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > DrawingArea.bottomX) {
			int l2 = (i + k1) - DrawingArea.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 <= 0 || j1 <= 0) {
		} else {
			method347(l, k1, j1, i2, i1, l1, myPixels, DrawingArea.pixels);
		}
	}

	public void method347(int i, int j, int k, int l, int i1, int k1,
			int ai[], int ai1[]) {
		int l1 = -(j >> 2);
		j = -(j & 3);
		for (int i2 = -k; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
			}

			for (int k2 = j; k2 < 0; k2++)
				ai1[i++] = ai[i1++];

			i += k1;
			i1 += l;
		}
	}

	public void drawdisabledSprite(int i, int j) {
		int k = 128;// was parameter
		xPosition = i;
		yPosition = j;
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawSprite(int i, int k, int color) {
		autoUpdate();
		int tempWidth = myWidth + 2;
		int tempHeight = myHeight + 2;
		xPosition = i;
		yPosition = k;
		int[] raster = new int[tempWidth * tempHeight];
		for (int x = 0; x < myWidth; x++) {
			for (int y = 0; y < myHeight; y++) {
				if (myPixels[x + y * myWidth] != 0)
					raster[(x + 1) + (y + 1) * tempWidth] = myPixels[x + y
							* myWidth];
			}
		}
		for (int x = 0; x < tempWidth; x++) {
			for (int y = 0; y < tempHeight; y++) {
				if (raster[(x) + (y) * tempWidth] == 0) {
					if (x < tempWidth - 1
							&& raster[(x + 1) + ((y) * tempWidth)] != 0
							&& raster[(x + 1) + ((y) * tempWidth)] != 0xffffff) {
						raster[(x) + (y) * tempWidth] = color;
					}
					if (x != 0
							&& raster[(x - 1) + ((y) * tempWidth)] != 0
							&& raster[(x - 1) + ((y) * tempWidth)] != 0xffffff) {
						raster[(x) + (y) * tempWidth] = color;
					}
					if (y < tempHeight - 1
							&& raster[(x) + ((y + 1) * tempWidth)] != 0
							&& raster[(x) + ((y + 1) * tempWidth)] != 0xffffff) {
						raster[(x) + (y) * tempWidth] = color;
					}
					if (y != 0
							&& raster[(x) + ((y - 1) * tempWidth)] != 0
							&& raster[(x) + ((y - 1) * tempWidth)] != 0xffffff) {
						raster[(x) + (y) * tempWidth] = color;
					}
				}
			}
		}
		i--;
		k--;
		i += anInt1442;
		k += anInt1443;
		int l = i + k * DrawingArea.width;
		int i1 = 0;
		int j1 = tempHeight;
		int k1 = tempWidth;
		int l1 = DrawingArea.width - k1;
		int i2 = 0;
		if (k < DrawingArea.topY) {
			int j2 = DrawingArea.topY - k;
			j1 -= j2;
			k = DrawingArea.topY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.width;
		}
		if (k + j1 > DrawingArea.bottomY) {
			j1 -= (k + j1) - DrawingArea.bottomY;
		}
		if (i < DrawingArea.topX) {
			int k2 = DrawingArea.topX - i;
			k1 -= k2;
			i = DrawingArea.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > DrawingArea.bottomX) {
			int l2 = (i + k1) - DrawingArea.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			try {
				method349(DrawingArea.pixels, raster, i1, l, k1, j1, l1, i2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void drawCenteredSprite(int x, int y) {
		drawSprite(x - myWidth / 2, y - myHeight / 2);
	}

	public void drawSprite(int i, int k) {
		autoUpdate();
		i += anInt1442;
		k += anInt1443;
		xPosition = i;
		yPosition = k;
		int l = i + k * DrawingArea.width;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = DrawingArea.width - k1;
		int i2 = 0;
		if (k < DrawingArea.topY) {
			int j2 = DrawingArea.topY - k;
			j1 -= j2;
			k = DrawingArea.topY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.width;
		}
		if (k + j1 > DrawingArea.bottomY)
			j1 -= (k + j1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int k2 = DrawingArea.topX - i;
			k1 -= k2;
			i = DrawingArea.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > DrawingArea.bottomX) {
			int l2 = (i + k1) - DrawingArea.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			method349(DrawingArea.pixels, myPixels, i1, l, k1, j1, l1, i2);
		}
	}

	public void drawSprite2(int i, int k, int color) {
		autoUpdate();
		int tempWidth = myWidth + 2;
		int tempHeight = myHeight + 2;
		xPosition = i;
		yPosition = k;
		int[] tempArray = new int[tempWidth * tempHeight];
		for (int x = 0; x < myWidth; x++) {
			for (int y = 0; y < myHeight; y++) {
				if (myPixels[x + y * myWidth] != 0)
					tempArray[(x + 1) + (y + 1) * tempWidth] = myPixels[x + y
							* myWidth];
			}
		}
		for (int x = 0; x < tempWidth; x++) {
			for (int y = 0; y < tempHeight; y++) {
				if (tempArray[(x) + (y) * tempWidth] == 0) {
					if (x < tempWidth - 1
							&& tempArray[(x + 1) + ((y) * tempWidth)] != 0
							&& tempArray[(x + 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (x > 0
							&& tempArray[(x - 1) + ((y) * tempWidth)] != 0
							&& tempArray[(x - 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y < tempHeight - 1
							&& tempArray[(x) + ((y + 1) * tempWidth)] != 0
							&& tempArray[(x) + ((y + 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y > 0
							&& tempArray[(x) + ((y - 1) * tempWidth)] != 0
							&& tempArray[(x) + ((y - 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
				}
			}
		}
		i--;
		k--;
		i += anInt1442;
		k += anInt1443;
		int l = i + k * DrawingArea.width;
		int i1 = 0;
		int j1 = tempHeight;
		int k1 = tempWidth;
		int l1 = DrawingArea.width - k1;
		int i2 = 0;
		if (k < DrawingArea.topY) {
			int j2 = DrawingArea.topY - k;
			j1 -= j2;
			k = DrawingArea.topY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.width;
		}
		if (k + j1 > DrawingArea.bottomY) {
			j1 -= (k + j1) - DrawingArea.bottomY;
		}
		if (i < DrawingArea.topX) {
			int k2 = DrawingArea.topX - i;
			k1 -= k2;
			i = DrawingArea.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > DrawingArea.bottomX) {
			int l2 = (i + k1) - DrawingArea.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			method349(DrawingArea.pixels, tempArray, i1, l, k1, j1, l1, i2);
		}
	}

	public void flashSprite(int i, int j, int k) {
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}
	public Sprite() { }
	public void autoUpdate() { }
	public void method349(int ai[], int ai1[], int j, int k, int l, int i1,
			int j1, int k1) {
		int i;// was parameter
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			for (int k2 = l; k2 < 0; k2++) {
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			k += j1;
			j += k1;
		}
	}

	public void drawSprite2(int i, int j) {
		autoUpdate();
		int k = 225;// was parameter
		i += anInt1442;
		j += anInt1443;
		xPosition = i;
		yPosition = j;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void method351(int i, int j, int ai[], int ai1[], int l, int i1,
			int j1, int k1, int l1) {
		int k;// was parameter
		int j2 = 256 - k1;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00) + ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public void method352(int i, int j, int ai[], int k, int ai1[], int i1,
			int j1, int k1, int l1, int i2) {
		autoUpdate();
		try {
			int j2 = -l1 / 2;
			int k2 = -i / 2;
			int l2 = (int) (Math.sin(j / 326.11000000000001D) * 65536D);
			int i3 = (int) (Math.cos(j / 326.11000000000001D) * 65536D);
			l2 = l2 * k >> 8;
			i3 = i3 * k >> 8;
			int j3 = (i2 << 16) + (k2 * l2 + j2 * i3);
			int k3 = (i1 << 16) + (k2 * i3 - j2 * l2);
			int l3 = k1 + j1 * DrawingArea.width;
			for (j1 = 0; j1 < i; j1++) {
				int i4 = ai1[j1];
				int j4 = l3 + i4;
				int k4 = j3 + i3 * i4;
				int l4 = k3 - l2 * i4;
				for (k1 = -ai[j1]; k1 < 0; k1++) {
					DrawingArea.pixels[j4++] = myPixels[(k4 >> 16) + (l4 >> 16)
							* myWidth];
					k4 += i3;
					l4 -= l2;
				}

				j3 += l2;
				k3 += i3;
				l3 += DrawingArea.width;
			}

		} catch (Exception _ex) {
		}
	}

	public void method353(int i, double d, int l1) {
		autoUpdate();
		// all of the following were parameters
		int j = 15;
		int k = 20;
		int l = 15;
		int j1 = 256;
		int k1 = 20;
		// all of the previous were parameters
		try {
			int i2 = -k / 2;
			int j2 = -k1 / 2;
			int k2 = (int) (Math.sin(d) * 65536D);
			int l2 = (int) (Math.cos(d) * 65536D);
			k2 = k2 * j1 >> 8;
			l2 = l2 * j1 >> 8;
			int i3 = (l << 16) + (j2 * k2 + i2 * l2);
			int j3 = (j << 16) + (j2 * l2 - i2 * k2);
			int k3 = l1 + i * DrawingArea.width;
			for (i = 0; i < k1; i++) {
				int l3 = k3;
				int i4 = i3;
				int j4 = j3;
				for (l1 = -k; l1 < 0; l1++) {
					int k4 = myPixels[(i4 >> 16) + (j4 >> 16) * myWidth];
					if (k4 != 0)
						DrawingArea.pixels[l3++] = k4;
					else
						l3++;
					i4 += l2;
					j4 -= k2;
				}

				i3 += k2;
				j3 += l2;
				k3 += DrawingArea.width;
			}

		} catch (Exception _ex) {
		}
	}

	public void method354(Background background, int i, int j) {
		autoUpdate();
		j += anInt1442;
		i += anInt1443;
		int k = j + i * DrawingArea.width;
		int l = 0;
		int i1 = myHeight;
		int j1 = myWidth;
		int k1 = DrawingArea.width - j1;
		int l1 = 0;
		if (i < DrawingArea.topY) {
			int i2 = DrawingArea.topY - i;
			i1 -= i2;
			i = DrawingArea.topY;
			l += i2 * j1;
			k += i2 * DrawingArea.width;
		}
		if (i + i1 > DrawingArea.bottomY)
			i1 -= (i + i1) - DrawingArea.bottomY;
		if (j < DrawingArea.topX) {
			int j2 = DrawingArea.topX - j;
			j1 -= j2;
			j = DrawingArea.topX;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if (j + j1 > DrawingArea.bottomX) {
			int k2 = (j + j1) - DrawingArea.bottomX;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if (!(j1 <= 0 || i1 <= 0)) {
			method355(myPixels, j1, background.palettePixels, i1,
					DrawingArea.pixels, 0, k1, k, l1, l);
		}
	}

	public void method355(int ai[], int i, byte abyte0[], int j, int ai1[],
			int k, int l, int i1, int j1, int k1) {
		int l1 = -(i >> 2);
		i = -(i & 3);
		for (int j2 = -j; j2 < 0; j2++) {
			for (int k2 = l1; k2 < 0; k2++) {
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			for (int l2 = i; l2 < 0; l2++) {
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			i1 += l;
			k1 += j1;
		}

	}
	
	public void drawSpriteWithOpacity(int xPos, int yPos, int o) {
		this.xPosition = xPos;
		this.yPosition = yPos;
		int i1 = xPos + yPos * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (yPos < DrawingArea.topY) {
			int k2 = DrawingArea.topY - yPos;
			k1 -= k2;
			yPos = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (yPos + k1 > DrawingArea.bottomY)
			k1 -= (yPos + k1) - DrawingArea.bottomY;
		if (xPos < DrawingArea.topX) {
			int l2 = DrawingArea.topX - xPos;
			l1 -= l2;
			xPos = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (xPos + l1 > DrawingArea.bottomX) {
			int i3 = (xPos + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, o, i1);
		}
	}

	public int myPixels[];
	public int myWidth;
	public int myHeight;
	public int anInt1442;
	public int anInt1443;
	public int maxWidth;
	public int maxHeight;

	public void drawTransparentSprite(int i, int j, int opacity) {
		int k = opacity;// was parameter
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.width - l1;
		int j2 = 0;
		if (j < DrawingArea.topY) {
			int k2 = DrawingArea.topY - j;
			k1 -= k2;
			j = DrawingArea.topY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.width;
		}
		if (j + k1 > DrawingArea.bottomY)
			k1 -= (j + k1) - DrawingArea.bottomY;
		if (i < DrawingArea.topX) {
			int l2 = DrawingArea.topX - i;
			l1 -= l2;
			i = DrawingArea.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > DrawingArea.bottomX) {
			int i3 = (i + l1) - DrawingArea.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public boolean isMousedOver(int spriteX, int spriteY, int mouseX, int mouseY) {
		return mouseX >= spriteX && mouseY >= spriteY && mouseX <= spriteX + myWidth && mouseY <= spriteY + myHeight;
	}
}
