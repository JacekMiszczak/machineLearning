package com.github.ML.data.transformation

import com.github.ML.data.DataSet

/**
 * Created by Krecik on 24-10-2014.
 */
public interface Discretizer {

    public DataSet discretizeAll(DataSet dataSet)

    public DataSet discretizeAttribute(int index, DataSet dataSet)



}