import ServiceDAO.ServiceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {
	ServiceDAO serviceDAO = new ServiceDAO();
        serviceDAO.getRefuellingReport();
    }
}

