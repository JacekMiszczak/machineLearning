package com.github.ML

import com.github.ML.classifier.bayes.NaiveBayes
import com.github.ML.classifier.knn.KNN
import com.github.ML.data.DataSet
import com.github.ML.evaluation.Evaluation
import com.github.ML.evaluation.EvaluationUtils


ds = Utils.ionosphere


knn = new KNN(5, KNN.euclideanDistance, KNN.equalVote)
model = knn.buildClassifier(ds)

Evaluation eval = EvaluationUtils.evaluate(ds, model)
println eval.weightedRecall
println eval.weightedPrecision
println eval.weightedFMeasure