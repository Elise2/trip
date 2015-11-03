package bean;

public class FileExtention {

	String tablename;
	String dbname;
	String displayname;
	String datatype;
	String detail;
	String show;

	public FileExtention() {
		dbname = "trip";
		show = null;
		detail = null;
	}

	public FileExtention(String tablename, String dbname, String displayname,
			String datatype, String detail, String show) {

		this.tablename = tablename;
		this.dbname = dbname;
		this.displayname = displayname;
		this.datatype = datatype;
		this.detail = detail;
		this.show = show;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	@Override
	public String toString() {
		return "FileExtention [tablename=" + tablename + ", dbname=" + dbname
				+ ", displayname=" + displayname + ", datatype=" + datatype
				+ ", detail=" + detail + ", show=" + show + "]";
	}

}
