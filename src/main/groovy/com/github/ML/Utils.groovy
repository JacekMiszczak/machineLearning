package com.github.ML

import com.github.ML.data.DataSet
import com.github.ML.data.loader.ARFFLoader
import com.github.ML.data.loader.Loader


class Utils {

    static DataSet getIris(){
        File iris = new File(Utils.classLoader.getResource("iris.arff").toURI())
        Loader loader = new ARFFLoader()
        loader.load(iris)
    }

    static DataSet getGlass(){
        File glass = new File(Utils.classLoader.getResource("glass.arff").toURI())
        Loader loader = new ARFFLoader()
        loader.load(glass)
    }

    static DataSet getWine(){
        File wine = new File(Utils.classLoader.getResource("wine.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(wine)
        ds.classAttributeIndex = 0
        return ds
    }

    static DataSet getWeatherNumeric(){
        File weather = new File(Utils.classLoader.getResource("weather.numeric.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(weather)
        return ds
    }

    static DataSet getWeatherNominal(){
        File weather = new File(Utils.classLoader.getResource("weather.nominal.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(weather)
        return ds
    }

}
