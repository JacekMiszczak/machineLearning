package com.github.ML.classifier.bayes


class DiscreteCalculator implements ProbabilityCalculator {

    List<List<Double>> probabilities

    @Override
    double calculateProbability(Number value, int clazz) {
        return probabilities[clazz][value as int]
    }
}
