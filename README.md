Enjektor is an application framework and inversion of control container(IoC) for the Java platform.


### Quick Installation

If you are using **Maven** you can add the repository by adding the following XML to your project pom.xml file.

Add as parent to your pom.xml;

```xml
<parent>
    <groupId>com.github.enjektor</groupId>
    <artifactId>enjektor</artifactId>
    <version>0.0.4</version>
</parent>
```

Dependencies that you need to use to enjektor:

```xml
 <dependencies>
        <dependency>
            <groupId>com.github.enjektor</groupId>
            <artifactId>enjektor-context</artifactId>
        </dependency>

        <dependency>
            <groupId>com.github.enjektor</groupId>
            <artifactId>enjektor-utils</artifactId>
        </dependency>
</dependencies>
```

You don't have to care about versions of dependencies. Maven parent tag will do it for you.

### Basic Injection

Let's assume you need a class that returns a string and you don't want to create it directly. Enjektor helps you in this scenario.

In this scenario all classes that we have are in the same package/directory.

```tree
.
└── tutorial
    ├── AnyDependencyThatYouNeed.java
    └── Main.java
```

Check the snippet listed below:

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency // attention!
public class AnyDependencyThatYouNeed {

    public String dummyMethod() {
        return "foo-bar";
    }
}
```

#### What is the @Dependency annotation?

@Dependency is an annotation that a class should be registered in the IoC container.

You must specify the classes you want to add to the IoC container with @Dependency annotation.

---

Enjektor creates your dependencies automatically. All you need to know is that you can access your dependencies via ApplicationContext.

```java
import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(Main.class);
        AnyDependencyThatYouNeed anyDependencyThatYouNeed = applicationContext.getBean(AnyDependencyThatYouNeed.class);
        anyDependencyThatYouNeed.dummyMethod(); // foo-bar
    }
}
```

ApplicationContext object has two methods. These are:

```java
public interface ApplicationContext {
    <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;

    <T> T getBean(final Class<T> classType, final String beanName) throws IllegalAccessException, InstantiationException;
}
```

#### Demonstration of the first signature

The first signature is ideal for getting the concrete dependency. What do I mean by concrete dependency? It means that it is not the implementation of any interface.

For example:

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class StringUtils {

    public String upperCase() {
        throw new UnsupportedOperationException();
    }

    public String lowerCase() {
        throw new UnsupportedOperationException();
    }
}
```

```java
public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(Main.class);
        StringUtils stringUtils = applicationContext.getBean(StringUtils.class);
        stringUtils.lowerCase();
        stringUtils.upperCase();
    }
}
```

#### Demonstration of the second signature

Let's assume that we have a class and two concrete classes of the mentioned class.

##### Middleware

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public interface Middleware {
    void doFilterInternal(String password);
}
```

##### SessionMiddleware

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class SessionMiddleware implements Middleware {

    @Override
    public void doFilterInternal(String password) {
        System.out.println("session middleware has done");
    }
}
```

##### TokenMiddleware

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class SessionMiddleware implements Middleware {

    @Override
    public void doFilterInternal(String password) {
        System.out.println("session middleware has done");
    }
}
```

Let's assume that you need token middleware to secure your endpoints in your microservices.

You can do it in two ways using enjektor:

#### Default Bean Name

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(Main.class);
        Middleware middleware = applicationContext.getBean(Middleware.class, "tokenMiddleware");
        middleware.doFilterInternal("dummypass");
    }
}
```

#### Customized Bean Name

@Dependency annotation has a field named "name". If you do not modify this field enjektor creates the name of your dependency with this pattern:

- For example, let's say we have a dependency named TokenMiddleware.

> Enjektor modifies this name as "tokenMiddleware" and registers to the IoC container.

More examples about dependency naming policy:

```java
SecurityManager -> securityManager
EntityHolder -> entityHolder
Transaction -> transaction
DefaultJdbcRepository -> defaultJdbcRepository
```

let's change the dependency name to "token"

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency(name = "token")
public class TokenMiddleware implements Middleware {

    @Override
    public void doFilterInternal(String password) {
        System.out.println("token middleware has done!");
    }
}
```

> Notice the second parameter of applicationContext.

```java
package com.github.enjektor.tutorial;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(Main.class);
        Middleware middleware = applicationContext.getBean(Middleware.class, "token");
        middleware.doFilterInternal("dummypass");
    }
}
```
