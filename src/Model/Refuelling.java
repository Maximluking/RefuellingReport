package Model;

public class Refuelling {

    private String groupName;
    private String name;
    private String unit;
    private String dt;
    private String x;
    private String y;
    private String location;
    private String inout;
    private String dtStop;
    private String dtStart;

    public Refuelling(String groupName, String name, String unit, String dt, String x, String y, String location, String inout, String dtStop, String dtStart) {
        this.groupName = groupName;
        this.name = name;
        this.unit = unit;
        this.dt = dt;
        this.x = x;
        this.y = y;
        this.location = location;
        this.inout = inout;
        this.dtStop = dtStop;
        this.dtStart = dtStart;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getDt() {
        return dt;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getLocation() {
        return location;
    }

    public String getInout() {
        return inout;
    }

    public String getDtStop() {
        return dtStop;
    }

    public String getDtStart() {
        return dtStart;
    }

    @Override
    public String toString() {
        return groupName + ";" +
                name + ";" +
                unit + ";" +
                dt + ";" +
                x + ";" +
                y + ";" +
                location + ";" +
                inout + ";" +
                dtStop + ";" +
                dtStart + ";";
    }
}

