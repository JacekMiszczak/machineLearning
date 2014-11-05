package com.github.ML.data.transformation

import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import groovy.transform.Canonical

/**
 * MDL discretizer implemented as described in "Multi-Interval Discretization of Continuous-Valued
 * Attributes for Classification Learning" by Fayyad and Irani
 */
@Canonical
class MDLDiscretizer extends AbstractDiscretizer {

    boolean depthCriterion = false
    int maxDepth = 2 // you're going to end up with up to 2^(maxDepth) bins

    //@Override
    List<Double> calculateCutPoints(int index, DataSet dataSet, int depth = maxDepth) {
        if (depth == 0)
            return []
        List<Double> possibles = findCutPointCandidates(dataSet, index)
        if (possibles.empty)
            return []
        double best = 0
        double maxGain = -10
        possibles.each {
            double gain = gain(index, it, dataSet)
            if (gain > maxGain){
                maxGain = gain
                best = it
            }
        }
        if (depthCriterion ||  (maxGain > (calculateCriterionGain(index, best, dataSet)))){
            List<DataSet> divided = divideDataSet(index, best, dataSet)
            depth -= depthCriterion ? 1 : 0
            return [calculateCutPoints(index, divided[0], depth), best, calculateCutPoints(index, divided[1], depth)].flatten()
        } else {
            return []
        }
    }

    private double P(int classIdx, DataSet dataSet){
        Map<Integer, Integer> classDist = dataSet.classDistribution
        assert classDist[classIdx] == dataSet.count {it.classAttributeValue == classIdx}
        return ((classDist[classIdx] as double)/dataSet.size())
    }

    private double E(int attribute, double cutpoint, DataSet dataSet){
        List<DataSet> divided = divideDataSet(attribute, cutpoint, dataSet)
        (entropy(divided[0]) * divided[0].size() + entropy(divided[1]) * divided[1].size()) / dataSet.size()
    }

    private List<DataSet> divideDataSet(int attribute, double cutpoint, DataSet dataSet){
        List<DataSet> out = [dataSet.copyStructure(), dataSet.copyStructure()]
        dataSet.instances.each {
            out[it.attributes[attribute] > cutpoint ? 1 : 0].add(it)
        }
        assert out[0].size() + out[1].size() == dataSet.size()
        out[0].each{
            assert it.attributes[attribute] <= cutpoint
        }
        out[1].each {
            assert it.attributes[attribute] > cutpoint
        }
        out
    }

    private double entropy(DataSet dataSet){
        double out = 0
        dataSet.getUniqueValuesOf(dataSet.classAttributeIndex).each {
            double p = P(it, dataSet)
            out =+ p * log2(p)
        }


//        dataSet.classDescriptor.possibleValues.size().times {
//            double p = P(it, dataSet)
//            out =+ (p != 0 ? p * log2(p) : 0)
//        }
        return -out
    }

    private double log2(double n){
        (Math.log10(n) / Math.log10(2))
    }

    private double gain(int attribute, double cutpoint, DataSet dataSet){
        entropy(dataSet) - E(attribute, cutpoint, dataSet)
    }

    private List<Double> findCutPointCandidates(DataSet dataSet, int attribute){
        DataSet copied = dataSet.copy()
        copied.sort {it.attributes[attribute]}
        List<Double> out = []
        Instance previous = null
        copied.each { Instance it ->
            if (previous){
                if (previous.classAttributeValue != it.classAttributeValue)
                    out.add((previous.attributes[attribute] + it.attributes[attribute]) / 2)
            }
            previous = it
        }
        out
    }

    private calculateCriterionGain(int attribute, double cutpoint, DataSet dataSet){
        List<DataSet> divided = divideDataSet(attribute, cutpoint, dataSet)
        int k = dataSet.getUniqueValuesOf(dataSet.classAttributeIndex).size()
        int k1 = divided[0].getUniqueValuesOf(dataSet.classAttributeIndex).size()
        int k2 = divided[1].getUniqueValuesOf(dataSet.classAttributeIndex).size()
        double delta = log2(Math.pow(3, k) - 2) - (k * entropy(dataSet) - k1 * entropy(divided[0]) - k2 * entropy(divided[1]))
        ((log2(dataSet.size() - 1) + delta) / dataSet.size())
    }
}
