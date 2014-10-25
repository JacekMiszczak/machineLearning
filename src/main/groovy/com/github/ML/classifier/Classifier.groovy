package com.github.ML.classifier

import com.github.ML.data.DataSet
import com.github.ML.data.Instance


public interface Classifier {

    public void buildClassifier(DataSet dataSet)

    public List<Number> classify(List<Instance> instances)

    public Number classify(Instance instance)



}