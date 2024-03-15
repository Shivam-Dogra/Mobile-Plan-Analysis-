package com.ACC.MobilePlanPrice.model;

public class MobilePlan {
    private String provider;
    private String planName;
    private String monthlyCost;
    private String dataAllowance;
    private String networkCoverage;
    private String callAndTextAllowance;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(String monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public String getDataAllowance() {
        return dataAllowance;
    }

    public void setDataAllowance(String dataAllowance) {
        this.dataAllowance = dataAllowance;
    }

    public String getNetworkCoverage() {
        return networkCoverage;
    }

    public void setNetworkCoverage(String networkCoverage) {
        this.networkCoverage = networkCoverage;
    }

    public String getCallAndTextAllowance() {
        return callAndTextAllowance;
    }

    public void setCallAndTextAllowance(String callAndTextAllowance) {
        this.callAndTextAllowance = callAndTextAllowance;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public MobilePlan() {
        // TODO Auto-generated constructor stub
    }

    public MobilePlan(String provider, String planName, String monthlyCost, String dataAllowance,
                      String networkCoverage, String callAndTextAllowance) {
        super();
        this.provider = provider;
        this.planName = planName;
        this.monthlyCost = monthlyCost;
        this.dataAllowance = dataAllowance;
        this.networkCoverage = networkCoverage;
        this.callAndTextAllowance = callAndTextAllowance;

    }
}
