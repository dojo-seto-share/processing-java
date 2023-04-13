package jp.coderdojoseto.processing;

import java.util.LinkedHashMap;

/**
 * スプライトオブジェクト
 * @author shinya
 *
 */
public class Sprite {
	
	/** 表示対象アプレット */
	private Scratch scratch = null;
	
	/** コスチュームリスト */
	private LinkedHashMap<String, Costume> costumeList = new LinkedHashMap<>();
	/** 現在のコスチューム */
	private Costume currentCostume = null;
	
	/** サウンドリスト */
	private LinkedHashMap<String, Sound> soundList = new LinkedHashMap<>();
	/** 代表サウンド */
	private Sound masterSound = null;
	
	
	/** セリフ */
	private Speech speech = null;
	
	/** スプライトのX座標 */
	public float x = 0;
	/** スプライトのY座標 */
	public float y = 0;
	/** スプライトのZ座標 */
	//public float z = 0;
	/** スプライトのサイズ(%) */
	public float size = 100;
	/** スプライトの角度 */
	public float direction = 90;
	/** 表示する */
	public boolean isShow = true;
	/** 端についたら跳ね返る */
	public boolean isOnEdgeBounce = false;
	/** 回転スタイル */
	public enum ROTATION_STYLE {
		ALL_AROUND,
		LEFT_RIGHT,
		DONT_ROTATION,
	}
	/** 回転スタイル */
	public ROTATION_STYLE rotationStyle = ROTATION_STYLE.ALL_AROUND;
	/** 当たり判定モード */
	public enum TOUCH_SCOPE {
		FULL,
		RECTANGLE,
		SQUARE,
		CIRCLE,
	}
	/** 当たり判定 */
	private TOUCH_SCOPE touchMode = TOUCH_SCOPE.FULL;
	/** 当たり判定幅（半分） */
	private float touchW = 0;
	/** 当たり判定高さ（半分） */
	private float touchH = 0;
	/** 当たり判定距離（半径） */
	private float touchDistance = 0;
	
	
	@Override
	public String toString() {
		String sep = "\n";
		return "x=" + x + sep
				+"y=" + y + sep
				+"rx=" + Scale.toRealX(x) + sep
				+"ry=" + Scale.toRealX(y) + sep				
				+"size=" + size + sep
				+"dir=" + direction + sep
				+"isShow=" + isShow + sep
				;
	}
	
	/**
	 * 幅：スクラッチサイズ
	 * @return
	 */
	public float width() {
		return size / 100 * currentCostume.shape.getWidth();
	}
	/**
	 * 高さ：スクラッチサイズ
	 * @return
	 */
	public float height() {
		return size / 100 * currentCostume.shape.getHeight();
	}
	
	

	//======================================
	// 管理関数
	//======================================
	/**
	 * スプライトを作る
	 * @param applet
	 * @param name 名前
	 * @param costumeFileName ファイル名
	 */
	public Sprite(Scratch applet, String costumeFileName) {
		this(applet,
				costumeFileName.substring(0, costumeFileName.lastIndexOf(".")),
				costumeFileName);
	}
	
	/**
	 * スプライトを作る
	 * @param applet
	 * @param name 名前
	 * @param costumeFileName ファイル名
	 */
	public Sprite(Scratch scratch, String name, String costumeFileName) {
		this.scratch = scratch;
		
		Costume cos = new Costume(name, costumeFileName, this, this.scratch);
		costumeList.put(name, cos);
		currentCostume = cos;
		speech = new Speech(scratch, this);
		this.touchFull();
	}
	
	/**
			
		}
	 * コスチュームを追加する
	 * @param costumeFileName ファイル名
	 */
	public void addCostume(String costumeFileName) {
		String name = costumeFileName.substring(0, costumeFileName.lastIndexOf("."));
		addCostume(name, costumeFileName);
	}
	
	/**
	 * コスチュームを追加する
	 * @param name 名前
	 * @param costumeFileName ファイル名
	 */
	public void addCostume(String name, String costumeFileName) {
		Costume cos = new Costume(name, costumeFileName, this, this.scratch);
		costumeList.put(name, cos);
	}
	
	
	/**
	 * スプライトを表示する。
	 */

