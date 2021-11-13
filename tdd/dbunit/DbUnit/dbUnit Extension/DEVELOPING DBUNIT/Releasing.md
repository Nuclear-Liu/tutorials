## Releasing

### Overview

The following steps describe how to release dbUnit. 
While the Maven release plugin automates steps such as version change, commit, and deploy, dbUnit has manual file information update steps that are outside of its scope.

While the below steps do not use the Maven release plugin, you can use it by performing the manual file edits first then running the release plugin steps.

### Release Steps

1. Update pom.xml
    * Set version to release version
    * Update japicmp-maven-plugin configuration
        * Add reportSet for prior version to new version comparison
        * Comment out reportSet comparison to latest snapshot until a new snapshot exists
2. Update site.xml
    * Add menu entry for japicmp prior version to new version comparison
    * Comment out comparison to latest snapshot until a new snapshot exists
3. Update changes.xml
    * Update release version
    * Set release date to today
4. Update index.xml
    * Add entry to release section; ensure correct links
5. Commit files "Prep release x.y.z"
6. Tag the commit
7. Push the commit
8. Build and deploy the release 

    ```shell
    mvn clean
    mvn deploy -Psonatype-oss-release
    ```
   
9. Login to Sonatype OSS and process the release through staging (requires privileges)
10. Build and deploy the site
    * Build site: 
    
        `mvn site`
    * Review site results for issues and correct as necessary
    * Deploy to SourceForge; reference how-to: Deploying to sourceforge.net

        ```shell
        ssh -t sf-username,dbunit@shell.sourceforge.net create
        mvn site:deploy
        ```
      
11. Generate Announcement Email 
    `mvn changes:announcement-generate -Dchanges.version=theNewVersion`

    Review and adjust the text as desired before using.

12. Email Users List 
13. Post News on SourceForge 
14. Prepare next release in SCM

    * Update pom.xml
        * Update to SNAPSHOT version
        * Update japicmp-maven-plugin configuration with comparison of latest release to new snapshot
    * Update index.xml with new SNAPSHOT entry
    * Update changes.xml with new SNAPSHOT entry
