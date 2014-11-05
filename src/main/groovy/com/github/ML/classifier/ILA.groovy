package com.github.ML.classifier

import com.github.ML.data.DataSet
import com.github.ML.data.Instance


class ILA implements ModelInducer {

    @Override
    Classifier buildClassifier(DataSet dataSet) {
        List<Integer> nonClassAttributes = dataSet.nonClassAttributes

        List<DataSet> subtables = createSubtables(dataSet)
        subtables = subtables.grep({DataSet subtable ->
            !subtable.empty
        })
        RuleSet out = new RuleSet()
        subtables.size().times {
            DataSet active = subtables.head().copy()
            List<DataSet> nonActive = subtables.tail()
            int combinationSize = 1
            while (!active.empty){

                List<List<Integer>> partialPermutations = partialPermutations(nonClassAttributes, combinationSize)
                List<Rule> allRules = []
                for (int i = 0; i < partialPermutations.size(); i++) {
                    List<List<Number>> valuesForPermutation = []
                    for (int j = 0; j < partialPermutations[i].size(); j++) {
                        valuesForPermutation.add(active.getUniqueValuesOf(partialPermutations[i][j]))
                    }
                    allRules.addAll(rulesFromCombinations(valuesForPermutation, partialPermutations[i]))

                }
                List<Rule> filteredRules = filterRules(allRules, nonActive)

                if (!filteredRules){
                    if (combinationSize == nonClassAttributes.size()){
                        println("Two identical elements have two different classes. Full classifier cannot be built.")
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
                out.add(bestRule)
            }


            subtables.add(subtables.remove(0))
        }
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

    private List<List<Number>> partialPermutations(List<Number> list, int k){
        Set<List<Number>> permutations = list.permutations()
        permutations = permutations.collect({it.take(k).sort()})
        return permutations.toList()
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

    private List<Rule> filterRules(List<Rule> rules, List<DataSet> subtables){
        List<Rule> out = []
//        rules.each { Rule rule ->
//            if (!(subtables.find { DataSet subtable ->
//                onePasses(rule, subtable)
//            }))
//                out.add(rule)
//        }
        out = rules.grep({Rule rule ->
            !(subtables.find { DataSet subtable ->
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
            if (score > bestScore){
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
