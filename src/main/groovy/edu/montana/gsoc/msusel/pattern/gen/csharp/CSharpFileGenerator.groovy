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
package edu.montana.gsoc.msusel.pattern.gen.csharp

import edu.montana.gsoc.msusel.codetree.CodeTree
import edu.montana.gsoc.msusel.codetree.node.structural.Project
import edu.montana.gsoc.msusel.pattern.gen.AbstractFileGenerator
import groovy.xml.StreamingMarkupBuilder

import java.time.Year

/**
 * @author Isaac Griffith
 * @version 1.3.0
 */
class CSharpFileGenerator extends AbstractFileGenerator {

    /**
     * UUID for project
     */
    UUID projectID = UUID.randomUUID()
    /**
     * UUID for solution
     */
    UUID solutionID = UUID.randomUUID()
    /**
     * Constant defining the Visual Studio version
     */
    private static final String VS_VERSION = "12.0.31101.0"
    /**
     * Constant defining the minimum Visual Studio version
     */
    private static final String MIN_VS_VERSION = "10.0.40219.1"
    /**
     * Constant defining the Solution file format version
     */
    private static final String FORMAT_VERSION = "12.00"
    /**
     * Constant defining the Tools version
     */
    private static final String TOOLS_VERSION = "12.0"
    /**
     * Constant defining the .Net framework version
     */
    private static final String FRAMEWORK_VERSION = "v4.5.2"
    
    /**
     * Constructs a new C# File Generator
     */
    public CSharpFileGenerator() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param tree
     */
    void generate(CodeTree tree) {
    }

    /**
     * 
     */
    private void createNuGetConfig() {
    }

    @Override
    def createBuildFile() {
    }

    @Override
    def createLicenseFile() {
        '''
        The MIT License (MIT)

        [ProjectName] created by SparQLine Design Pattern Generator
        Copyright (c) 2015-2017 Isaac Griffith, SparQLine Analytics, LLC
        
        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:
        
        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.
        
        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
        '''.replaceAll("\\[ProjectName\\]", "${ctree.getSystem().getKey()}")
    }

    @Override
    def createReadmeMDFile() {
        '''
        #[ProjectName]
        An implementation of the [PatternName] as generated by the SparQLine Analytics, LLC Design Pattern Generator
        '''.replaceAll("\\[ProjectName\\]", "${ctree.getSystem().getKey()}")
        .replaceAll("\\[PatternName\\]", patternName)
    }

    def createSolutionFile() {
        '''
        Microsoft Visual Studio Solution File, Format Version [Format_Version]
        # Visual Studio 2015
        VisualStudioVersion = [VS_Version]
        MinimumVisualStudioVersion = [Min_Version]
        Project("{[SolutionID]}") = "[ProjectName]", "src\\[ProjectName]\\[ProjectName].csproj", "{[ProjectID]}"
        EndProject
        Global
            GlobalSection(SolutionConfigurationPlatforms) = preSolution
                Debug|Any CPU = Debug|Any CPU
                Release|Any CPU = Release|Any CPU
            EndGlobalSection
            GlobalSection(ProjectConfigurationPlatforms) = postSolution
                {[ProjectID]}.Debug|Any CPU.ActiveCfg = Debug|Any CPU
                {[ProjectID]}.Debug|Any CPU.Build.0 = Debug|Any CPU
                {[ProjectID]}.Release|Any CPU.ActiveCfg = Release|Any CPU
                {[ProjectID]}.Release|Any CPU.Build.0 = Release|Any CPU
            EndGlobalSection
            GlobalSection(SolutionProperties) = preSolution
                HideSolutionNode = FALSE
            EndGlobalSection
        EndGlobal
        '''.replaceAll("\\[SolutionID\\]", solutionID.toString())
        .replaceAll("\\[ProjectID\\]", projectID.toString())
    }

