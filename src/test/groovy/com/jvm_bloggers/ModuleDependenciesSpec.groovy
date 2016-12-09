package com.jvm_bloggers

import jdepend.framework.JDepend
import jdepend.framework.JavaClass
import jdepend.framework.JavaPackage
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Ignore
class ModuleDependenciesSpec extends Specification {

    static String BASE_PACKAGE = "com.jvm_bloggers"

    static String COMMON_PACKAGE = "${BASE_PACKAGE}.common"
    static String ENTITIES_PACKAGE = "${BASE_PACKAGE}.entities"
    static String DOMAIN_PACKAGE = "${BASE_PACKAGE}.domain"
    static String SERVICE_PACKAGE = "${BASE_PACKAGE}.service"
    static String FRONTEND_PACKAGE = "${BASE_PACKAGE}.frontend"

    static String WICKET_PACKAGE = "org.apache.wicket"
    static String WICKETSTUFF_PACKAGE = "org.wicketstuff"

    @Shared
    JDepend jdepend

    def setupSpec() {
        jdepend = new JDepend()
        jdepend.addDirectory("build/classes/main")
    }

    @Unroll
    def "should check for no dependencies between between #baseModule and #forbiddenModule"() {
        expect:
        assertPackageNotDependsOn(baseModule, forbiddenModule)

        where:
        baseModule       | forbiddenModule
        COMMON_PACKAGE   | ENTITIES_PACKAGE
        COMMON_PACKAGE   | DOMAIN_PACKAGE
        COMMON_PACKAGE   | SERVICE_PACKAGE
        COMMON_PACKAGE   | FRONTEND_PACKAGE

        ENTITIES_PACKAGE | DOMAIN_PACKAGE
        ENTITIES_PACKAGE | SERVICE_PACKAGE
        ENTITIES_PACKAGE | FRONTEND_PACKAGE

        DOMAIN_PACKAGE   | SERVICE_PACKAGE
        DOMAIN_PACKAGE   | FRONTEND_PACKAGE

//        SERVICE_PACKAGE  | ENTITIES_PACKAGE TODO: Think about fixing it in the future
        SERVICE_PACKAGE  | FRONTEND_PACKAGE

        FRONTEND_PACKAGE | ENTITIES_PACKAGE
        FRONTEND_PACKAGE | SERVICE_PACKAGE
    }

    @Unroll
    def "should not allow for Wicket dependencies in #module"() {
        expect:
        assertPackageNotDependsOn(module, WICKET_PACKAGE)
        assertPackageNotDependsOn(module, WICKETSTUFF_PACKAGE)

        where:
        module << [ COMMON_PACKAGE, ENTITIES_PACKAGE, DOMAIN_PACKAGE, SERVICE_PACKAGE ]
    }

    @Unroll
    def "should not allow for JPA related dependencies in #module"() {
        expect:
        assertPackageNotDependsOn(module, "javax.persistence")

        where:
        module << [ COMMON_PACKAGE, DOMAIN_PACKAGE, SERVICE_PACKAGE, FRONTEND_PACKAGE ]
    }

    @Unroll
    def "should not allow for Hibernate related dependencies in #module"() {
        expect:
        assertPackageNotDependsOn(module, "org.hibernate")

        where:
        module << [ COMMON_PACKAGE, DOMAIN_PACKAGE, SERVICE_PACKAGE, FRONTEND_PACKAGE ]
    }

    @Unroll
    def "should not allow for Spring Data related dependencies in #module"() {
        expect:
        assertPackageNotDependsOn(module, "org.springframework.data")

        where:
        module << [ COMMON_PACKAGE, SERVICE_PACKAGE, FRONTEND_PACKAGE ]
    }

    private void assertPackageNotDependsOn(String packageName, String forbiddenPackageName) {
        Collection<JavaPackage> projectPackages = jdepend.analyze()

        List<JavaPackage> packagesToAnalyze = projectPackages.findAll({ p -> p.getName().startsWith(packageName) })
        assert packagesToAnalyze.size() > 0, "Package with name $packageName does not exist in project!"

        List<JavaClass> classesList = packagesToAnalyze.collect({ p -> p.getClasses() }).flatten()
        List<String> classNamesViolatingRules = classesList
                .findAll { clazz -> containsForbiddenPackageInImport(clazz, forbiddenPackageName) }
                .collect({ clazz -> clazz.getName() })
        assert classNamesViolatingRules.isEmpty(), "Found violators with forbidden import >> $forbiddenPackageName << in following classes: \n\t - " + classNamesViolatingRules.join("\n\t - ")
    }

    private boolean containsForbiddenPackageInImport(JavaClass javaClass, String packageName) {
        List<String> importedClasses = javaClass.importedPackages.collect { p -> p.getName() }
        return importedClasses.any { importName -> importName.startsWith(packageName) }
    }

}