package com.github.ML.classifier.bayes

import com.github.ML.classifier.Classifier
import com.github.ML.classifier.ModelInducer
import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor


class NaiveBayes implements ModelInducer {

    @Override
    Classifier buildClassifier(DataSet dataSet) {
        BayesModel model = new BayesModel(dataSet.numClasses)
        dataSet.descriptors.eachWithIndex{ Descriptor descriptor, int i ->
            if (descriptor.attributeType == AttributeType.NUMERICAL)
                model.calculators.add(handleNumericalAttribute(i, dataSet))
            else {
                if (descriptor.classAttribute)
                    model.calculators.add(handleClassAttribute(i, dataSet))
                else
                    model.calculators.add(handleDiscreteAttribute(i, dataSet))
            }
        }
        return model
    }

    ProbabilityCalculator handleDiscreteAttribute(int attribute, DataSet dataSet){
        Map<Integer, Integer>  classCounts = dataSet.classDistribution
        int possibleValues = (dataSet.descriptors[attribute] as DiscreteDescriptor).possibleValues.size()
        List<List<Double>> valueCounts = []
        dataSet.numClasses.times {
            valueCounts.add([1]*possibleValues)
        }
        dataSet.instances.each {Instance it ->
            valueCounts[it.classAttributeValue][it.attributes[attribute] as int]++
        }
        DiscreteCalculator out = new DiscreteCalculator()
        int i = -1
        out.probabilities = valueCounts.collect { List<Double> counts ->
            i++
            counts.collect { double count ->
                count/(classCounts[i]+possibleValues)
            }
        }
        out
    }

    ProbabilityCalculator handleNumericalAttribute(int attribute, DataSet dataSet){
        Map<Integer, Double> sums = [:].withDefault {0}
        Map<Integer, Double> sumSqs = [:].withDefault {0}

        dataSet.instances.each {Instance it ->
            sums[it.classAttributeValue] += it.attributes[attribute]
            sumSqs[it.classAttributeValue] += it.attributes[attribute]**2
        }
        NumericalCalculator out = new NumericalCalculator()
        dataSet.numClasses.times {
            double n = dataSet.classDistribution[it]
            out.means[it] = sums[it]/n
            out.stdDevs[it] = Math.sqrt((sumSqs[it] - ((sums[it]**2)/n))/(n-1))
        }
        out

    }

    ProbabilityCalculator handleClassAttribute(int attribute, DataSet dataSet){
        float numInstances = dataSet.instances.size() as float
        Map<Integer, Integer> distribution = dataSet.classDistribution
        ClassCalculator out = new ClassCalculator()
        out.classProbabilities = distribution.collectEntries {int clazz, int count -> [clazz, count/numInstances]}
        out
    }
}