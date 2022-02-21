package com.pcarneiro.jmapmerger;

class HistogramMergeEntry {

    private String objectType;
    private Integer instances;
    private Integer nextInstances;
    private Long bytes;
    private Long nextBytes;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getInstances() {
        return instances;
    }

    public void setInstances(Integer instances) {
        this.instances = instances;
    }

    public Integer getNextInstances() {
        return nextInstances;
    }

    public void setNextInstances(Integer nextInstances) {
        this.nextInstances = nextInstances;
    }

    public Long getBytes() {
        return bytes;
    }

    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

    public Long getNextBytes() {
        return nextBytes;
    }

    public void setNextBytes(Long nextBytes) {
        this.nextBytes = nextBytes;
    }
}
