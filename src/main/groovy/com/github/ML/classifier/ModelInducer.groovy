package com.github.ML.classifier

import com.github.ML.data.DataSet


public interface ModelInducer {

    public Classifier buildClassifier(DataSet dataSet)

}