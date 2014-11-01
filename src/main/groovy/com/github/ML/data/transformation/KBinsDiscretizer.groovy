package com.github.ML.data.transformation

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import groovy.transform.Canonical

@Canonical
class KBinsDiscretizer extends AbstractDiscretizer {

    int k

    @Override
    DataSet discretizeAttribute(int index, DataSet dataSet) {
        assert dataSet.descriptors[index].attributeType == AttributeType.NUMERICAL

        DataSet out = dataSet.copy()

        List<Number> uniqueVals = out.getUniqueValuesOf(index)

        List<Double> cutPoints = []
        uniqueVals.sort()
        int perBin = uniqueVals.size() / k

        for (int i = 1; i < k; i++){
            cutPoints.add(
                    (uniqueVals[i*perBin] + uniqueVals[(i*perBin)-1]) / 2
            )
        }

        transformDataSet(cutPoints, out, index)

        out
    }
}