	public void display() {
		if(isShow) {
			double w = width() * Scale.screenRate;
			double h = height() * Scale.screenRate;
			if(rotationStyle.equals(ROTATION_STYLE.ALL_AROUND)){
				
				// 回転補正
				double wrad = Math.toRadians(Scale.toDegAngle(direction));
				double wx = Math.cos(wrad) * (w/2);
				double wy = Math.sin(wrad) * (h/2);
				double hrad = Math.toRadians(Scale.toDegAngle(direction + 90));
				double hx = Math.cos(hrad) * (w/2);
				double hy = Math.sin(hrad) * (h/2);
				
				// 画像回転
				currentCostume.rotate((int)direction);
				
				scratch.shape(currentCostume.shape, 
						(float)(Scale.toRealX(x) - (wx + wy)),
						(float)(Scale.toRealY(y) + (hx + hy)),
						(float)w,
						(float)h);
			} else if(rotationStyle.equals(ROTATION_STYLE.LEFT_RIGHT)){

				currentCostume.rotate(90);
				if(direction >= 0) {
					scratch.shape(currentCostume.shape, 
							(float)(Scale.toRealX(x) - (w / 2)),
							(float)(Scale.toRealY(y) - (h / 2)),
							(float)(w),
							(float)h);
				} else {
					scratch.shape(currentCostume.shape, 
							(float)(Scale.toRealX(x) + (w / 2)),
							(float)(Scale.toRealY(y) - (h / 2)),
							(float)(w * -1),
							(float)h);
				}
			} else {
				currentCostume.rotate(90);
				scratch.shape(currentCostume.shape, 
						(float)(Scale.toRealX(x) - (w / 2)),
						(float)(Scale.toRealY(y) - (h / 2)),
						(float)(w),
						(float)h);
			}
			//System.out.println("rad="+rad);
			//System.out.println("hx="+hx);
			//System.out.println("hy="+hy);
			
			// 吹き出し表示
			speech.show();
		}

		//System.out.println(toString());
	}

	/**
	 * 音を追加する 
	 * @param name 名前
	 * @param soundFileName 音声ファイル名
	 */
	public void addSound(String name, String soundFileName) {
		Sound sound = new Sound(name, soundFileName);
		soundList.put(name, sound);
		if(masterSound == null) {
			masterSound = sound;
		}
	}
	
	/**
	 * 音を追加する
	 * @param soundFileName 音声ファイル名
	 */
	public void addSound(String soundFileName) {
		String name = soundFileName.substring(0, soundFileName.lastIndexOf("."));
		addSound(name, soundFileName);
	}

	
	//======================================
	// ブロック関数
	//======================================
	

	
	//----------------------------------------------------------------------
	// 動き
	//----------------------------------------------------------------------
	
	/**
	 * 歩動かす
	 * @param step 歩数
	 */
	public void move(float steps) {
		// 角度（スクラッチは、0度が90になる）
		double rad = Math.toRadians(direction - 90);
		// X移動距離
		double xMove = steps * Math.cos(rad);
		// Y移動距離
		double yMove = steps * Math.sin(rad) * -1;
		// 座標移動
		move(Math.round(xMove), Math.round(yMove));
	}

	/**
	 * 回す
	 * @param degrees 角度
	 */
	public void turn(float degrees) {
		// 画像を回転
		direction(direction + degrees);
		
	}

