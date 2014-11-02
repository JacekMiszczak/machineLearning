package com.github.ML.data.transformation

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import groovy.transform.Canonical

@Canonical
class KRangesDiscretizer extends AbstractDiscretizer {

    int k

    @Override
    List<Double> calculateCutPoints(int index, DataSet dataSet) {
        assert dataSet.descriptors[index].attributeType == AttributeType.NUMERICAL

        List<Number> uniqueVals = dataSet.getUniqueValuesOf(index)

        List<Double> cutPoints = []
        uniqueVals.sort()

        double rangeSize = (uniqueVals[-1] - uniqueVals[0]) / k
        double first = uniqueVals[0]
        (k-1).times {
            first += rangeSize
            cutPoints.add(first)
        }

        cutPoints
    }
}
