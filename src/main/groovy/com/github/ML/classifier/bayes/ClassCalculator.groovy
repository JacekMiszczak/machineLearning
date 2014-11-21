package com.github.ML.classifier.bayes

import groovy.transform.Canonical


@Canonical
class ClassCalculator implements ProbabilityCalculator {


    Map<Integer, Double> classProbabilities;

    @Override
    double calculateProbability(Number value, int clazz) {
        return classProbabilities[clazz]
    }
}
