package com.github.ML

import com.github.ML.classifier.ModelInducer
import com.github.ML.classifier.bayes.NaiveBayes
import com.github.ML.classifier.ila.ILA
import com.github.ML.classifier.knn.KNN
import com.github.ML.data.DataSet
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.MDLDiscretizer
import com.github.ML.evaluation.CrossValidation

ModelInducer inducer
Discretizer disc = new MDLDiscretizer(true, 5)

List<DataSet> dataSets = [Utils.iris, Utils.wine, Utils.glassClean, Utils.diabetes]

dataSets.each {DataSet ds ->
    println ds.name
    discretized = disc.discretizeAll(ds)
    Utils.printHeader(['algorithm'])
    inducer = new NaiveBayes()
    eval = CrossValidation.evaluate(inducer, discretized, 5)
    Utils.printLine(['NB'], eval)

    inducer = new ILA()
    eval = CrossValidation.evaluate(inducer, discretized, 5)
    Utils.printLine(['ILA'], eval)

    inducer = new KNN(10, KNN.manhattanDistance, KNN.weightedVote)
    eval = CrossValidation.evaluate(inducer, ds, 5)
    Utils.printLine(['KNN'], eval)

    println()

}