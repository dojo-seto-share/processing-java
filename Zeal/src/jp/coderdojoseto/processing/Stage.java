package jp.coderdojoseto.processing;

import java.awt.Image;
import java.io.File;
import java.util.LinkedHashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Stage {
	
	private LinkedHashMap<String, PImage> backdrops = new LinkedHashMap<>();
	private PImage currentImage = null;
	private PApplet applet = null;
	
	public Stage(PApplet applet) {
		this.applet = applet;
	}
	
	/**
	 * 背景画像を追加する。
	 * @param name
	 * @param imageFile
	 */
	@SuppressWarnings("deprecation")
	public void addBackdrop(String name, File imageFile) {
		PImage pimage = null;
		if(imageFile.getName().toLowerCase().endsWith("svg") 
				|| imageFile.getName().toLowerCase().endsWith("svgz")){ 
			
			Image image =  Util.transcodeSVGToBufferedImage(imageFile, applet.width, applet.height);
			pimage = new PImage(image);
		} else {
			pimage = applet.loadImage(imageFile.toString());
			pimage.resize(applet.width, applet.height);
		}
		
		backdrops.put(name, pimage);
		if(currentImage == null) {
			currentImage = pimage;
			applet.background(currentImage);
		}
	}
	 
	
	
	public void clear() {
		if(currentImage == null) {
			applet.background(0xffffffff);
		}else {
			applet.background(currentImage);
		}
	}
	
	
	

}
