package com.github.ML.classifier.ila

import com.github.ML.classifier.Classifier
import com.github.ML.classifier.ModelInducer
import com.github.ML.data.DataSet
import com.github.ML.data.Instance


class ILA implements ModelInducer {

    boolean verbose = false

    @Override
    Classifier buildClassifier(DataSet dataSet) {
        List<Integer> nonClassAttributes = dataSet.nonClassAttributes

        List<DataSet> subtables = createSubtables(dataSet)
        subtables = subtables.grep({DataSet subtable ->
            !subtable.empty
        })
        RuleSet out = new RuleSet()
        DataSet unclassifiable = dataSet.copyStructure()
        subtables.size().times {
            DataSet active = subtables.head().copy()
            List<DataSet> nonActive = subtables.tail()
            int combinationSize = 1
            while (!active.empty){

                List<List<Integer>> partialPermutations = (kCombinations(nonClassAttributes, combinationSize) as List<List<Integer>>)
                List<Rule> allRules = []
                for (int i = 0; i < partialPermutations.size(); i++) {
                    List<List<Number>> valuesForPermutation = []
                    for (int j = 0; j < partialPermutations[i].size(); j++) {
                        valuesForPermutation.add(active.getUniqueValuesOf(partialPermutations[i][j]))
                    }
                    allRules.addAll(rulesFromCombinations(valuesForPermutation, partialPermutations[i]))

                }
                List<Rule> filteredRules = filterRules(allRules, active, nonActive)

                if (!filteredRules){
                    if (combinationSize == nonClassAttributes.size()){
                        if (verbose)
                            println("Two identical elements have two different classes. Full classifier cannot be built.")
                        active.each {
                            unclassifiable.addInstance(it.copy())
                        }
                        break
                    }
                    combinationSize++
                    continue
                }

                Rule bestRule = findBestRule(filteredRules, active)
                bestRule.conclusion = active[0].classAttributeValue
                List<Instance> remaining = active.grep({ Instance instance ->
                    !bestRule.instancePasses(instance)
                })
                active.instances.clear()
                active.instances.addAll(remaining)
//                println bestRule
                out.add(bestRule)
            }


            subtables.add(subtables.remove(0))
        }
        out.add(createFallbackRule(unclassifiable, dataSet))
        out
    }


    private Rule createFallbackRule(DataSet remaining, DataSet all){
        if (remaining.empty)
            remaining = all
        int mostFrequentClass = -1
        int highestFrequency = -1
        remaining.classDistribution.each {int k, int v ->
            if (v > highestFrequency){
                mostFrequentClass = k
                highestFrequency = v
            }
        }
        Rule out = new Rule()
        out.conclusion = mostFrequentClass
        out
    }

    private List<DataSet> createSubtables(DataSet dataSet){
        List<DataSet> subtables = []
        dataSet.classDescriptor.possibleValues.size().times {
            subtables.add(dataSet.copyStructure())
        }
        dataSet.each {Instance instance ->
            subtables[instance.classAttributeValue].addInstance(instance.copy())
        }
        subtables
    }

    private List<List<Number>> kCombinations(List<Number> list, int k){
       List<List<Number>> combinations = []
        ([[true, false]]*list.size()).eachCombination {List <Boolean> combination ->
            if (combination.count(true) == k){
                combinations.add(combination.findIndexValues {it}.collect({list[it.toInteger()]}))
            }
        }
        return combinations
    }

    private List<Rule> rulesFromCombinations(List<List<Number>> lists, List<Integer> attributeIdxs){
        List<List<Number>> combinations = lists.combinations()

        List<Rule> out = []
        combinations.each{ List<Number> combination ->
            Rule rule = new Rule()
            combination.eachWithIndex{ Number value, int i ->
                rule.conditions[attributeIdxs[i]] = (value as int)
            }
            out.add(rule)
        }
        out
    }

    private List<Rule> filterRules(List<Rule> rules, DataSet active, List<DataSet> nonActive){
        List<Rule> out
        // first get rid of rules that no active instance passes, effectively ending up
        // only with attribute combinations existing in active
        out = rules.grep({Rule rule ->
            active.any({rule.instancePasses(it)})
        })

        // then get rid of rules, that any instance from non active tables passes
        out = out.grep({Rule rule ->
            !(nonActive.find { DataSet subtable ->
                subtable.any({rule.instancePasses(it)})
            })
        })
        out
    }

    private boolean onePasses(Rule rule, DataSet dataSet){
        boolean res = dataSet.find { Instance instance ->
            rule.instancePasses(instance)
//            if (rule.instancePasses(instance)){
//                res = true
//            }
        }
        return res
    }

    private Rule findBestRule(List<Rule> rules, DataSet table){
        int bestScore = 0
        Rule best = null
        rules.each { Rule rule ->
            int score = gradeRule(rule, table)
            if (score >= bestScore){
                bestScore = score
                best = rule
            }
        }
        best
    }

    private int gradeRule(Rule rule, DataSet table){
        int score= 0
        table.each {
            if (rule.instancePasses(it))
                score++
        }
        score
    }
}
