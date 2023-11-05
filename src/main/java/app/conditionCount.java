package app;

public class conditionCount {
    public String healthCondition;
    public String count;

    public conditionCount(String healthCondition  , String count ){
        this.healthCondition = healthCondition;
        this.count = count;
    }

    public String getHealthCondition (){
        return this.healthCondition;
    }

    public String getCount(){

        return this.count;
    }

}
