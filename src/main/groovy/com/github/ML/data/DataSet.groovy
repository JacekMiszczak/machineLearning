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
    int classAttributeIndex

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
        classAttributeIndex = index
    }

    int getClassAttributeIndex(){
        classAttributeIndex
    }

    DiscreteDescriptor getClassDescriptor(){
        return (descriptors[classAttributeIndex] as DiscreteDescriptor)
    }

    Map<Integer, Integer> getClassDistribution(){
        DiscreteDescriptor classDescriptor = (descriptors[getClassAttributeIndex()] as DiscreteDescriptor)
        Map<Integer, Integer> distribution = [:]
        classDescriptor.possibleValues.size().times {
            distribution[it] = 0
        }
        instances.each { Instance it ->
            distribution[it.attributes[classAttributeIndex].intValue()]++
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
        out.setClassAttributeIndex(classAttributeIndex)
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