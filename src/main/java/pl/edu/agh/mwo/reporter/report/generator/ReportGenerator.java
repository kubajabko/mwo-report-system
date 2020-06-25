package pl.edu.agh.mwo.reporter.report.generator;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report5;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportGenerator implements IReportGenerator {

    private final Company company;
    private String employeeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String keyword;

//    public ReportGenerator(Company company) {
//        this.company = company;
//    }


    public ReportGenerator(Company company, String employeeName, LocalDate dateFrom, LocalDate dateTo) {
        this.company = company;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public ReportGenerator(Company company, String employeeName, LocalDate dateFrom, LocalDate dateTo, String keyword) {
        this.company = company;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keyword=keyword;
    }

    public Report1 generateReport1() {
        Report1 report1 = new Report1(employeeName,dateFrom, dateTo);
        for (Person person : company.getPersons()) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Task task : person.getTasks()) {
                hours = hours.add(task.getHours());
            }
            report1.addPersonWithTotalNumberOfHours(person, hours);
        }
        return report1;
    }

    public Report2 generateReport2() {
        Report2 report2 = new Report2(employeeName,dateFrom, dateTo);
        final Set<String> projectNames = new HashSet<>();
        company.getPersons().forEach(person -> person.getTasks().forEach(task -> projectNames.add(task.getProjectName())));

        for (String projectName : projectNames) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Person person : company.getPersons()) {
                for (Task task : person.getTasks()) {
                    if (task.getProjectName().equals(projectName)) {
                        hours = hours.add(task.getHours());
                    }
                }
            }
            report2.addProjectWithTotalNumberOfHours(projectName, hours);
        }

        return report2;
    }

    public Report5 generateReport5() {
        Report5 report5 = new Report5(employeeName,dateFrom, dateTo, keyword);
       // List<Task> tablicaTask= new ArrayList<>();
        for (Person person : company.getPersons()) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Task task : person.getTasks()) {
                if (task.getName().contains(keyword)) {
                    report5.addTask(task);
                }
            }

        }
        return report5;
    }
}
