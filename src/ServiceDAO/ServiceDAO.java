package ServiceDAO;

import service.DateService;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class ServiceDAO {

    final static String DriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String startPoint = "2017-06-01 00:00:00.000";
    public static String endPoint = "2018-03-01 00:00:00.000";
    public static String tempDate = startPoint;
    public static String tempStopDate;
    private static int result;


    private DateService dateService;
    private Connection connection;
    private Statement statement;


    public ServiceDAO(){
       this.dateService = new DateService();
    }

    public void copyDB() throws SQLException, IOException, ClassNotFoundException, ParseException {

        if(dateService.ifEndOfSearch(tempDate)){
            Class.forName(DriverName);
            try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;\" +  \n" +
                                "   \"databaseName=Tracker;user=phpserverscripts;password=1iF#%sqdlGKpEHbaio8p3cS$;");
                    statement = connection.createStatement();
                    result = statement.executeUpdate("INSERT INTO Tracker.dbo.tracking_new\n" +
                            "           (unit\n" +
                            "           ,dt\n" +
                            "           ,x\n" +
                            "           ,y\n" +
                            "           ,speed\n" +
                            "           ,heading\n" +
                            "           ,alt\n" +
                            "           ,event\n" +
                            "           ,sat\n" +
                            "           ,location\n" +
                            "           ,ad1\n" +
                            "           ,ad2\n" +
                            "           ,ad3\n" +
                            "           ,ad4\n" +
                            "           ,io0\n" +
                            "           ,io1\n" +
                            "           ,io2\n" +
                            "           ,io3\n" +
                            "           ,io4\n" +
                            "           ,io5\n" +
                            "           ,io6\n" +
                            "           ,io7\n" +
                            "           ,cnt\n" +
                            "           ,odom\n" +
                            "           ,map\n" +
                            "           ,ss0\n" +
                            "           ,ss1\n" +
                            "           ,ss2\n" +
                            "           ,ss3\n" +
                            "           ,rs\n" +
                            "           ,app)SELECT unit\n" +
                            "           ,dt\n" +
                            "           ,x\n" +
                            "           ,y\n" +
                            "           ,speed\n" +
                            "           ,heading\n" +
                            "           ,alt\n" +
                            "           ,event\n" +
                            "           ,sat\n" +
                            "           ,location\n" +
                            "           ,ad1\n" +
                            "           ,ad2\n" +
                            "           ,ad3\n" +
                            "           ,ad4\n" +
                            "           ,io0\n" +
                            "           ,io1\n" +
                            "           ,io2\n" +
                            "           ,io3\n" +
                            "           ,io4\n" +
                            "           ,io5\n" +
                            "           ,io6\n" +
                            "           ,io7\n" +
                            "           ,cnt\n" +
                            "           ,odom\n" +
                            "           ,map\n" +
                            "           ,ss0\n" +
                            "           ,ss1\n" +
                            "           ,ss2\n" +
                            "           ,ss3\n" +
                            "           ,rs\n" +
                            "           ,app FROM Tracker.dbo.tracking WHERE Tracker.dbo.tracking.dt > '" + tempDate +
                            "' AND Tracker.dbo.tracking.dt < '" + dateService.nextDay(tempDate) + "';");

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(60000); // this pause for main thread need for normal work geokoders on localhost
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(tempDate + " - " + tempStopDate + ": " + result);
                connection.close();
                statement.close();
                result = 0;
                tempStopDate = dateService.nextDay(tempDate);
                tempDate = tempStopDate;
                copyDB();
            }
        }else {System.out.println("Process has finished on time: " + tempStopDate + ".");}
    }
}