package com.github.ML.classifier

import com.github.ML.data.Instance


abstract class AbstractClassifier implements Classifier {

    public List<Number> classify(List<Instance> instances){
        instances.collect {Instance it ->
            classify(it)
        }
    }

}
