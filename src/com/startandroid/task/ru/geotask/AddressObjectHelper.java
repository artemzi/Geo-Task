package com.startandroid.task.ru.geotask;

public class AddressObjectHelper {

    private String firstAddressLine, secondAddressLine, thirdAddressLine,
            fourthAddressLine;
    private double latitude, longitude;

    public AddressObjectHelper(String firstAddressLine,
                               String secondAddressLine, String thirdAddressLine, String fourthAddressLine,
                               double latitude, double longitude) {
        this.firstAddressLine = firstAddressLine;
        this.secondAddressLine = secondAddressLine;
        this.thirdAddressLine = thirdAddressLine;
        this.fourthAddressLine = fourthAddressLine;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return firstAddressLine + " " + secondAddressLine;
    }

	public String getFourthAddressLine() {
		return fourthAddressLine;
	}

	public void setFourthAddressLine(String fourthAddressLine) {
		this.fourthAddressLine = fourthAddressLine;
	}

	public String getThirdAddressLine() {
		return thirdAddressLine;
	}

	public void setThirdAddressLine(String thirdAddressLine) {
		this.thirdAddressLine = thirdAddressLine;
	}
}
