package ServiceDAO;

import Model.Refuelling;
import service.DateService;
import service.ParseTxt;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    final static String DriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String startPoint = "2017-01-01 00:00:00.000";
    public static String endPoint = "2018-03-01 00:00:00.000";
    public static String tempDate = startPoint;
    public static String tempStopDate;


    private DateService dateService;
    private List<Refuelling> refuellings;
    private ParseTxt parseTxt;

    public ServiceDAO() {
        this.dateService = new DateService();
        this.refuellings = new ArrayList<>();
        this.parseTxt = new ParseTxt();
    }

    public void getRefuellingReport() throws SQLException, IOException, ClassNotFoundException, ParseException {

        if(dateService.ifEndOfSearch(tempDate)){
            Class.forName(DriverName);
            try (
                    Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;\" +  \n" +
                            "   \"databaseName=Tracker;user=xxx;password=xxx;");
                    PreparedStatement ps = connection.prepareStatement("SELECT g.name as group_name, v.Name as name, v.unit, r.dt, f.x, f.y, r.location, r.inout, \n" +
                            "(SELECT TOP 1 rd.dt FROM Tracker.dbo.trackingT rd with (nolock) \n" +
                            "WHERE rd.unit=r.unit AND rd.dt>='" + tempDate + "' AND rd.dt<=DATEADD(s, r.dt, '19700101') \n" +
                            "AND rd.event IN ('0', '00') ORDER BY rd.dt DESC) as stop_dt,\n" +
                            " (SELECT TOP 1 tr.dt FROM Tracker.dbo.trackingT tr \n" +
                            " WHERE tr.dt<='" + dateService.nextDay(tempDate) + "' AND tr.unit=r.unit AND tr.dt>=DATEADD(s, r.dt, '19700101')\n" +
                            "  AND tr.event IN ('0', '00') ORDER BY tr.dt ASC)\n" +
                            "   as start_dt FROM Tracker.dbo.fuel f, Tracker.dbo.vehicles v, Tracker.dbo.groups g, Tracker.dbo.ReportData r with (nolock) \n" +
                            "   WHERE g.id_cust='251' AND f.dt>='" + tempDate + "' \n" +
                            "   AND f.dt<='" + dateService.nextDay(tempDate) + "' AND id_group=v.CGroup AND f.unit=v.unit AND f.event=101 AND f.location!='' AND r.unit=f.unit AND \n" +
                            "   DATEADD(s, r.dt, '19700101')>='" + tempDate + "' \n" +
                            "   AND DATEADD(s, r.dt, '19700101')<='" + dateService.nextDay(tempDate) + "' AND r.inout>0 ORDER BY g.name;");
                    ResultSet resultSet = ps.executeQuery();
            ) {
                while (resultSet.next()) {
                    String groupName = resultSet.getString("group_name");
                    String name = resultSet.getString("name");
                    String unit = resultSet.getString("unit");
                    String dt = resultSet.getString("dt");
                    String x = resultSet.getString("x");
                    String y = resultSet.getString("y");
                    String location = resultSet.getString("location");
                    String inout = resultSet.getString("inout");
                    String dtStop = resultSet.getString("stop_dt");
                    String dtStart = resultSet.getString("start_dt");

                    refuellings.add(new Refuelling(groupName, name, unit, dt, x, y, location, inout, dtStop, dtStart));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                tempStopDate = dateService.nextDay(tempDate);
                tempDate = tempStopDate;
                parseTxt.saveResultToFile(refuellings);
                getRefuellingReport();
            }
        }else {System.out.println("Process has finished on time: " + tempStopDate + ".");}
    }
}
//add commit