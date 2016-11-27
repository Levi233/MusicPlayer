package com.chenhao.musicplayer.bean;

public class MusicInfo extends OnlineInfo{
	private long rid;
	private int mvflag;
	private String mvquality;
	private String artist;
	private String album;
	private int duration;
	private String format;
	private int hot;
	private String res;
	private int pay_flag;
	private int pic_label;
	private String audio_id;
	private String float_adid;
	private String minfo;
	private int kmark;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getArtist() {
		return artist;
	}

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public int getMvflag() {
		return mvflag;
	}

	public void setMvflag(int mvflag) {
		this.mvflag = mvflag;
	}

	public String getMvquality() {
		return mvquality;
	}

	public void setMvquality(String mvquality) {
		this.mvquality = mvquality;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public int getPay_flag() {
		return pay_flag;
	}

	public void setPay_flag(int pay_flag) {
		this.pay_flag = pay_flag;
	}

	public int getPic_label() {
		return pic_label;
	}

	public void setPic_label(int pic_label) {
		this.pic_label = pic_label;
	}

	public String getAudio_id() {
		return audio_id;
	}

	public void setAudio_id(String audio_id) {
		this.audio_id = audio_id;
	}

	public String getFloat_adid() {
		return float_adid;
	}

	public void setFloat_adid(String float_adid) {
		this.float_adid = float_adid;
	}

	public String getMinfo() {
		return minfo;
	}

	public void setMinfo(String minfo) {
		this.minfo = minfo;
	}

	public int getKmark() {
		return kmark;
	}

	public void setKmark(int kmark) {
		this.kmark = kmark;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "MusicInfo{" +
				"rid=" + rid +
				", mvflag=" + mvflag +
				", mvquality='" + mvquality + '\'' +
				", artist='" + artist + '\'' +
				", album='" + album + '\'' +
				", duration=" + duration +
				", format='" + format + '\'' +
				", hot=" + hot +
				", res='" + res + '\'' +
				", pay_flag=" + pay_flag +
				", pic_label=" + pic_label +
				", audio_id='" + audio_id + '\'' +
				", float_adid='" + float_adid + '\'' +
				", minfo='" + minfo + '\'' +
				", kmark=" + kmark +
				", url='" + url + '\'' +
				"} " + super.toString();
	}
}
