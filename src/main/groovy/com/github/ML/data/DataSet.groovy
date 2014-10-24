package com.github.ML.data

import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor
import groovy.transform.CompileStatic

@CompileStatic
public class DataSet {

    final List<Instance> instances = []
    final List<Descriptor> descriptors = []

    String name


    void setClassAttributeIndex(int index){
        descriptors.each {it.classAttribute = false}
        descriptors[index].classAttribute = true
    }

    int getClassAttributeIndex(){
        for (int i = 0; i < descriptors.size(); i++) {
            if (descriptors[i].classAttribute)
                return i
        }
        throw new IllegalStateException("DataSet has no class attribute index")
    }

    Map<String, Integer> getClassDistribution(){
        DiscreteDescriptor classDescriptor = (descriptors[getClassAttributeIndex()] as DiscreteDescriptor)
        Map<String, Integer> distribution = [:]
        classDescriptor.possibleValues.each {
            distribution[it] = 0
        }
        instances.each { Instance it ->
            distribution[it.classAttribute.stringValue]++
        }
        distribution
    }
}