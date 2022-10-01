# Changelog


[Full Changelog](https://github.com/enjektor/enjektor/compare/4fc2fe1322501fb03a5a6cb8d445eb4b5b2fc31a...HEAD)

**Implemented enhancements:**

- annotate all interfaces with dependency [\#105](https://github.com/enjektor/enjektor/issues/105)
- ENJ-105 feat: create new scanner with less verbosity which scans conc… [\#109](https://github.com/enjektor/enjektor/pull/109) ([enesusta](https://github.com/enesusta))

**Fixed bugs:**

- Implement native bean get method to fix list injection [\#97](https://github.com/enjektor/enjektor/issues/97)
- Remove bean map from injection behaviour [\#93](https://github.com/enjektor/enjektor/issues/93)

**Closed issues:**

- create new scanner with less verbosity which scans concrete implementation of given interface [\#106](https://github.com/enjektor/enjektor/issues/106)
- seperate inject strategies [\#102](https://github.com/enjektor/enjektor/issues/102)
- Add new method which add pairs before context is heat [\#95](https://github.com/enjektor/enjektor/issues/95)
- Add put method to register external bean [\#91](https://github.com/enjektor/enjektor/issues/91)
- Add jar plugin to parent pom [\#88](https://github.com/enjektor/enjektor/issues/88)
- Add exec plugin to parent pom [\#86](https://github.com/enjektor/enjektor/issues/86)
- Add documentation template [\#84](https://github.com/enjektor/enjektor/issues/84)
- Add wrapper class which helps to bootstrap enjektor context [\#82](https://github.com/enjektor/enjektor/issues/82)
- Move enjektor-epel and enjektor-starter to seperate repository [\#80](https://github.com/enjektor/enjektor/issues/80)
- Delete enjektor-utils maven submodule, and move its sources to enjektor-core [\#78](https://github.com/enjektor/enjektor/issues/78)
- Fix DefaultInjectionStrategy injection bug. [\#71](https://github.com/enjektor/enjektor/issues/71)
- Read configuration file, and hold all key/values pair on ioc context [\#66](https://github.com/enjektor/enjektor/issues/66)
- Get rid of if-else statement on DependenciesAnnotationCollector [\#53](https://github.com/enjektor/enjektor/issues/53)
- Inject dependencies in class which annotated with @Dependencies [\#48](https://github.com/enjektor/enjektor/issues/48)
- Add qualifier property to @Inject annotation [\#42](https://github.com/enjektor/enjektor/issues/42)
- Fix bug on DefaultDependencyInitializer [\#39](https://github.com/enjektor/enjektor/issues/39)
- Implement a consumer that set key references to null to clear allocated memory which allocated by hashmaps [\#36](https://github.com/enjektor/enjektor/issues/36)
- Use weak map types to decrease memory consumption [\#34](https://github.com/enjektor/enjektor/issues/34)
- Reimplement application context algorithm with primitive collections to consume less memory. [\#32](https://github.com/enjektor/enjektor/issues/32)
- Plug-in dependency initializer moduls to scale DI horizontally [\#28](https://github.com/enjektor/enjektor/issues/28)
- Try to implement constructor based injection [\#27](https://github.com/enjektor/enjektor/issues/27)
- Add docs [\#17](https://github.com/enjektor/enjektor/issues/17)
- Create new maven module called enjektor-utils that contains utility classes. [\#12](https://github.com/enjektor/enjektor/issues/12)
- Implement a class that holds instances of given class. [\#9](https://github.com/enjektor/enjektor/issues/9)
- Implement configuration based injection [\#5](https://github.com/enjektor/enjektor/issues/5)
- Implement basic scanner that scannes all class [\#2](https://github.com/enjektor/enjektor/issues/2)
- Create sub-modules which is about core and context modules. [\#1](https://github.com/enjektor/enjektor/issues/1)

**Merged pull requests:**

- ENJ-102 feat: seperate inject strategies [\#103](https://github.com/enjektor/enjektor/pull/103) ([enesusta](https://github.com/enesusta))
- PR [\#101](https://github.com/enjektor/enjektor/pull/101) ([enesusta](https://github.com/enesusta))
- ENJ-97 Implement native bean get method to fix list injection [\#98](https://github.com/enjektor/enjektor/pull/98) ([enesusta](https://github.com/enesusta))
- Add new method which add pairs before context is heat [\#96](https://github.com/enjektor/enjektor/pull/96) ([enesusta](https://github.com/enesusta))
- ENJ-93 Remove bean map from injection behaviour [\#94](https://github.com/enjektor/enjektor/pull/94) ([enesusta](https://github.com/enesusta))
- ENJ-91 add put method to register external bean [\#92](https://github.com/enjektor/enjektor/pull/92) ([enesusta](https://github.com/enesusta))
- ENJ-88 add jar plugin [\#89](https://github.com/enjektor/enjektor/pull/89) ([enesusta](https://github.com/enesusta))
- ENJ-86 add exec plugin to parent pom [\#87](https://github.com/enjektor/enjektor/pull/87) ([enesusta](https://github.com/enesusta))
- Merge dev to master [\#85](https://github.com/enjektor/enjektor/pull/85) ([enesusta](https://github.com/enesusta))
- ENJ-82 add class which instantiate setup of enjektor context [\#83](https://github.com/enjektor/enjektor/pull/83) ([enesusta](https://github.com/enesusta))
- ENJ-80 move starter and epel to seperate repositories [\#81](https://github.com/enjektor/enjektor/pull/81) ([enesusta](https://github.com/enesusta))
- ENJ-78 move util sources to enjektor-core [\#79](https://github.com/enjektor/enjektor/pull/79) ([enesusta](https://github.com/enesusta))
- ENJ-71 Fix DefaultInjectionStrategy bug [\#72](https://github.com/enjektor/enjektor/pull/72) ([enesusta](https://github.com/enesusta))
- ENJ-68 Add StringListWrapper to wrap List\<String\> and implemented rel… [\#70](https://github.com/enjektor/enjektor/pull/70) ([enesusta](https://github.com/enesusta))
- ENJ-66 Read configuration file, and hold all key/values pair on ioc c… [\#67](https://github.com/enjektor/enjektor/pull/67) ([enesusta](https://github.com/enesusta))
- Release v0.0.7.1 [\#65](https://github.com/enjektor/enjektor/pull/65) ([enesusta](https://github.com/enesusta))
- ENJ-48 Add logics which related @Dependencies annotation [\#52](https://github.com/enjektor/enjektor/pull/52) ([enesusta](https://github.com/enesusta))
- Release v0.0.7 [\#47](https://github.com/enjektor/enjektor/pull/47) ([enesusta](https://github.com/enesusta))
- ISSUE-42 Implement qualifier logics for @Inject annotation [\#46](https://github.com/enjektor/enjektor/pull/46) ([enesusta](https://github.com/enesusta))
- ISSUE-39 Fix bug that causes null references on IoC container [\#40](https://github.com/enjektor/enjektor/pull/40) ([enesusta](https://github.com/enesusta))
- Release v0.0.6 [\#38](https://github.com/enjektor/enjektor/pull/38) ([enesusta](https://github.com/enesusta))
- ENJ-36 Implement deallocation handler to delete references which hold… [\#37](https://github.com/enjektor/enjektor/pull/37) ([enesusta](https://github.com/enesusta))
- ENJ-32 Use weakHashMap instead of hashMap [\#35](https://github.com/enjektor/enjektor/pull/35) ([enesusta](https://github.com/enesusta))
- ENJ-27 Try to implement constructorish based injection [\#31](https://github.com/enjektor/enjektor/pull/31) ([enesusta](https://github.com/enesusta))
- Release v0.0.5.1 [\#30](https://github.com/enjektor/enjektor/pull/30) ([enesusta](https://github.com/enesusta))
- ENJ-28 Implement plug-in dependency initializer logics [\#29](https://github.com/enjektor/enjektor/pull/29) ([enesusta](https://github.com/enesusta))
- Release v0.0.5 [\#26](https://github.com/enjektor/enjektor/pull/26) ([enesusta](https://github.com/enesusta))
- Implement PrimiviteApplicationContext [\#25](https://github.com/enjektor/enjektor/pull/25) ([enesusta](https://github.com/enesusta))
- Release v0.0.4 [\#19](https://github.com/enjektor/enjektor/pull/19) ([enesusta](https://github.com/enesusta))
- Add docs [\#18](https://github.com/enjektor/enjektor/pull/18) ([enesusta](https://github.com/enesusta))
- Implementation of multiple dependency configuration based injection [\#16](https://github.com/enjektor/enjektor/pull/16) ([enesusta](https://github.com/enesusta))
- v0.0.2 release. [\#15](https://github.com/enjektor/enjektor/pull/15) ([enesusta](https://github.com/enesusta))
- Add Dependency class that holds concrete instances of given class type [\#14](https://github.com/enjektor/enjektor/pull/14) ([enesusta](https://github.com/enesusta))
- Add naming utils to resolve classname on dependency context. [\#13](https://github.com/enjektor/enjektor/pull/13) ([enesusta](https://github.com/enesusta))
- Implemented subclass and base scanners. [\#3](https://github.com/enjektor/enjektor/pull/3) ([enesusta](https://github.com/enesusta))

