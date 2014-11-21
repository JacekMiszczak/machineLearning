package com.github.ML.classifier.bayes

import com.github.ML.classifier.AbstractClassifier
import com.github.ML.data.Instance
import groovy.transform.Canonical

@Canonical
class BayesModel extends AbstractClassifier {

    int numClasses
    List<ProbabilityCalculator> calculators = []


    @Override
    int classify(Instance instance) {
        int best = 0
        double bestP = 0
        numClasses.times { int clazz ->
            double curr = 1
            instance.attributes.eachWithIndex{ Number value, int i ->
                curr *= calculators[i].calculateProbability(value, clazz)
            }
            if (curr > bestP){
                best = clazz
                bestP = curr
            }
        }
        best
    }

}
