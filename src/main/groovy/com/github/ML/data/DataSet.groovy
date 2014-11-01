package com.github.ML.data

import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor
import groovy.transform.CompileStatic

@CompileStatic
public class DataSet implements List<Instance> {

    @Delegate
    final List<Instance> instances = []
    final List<Descriptor> descriptors = []

    String name

    void addInstance(Instance instance){
        instance.dataSet = this
        instances.add(instance)
    }

    void addInstances(List<Instance> instaces){
        instaces.each{ Instance it ->
            addInstance(it)
        }
    }


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

    int getNumClasses(){
        (descriptors[classAttributeIndex] as DiscreteDescriptor).possibleValues.size()
    }

    DataSet copyStructure(){
        DataSet out = new DataSet()
        out.name = name
        descriptors.each{ Descriptor it ->
            out.descriptors.add(it)
        }
        out
    }

    DataSet copy(){
        DataSet out = copyStructure()
        instances.each { Instance it ->
            out.addInstance(it.copy())
        }
        out
    }

    DataSet plus(DataSet other){
        DataSet out = this.copyStructure()
        out.addInstances(instances)
        out.addInstances(other.instances)
        out
    }

    List<Number> getUniqueValuesOf(int index){
        List<Number> out = instances.collect { Instance it ->
            it.attributes[index]
        }
        out.unique().toList()
    }
}