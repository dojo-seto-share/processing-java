package jp.coderdojoseto.processing; 

import processing.core.PShape;

/**
 * コスチューム管理クラス
 * コスチュームは変形させるともとのオリジナルの画像が失われるので
 * その情報を保持する。
 * @author shinya
 *
 */
public class Costume implements Cloneable{
	/** 実体 */
	public PShape shape = null;
	/** 向き:初期角度は90 */
	public int rotated = 90;
	/** イメージファイル名 */
	public String fileName = null;
	/** コスチューム名 */
	public String name = null;
	/** スプライト */
	private Sprite sprite = null;
	/** Scratch */
	private Scratch scratch = null;
	
	public Costume(String name, String fileName, Sprite sprite, Scratch scratch) {
		this.shape = scratch.loadShape(fileName);;
		this.name = name;
		this.fileName = fileName;
		this.sprite = sprite;
		this.scratch = scratch;
	}
	
	/**
	 * 画像回転
	 * @param direction スクラッチ角度
	 */
	public void rotate(int direction) {
		int sa = direction - rotated;
		shape.rotate((float)Math.toRadians(sa));
		rotated = direction;
	}
	
	/**
	 * クローン
	 */
	@Override
	public Costume clone() {
		Costume ret = new Costume(name, fileName, sprite, scratch);
		ret.rotated = this.rotated;
		
		return ret;
	}
	
}
