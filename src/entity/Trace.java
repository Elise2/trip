package entity;

public class Trace {
	private String img;
	private String content;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Trace(String img, String content) {
		super();
		this.img = img;
		this.content = content;
	}

	public Trace() {
		super();
	}

}
