package com.github.ML.data

import com.github.ML.data.attribute.Attribute
import com.github.ML.data.attribute.DiscreteAttribute
import groovy.transform.CompileStatic

@CompileStatic
public class Instance {

    DataSet dataSet
    final List<Object> attributes = []

    public Attribute getAttributeAt(int index){
        dataSet.descriptors[index].buildAttribute(attributes[index])
    }

    public DiscreteAttribute getClassAttribute(){
        (getAttributeAt(dataSet.classAttributeIndex) as DiscreteAttribute)
    }


}