package com.github.ML.data

import groovy.transform.CompileStatic

@CompileStatic
public class Instance {

    DataSet dataSet
    final List<Number> attributes = []

    public Attribute getAttributeAt(int index){
        dataSet.descriptors[index].buildAttribute(attributes[index])
    }

    public Attribute getClassAttribute(){
        getAttributeAt(dataSet.classAttributeIndex)
    }


}