grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
    // configure settings for the run-app JVM
    run: [maxMemory: 1024, minMemory: 1024, debug: false, maxPerm: 256, forkReserve: false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

// Exclude files from a generated war
grails.war.resources = { stagingDir, args ->
    //delete (file: "${stagingDir}/WEB-INF/lib/hibernate-core-3.3.1.GA.jar")
    delete { fileset(dir: "${stagingDir}/bootswatch", includes: '*') }
    delete { fileset(dir: "${stagingDir}/swatchmaker", includes: '*') }
    delete { fileset(dir: "${stagingDir}/font-awesome", includes: '*') }
    delete { fileset(dir: "${stagingDir}/images", includes: '*.psd') }
    delete { fileset(dir: "${stagingDir}/sass", includes: '*') }
    delete { fileset(dir: "${stagingDir}/fancybox", includes: '*') }
};

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"

        mavenRepo "http://repo.spring.io/milestone/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.24'

        // Due to spring-web conflict, require include manually to solve problems
        //compile 'org.springframework:spring-web:3.2.4.RELEASE'
        compile (
                'org.springframework.social:spring-social-core:1.1.0.RELEASE',
                'org.springframework.social:spring-social-facebook:1.1.1.RELEASE') {
            transitive = false
        }

        // JetS3t Amazon S3
        runtime 'net.java.dev.jets3t:jets3t:0.9.0'
        
        compile 'commons-codec:commons-codec:1.8'

        //test 'com.googlecode.jmockit:jmockit:1.0'

        compile 'com.fasterxml.jackson.core:jackson-databind:2.3.3'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.53"

        // plugins for the compile step
        compile ":scaffolding:2.0.3"
        compile ':cache:1.1.2'

        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.15" // or ":hibernate4:4.1.11.1"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        runtime ":resources:1.2.7"
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"

        // Mail Support
        compile ":mail:1.0.1"

        // Twitter Bootstrap
        compile ":twitter-bootstrap:3.0.3"

        // Font Awesome
        //compile ":font-awesome-resources:3.2.1.3"
        //compile ":font-awesome-resources:4.0.3.1"

        // Google Closure Compiler plugin for Grails
        // This plugin compiles/optimizes your javascript resources with the Google Closure Compiler.
        // It provides three compilation levels. WHITESPACE_ONLY, SIMPLE_OPTIMIZATIONS and ADVANCED_OPTIMIZATIONS.
        //compile ":closure-compiler:0.9.1"

        //build ":svn:1.0.2"
      
        // Spring Security Core Plugin
        compile ":spring-security-core:2.0-SNAPSHOT"

        // Facebook Authentication for Spring Security Core plugin.
        compile ":spring-security-facebook:0.15.2-CORE2"

        // Twitter authentication support for the Spring Security plugin.
        compile ":spring-security-twitter:0.6"
        
        //build ":lesscss-resources:1.3.0.3"

        //runtime ":database-migration:1.1"

        //compile ':cache:1.0.1'
       
        //fix for cached-resources
        compile ":cache-headers:1.1.5"

        compile ":browser-detection:0.4.3"

		compile ":modernizr:2.7.1.1"

        // compile ":codenarc:0.20"

        // Grails Avatar Plugin
        // This plugin provides a taglib for displaying avatars. At the moment we support gravatars (globally-recognized avatars), Twitter avatar profile and Facebook avatar profile.
        compile ":avatar:0.6.3"

		// Google Chart Plugin
		// 移除原因：功能不完整且效果不佳
		//compile ":google-chart:0.5.2"

        runtime ":cors:1.1.6"
    }
}
