package jp.coderdojoseto.processing;

import jp.coderdojoseto.processing.Sprite.ROTATION_STYLE;

public abstract class スクラッチ extends Scratch {

	//=============================================================
	// 初期設定関係
	//=============================================================
	@Override
	public void setup() {
		準備();
	}
	
	/**
	 * 最初の準備をする
	 */
	abstract public void 準備();
	
	@Override
	public void pressFlag() {
		旗が押されたとき();
	}
	
	abstract public void 旗が押されたとき();
	
	/**
	 * ステージの背景を設定する
	 * @param 背景ファイル
	 */
	public void ステージ設定(String 背景ファイル) {
		setStage(背景ファイル);
	}
	
	/**
	 * スプライトを作る
	 * @param コスチューム名
	 * @return 作ったスプライト
	 */
	public Sprite スプライトを作る(String コスチューム名) {
		return createSprite(コスチューム名);
	}
	
	//=============================================================
	// 時間設定関係
	//=============================================================
	
	/**
	 * 始まってからの経過した時間の判定
	 * @param 秒 経過時間
	 * @return 時間が来たらTrue
	 */
	public boolean 時間(float 秒) {
		return time(秒);
	}
	
	/**
	 * 開始してから、指定した時間の範囲に含まれるか判定する
	 * @param 始まりの時間
	 * @param 終わりの時間
	 * @return
	 */
	public boolean 時間(float 始まりの時間, float 終わりの時間) {
		return time(始まりの時間, 終わりの時間);
	}
	
	/**
	 * 次の時間の判定
	 * @param 秒 次の時間
	 * @return 時間が来たらTrue
	 */
	public boolean 次の時間(float 秒) {
		return timeNext(秒);
	}
	
	/**
	 * 前回の検査時間から指定した範囲時間が経過したのかを判定する
	 * @param 前回からの始まり時間
	 * @param 前回からの終わり時間
	 * @return
	 */
	public boolean 次の時間(float 始まりの時間, float 終わりの時間) {
		return timeNext(始まりの時間, 終わりの時間);
	}
	
	/**
	 * 開始してからの時間（秒）
	 * @return　開始してからの時間（秒）
	 */
	public float 経過時間() {
		return totalTimeSec;
	}
	
	//=============================================================
	// Processing関係
	//=============================================================
	
	/**
	 * 文字を表示する
	 * @param 文字
	 * @param X座標
	 * @param Y座標
	 * @param サイズ
	 */
	public void 文字を書く(String 文字, float X座標, float Y座標, int サイズ) {
		push();
		textSize(サイズ);
		text(文字, Scale.toRealX(X座標), Scale.toRealY(Y座標));
		pop();
		
	}
	
	//=============================================================
	// スプライトブロック
	//=============================================================
	
	//=============================================================
	// 動き
	//=============================================================
	
	/**
	 * 歩数動かす
	 * @param 歩数
	 */
	public void 歩く(Sprite スプライト, float 歩数) {
		スプライト.move(歩数);
	}
	
	/**
	 * 右に回る
	 * @param スプライト
	 * @param 角度
	 */
	public void 右に回る(Sprite スプライト, int 角度) {
		turnRight(スプライト, 角度);
	}
	
	/**
	 * 左に回る
	 * @param スプライト
	 * @param 角度
	 */
	public void 左に回る(Sprite スプライト, int 角度) {
		turnLeft(スプライト, 角度);
	}
	
	/**
	 * X座標、Y座標に行く
	 * @param スプライト
	 * @param X座標
	 * @param Y座標
	 */
	public void 座標に行く(Sprite スプライト, int X座標, int Y座標) {
		goTo(スプライト, X座標, Y座標);
	}
	
	/**
	 * どこかの場所に行く
	 * @param スプライト
	 */
	public void どこかに行く(Sprite スプライト) {
		goToRandom(スプライト);
	}
	
	/**
	 * マウスの場所へ行く
	 * @param スプライト
	 */
	public void マウスへ行く(Sprite スプライト) {
		goToMouse(スプライト);
	}
	
	/**
	 * 向き
	 * @param スプライト
	 * @param 角度
	 */
	public void 向き(Sprite スプライト, float 角度) {
		direction(スプライト, 角度);
	}
	
	/**
	 * マウスの方に向きを変える
	 * @param スプライト
	 */
	public void 舞うその方に向く(Sprite スプライト) {
		directionToMouse(スプライト);
	}
	
