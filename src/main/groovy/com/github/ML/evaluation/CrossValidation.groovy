package com.github.ML.evaluation

import com.github.ML.classifier.Classifier
import com.github.ML.classifier.ModelInducer
import com.github.ML.data.DataSet
import com.github.ML.data.Instance


class CrossValidation{

    public static Evaluation evaluate(ModelInducer inducer, DataSet dataSet, int numFolds = 5){
        List<DataSet> folds = getFolds(dataSet, numFolds)

        Evaluation out = null

        numFolds.times {
            Classifier classifier = inducer.buildClassifier(folds.tail().sum() as DataSet)
            EvaluationUtils.evaluate(folds.head(), classifier, out)
            folds.add(folds.remove(0))
        }

        out
    }

    private static List<DataSet> getFolds(DataSet dataSet, int numFolds){
        List<DataSet> folds = []
        numFolds.times {
            folds.add(dataSet.copyStructure())
        }

        dataSet.eachWithIndex { Instance instance, int i ->
            folds[i % numFolds].addInstance(instance.copy())
        }

        folds
    }
}
