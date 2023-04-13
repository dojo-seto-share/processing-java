package jp.coderdojoseto.processing;

import jp.coderdojoseto.processing.Sprite.ROTATION_STYLE;

public class Test extends Scratch {

	public static void main(String[] args) {
		Scratch.main();
	}

	Sprite cat;
	Sprite cat2;
	String msg;
	
	/**
	 * スプライトや背景準備
	 */
	public void setup() {
		setStage("backdrops/Baseball 1.svg");
		cat = createSprite("costumes/Cat/cat-a.svg");
		addCostume(cat, "costumes/Cat/cat-b.svg");
		cat2 = createSprite("costumes/Cake/cake-a.svg");
		cat.addSound("pon", "sounds/Pop.wav");
		turnRight(cat, 15);
		turnRight(cat2, 40);
		//cat.direction(90);
		cat.isOnEdgeBounce = true;
		cat.rotationStyle = ROTATION_STYLE.LEFT_RIGHT;
		cat2.isOnEdgeBounce = true;
		cat2.say("aaaa", 10, 30);
		//textFont(loadFont("IPAexGothic-48.vlw"));
		//msg = askAndWait("こんにちは");
		cat.touchCircle(100);
		cat2.touchCircle(100);

	}

	/**
	 * 旗が押されたら
	 */
	public void pressFlag() {
		//int x = (int) (random(-100f, 100f));
		//int y = (int) (random(-100f, 100f));
		//goTo(cat, x, y);
		//move(cat, 1);
		textSize(30);
		text(totalTimeSec,100,100);
		//turnRight(cat, 5);
		cat.goToMouse();
		//cat.move(10);
		//goTo(cat, 100, 100);
		//cat2.move(3);
		cat.nextCostume();
		//cat.say("おはよう");
		//goTo(cat, 0, 0);
		
		//cat.directionToMouse();
		//cat2.directionToMouse();
		cat2.turn(12);
		cat2.goToBack();
		
		cat.play();
		
		//cat2.say(msg);
		if(timeNext(3)) {
			cat2.say("3秒経過", 1);
		}
		if(timeNext(5)) {
			cat2.say("5秒経過", 1);
		}
		if(touching(cat2, cat)) {
			cat.say("あたった", 1);
		}
		
		
		//System.out.println(cat.toString());
		
	}
}