	/**
	 * 歩く
	 * @param スプライト
	 * @param X歩
	 * @param Y歩
	 */
	public void 歩く(Sprite スプライト, float X歩, float Y歩) {
		move(スプライト, X歩, Y歩);
	}
	
	/**
	 * 回転方法
	 * @param スプライト
	 * @param 方式
	 */
	public void 回転方法(Sprite スプライト, ROTATION_STYLE 方法) {
		rotationStyle(スプライト, 方法);
	}
	
	/**
	 * もし端についたら跳ね返る
	 * @param スプライト
	 */
	public void もし端についたら跳ね返る(Sprite スプライト) {
		ifOnEdgeBounce(スプライト);
	}

	
	
	//=============================================================
	// 見た目
	//=============================================================
	


	/**
	 * 大きな文字で〜と言う
	 * @param スプライト
	 * @param セリフ
	 * @param 言う時間秒
	 * @param 文字の大きさ
	 */
	public void 言う(Sprite スプライト, String セリフ, float 言う時間秒, int 文字の大きさ) {
		say(スプライト, セリフ, 言う時間秒, 文字の大きさ);
	}
	
	/**
	 * 〜と言う
	 * @param スプライト
	 * @param セリフ
	 * @param 言う時間秒
	 */
	public void 言う(Sprite スプライト, String セリフ, float 言う時間秒) {
		say(スプライト, セリフ, 言う時間秒);
	}
	
	/**
	 * 〜と言う
	 * @param スプライト
	 * @param セリフ
	 */
	public void 言う(Sprite スプライト, String セリフ) {
		say(スプライト, セリフ);
		
	}
	
	/**
	 * 大きさをサイズずつ変える
	 * @param スプライト
	 * @param サイズ
	 */
	public void 大きさずつ(Sprite スプライト, float サイズ) {
		changeSize(スプライト, サイズ);
	}
	/**
	 * スプライトの大きさを変える
	 * @param スプライト
	 * @param サイズ %
	 */
	public void 大きさ(Sprite スプライト, float サイズ) {
		setSize(スプライト, サイズ);
	}
	
	/**
	 * スプライトを表示する
	 * @param スプライト
	 */
	public void 表示(Sprite スプライト) {
		show(スプライト);
	}
	
	/**
	 * スプライトを隠す
	 * @param スプライト
	 */
	public void 隠す(Sprite スプライト) {
		hide(スプライト);
	}
	
	/**
	 * コスチュームを追加する
	 * @param スプライト
	 * @param 名前
	 * @param コスチュームファイル
	 */
	public void コスチューム追加(Sprite スプライト, String 名前, String コスチュームファイル) {
		addCostume(スプライト, 名前, コスチュームファイル);
	}
	
	/**
	 * コスチュームを追加する
	 * @param スプライト
	 * @param コスチュームファイル
	 */
	public void コスチューム追加(Sprite スプライト, String コスチュームファイル) {
		addCostume(スプライト, コスチュームファイル);
	}
	
	/**
	 * 最前面に移動
	 * @param スプライト
	 */
	public void 最前面に移動(Sprite スプライト) {
		goToFront(スプライト);
	}
	
	/**
	 * 最後面に移動
	 * @param スプライト
	 */
	public void 最後面に移動(Sprite スプライト) {
		goToBack(スプライト);
	}
	
	/**
	 * 前層に移動
	 * @param スプライト
	 * @param 移動層
	 */
	public void 前層に移動(Sprite スプライト, int 移動層) {
		goForward(スプライト, 移動層);
	}
	
	/**
	 * 後層に移動
	 * @param スプライト
	 * @param 移動層
	 */
	public void 後層に移動(Sprite スプライト, int 移動層) {
		goBackward(スプライト, 移動層);
	}
	
	/**
	 * 次のコスチュームに変更する
	 * @param スプライト
	 */
	public void 次のコスチューム(Sprite スプライト) {
		nextCostume(スプライト);
	}
	
	/**
	 * コスチュームを選択する
	 * @param スプライト
	 * @param コスチューム名
	 */
	public void コスチューム選択(Sprite スプライト, String コスチューム名) {
		selectCostume(スプライト, コスチューム名);
	}
	
	/**
	 * コスチュームを選択する
	 * @param スプライト
	 * @param コスチューム番号
	 */
	public void コスチューム選択(Sprite スプライト, int コスチューム番号) {
		selectCostume(スプライト, コスチューム番号);
	}
	
	//=============================================================
	// 音
	//=============================================================
	
