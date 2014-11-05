package com.github.ML.data

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
public class Instance {

    DataSet dataSet
    final List<Number> attributes = []

    public Attribute getAttributeAt(int index){
        dataSet.descriptors[index].buildAttribute(attributes[index])
    }

    public Attribute getClassAttribute(){
        getAttributeAt(dataSet.classAttributeIndex)
    }

    public int getClassAttributeValue(){
        attributes[dataSet.classAttributeIndex] as int
    }

    public Instance copy(){
        Instance out = new Instance(this.dataSet)
        this.attributes.each { Number it ->
            out.attributes.add(it)
        }
        out
    }

    public String toString(){
        attributes.toString()
    }

}