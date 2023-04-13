package jp.coderdojoseto.processing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import processing.core.PApplet;

public abstract class Scratch extends PApplet{
	
	// ==========================
	// 定数
	// ==========================	
	/** フレームレート */
	public static final float SCRATCH_FPS = 30;
	/** 1FPSの秒数 */
	public static final float FPS_SECONDS = 1 / SCRATCH_FPS;

	
	
	// ==========================
	// 変数
	// ==========================	
	
	/** ステージ */
	private Stage stage = null;
	/** スプライトリスト */
	private List<Sprite> sprites = new ArrayList<>();
	/** 初回表示フラグ（setupの変わりに使う） */
	private boolean isFirstDraw = true;
	
	// ====================================================
	// Scratch用Processing変更メソッド
	// ====================================================
	/**
	 * 起動用メイン処理
	 */
	protected static void main() {
		// 継承しているクラスのクラス名を取得する
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		PApplet.main(className);
	}
	/**
	 * 画面表示
	 * そのまま使用すると、スプライトが消えないので、
	 * 一旦きれいにして、スプライト分塗りつぶす。
	 * Processingは60フレームなので、Scratchにあわせて30フレームにする
	 * 奇数フレームは、スプライトの位置計算のみ行い、偶数フレームは、表示だけ行う
	 */
	public void draw() {
		// 初回は初期処理を行う。
		// setUp()がその役割だけど、オーバーライドするのでここで定義する。
		if(isFirstDraw) {
			init();
			isFirstDraw = false;

		} else {
			// 経過時間設定
			countTotalTimeSec();
		}
		
		// 画面クリア
		stage.clear();
		
		// 呼び出し
		pressFlag();
		
		// スプライト表示
		display();
	}
	


	
	/**
	 * 旗が押されたとき
	 * 基本的にはdrawを使用せず、pressFlagのみを使用する。
	 */
	abstract void pressFlag();
	
	

	//=============================================================
	// 初期処理関係
	//=============================================================

	/**
	 * コンストラクタ
	 */
	public Scratch() {
		super();
		// ステージの初期化
		this.stage = new Stage(this);
	}
	
	/**
	 * settingsの上書き
	 */
	public void settings() {
		// ProcessingJavaでは、画面サイズの設定はsetUpでなく、settingsで行う必要がある。
		
		// 初期画面の大きさはScratchに合わせる。
		size((int)(Scale.screenRate * Scale.screenWidth),
			  (int)(Scale.screenRate * Scale.screenHeight));
	}
	
	/**
	 * setUp処理
	 */
	private void init() {
		// フォント設定
		textFont(loadFont("IPAexGothic-48.vlw"));
		
		// FPS設定
		frameRate(30);
	}
	
	/**
	 * ステージ初期設定
	 * @param backDropFileName
	 */
	public void setStage(String backDropFileName) {
		File backDropFile = new File(backDropFileName);
		if(!backDropFile.exists()) {
			System.err.println("背景画像ファイルが見つかりません。 "+backDropFile.getAbsolutePath());
			return;
		}
		stage.addBackdrop("1", backDropFile);
	}
	
	/**
	 * スプライト作成
	 * @param costumeFileName
	 * @return
	 */
	protected Sprite createSprite(String costumeFileName) {
		Sprite sprite = new Sprite(this, costumeFileName);
		sprites.add(sprite);
		return sprite;
	}
	
	//=============================================================
	// 裏方共通処理
	//=============================================================
	
	/**
	 * 画面を表示する
	 */
	private void display() {

		// スプライト再表示
		for(Sprite sprite : sprites) {
			sprite.display();
		}
	}
	
	/**
	 * スプライトの順番を入れ替える
	 * @param a
	 * @param b
	 */
	private void changeSpriteLayer(int a, int b) {
		Sprite as = sprites.get(a);
		Sprite bs = sprites.get(b);
		sprites.set(a, bs);
		sprites.set(b, as);
	}
	
	//=============================================================
	// 時間設定関係
	//=============================================================

	// 開始からの秒数
	public float totalTimeSec = 0;
	// timeNext時の前回時間保持
	private float beforeTimeSec = 0;
	// 1フレーム内のtimeNext時の呼び出し順番保持
	private int nextTimeCallCount = 0;
	// これまでに完了したtimeNextの順番保持
	private int nextTimeFinCount = 0;
	
	/**
	 * 開始からの経過時間を設定する
	 */
	private void countTotalTimeSec() {
		// リアル時間だと、デバッグがしにくいのでフレーム計算にする
		totalTimeSec += FPS_SECONDS;
		nextTimeCallCount=0;
	}
	
	/**
	 * 開始してから指定の時間になったか判定する
	 * @param seconds 検査する時間
	 * @return その時間の場合はTrue
	 */
	public boolean time(float seconds) {
		return time(seconds, seconds + (FPS_SECONDS * 1.1f));
	}
	
	/**
	 * 開始してから、指定した時間の範囲に含まれるか判定する
	 * @param startSeconds 始まりの時間
	 * @param endSeconds 終わりの時間
	 * @return 範囲以内の場合はTrue
	 */
	public boolean time(float startSeconds, float endSeconds) {
		return startSeconds <= totalTimeSec && totalTimeSec < endSeconds;
	}
	
