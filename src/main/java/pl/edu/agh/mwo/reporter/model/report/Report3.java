package pl.edu.agh.mwo.reporter.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Report3 {

    private final String title = "Raport 3. Liczba godzin zrealizowanych przez pracowników w projektach";
    private final List<Record> records;
    private final List<String> projectNames;
    private String employeeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String keyword;

    public Report3(List<String> projectNames) {
        this.projectNames = projectNames;
        this.records = new ArrayList<>();
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keyword=keyword;

    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getTitle() {
        return title;
    }

    public List<Record> getRecords() {
        return records;
    }

    public List<String> getPersonNames() {
        return records.stream()
                .map(Record::getPersonName)
                .collect(Collectors.toList());
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public Record getRecordForPerson(String personName) {
        return records.stream().filter(record -> record.getPersonName().equals(personName)).findAny().orElse(null);
    }

    public void addHoursForPersonByProject(String projectName, String personName, BigDecimal hours) {
        final Optional<Record> record = records.stream()
                .filter(rec -> rec.getPersonName().equals(personName))
                .findAny();

        if (record.isPresent()) {
            record.get().addHoursForProject(projectName, hours);
        } else {
            Record newRecord = new Record(personName, this.projectNames);
            newRecord.addHoursForProject(projectName, hours);
            records.add(newRecord);
        }
    }

    public static class Record {
        private final String personName;
        private final Map<String, BigDecimal> hoursPerProject;
        private BigDecimal totalNumberOfHours;

        public Record(String personName, List<String> projectNames) {
            this.personName = personName;
            this.totalNumberOfHours = BigDecimal.ZERO;
            this.hoursPerProject = new HashMap<>();
            projectNames.forEach(project -> this.hoursPerProject.put(project, BigDecimal.ZERO));
        }

        public void addHoursForProject(String projectName, BigDecimal hours) {
            BigDecimal oldValue = hoursPerProject.get(projectName);
            BigDecimal newValue =  oldValue.add(hours);
            hoursPerProject.put(projectName, newValue);

            totalNumberOfHours = totalNumberOfHours.add(hours);
        }

        public String getPersonName() {
            return personName;
        }

        public BigDecimal getHoursForProject(String projectName) {
            return hoursPerProject.get(projectName);
        }

        public Map<String, BigDecimal> getHoursPerProject() {
            return hoursPerProject;
        }

        public BigDecimal getTotalNumberOfHours() {
            return totalNumberOfHours;
        }
    }

}
