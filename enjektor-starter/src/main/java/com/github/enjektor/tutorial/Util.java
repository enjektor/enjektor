package com.github.enjektor.tutorial;

import com.github.enjektor.Int;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.annotations.Qualifier;

@Dependency
public class Util {

    @Inject
    private AnyDependencyThatYouNeed<Integer> anyDependencyThatYouNeed;

    @Inject
    private OtherDependency otherDependency;

    @Inject
    @Qualifier("aInt")
    private Int anInt;

    public void invoke() {
        System.out.println(anInt.x());
        otherDependency.invoke();
        anyDependencyThatYouNeed.dummyMethod(new Integer[]{1, 2, 3});
    }

}
