package jp.coderdojoseto.processing;

import jp.coderdojoseto.processing.Sprite.ROTATION_STYLE;

public class Test extends Scratch {

	public static void main(String[] args) {
		Scratch.main();
	}

	Sprite cat;
	Sprite cake;
	
	/**
	 * スプライトや背景準備
	 */
	public void setup() {
		setStage("backdrops/Baseball 1.svg");
		cat = createSprite("costumes/Cat/cat-a.svg");
		addCostume(cat, "costumes/Cat/cat-b.svg");
		cake = createSprite("costumes/Cake/cake-a.svg");
		cat.addSound("pon", "sounds/Pop.wav");
		turnRight(cat, 15);
		cat.isOnEdgeBounce = true;
		cat.rotationStyle = ROTATION_STYLE.LEFT_RIGHT;
		cake.isOnEdgeBounce = true;
		//textFont(loadFont("IPAexGothic-48.vlw"));
		//msg = askAndWait("こんにちは");
		cat.touchCircle(100);
		cake.touchCircle(100);

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
		cake.turn(12);
		cake.goToBack();
		
		cat.play();
		
		//cat2.say(msg);
		if(timeNext(3)) {
			cake.say("3秒経過", 1);
		}
		if(timeNext(5)) {
			cake.say("5秒経過", 1);
		}
		if(touching(cake, cat)) {
			cat.say("あたった", 1);
		}
		
		
		//System.out.println(cat.toString());
		
	}
}
