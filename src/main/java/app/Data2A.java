package app;

public class Data2A {
    private String year;
    private String ageGroup;
    private String healthCondition;
    private String indigenousStatus;
    private int totalHealthConditionCount;
    private int totalAgeDemographicsCount;
    private String sex;

    public Data2A(String year, String ageGroup, String healthCondition, String indigenousStatus, int totalHealthConditionCount, int totalAgeDemographicsCount, String sex) {
        this.year = year;
        this.ageGroup = ageGroup;
        this.healthCondition = healthCondition;
        this.indigenousStatus = indigenousStatus;
        this.totalHealthConditionCount = totalHealthConditionCount;
        this.totalAgeDemographicsCount = totalAgeDemographicsCount;
        this.sex = sex;
    }

    // Add getters and setters as needed

    public String getYear() {
        return year;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public String getIndigenousStatus() {
        return indigenousStatus;
    }

    public int getTotalHealthConditionCount() {
        return totalHealthConditionCount;
    }

    public int getTotalAgeDemographicsCount() {
        return totalAgeDemographicsCount;
    }

    public String getSex() {
        return sex;
    }
}

