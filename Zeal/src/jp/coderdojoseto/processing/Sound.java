package jp.coderdojoseto.processing;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Cloneable{
	
	/** ファイル名 */
	public File soundFile = null;
	/** 名前 */
	public String name = null;
	/** 音声再生クラス */
	private Clip clip = null;
	/** 音量(0-1) */
	private float volume = 1;

	/**
	 * コンストラクタ
	 * @param name
	 * @param soundFileName
	 */
	public Sound(String name, String soundFileName) {
		this.name = name;
		soundFile = new File(soundFileName);
	}
	
	/**
	 * 音声ファイルを読みこむ
	 */
	private Clip loadFile() {
		if(!soundFile.exists()) {
			System.err.println("音声ファイルがありません " + soundFile.getAbsolutePath());
			return null;
		}
		
		//指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile)){
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			//再生準備完了
			c.open(ais);
			
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 音を鳴らす
	 */
	public void play() {
		play(false);
	}
	/**
	 * 音を鳴らして終わるまで待つ
	 */
	public void playUntilDone() {
		play(true);
	}
	/**
	 * 音を鳴らす
	 * @param isWait
	 */
	private void play(boolean isWait) {
		if(clip == null) {
			clip = loadFile();
		}
		// 頭に戻す
		clip.setFramePosition(0);
		clip.start();
		//clip.loop(0);
		clip.flush();
		if(isWait) {
			while (clip.isActive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	/**
	 * 音を停止する。
	 */
	public void stop() {
		if(clip == null) {
			return;
		}
		if(clip.isActive()) {
			clip.stop();
			clip.flush();
		}
	}
	
	/**
	 * 再生中か判別する
	 * @return
	 */
	public boolean nowPlaying() {
		if(clip == null) {
			return false;
		}else {
			return clip.isActive();
		}
	}
	
	/**
	 * 音量をずつ変える
	 */
	public void changeVolume(int volume) {
		this.volume += ((float)volume) / 100f;
		volumeControl();
	}
	
	/**
	 * 音量を%にする
	 */
	public void volume(int volume) {
		this.volume = ((float)volume) / 100f;
		volumeControl();
	}
	
	/**
	 * 音量設定
	 */
	private void volumeControl() {
		if(clip != null) {
			FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			ctrl.setValue(volume);
		}
	}
	
	@Override
	public Sound clone() {
		Sound ret = new Sound(this.name, this.soundFile.toString());
		ret.volume = this.volume;
		return ret;
	}

//	@Override
//	protected void finalize() throws Throwable {
//		if(ais != null) {
//			try {
//				ais.close();
//			}catch(Throwable e) {
//				// 握りつぶし
//			}
//			ais = null;
//		}
//	}

	
}
