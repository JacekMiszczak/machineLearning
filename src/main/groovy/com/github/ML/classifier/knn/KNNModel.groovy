package com.github.ML.classifier.knn

import com.github.ML.classifier.AbstractClassifier
import com.github.ML.common.Pair
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import groovy.transform.Canonical
//import javafx.util.Pair

@Canonical
class KNNModel extends AbstractClassifier {

    int k
    DataSet dataSet
    Closure<Double> distanceFunction
    Closure<Integer> votingMethod


    @Override
    int classify(Instance instance) {
        return votingMethod.call(findKNearest(instance))
    }

    List<Pair<Instance, Double>> findKNearest(Instance instance){
        dataSet.collect { Instance it ->
            return new Pair<Instance, Double>(it, distanceFunction.call(instance, it))
        }.sort {Pair<Instance, Double> pair -> pair.second}.subList(0, k+1)
    }
}