	/**
	 * 移動する
	 * @param x 移動するX座標
	 * @param y 移動するY座標
	 */
	public void goTo(float x, float y) {
		// 画面橋を上限とする
		if(Math.abs(x) > (Scale.screenWidth + width()) / 2 ) {
			x = Math.signum(x) * (Scale.screenWidth + width()) / 2;
		}
		
		if(Math.abs(y) > (Scale.screenHeight + height()) / 2 ) {
			y = Math.signum(y) * (Scale.screenHeight + height()) / 2;
		}
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * どこかの場所へ行く
	 */
	public void goToRandom() {
		this.x = (float)(Scale.screenWidth * (Math.random() - 0.5));
		this.y = (float)(Scale.screenHeight * (Math.random() - 0.5));
	}
	
	/**
	 * マウスの場所へ行く
	 */
	public void goToMouse() {
		this.x = Scale.toScratchX(scratch.mouseX);
		this.y = Scale.toScratchY(scratch.mouseY);
	}
	
	/**
	 * 向き
	 * @param direction
	 */
	public void direction(float direction) {
		direction =  direction % 360;
		if(direction > 180) {
			direction = direction - 360;
		}
		this.direction = direction;
	}
	
	/**
	 * マウスの方に向きを変える
	 */
	public void directionToMouse() {
		// ポインター位置
		double mx = Scale.toScratchX(scratch.mouseX);
		double my = Scale.toScratchY(scratch.mouseY);
		
		double rad = Math.atan(mx/my);
		float newDirection = (int)Math.toDegrees(rad);
		
		if( my < 0 ) {
			newDirection += 180;
		}
		direction(newDirection);
		
//		System.out.println("mx="+ mx);
//		System.out.println("my="+ my);
//		System.out.println("rad="+ rad);
//		System.out.println("dir="+ direction);
	}
	
	/**
	 * 位置を移動する。
	 * @param moveX
	 * @param moveY
	 */
	public void move(float moveX, float moveY) {
		float newX = x + moveX;
		float newY = y + moveY;
		if(isOnEdgeBounce) {
			// 半分サイズ
			float hWidth = width()/2;
			float hHeight = height()/2;
			
			
			// X端判定
			if( Math.abs(newX) > Scale.maxX - hWidth ){
				float bounceX = (Scale.maxX - (hWidth + hWidth + (Math.abs(newX) - Scale.maxX)));
				newX = bounceX * (newX > 0 ? 1 : -1);
				
				// 向き反転
				direction(direction * -1);
			}

			// Y端判定
			if( Math.abs(newY) > Scale.maxY - hHeight ){
				float bounceY = (Scale.maxY - (hHeight + hHeight + (Math.abs(newY) - Scale.maxY)));
				newY = bounceY * (newY > 0 ? 1 : -1);
				
				// 向き反転
				direction(((direction - 90) * -1) + 90);
			}
		}

		goTo(newX, newY);

	}
	
	
	//----------------------------------------------------------------------
	// 見た目
	//----------------------------------------------------------------------
	
	/**
	 * 言う
	 * @param message 文字
	 * @param seconds 時間
	 * @param size 文字の大きさ
	 */
	public void say(String message, float seconds, int size) {
		speech.newMsg(message, seconds, size);
	}
	
	/**
	 * 言う
	 * @param message 文字
	 * @param seconds 時間
	 */
	public void say(String message, float seconds) {
		speech.newMsg(message, seconds, 20);
	}
	
	/**
	 * 言う
	 * @param message 文字
	 */
	public void say(String message) {
		speech.newMsg(message, 0, 20);
	}
	
	/**
	 * 大きさをずつ変える
	 * @param size
	 */
	public void changeSize(float size) {
		this.size += size;
	}
	
	/**
	 * 大きさを%にする。
	 * @param size
	 */
	public void setSize(float size) {
		this.size = size;
	}
	
	/**
	 * 表示する
	 */
	public void show() {
		this.isShow = true;
	}
	
	/**
	 * 隠す
	 */
	public void hide() {
		this.isShow = false;
	}
	
	/**
	 * 最前面に移動
	 */
	public void goToFront() {
		scratch.goToFront(this);
	}
	
	/**
	 * 最後面に移動
	 */
	public void goToBack() {
		scratch.goToBack(this);
	}
	
	/**
	 * 前層に移動
	 * @param layers 移動層数
	 */
	public void goForward(int layers) {
		scratch.goForward(this, layers);
	}
	
	/**
	 * 後層に移動
	 * @param layers 移動層数
	 */
	public void goBackward(int layers) {
		scratch.goBackward(this, layers);
	}
	
	/**
	 * 次のコスチュームに変更する。
	 */
	public void nextCostume() {
		Costume next = null;
		boolean isHit = false;
		for(Costume p : costumeList.values()) {
			if(isHit) {
				next = p;
				break;
			}
			if(p == currentCostume) {
				isHit = true;
			}
		}
		if(next == null) {
			currentCostume = costumeList.values().iterator().next();
		} else {
			currentCostume = next;
		}
	}
	
	/**
	 * コスチュームを選択する。
	 * @param name
	 */
	public void seletCostume(String name) {
		Costume p =  costumeList.get(name);
		if(p != null) {
			currentCostume = costumeList.get(name);
		}
	}

	//----------------------------------------------------------------------
	// 音
	//----------------------------------------------------------------------
	
	/**
	 * 音を鳴らす
	 * @param name
	 */
	public void play(String name) {
		if(soundList.containsKey(name)) {
			soundList.get(name).play();
		}
	}
	/**
	 * 音を鳴らす
	 * @param name
	 */
	public void play() {
		if(masterSound != null){
			masterSound.play();
		}
	}
	
	/**
	 * 終わるまで音を鳴らす
	 * @param name
	 */
	public void playUntilDone(String name) {
		if(soundList.containsKey(name)) {
			soundList.get(name).playUntilDone();
		}
	}
	
	/**
	 * 終わるまで音を鳴らす
	 */
	public void playUntilDone() {
		if(masterSound != null){
			masterSound.playUntilDone();
		}
	}
	
	/**
	 * 音量をずつ変更する
	 * @param name
	 * @param volume
	 */
	public void changeVolume(String name, int volume) {
		if(soundList.containsKey(name)) {
			soundList.get(name).changeVolume(volume);
		}
	}
	
	/**
	 * 音量をずつ変更する
	 * @param name 音の名前
	 * @param volume 音量
	 */
	public void changeVolume(int volume) {
		if(masterSound != null){
			masterSound.changeVolume(volume);
		}
	}
	
	/**
	 * 音量を%にする
	 * @param name 音の名前
	 * @param volume 音量
	 */
	public void volume(String name, int volume) {
		if(soundList.containsKey(name)) {
			soundList.get(name).volume(volume);
		}
	}
	
	/**
	 * 音量を%にする
	 * @param volume 音量
	 */
	public void volume(int volume) {
		if(masterSound != null){
			masterSound.volume(volume);
		}
	}
	
	//----------------------------------------------------------------------
	// イベント
	//----------------------------------------------------------------------
	/*
	 * 旗が押されたとき：作成内
	 * キーが押されたとき：標準のkeyPressedでDraw内で判定か、keyPressed()関数を使用する
	 * このスプライトが押されたとき；自作のtouchingMouseとmousePressed()を利用する。
	 * 背景が〜になったとき、これは実装しない。
	 * メッセージ系：変数や関数呼び出しで工夫する
	 */
	
	//----------------------------------------------------------------------
	// 制御
	//----------------------------------------------------------------------
	
	/**
	 * クローンを作る
	 */
	@Override
	public Sprite clone() {
		Sprite ret = new Sprite(scratch, currentCostume.name, currentCostume.fileName);
		// プロパティセット
		ret.x = this.x;
		ret.y = this.y;
		ret.size = this.size;
		ret.direction = this.direction;
		ret.isShow = this.isShow;
		ret.isOnEdgeBounce = this.isOnEdgeBounce;
		ret.rotationStyle = this.rotationStyle;
		ret.touchMode = this.touchMode;
		ret.touchW = this.touchW;
		ret.touchH = this.touchH;
		
		// コスチューム
		for(Costume costume : costumeList.values()) {
			if(!costume.name.equals(currentCostume.name)) {
				Costume newCos = new Costume(costume.name, costume.fileName, ret, scratch);
				ret.costumeList.put(costume.name, newCos);
			}
		}
		// 音
		for(Sound sound : soundList.values()) {
			Sound newSound = new Sound(sound.name, sound.soundFile.toString());
			ret.soundList.put(newSound.name, newSound);
			if(masterSound != null && masterSound.name.equals(sound.name)) {
				ret.masterSound = newSound;
			}
		}
		
		return ret;
	}
	
	/**
	 * このクローンを削除する
	 */
	public void deleteClone() {
		scratch.deleteClone(this);
	}
	
	//----------------------------------------------------------------------
	// 調べる
	//----------------------------------------------------------------------
	/**
	 * 接触判定
	 * @param pointX 判定位置X
	 * @param pointY 判定位置Y
	 * @return
	 */
	private boolean touchSprite(float pointX, float pointY) {
		boolean ret = false;
		
		switch(this.touchMode) {
		case FULL:
		case RECTANGLE:
		case SQUARE:
			//判定
			if(
				(x > this.x - touchW && x < this.x + touchW)
				&&
				(y > this.y - touchH && y < this.y + touchH)
			) {
				ret = true;
			} else {
				ret = false;
			}
			break;
			
		case CIRCLE:
			// 距離計算
			double distance = Math.sqrt(
					(x - this.x) * (x - this.x)
					+
					(y - this.y) * (y - this.y)
					);
			if(distance < this.touchDistance) {
				ret = true;
			} else {
				ret = false;
			}
			break;
		}
		
		return ret;
	}
	
	/**
	 * 接触範囲を全体にする
	 */
	public void touchFull() {
		this.touchMode = TOUCH_SCOPE.FULL;
		this.touchW = this.width() / 2;
		this.touchH = this.height() / 2;
	}
	/**
	 * 接触範囲を長方形にする
	 * @param width 幅
	 * @param height 高さ
	 */
	public void touchRectangle(float width, float height) {
		this.touchMode = TOUCH_SCOPE.RECTANGLE;
		this.touchW = width / 2;
		this.touchH = height / 2;
	}
	/**
	 * 接触範囲を正方形にするSQUARE
	 * @param size サイズ
	 */
	public void touchSquare(float size) {
		this.touchMode = TOUCH_SCOPE.SQUARE;
		this.touchW = size / 2;
		this.touchH = size / 2;
		
	}
	/**
	 * 接触範囲を円形にする
	 * @param size サイズ
	 */
	public void touchCircle(float size) {
		this.touchMode = TOUCH_SCOPE.CIRCLE;
		this.touchW = size / 2;
		this.touchH = size / 2;
		this.touchDistance = size / 2;
	}
	
	
	/**
	 * マウスのポインターに触れた
	 * @return
	 */
	public boolean touchingMouse() {
		return touchSprite(
				Scale.toScratchX(scratch.mouseX),
				Scale.toScratchY(scratch.mouseY));
	}
	
	/**
	 * 端に触れた
	 * @return
	 */
	public boolean touchingEdge() {
		// 上
		if(touchSprite(this.x, Scale.minY)) {
			return true;
		}
		// 下
		else if(touchSprite(this.x, Scale.maxY)) {
			return true;
		}
		// 右
		else if(touchSprite(Scale.maxX, this.y)) {
			return true;
		}
		// 左
		else if(touchSprite(Scale.minX, this.y)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean touching(Sprite s) {
		// 両方円形の場合は半径同士でチェック
		if(this.touchMode.equals(TOUCH_SCOPE.CIRCLE)
			&&
			s.touchMode.equals(TOUCH_SCOPE.CIRCLE)) {
			double distance = Math.sqrt(
					(s.x - this.x) * (s.x - this.x)
					+
					(s.y - this.y) * (s.y - this.y)
					);
			if(distance < s.touchDistance + this.touchDistance) {
				return true;
			} else {
				return false;
			}
		}
		// 両方円形でない場合
		//  ただし、この場合、片方が円形の場合、円と角の当たり判定が広くなるがまぁ誤差とする。
		else {
			// 方形の上下に、円形の距離を足した部分に、端があれば接触
			if(
				// 自分の左端に対して、相手の右端が 右（大きい）
				// かつ
				// 自分の右端に対して、相手の左端が 左（小さい）
				(this.x - this.touchW < s.x + s.touchW && this.x + this.touchW > s.x - s.touchW)
				&&
				// 自分の下端に対して、相手の上端が 上（大きい）
				// かつ
				// 自分の上端に対して、相手の下端が 下（小さい）
				(this.y - this.touchH < s.y + s.touchH && this.y + this.touchH > s.y - s.touchH)
			) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * マウスまでの距離
	 * @return
	 */
	public float distanceMouse() {
		return (float)Math.sqrt(
				Math.pow(this.x - Scale.toScratchX(scratch.mouseX), 2)
				+ Math.pow(this.y - Scale.toScratchY(scratch.mouseY), 2)); 
	}
	
	/**
	 * スプライトまでの距離
	 * @param s
	 * @return
	 */
	public float distance(Sprite s) {
		return (float)Math.sqrt(
				Math.pow((this.x - s.x), 2) +
				Math.pow((this.y - s.y), 2));
	}
	
	// 聞いて待つ系は、マルチスレッド化対応後に考える
	// 通常の使い方ならｍAWSの標準ポップアップで十分とは思う
	
	
}
