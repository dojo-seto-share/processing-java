package jp.coderdojoseto.processing;

public class サンプル extends スクラッチ {
	
	public static void main(String[] args) {
		Scratch.main();
	}

	Sprite 猫;
	
	@Override
	public void 準備() {
		ステージ設定("backdrops/Baseball 1.svg");
		猫 = スプライトを作る("costumes/Cat/cat-a.svg");
		コスチューム追加(猫, "costumes/Cat/cat-b.svg");
		向き(猫, 15);
		もし端についたら跳ね返る(猫);

	}

	@Override
	public void 旗が押されたとき() {
		歩く(猫, 10);
		文字を書く(経過時間() + "", -200, 150, 20);
		
		if(時間(5)) {
			言う(猫, "5秒経過", 1);
		}
		
		if(時間(8, 12)) {
			言う(猫, 経過時間() + "");
		}
	}

}
