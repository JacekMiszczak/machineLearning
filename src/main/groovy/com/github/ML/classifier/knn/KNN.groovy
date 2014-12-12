package com.github.ML.classifier.knn

import com.github.ML.classifier.Classifier
import com.github.ML.classifier.ModelInducer
import com.github.ML.common.Pair
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import groovy.transform.Canonical


@Canonical
class KNN implements ModelInducer {

    int k
    Closure<Double> distanceFunction
    Closure<Integer> votingMethod

    @Override
    Classifier buildClassifier(DataSet dataSet) {
        return new KNNModel(k, dataSet, distanceFunction, votingMethod)
    }


    static Closure<Double> euclideanDistance = {Instance a, Instance b ->
        double out = 0.0
        int cIndex = a.dataSet.classAttributeIndex
        a.dataSet.descriptors.size().times { int i ->
            if (i != cIndex)
                out += (a.attributes[i] - b.attributes[i])**2
        }
        out
    }

    static Closure<Double> manhattanDistance = {Instance a, Instance b ->
        double out = 0.0
        int cIndex = a.dataSet.classAttributeIndex
        a.dataSet.descriptors.size().times { int i ->
            if (i != cIndex)
                out += Math.abs(a.attributes[i] - b.attributes[i])
        }
        out
    }

    static Closure<Double> chebyshevDistance = {Instance a, Instance b ->
        List<Double> perDim = []
        int cIndex = a.dataSet.classAttributeIndex
        a.dataSet.descriptors.size().times { int i ->
            if (i != cIndex)
                perDim.add(Math.abs(a.attributes[i] - b.attributes[i]))
        }
        return perDim.max()
    }

    static Closure<Integer> equalVote = { List<Pair<Instance, Double>> nearest ->
        Map<Integer, Integer> scores = [:].withDefault {0}
        nearest.each {Pair<Instance, Double> pair ->
            scores[pair.first.classAttributeValue]++
        }
        return findMajorityVote(scores)
    }

    static Closure<Integer> weightedVote = {List<Pair<Instance, Double>> nearest ->
        Map<Integer, Double> scores = [:].withDefault {0}
        double sum = nearest.sum {Pair<Instance, Double> it -> Math.exp(-1 * it.second)}
        nearest.each {Pair <Instance, Double> pair ->
            scores[pair.first.classAttributeValue] += (Math.exp(-1 * pair.second) / sum)
        }
        return findMajorityVote(scores)

    }

    static Integer findMajorityVote(Map<Integer, Double> scores){
        int best = -1
        double bestScore = -1
        scores.each {int clazz, int score ->
            if (score > bestScore){
                best = clazz
                bestScore = score
            }
        }
        return best
    }
}
