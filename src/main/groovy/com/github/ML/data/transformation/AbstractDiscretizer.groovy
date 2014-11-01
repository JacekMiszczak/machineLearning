package com.github.ML.data.transformation

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor


abstract class AbstractDiscretizer implements Discretizer {

    @Override
    DataSet discretizeAll(DataSet dataSet) {
        def toDiscretize = []
        dataSet.descriptors.eachWithIndex { Descriptor descriptor, int i ->
            if (descriptor.attributeType == AttributeType.NUMERICAL)
                toDiscretize.add(i)
        }
        DataSet out = dataSet
        toDiscretize.each {int i ->
            out = discretizeAttribute(i, out)
        }
        out
    }

    protected void transformDataSet(List<Double> cutpoints, DataSet dataSet, int i){
        Descriptor descriptor = dataSet.descriptors[i]
        DiscreteDescriptor newDescriptor = new DiscreteDescriptor()
        newDescriptor.name = descriptor.name
        newDescriptor.dataSet = descriptor.dataSet
        newDescriptor.classAttribute = descriptor.classAttribute
        List<String> possibleValues = []
        String previous = "-inf"
        cutpoints.each {
            possibleValues.add("($previous, $it]")
            previous = it.toString()
        }
        possibleValues.add("($previous, inf)")
        newDescriptor.possibleValues = possibleValues

        dataSet.descriptors[i] = newDescriptor
        dataSet.each{ Instance instance ->
            transformInstance(cutpoints, instance, i)
        }
    }

    protected void transformInstance(List<Double> cutpoints, Instance instance, int i){
        double value = instance.attributes[i]
        int binNo = 0
        cutpoints.eachWithIndex{ double cutpoint, int j ->
            if (value > cutpoint)
                binNo = j+1
        }
        instance.attributes[i] = binNo
    }
}
