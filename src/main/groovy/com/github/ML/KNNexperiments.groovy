package com.github.ML

import com.github.ML.classifier.ModelInducer
import com.github.ML.classifier.knn.KNN
import com.github.ML.data.DataSet
import com.github.ML.evaluation.CrossValidation

folds = [3,5,7]
//folds = [5]

ModelInducer inducer

List<DataSet> dataSets = [Utils.iris, Utils.wine, Utils.glassClean, Utils.diabetes, Utils.ionosphere]
//List<DataSet> dataSets = [Utils.wine]
ks = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]

Map<String, Closure<Double>> distances = ['euclidean': KNN.euclideanDistance, 'manhattan': KNN.manhattanDistance,
                                            'chebyshev': KNN.chebyshevDistance]
//Map<String, Closure<Double>> distances = ['euclidean': KNN.euclideanDistance]

Map<String, Closure<Integer>> votings = ['equal': KNN.equalVote, 'weighted': KNN.weightedVote]
//Map<String, Closure<Integer>> votings = ['weighted': KNN.weightedVote]





//full experiments

dataSets.each {DataSet ds ->
    println ds.name
    Utils.printHeader(['folds', 'k', 'distance', 'voting'], [])
    votings.each {String voteName, Closure<Integer> voteMethod ->
        distances.each {String distName, Closure<Double> distMethod ->
            ks.each {int k ->
                folds.each {int numFolds ->
                    inducer = new KNN(k, distMethod, voteMethod)
                    eval = CrossValidation.evaluate(inducer, ds, numFolds)
                    Utils.printLine([numFolds.toString(), k.toString(), distName, voteName], eval)
                }
            }
        }
    }
    println()
}