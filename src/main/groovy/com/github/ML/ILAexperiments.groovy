package com.github.ML

import com.github.ML.classifier.ILA
import com.github.ML.classifier.ModelInducer
import com.github.ML.data.DataSet
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.KBinsDiscretizer
import com.github.ML.evaluation.CrossValidation
import com.github.ML.evaluation.Evaluation


// for each discretization
//   for each dataset
//     for different disc params
//       do eval

ModelInducer ila = new ILA()
Evaluation eval
Discretizer disc
DataSet discrete

//let's start with evaluationg on weather nominal, one experiment
//DataSet ds = Utils.weatherNominal
//
//println ds.name
//eval = CrossValidation.evaluate(ila, ds)
//Utils.printHeader([], [])
//Utils.printLine([], eval, [])
//
//println()
//println()


List<DataSet> dataSets = [Utils.iris, Utils.wine]
ks = [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

//now let's check what K bins disc can do
println "KBins"
dataSets.each {
    println it.name
    Utils.printHeader(["k"], [])
    ks.each {int k ->
        disc = new KBinsDiscretizer(k)
        discrete = disc.discretizeAll(it)
        eval = CrossValidation.evaluate(ila, discrete)
        Utils.printLine([k.toString()], eval, [])
    }
    println()
}

