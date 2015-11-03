package entity;

public class City {
	private String cityName;
	private int cityId;
	private boolean isCity;
	private int cityTraverNum;
	private int cityCategory;
	private String nameSort;
	private String cityImg;

	public City(String cityName, int cityId, boolean isCity,
			int city_traver_num, int cityCategory, String nameSort) {
		super();
		this.cityName = cityName;
		this.cityId = cityId;
		this.isCity = isCity;
		this.cityTraverNum = city_traver_num;
		this.cityCategory = cityCategory;
		this.nameSort = nameSort;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public boolean isCity() {
		return isCity;
	}

	public void setCity(boolean isCity) {
		this.isCity = isCity;
	}

	public int getCityTraverNum() {
		return cityTraverNum;
	}

	public void setCityTraverNum(int cityTraverNum) {
		this.cityTraverNum = cityTraverNum;
	}

	public int getCityCategory() {
		return cityCategory;
	}

	public void setCityCategory(int cityCategory) {
		this.cityCategory = cityCategory;
	}

	public String getNameSort() {
		return nameSort;
	}

	public void setNameSort(String nameSort) {
		this.nameSort = nameSort;
	}

	public City() {
		super();
	}

}
