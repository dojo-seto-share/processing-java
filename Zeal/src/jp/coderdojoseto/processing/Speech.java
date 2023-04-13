package jp.coderdojoseto.processing;


public class Speech {
	
	//=================
	// 定数
	//=================
	private static final float POP_SIZE_RATE = .70f;
	
	//=================
	// プロパティ
	//=================
	private Scratch scratch = null;
	private Sprite sprite = null;
	private String msg = null;
	private int size = 0;
	private float removeTime = 0;
	private boolean isSpeak = false;
	public Speech(Scratch applet, Sprite sprite) {
		this.scratch = applet;
		this.sprite = sprite;
	}
	
	public void newMsg(String msg, float seconds, int size) {
		this.msg = msg;
		this.size = size;
		this.removeTime = scratch.totalTimeSec + seconds;
		this.isSpeak = true;
	}
	
	public void show() {
		if(isSpeak || removeTime > scratch.totalTimeSec) {
			// 吹き出し
			scratch.push();
			scratch.stroke(180);
			scratch.fill(255);
			scratch.rect(
					Scale.toRealX(sprite.x + (sprite.width() / 4) - 2),
					Scale.toRealY(sprite.y + (sprite.height() / 2) + (size / 2 + 2)),
					Scale.toRealSize((int)(size * POP_SIZE_RATE) * (msg.length())),
					Scale.toRealSize((int)(size * POP_SIZE_RATE)),
					15);
			scratch.textSize(size);
			scratch.fill(0);
			scratch.text(
					msg,
					Scale.toRealX(sprite.x + (sprite.width() / 4)),
					Scale.toRealY(sprite.y + (sprite.height() / 2)));
			scratch.pop();
			isSpeak = false;
		}
	}
}
