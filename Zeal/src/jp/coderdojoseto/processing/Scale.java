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
	public static final float maxX = screenWidth / 2;
	// スクラッチX下限
	public static final float minX = maxX * -1;
	// スクラッチY上限
	public static final float maxY = screenHeight / 2;
	// スクラッチY下限
	public static final float minY = maxY * -1;
	
	// 100%時のDPI
	public static final int standardDPI = 96;
	
	// 画面拡大率
	public static final float magnification = 2; 

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
	public static float zeroX = (screenWidth / 2) * screenRate;
	
	// 中心座標Y
	public static float zeroY = (screenHeight / 2) * screenRate;
	
	public static float toRealSize(float scratchSize) {
		return scratchSize * screenRate;
	}
	
	public static float toScratchSize(float realSize) {
		return realSize / screenRate;
	}
	
	public static float toRealX(float scratchX) {
		return scratchX * screenRate + zeroX;
	}
	
	public static float toRealY(float scratchY) {
		return scratchY * -1 * screenRate + zeroY;
	}
	
	public static float toScratchX(float realX) {
		return (realX - zeroX) / screenRate;
	}
	
	public static float toScratchY(float realY) {
		return (realY - zeroY) / screenRate * -1;
	}
	
	public static float toDegAngle(float direction) {
		return (direction * -1) + 90;
		//return Math.toRadians(dig);
	}
	
}
