package jp.coderdojoseto.processing;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 画面のDPIから表示する解像度を計算する。
 * @author shinya
 *
 */
public class Scale {
	
	// スクラッチ画面Xサイズ
	public static final int screenWidth = 480;
	// スクラッチ画面Yサイズ
	public static final int screenHeight = 360;
	// スクラッチX上限
	public static final int maxX = screenWidth / 2;
	// スクラッチX下限
	public static final int minX = maxX * -1;
	// スクラッチY上限
	public static final int maxY = screenHeight / 2;
	// スクラッチY下限
	public static final int minY = maxY * -1;
	
	// 100%時のDPI
	public static final int standardDPI = 96;
	
	// 画面拡大率
	public static final int magnification = 2; 

	// 画面サイズ
	public static final Dimension realScreenSize;
	
	// DPI
	public static final int dpi;
	
	static {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//スクリーンサイズ
		realScreenSize = toolkit.getScreenSize();
		//スクリーン解像度（1インチあたりのドット数）
		dpi = toolkit.getScreenResolution();
	}
	// 画面掛率
	public static final float screenRate = (float)magnification  * ((float)dpi / (float)standardDPI);;
	
	// 中心座標X
	public static int zeroX = (int)((screenWidth / 2) * screenRate);
	
	// 中心座標Y
	public static int zeroY = (int)((screenHeight / 2) * screenRate);
	
	public static float toRealSize(int scratchSize) {
		return (int)(scratchSize * screenRate);
	}
	
	public static int toScratchSize(double realSize) {
		return (int)(realSize / screenRate);
	}
	
	public static float toRealX(int scratchX) {
		return (int)(scratchX * screenRate) + zeroX;
	}
	
	public static float toRealY(int scratchY) {
		return (int)(scratchY * -1 * screenRate) + zeroY;
	}
	
	public static int toScratchX(double realX) {
		return (int)((realX - zeroX) / screenRate);
	}
	
	public static int toScratchY(double realY) {
		return (int)((realY - zeroY) / screenRate * -1);
	}
	
	public static double toDegAngle(int direction) {
		return (direction * -1) + 90;
		//return Math.toRadians(dig);
	}
	
}
