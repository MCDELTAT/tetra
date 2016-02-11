package com.genocide.fbrowser;
//package com.juscam.parse;

// DataObject.java

public class DataObject {
    public String contig;

    public String organism;

    public int size;

    public double dim1;

    public double dim2;

    public double dim3;

    public DataObject(String dataContig, String dataOrganism, int dataSize, double dataDim1, double dataDim2, double dataDim3) {

        contig = dataContig;

        organism = dataOrganism;

        size = dataSize;

        dim1 = dataDim1;

        dim2 = dataDim2;

        dim3 = dataDim3;

    }



}