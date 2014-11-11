package com.github.ML

import com.github.ML.classifier.ILA
import com.github.ML.classifier.ModelInducer
import com.github.ML.classifier.RuleSet
import com.github.ML.data.DataSet
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.KBinsDiscretizer
import com.github.ML.data.transformation.KRangesDiscretizer
import com.github.ML.data.transformation.MDLDiscretizer
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
DataSet ds = Utils.weatherNominal

println ds.name
eval = CrossValidation.evaluate(ila, ds)
Utils.printHeader([], ["ruleSetSize"])
Utils.printLine([], eval, [(ila.buildClassifier(ds) as RuleSet).rules.size.toString()])



println()
ds = Utils.wine
disc = new MDLDiscretizer(true, 8)
ds = disc.discretizeAll(ds)
Utils.prettyPrintRuleSet((ila.buildClassifier(ds) as RuleSet), ds)
println()
println()


List<DataSet> dataSets = [Utils.iris, Utils.wine]
ks = [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

//
//println "parameterlessMDL"
//dataSets.each {
//    println it.name
//    Utils.printHeader([], ["ruleSetSize"])
//    disc = new MDLDiscretizer()
//    discrete = disc.discretizeAll(it)
//    eval = CrossValidation.evaluate(ila, discrete)
//    int rulesSize = (ila.buildClassifier(discrete) as RuleSet).rules.size
//    Utils.printLine([], eval, [rulesSize.toString()])
//    println()
//}
//
//println "DepthMDL"
//dataSets.each {
//    println it.name
//    Utils.printHeader(["depth"], ["ruleSetSize"])
//    ks.each {int k ->
//        disc = new MDLDiscretizer(true, k)
//        discrete = disc.discretizeAll(it)
//        eval = CrossValidation.evaluate(ila, discrete)
//        int rulesSize = (ila.buildClassifier(discrete) as RuleSet).rules.size
//        Utils.printLine([k.toString()], eval, [rulesSize.toString()])
//    }
//    println()
//}
//
//println "KBins"
//dataSets.each {
//    println it.name
//    Utils.printHeader(["k"], ["ruleSetSize"])
//    ks.each {int k ->
//        disc = new KBinsDiscretizer(k)
//        discrete = disc.discretizeAll(it)
//        eval = CrossValidation.evaluate(ila, discrete)
//        int rulesSize = (ila.buildClassifier(discrete) as RuleSet).rules.size
//        Utils.printLine([k.toString()], eval, [rulesSize.toString()])
//    }
//    println()
//}
//
//println "KRanges"
//dataSets.each {
//    println it.name
//    Utils.printHeader(["k"], ["ruleSetSize"])
//    ks.each {int k ->
//        disc = new KRangesDiscretizer(k)
//        discrete = disc.discretizeAll(it)
//        eval = CrossValidation.evaluate(ila, discrete)
//        int rulesSize = (ila.buildClassifier(discrete) as RuleSet).rules.size
//        Utils.printLine([k.toString()], eval, [rulesSize.toString()])
//    }
//    println()
//}

