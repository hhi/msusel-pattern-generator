/**
 * MIT License
 *
 * MSUSEL Design Pattern Generator
 * Copyright (c) 2017-2018 Montana State University, Gianforte School of Computing
 * Software Engineering Laboratory
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.montana.gsoc.msusel.pattern.gen.java

/**
 * @author Isaac Griffith
 * @version 1.3.0
 */
class JavaGradleFileGenerator extends JavaFileGenerator {

    @Override
    def createBuildFile()
    {
        '''\
        // Apply the java plugin to add support for Java
        apply plugin: 'java'
        
        // In this section you declare where to find the dependencies of your project
        repositories {
            // Use 'jcenter' for resolving your dependencies.
            // You can declare any Maven/Ivy/file repository here.
            jcenter()
            mavenCentral()
        }
        
        // In this section you declare the dependencies for your production and test code
        dependencies {
            // The production code uses the SLF4J logging API at compile time
            compile 'org.slf4j:slf4j-api:1.7.21'
            
            // Declare the dependency for your favourite test framework you want to use in your tests.
            // TestNG is also supported by the Gradle Test task. Just change the
            // testCompile dependency to testCompile 'org.testng:testng:6.8.1' and add
            // 'test.useTestNG()' to your build script.
            testCompile 'junit:junit:4.12'
            testCompile 'org.mockito:mockito-all:1.10.19'
        }
        '''.stripIndent()
    }
    
    private createSettingGradle() {
        """\
        /*
        // To declare projects as part of a multi-project build use the 'include' method
        include 'shared'
        include 'api'
        include 'services:webservice'
        */
        
        rootProject.name = '${tree.getProject().getKey()}'
        """.stripIndent()
    }
    
    private createGradleBatFile() {
        '''
        @if "%DEBUG%" == "" @echo off
        @rem ##########################################################################
        @rem
        @rem  Gradle startup script for Windows
        @rem
        @rem ##########################################################################
        
        @rem Set local scope for the variables with windows NT shell
        if "%OS%"=="Windows_NT" setlocal
        
        @rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
        set DEFAULT_JVM_OPTS=
        
        set DIRNAME=%~dp0
        if "%DIRNAME%" == "" set DIRNAME=.
        set APP_BASE_NAME=%~n0
        set APP_HOME=%DIRNAME%
        
        @rem Find java.exe
        if defined JAVA_HOME goto findJavaFromJavaHome
        
        set JAVA_EXE=java.exe
        %JAVA_EXE% -version >NUL 2>&1
        if "%ERRORLEVEL%" == "0" goto init
        
        echo.
        echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
        echo.
        echo Please set the JAVA_HOME variable in your environment to match the
        echo location of your Java installation.
        
        goto fail
        
        :findJavaFromJavaHome
        set JAVA_HOME=%JAVA_HOME:"=%
        set JAVA_EXE=%JAVA_HOME%/bin/java.exe
        
        if exist "%JAVA_EXE%" goto init
        
        echo.
        echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
        echo.
        echo Please set the JAVA_HOME variable in your environment to match the
        echo location of your Java installation.
        
        goto fail
        
        :init
        @rem Get command-line arguments, handling Windowz variants
        
        if not "%OS%" == "Windows_NT" goto win9xME_args
        if "%@eval[2+2]" == "4" goto 4NT_args
        
        :win9xME_args
        @rem Slurp the command line arguments.
        set CMD_LINE_ARGS=
        set _SKIP=2
        
        :win9xME_args_slurp
        if "x%~1" == "x" goto execute
        
        set CMD_LINE_ARGS=%*
        goto execute
        
        :4NT_args
        @rem Get arguments from the 4NT Shell from JP Software
        set CMD_LINE_ARGS=%$
        
        :execute
        @rem Setup the command line
        
        set CLASSPATH=%APP_HOME%\\gradle\\wrapper\\gradle-wrapper.jar
        
        @rem Execute Gradle
        "%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%
        
        :end
        @rem End local scope for the variables with windows NT shell
        if "%ERRORLEVEL%"=="0" goto mainEnd
        
        :fail
        rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
        rem the _cmd.exe /c_ return code!
        if  not "" == "%GRADLE_EXIT_CONSOLE%" exit 1
        exit /b 1
        
        :mainEnd
        if "%OS%"=="Windows_NT" endlocal
        
        :omega
        '''
    }

    void generateFiles() {
        def tree = new FileTreeBuilder()
        tree.dir("${base}") {
            'build.gradle'(createBuildFile())
//            'gradlew'(createGradlewFile())
//            'gradlew.bat'(createGradleBatFile())
            'settings.gradle'(createSettingGradle())
            'LICENSE'(createLicenseFile())
            'README.md'(createReadmeMDFile())
        }
    }
}