    def generateProjectFile(Project project) {
        StreamingMarkupBuilder smb = new StreamingMarkupBuilder()
        smb.encoding = 'UTF-8'
        def str = smb.bind { builder ->
            mkp.xmlDeclaration(version:'1.0')
            Project(ToolsVersion: "${TOOLS_VERSION}", DefaultTargets: "Build", xmlns: "http://schemas.microsoft.com/developer/msbuild/2003") {
                // TODO Set correct tools version and xmlns
                Import(Project: "\$(MSBuildExtensionsPath)\\\$(MSBuildToolsVersion)\\Microsoft.Common.props", Condition: "Exists('\$(MSBuildExtensionsPath)\\\$(MSBuildToolsVersion)\\Microsoft.Common.props')")
                PropertyGroup {
                    Configuration(Condition: " '\$(Configuration)' == '' ") { mkp.yield("Release") }
                    Platform(Condition: " '\$(Platform)' == '' ") { mkp.yield("AnyCPU") }
                    ProjectGuid("${projectID.toString()}")
                    OutputType("Exe")
                    AppDesignerFolder("Properties")
                    RootNamespace("dpgen")
                    AssemblyName("${project.name()}")
                    TargetFrameworkVersion("${FRAMEWORK_VERSION}") // TODO Set correct framework version
                    FileAlignment("512") // TODO Identify what this means
                    AutoGenerateBindingRedirects("true")
                    PreBuildEvent()
                    PostBuildEvent()
                    OutputPath("bin\\Release")
                }
                PropertyGroup(Condition: " '\$(Configuration)|\$(Platform)' == 'Debug|AnyCPU' ") {
                    PlatformTarget("x64")
                    DebugSymbols("true")
                    DebugType("full")
                    Optimize("false")
                    OutputPath("bin\\Release")
                    DefineConstants("DEBUG;TRACE")
                    ErrorReport("prompt")
                    WarningLevel("4")
                }
                PropertyGroup(Condition: " '\$(Configuration)|\$(Platform)' == 'Release|AnyCPU' ") {
                    PlatformTarget("x64")
                    DebugType("pdbonly")
                    Optimize("true")
                    OutputPath("bin\\Release")
                    DefineConstants("TRACE")
                    ErrorReport("prompt")
                    WarningLevel("4")
                }
                ItemGroup {
                    // TODO ensure correct references are set
                    Reference(Include: "Project")
                    Reference(Include: "Project.Core")
                    Reference(Include: "Project.Drawing")
                    Reference(Include: "Project.Xml.Linq")
                    Reference(Include: "Project.Data.DataSetExtensions")
                    Reference(Include: "Microsoft.CSharp")
                    Reference(Include: "Project.Data")
                    Reference(Include: "Project.Xml")
                    Reference(Include: "WindowsBase")
                }
                ItemGroup {
                    // TODO include all files from this project
                    project.files().each { file ->
                        Compile(Include: file.name())
                    }
                    Compile(Include: "Properties\\AssemblyInfo.cs")
                }
//                ItemGroup {
//                    None(Include: "App.config") // TODO identify if we need this or not
//                }
                Import(Project: "\$(MSBuildToolsPath)\\Microsoft.CSharp.targets")
                mkp.comment("To modify your build process, add your task inside one of the targets below and uncomment it.\nOther similar extension points exist, see Microsoft.Common.targets.")
                Target(Name: "BeforeBuild") {
                }
                Target(Name: "AfterBuild") {
                }
            }
        }
    }
    
    def generateNuGetConfigFile() {
        StreamingMarkupBuilder smb = new StreamingMarkupBuilder()
        smb.encoding = 'UTF-8'
        def str = smb.bind { builder ->
            mkp.xmlDeclaration(version:'1.0')
            configuration {
                config {
                    mkp.comment '''
                        Used to specify the default location to expand packages.
                        See: nuget.exe help install
                        See: nuget.exe help update
                    '''
                    add(key:"repositoryPath", value: "packages")
            
                    mkp.comment "Proxy settings"
                }
            
                packageRestore {
                    mkp.comment "Allow NuGet to download missing packages"
                    add(key: "enabled", value: "True")
            
                    mkp.comment "Automatically check for missing packages during build in Visual Studio"
                    add(key: "automatic", value: "True")
                }
            
                mkp.comment '''
                    Used to specify the default Sources for list, install and update.
                    See: nuget.exe help list
                    See: nuget.exe help install
                    See: nuget.exe help update
                '''
                packageSources {
                    add(key: "NuGet official package source", value: "https://nuget.org/api/v2/")
                }
            
                mkp.comment "Used to store credentials"
                packageSourceCredentials()
            
                mkp.comment "Used to disable package sources"
                disabledPackageSources()
            
                mkp.comment '''
                    Used to specify default API key associated with sources.
                    See: nuget.exe help setApiKey
                    See: nuget.exe help push
                    See: nuget.exe help mirror
                '''
                apikeys()
            }
        }
    }
    
    def generateAppConfigFile() {
        ''
    }

    def generateAssemblyInfo(UUID id) {
        '''
        using Project.Reflection;
        using Project.Runtime.CompilerServices;
        using Project.Runtime.InteropServices;
        
        [assembly: AssemblyTitle("[ProjectName]")]
        [assembly: AssemblyDescription("")]
        [assembly: AssemblyConfiguration("")]
        [assembly: AssemblyCompany("SparQLine Analytics, LLC")]
        [assembly: AssemblyProduct("[ProjectName]")]
        [assembly: AssemblyCopyright("Copyright ©  [Year]")]
        [assembly: AssemblyTrademark("")]
        [assembly: AssemblyCulture("")]
        
        [assembly: ComVisible(false)]
        
        [assembly: Guid("[UUID]")]
        
        [assembly: AssemblyVersion("1.0.0.0")]
        [assembly: AssemblyFileVersion("1.0.0.0")]
        '''.replaceAll("\\[Year\\]", Year.now().getValue())
        .replaceAll("\\[UUID\\]", id.toString())
        .replaceAll("\\[ProjectName\\]", ctree.getSystem().getKey())
    }

    void generateFiles() {

    }
}
