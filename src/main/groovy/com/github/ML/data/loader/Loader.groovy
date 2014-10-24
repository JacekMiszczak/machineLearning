package com.github.ML.data.loader

import com.github.ML.data.DataSet


public interface Loader {

    DataSet load(File file)

}