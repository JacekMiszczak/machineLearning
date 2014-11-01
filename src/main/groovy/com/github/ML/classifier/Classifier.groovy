package com.github.ML.classifier

import com.github.ML.data.DataSet
import com.github.ML.data.Instance


public interface Classifier {

    public List<Integer> classify(List<Instance> instances)

    public int classify(Instance instance)



}