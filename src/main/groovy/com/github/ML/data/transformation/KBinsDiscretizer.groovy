package com.github.ML.data.transformation

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import groovy.transform.Canonical

@Canonical
class KBinsDiscretizer extends AbstractDiscretizer {

    int k

    @Override
    List<Double> calculateCutPoints(int index, DataSet dataSet) {
        assert dataSet.descriptors[index].attributeType == AttributeType.NUMERICAL

        List<Number> uniqueVals = dataSet.getUniqueValuesOf(index)

        List<Double> cutPoints = []
        uniqueVals.sort()
        int perBin = uniqueVals.size() / k

        for (int i = 1; i < k; i++){
            cutPoints.add(
                    (uniqueVals[i*perBin] + uniqueVals[(i*perBin)-1]) / 2
            )
        }
        cutPoints
    }
}
