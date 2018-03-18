package service;

import Model.Refuelling;
import ServiceDAO.ServiceDAO;

import java.io.*;
import java.util.List;

public class ParseTxt {
    private BufferedWriter bw;
    private BufferedReader br;

    public void saveResultToFile(List<Refuelling> arrayList) throws IOException {
        File tempFile = new File("Refuelling report.txt");
        bw = new BufferedWriter(new FileWriter(tempFile));
        if (arrayList.isEmpty()) {
            System.out.println("In period " + ServiceDAO.tempDate + " - " + ServiceDAO.tempStopDate + " are no items");
            bw.write("In period " + ServiceDAO.tempDate + " - " + ServiceDAO.tempStopDate + " are no items.\r\n");
            bw.close();
        } else {
            for (Refuelling refuelling : arrayList) {
                System.out.println(ServiceDAO.tempDate + " - " + ServiceDAO.tempStopDate + ": " + refuelling.toString());
                bw.write(refuelling.toString() + "\r\n");
            }
            bw.close();
            System.out.printf("BASE is saved successfully to file: %s\n", tempFile.getAbsolutePath());
        }
    }
}
