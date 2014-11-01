package com.github.ML.evaluation

import com.github.ML.classifier.Classifier
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import groovy.transform.CompileStatic

@CompileStatic
class EvaluationUtils {

    static public Evaluation evaluate(DataSet dataSet, Classifier classifier, Evaluation partial=null){

        Evaluation out = partial ?: new Evaluation(dataSet.numClasses)

        dataSet.each { Instance instance ->
            out.addResult(classifier.classify(instance), instance.classAttributeValue)
        }


        out
    }
}
