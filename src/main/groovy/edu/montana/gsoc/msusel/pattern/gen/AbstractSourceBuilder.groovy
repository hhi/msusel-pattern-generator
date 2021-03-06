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
package edu.montana.gsoc.msusel.pattern.gen

import com.google.common.collect.Lists
import edu.montana.gsoc.msusel.datamodel.DataModelMediator
import edu.montana.gsoc.msusel.datamodel.member.Method
import edu.montana.gsoc.msusel.datamodel.structural.File
import edu.montana.gsoc.msusel.datamodel.structural.Project
import edu.montana.gsoc.msusel.datamodel.type.Enum
import edu.montana.gsoc.msusel.datamodel.type.Type
import edu.montana.gsoc.msusel.pattern.cue.Cue
import edu.montana.gsoc.msusel.pattern.cue.CueManager
import edu.montana.gsoc.msusel.pattern.gen.event.EventManager
/**
 * @author Isaac Griffith
 * @version 1.3.0
 */
abstract class AbstractSourceBuilder {

    String base = "./"
    DataModelMediator tree
    EventManager evtMgr = EventManager.instance
    
    Cue currentCue
    AbstractPatternGenerator pg
    String pattern

    /**
     * @param node
     * @param tree
     * @return
     */
    def construct(Project node, DataModelMediator tree) {
        println ""
        println "Constructing Source Files from DataModelMediator"
        println ""

        selectCue()

        List<File> defpkg = []
        defpkg += tree.getUtils().getFiles().findAll { File file ->
            file.namespace == null
        }

        List<File> files = Lists.newArrayList(tree.getUtils().getFiles())
        files.removeAll(defpkg)

        def map = [:]
        files.each {
            if (map[it.namespace.name()])
                map[it.namespace.name()] << it
            else
                map[it.namespace.name()] = [it]
        }

        def filetree = new FileTreeBuilder()
        filetree.dir(base + "/src/main/java") {
            defpkg.each {
                "${it.name()}"(createUnitContents(it, StringBuilder.newInstance()))
            }
        }

        map.each {key, val ->
            File f = new File(base + "/src/main/java/" + key.toString().replaceAll(/\./, '/'))
            f.mkdirs()
            filetree = new FileTreeBuilder(f)
            filetree {
                val.each { File file ->
                    println "Creating file: ${file.name()}"
                    "${file.name()}"(createUnitContents(file, StringBuilder.newInstance()))
                }
            }
        }

        println "Done creating files"
        println ""
    }
    
    /**
     * @param file
     * @param builder
     * @return
     */
    abstract def createUnitContents(File file, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createTypeHeader(String designator, Type type, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createEnumItems(Enum type, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createFields(Type type, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createMethods(Type type, StringBuilder builder)
    
    /**
     * @param file
     * @param builder
     * @return
     */
    abstract def handleNamespace(File file, StringBuilder builder)
    
    /**
     * @param file
     * @param builder
     * @return
     */
    abstract def handleImports(File file, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createTypeComment(Type type, StringBuilder builder)
    
    /**
     * @param method
     * @param builder
     * @return
     */
    abstract def createMethodComment(Type type, Method method, StringBuilder builder)
    
    /**
     * @return
     */
    abstract String getLanguage()
    
    /**
     * @param file
     * @param builder
     * @return
     */
    abstract def createTypes(File file, StringBuilder builder)
    
    /**
     * @param type
     * @param builder
     * @return
     */
    abstract def createProperty(Type type, StringBuilder builder)
    
    void selectCue() {
        Random rand = new Random()
        println "Available patterns: ${CueManager.patterns}"
        println "Current pattern: ${pattern}"
        println "Available cues: ${CueManager.patterns[pattern].cues}"
        if (CueManager.patterns[pattern]) {
            int index = rand.nextInt(CueManager.patterns[pattern].cues.size())
            def list = []
            list += CueManager.patterns[pattern].cues.keySet()
            currentCue = CueManager.patterns[pattern].cues[list[index]]
            println "Registering for Cue: ${list[index]}"
            currentCue.register()
        }
        else
            currentCue = null
    }
}