	/**
	 * 音を鳴らす
	 * @param スプライト
	 */
	public void 音を鳴らす(Sprite スプライト) {
		play(スプライト);
	}
	
	/**
	 * 音を鳴らす
	 *スプライト2 @param スプライト
	 * @param 音の名前
	 */
	public void 音を鳴らす(Sprite スプライト, String 音の名前) {
		play(スプライト, 音の名前);
	}
	
	/**
	 * 終わるまで音を鳴らす
	 * @param スプライト
	 */
	public void 終わるまで鳴らす(Sprite スプライト) {
		playUntilDone(スプライト);
	}
	
	/**
	 * 終わるまで鳴らす
	 * @param スプライト
	 * @param 音の名前
	 */
	public void 終わるまで鳴らす(Sprite スプライト, String 音の名前) {
		playUntilDone(スプライト, 音の名前);
	}
	
	/**
	 * 音量をずつ変更する
	 * @param スプライト
	 * @param 音量
	 */
	public void 音量変更(Sprite スプライト, int 音量) {
		changeVolume(スプライト, 音量);
	}
	
	/**
	 * 音量変更
	 * @param スプライト
	 * @param 音の名前
	 * @param 音量
	 */
	public void 音量変更(Sprite スプライト, String 音の名前, int 音量) {
		changeVolume(スプライト, 音の名前, 音量);
	}
	
	/**
	 * 音量を設定する
	 * @param スプライト
	 * @param volume 音量
	 */
	public void 音量設定(Sprite スプライト, int volume) {
		volume(スプライト, volume);
	}
	
	//=============================================================
	// 制御
	//=============================================================
	
	/**
	 * クローンを作る
	 * @param スプライト
	 * @return クローンされたスプライト
	 */
	public Sprite クローンを作る(Sprite スプライト) {
		return createClone(スプライト);
	}
	
	/**
	 * クローン削除
	 * @param スプライト
	 */
	public void クローンを削除(Sprite スプライト) {
		deleteClone(スプライト);
	}
	
	//=============================================================
	// 調べる
	//=============================================================
	
	/**
	 * 聞いて待つ
	 * @param メッセージ
	 * @return 聞いた答え
	 */
	public String 聞いて待つ(String メッセージ) {
		return askAndWait(メッセージ);
	}
	
	/**
	 * お互いのスプライトに触れたか
	 * @param スプライト1
	 * @param スプライト2
	 * @return 触れた場合はTrue
	 */
	public boolean スプライトに触れたか(Sprite スプライト1, Sprite スプライト2) {
		return touching(スプライト1, スプライト2);
	}
	
	/**
	 * 当たり判定を全体にする
	 * @param スプライト
	 */
	public void 当たり判定を全体にする(Sprite スプライト) {
		touchFull(スプライト);
	}
	
	/**
	 * 当たり判定を長方形にする
	 * @param スプライト
	 * @param 横幅
	 * @param 高さ
	 */
	public void 当たり判定を長方形にする(Sprite スプライト, float 横幅, float 高さ) {
		touchRectangle(スプライト, 横幅, 高さ);
	}
	
	/**
	 * 当たり判定を正方形にする
	 * @param スプライト
	 * @param サイズ
	 */
	public void 当たり判定を正方形にする(Sprite スプライト, float サイズ) {
		touchSquare(スプライト, サイズ);
	}
	
	/**
	 * 当たり判定を丸にする
	 * @param スプライト
	 * @param サイズ
	 */
	public void 当たり判定を丸にする(Sprite スプライト, float サイズ) {
		touchCircle(スプライト, サイズ);
	}
	
	
	/**
	 * マウスポインタに触れたか
	 * @param スプライト
	 * @return 触れた場合はTRUE
	 */
	public boolean マウスポインタに触れたか(Sprite スプライト) {
		return touchingMouse(スプライト);
	}
	
	/**
	 * 端に触れたか
	 * @param スプライト
	 * @return 端に触れた場合はTRUE
	 */
	public boolean 端に触れたか(Sprite スプライト) {
		return touchingEdge(スプライト);
	}
	
	/**
	 * マウスまでの距離
	 * @param スプライト
	 * @return 距離
	 */
	public float マウスまでの距離(Sprite スプライト) {
		return distanceMouse(スプライト);
	}
	
	/**
	 * スプライト間の距離
	 * @param スプライト1
	 * @param スプライト2
	 * @return 距離
	 */
	public float スプライトの距離(Sprite スプライト1, Sprite スプライト2) {
		return distance(スプライト1, スプライト2);
	}
}














