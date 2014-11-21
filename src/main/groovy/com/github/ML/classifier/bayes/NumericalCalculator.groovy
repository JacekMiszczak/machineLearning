package com.github.ML.classifier.bayes

import groovy.transform.Canonical

/**
 * We presume normal distribution
 */
@Canonical
class NumericalCalculator implements ProbabilityCalculator {

    Map<Integer, Double> means = [:].withDefault {0}
    Map<Integer, Double> stdDevs = [:].withDefault {0}

    @Override
    double calculateProbability(Number value, int clazz) {
        return 1/(stdDevs[clazz]* Math.sqrt(2*Math.PI)) * Math.exp(
                (-1*((value-means[clazz])**2)/(2*stdDevs[clazz]*stdDevs[clazz])))


    }
}