	/**
	 * 前回の検査時間から指定した時間が経過したのかを判定する
	 * @param seconds 前回からの経過時間
	 * @return
	 */
	public boolean timeNext(float seconds) {
		return timeNext(seconds, seconds + (FPS_SECONDS * 1.1f));
	}
	
	/**
	 * 前回の検査時間から指定した範囲時間が経過したのかを判定する
	 * @param startSeconds 前回からの始まり時間
	 * @param endSeconds 前回からの終わり時間
	 * @return
	 */
	public boolean timeNext(float startSeconds, float endSeconds) {
		// 呼び出された順番(フレームごとに0に戻る)
		nextTimeCallCount++;
		// 自分の順番なのか判定する
		if(nextTimeFinCount == (nextTimeCallCount - 1)) {
		
			boolean isNow = time(beforeTimeSec + startSeconds, beforeTimeSec + endSeconds);
			if(isNow) {
				return true;
			}else {
				// 自分の番が終わったら次へ回す
				if(totalTimeSec >= beforeTimeSec + endSeconds) {
					beforeTimeSec = totalTimeSec;
					nextTimeFinCount++;
				}
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	
	
	//=============================================================
	// スプライトブロック
	//=============================================================
	
	//=============================================================
	// 動き
	//=============================================================

	
	
	/**
	 * 歩動かす
	 * @param s スプライト
	 * @param step 歩数
	 */
	public void move(Sprite s, int steps) {
		s.move(steps);
	}
	
	/**
	 * 右に、度回す
	 * @param s スプライト
	 * @param degrees 回す角度
	 */
	public void turnRight(Sprite s, int degrees) {
		s.turn(degrees);
	}
	
	/**
	 * 左に、度回す
	 * @param s スプライト
	 * @param degrees 回す覚悟
	 */
	public void turnLeft(Sprite s, int degrees) {
		s.turn(degrees * -1);
	}
	
	/**
	 * X座標をX、Y座標をYにする。
	 * @param s スプライト
	 * @param x X座標
	 * @param y Y座標
	 */
	public void goTo(Sprite s, int x, int y) {
	    s.goTo(x, y);
	}
	
	public void goToRandom(Sprite s) {
		s.goToRandom();
	}
	
	//=============================================================
	// 見た目
	//=============================================================
	
	/**
	 * コスチュームを追加する
	 * @param s スプライト
	 * @param costume コスチュームファイル
	 */
	public void addCostume(Sprite s, String costumeFile) {
		Path path = Paths.get(costumeFile);
		String fileName = path.getFileName().toString();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		s.addCostume(name , path.toString());
	}
	
	/**
	 * コスチュームを追加する
	 * @param s スプライト
	 * @param name 名前
	 * @param costume コスチュームファイル
	 */
	public void addCostume(Sprite s, String name, String costumeFile) {
		s.addCostume(name , costumeFile);
	}
	
	/**
	 * 最前面に移動
	 * @param s
	 */
	public void goToFront(Sprite s) {
		sprites.remove(s);
		sprites.add(s);
	}
	
	/**
	 * 最後面に移動
	 * @param s
	 */
	public void goToBack(Sprite s) {
		sprites.remove(s);
		sprites.add(0, s);
	}
	
	/**
	 * 前層に移動
	 * @param s
	 * @param layers 前に行く層
	 */
	public void goForward(Sprite s, int layers) {
		int cur = sprites.indexOf(s);
		if((cur + layers) > sprites.size()) {
			goToFront(s);
		}else {
			changeSpriteLayer(cur, cur + 1);
		}
	}
	
	/**
	 * 後層に移動
	 * @param s
	 * @param layers 前に行く層
	 */
	public void goBackward(Sprite s, int layers) {
		int cur = sprites.indexOf(s);
		if((cur - layers) <= 0) {
			goToBack(s);
		}else {
			changeSpriteLayer(cur, cur - 1);
		}
	}
	
	//=============================================================
	// 制御
	//=============================================================
	
	/**
	 * スプライトのクローンを作る
	 * @param s もとのスプライト
	 * @return クローン
	 */
	public Sprite createClone(Sprite s) {
		Sprite c = s.clone();
		sprites.add(c);
		return c;
	}
	
	/**
	 * クローンを削除する
	 * @param s 削除するクローン
	 */
	public void deleteClone(Sprite s) {
		sprites.remove(s);
	}
	
	//=============================================================
	// 調べる
	//=============================================================
	/**
	 * 聞いて待つ
	 * @message 質問内容
	 * @return 入力内容
	 */
	public String askAndWait(String massege) {
		return JOptionPane.showInputDialog(massege);
	}
	
	/**
	 * スプライトに触れたか
	 * @param s1 スプライト1
	 * @param s2 スプライト2
	 * @return 触れた場合はTrue
	 */
	public boolean touching(Sprite s1, Sprite s2) {
		return s1.touching(s2);
	}
}
