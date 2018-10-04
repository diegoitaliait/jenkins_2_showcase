package jobs_dsl

import jenkins.showcase.Folders

Folders.loadRootFolders().each { folderName ->
    folder("jenkins2/${folderName}") {}

    listView("${folderName}/${folderName}") {
        description("${folderName} Jobs")
        filterBuildQueue()
        filterExecutors()
        recurse()

        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }
    }
}
