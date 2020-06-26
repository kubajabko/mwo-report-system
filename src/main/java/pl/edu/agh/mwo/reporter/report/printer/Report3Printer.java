package pl.edu.agh.mwo.reporter.report.printer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.report.Report3;

public class Report3Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwisko i imie", "Ogólna liczba godzin"};
    private final Report3 report;
    private static final int[] COLUMNS_WIDTHS = {20, 20};

    public Report3Printer(Report3 report) {
        this.report = report;
    }

    @Override
    public void printToConsole() {
        List<String> projectNames = report.getProjectNames();
        List<String> personNames = report.getPersonNames();

        System.out.println("\n");
        System.out.println(report.getTitle());
        printHorizontalLine(projectNames);
        System.out.printf("|  %-30s  |", HEADERS[0]);
        projectNames.forEach(project -> System.out.printf("  %-15s  |", project));
        System.out.printf("  %-20s  |\n", HEADERS[1]);
        printHorizontalLine(projectNames);

        for (String personName : personNames) {
            Report3.Record record = report.getRecordForPerson(personName);

            System.out.printf("|  %-30s  |", record.getPersonName());
            record.getHoursPerProject().values().forEach(hours -> System.out.printf("  %-15s  |", hours));
            System.out.printf("  %-20s  |\n", record.getTotalNumberOfHours());

        }

        printHorizontalLine(projectNames);
    }

    @Override
    public void printToExcel(String excelFilePath) {

        List<String> projectNames = report.getProjectNames();
        List<String> personNames = report.getPersonNames();
        List<Report3.Record> rekords = report.getRecords();

        List<String> nagl = new ArrayList<>();

        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report3",
                report.getTitle(),
                HEADERS,
                report.getEmployeeName(),
                report.getDateFrom(),
                report.getDateTo(),
                report.getKeyword());

  //      excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

//        System.out.printf("|  %-30s  |", HEADERS[0]);
//        projectNames.forEach(project -> System.out.printf("  %-15s  |", project));
//        System.out.printf("  %-20s  |\n", HEADERS[1]);
//        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (String personName : personNames) {
            Report3.Record record = report.getRecordForPerson(personName);

            excelExporter.addRow();
            excelExporter.addCell(0, record.getPersonName());

            int i = 0;
            for (BigDecimal h : record.getHoursPerProject().values()){
                i++;
                excelExporter.addCell(i,h);
            }
//            record.getHoursPerProject().values().forEach( hours -> excelExporter.addCell(1, hours));
            i++;
            excelExporter.addCell(i, record.getTotalNumberOfHours());

        }


        excelExporter.saveToFile();

    }
    private void printHorizontalLine(List<String> projects) {
        System.out.print("-------------------------------------------------------------");
        projects.forEach(project -> System.out.print("--------------------"));
        System.out.print("\n");
    }
}

