package com.github.ML.classifier

import com.github.ML.data.Instance
import groovy.transform.Canonical

@Canonical
class RuleSet extends AbstractClassifier{

    @Delegate
    List<Rule> rules = []

    @Override
    int classify(Instance instance) {

        return rules.find {Rule rule ->
            rule.instancePasses(instance)
        }?.conclusion
    }

    @Override public String toString(){
        rules.toString()
    }
}
