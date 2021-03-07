package com.github.enjektor.tutorial;

import com.github.enjektor.Int;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Qualifier;
import lombok.AllArgsConstructor;

@Dependency
@AllArgsConstructor
public class Util {

    private final AnyDependencyThatYouNeed<Integer> anyDependencyThatYouNeed;
    private final OtherDependency otherDependency;
    @Qualifier("aInt") private final Int anInt;

    public void invoke() {
        System.out.println(anInt.x());
        otherDependency.invoke();
        anyDependencyThatYouNeed.dummyMethod(new Integer[]{1, 2, 3});
    }

}
