package pl.agh.edu.kis.sp2.sim.excel;

public class Data {
    private int NumberofRelationsData;
    private int NumberofActivityData;
    private int NumberofLocationData;
    private int NumberofWeatherData;
    private int NumberofTime;
    public Data() {

    }
    public Data(int NumberofRelationsData,int NumberofActivityData,int NumberofLocationData,int NumberofWeatherData,int NumberofTime)
    {
        this.NumberofRelationsData = NumberofRelationsData;
        this.NumberofActivityData = NumberofActivityData;
        this.NumberofLocationData = NumberofLocationData;
        this.NumberofWeatherData = NumberofWeatherData;
        this.NumberofTime = NumberofTime;
    }

    public int getNumberofRelationsData() {
        return NumberofRelationsData;
    }

    public void setNumberofRelationsData(int numberofRelationsData) {
        NumberofRelationsData = numberofRelationsData;
    }

    public int getNumberofActivityData() {
        return NumberofActivityData;
    }

    public void setNumberofActivityData(int numberofActivityData) {
        NumberofActivityData = numberofActivityData;
    }

    public int getNumberofLocationData() {
        return NumberofLocationData;
    }

    public void setNumberofLocationData(int numberofLocationData) {
        NumberofLocationData = numberofLocationData;
    }

    public int getNumberofWeatherData() {
        return NumberofWeatherData;
    }

    public void setNumberofWeatherData(int numberofWeatherData) {
        NumberofWeatherData = numberofWeatherData;
    }

    public int getNumberofTime() {
        return NumberofTime;
    }

    public void setNumberofTime(int numberofTime) {
        NumberofTime = numberofTime;
    }
}
