package com.github.ML

import com.github.ML.classifier.bayes.NaiveBayes
import com.github.ML.classifier.ila.ILA
import com.github.ML.classifier.ModelInducer
import com.github.ML.classifier.ila.RuleSet
import com.github.ML.data.DataSet
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.KBinsDiscretizer
import com.github.ML.data.transformation.KRangesDiscretizer
import com.github.ML.data.transformation.MDLDiscretizer
import com.github.ML.evaluation.CrossValidation
import com.github.ML.evaluation.Evaluation

//folds = [3,5,7]
folds=[5]

// for each discretization
//   for each dataset
//     for different disc params
//       do eval

ModelInducer inducer = new NaiveBayes()
Evaluation eval
Discretizer disc
DataSet discrete

//let's start with evaluationg on weather nominal, one experiment
//DataSet ds = Utils.weatherNominal
//
//println ds.name
//
//Utils.printHeader(['folds'], ["ruleSetSize"])
//folds.each {int it ->
//    eval = CrossValidation.evaluate(inducer, ds, it)
//    Utils.printLine([it.toString()], eval, [(inducer.buildClassifier(ds) as RuleSet).rules.size.toString()])
//}




println()
println()
println()


List<DataSet> dataSets = [Utils.iris, Utils.wine, Utils.diabetes]
ks = [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]



println "No discretization"
dataSets.each {
    println it.name
    Utils.printHeader(["folds"], [])
    discrete = it.copy()
    folds.each { int folds ->
        eval = CrossValidation.evaluate(inducer, discrete, folds)
        Utils.printLine([folds.toString()], eval, [])
    }
    println()
}


println "parameterlessMDL"
dataSets.each {
    println it.name
    Utils.printHeader(["folds"], [])
    disc = new MDLDiscretizer()
    discrete = disc.discretizeAll(it)
    folds.each { int folds ->
        eval = CrossValidation.evaluate(inducer, discrete, folds)
        Utils.printLine([folds.toString()], eval, [])
    }
    println()
}

println "DepthMDL"
dataSets.each {
    println it.name
    Utils.printHeader(["depth", "folds"], [])
    ks.each {int k ->
        disc = new MDLDiscretizer(true, k)
        discrete = disc.discretizeAll(it)
        folds.each { int folds ->
            eval = CrossValidation.evaluate(inducer, discrete, folds)
            Utils.printLine([k.toString(), folds.toString()], eval, [])
        }
    }
    println()
}

println "KBins"
dataSets.each {
    println it.name
    Utils.printHeader(["k", "folds"], [])
    ks.each {int k ->
        disc = new KBinsDiscretizer(k)
        discrete = disc.discretizeAll(it)
        folds.each { int folds ->
            eval = CrossValidation.evaluate(inducer, discrete, folds)
            Utils.printLine([k.toString(), folds.toString()], eval, [])
        }
    }
    println()
}

println "KRanges"
dataSets.each {
    println it.name
    Utils.printHeader(["k", "folds"], [])
    ks.each {int k ->
        disc = new KRangesDiscretizer(k)
        discrete = disc.discretizeAll(it)
        folds.each { int folds ->
            eval = CrossValidation.evaluate(inducer, discrete, folds)
            Utils.printLine([k.toString(), folds.toString()], eval, [])
        }
    }
    println()
}

