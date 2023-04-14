## Releasing _发布_

### Overview _概述_

The following steps describe how to release dbUnit. 
While the Maven release plugin automates steps such as version change, commit, and deploy, dbUnit has manual file information update steps that are outside of its scope.


以下步骤描述了如何发布 dbUnit。
虽然 Maven 发布插件自动执行诸如版本更改、提交和部署等步骤，但 dbUnit 具有超出其范围的手动文件信息更新步骤。

---

While the below steps do not use the Maven release plugin, you can use it by performing the manual file edits first then running the release plugin steps.


虽然以下步骤不使用 Maven 发布插件，但您可以通过先执行手动文件编辑然后运行发布插件步骤来使用它。

---

### Release Steps _发布步骤_

1. Update `pom.xml`
    * Set version to release version
    * Update `japicmp-maven-plugin` configuration
        * Add reportSet for prior version to new version comparison
        * Comment out reportSet comparison to latest snapshot until a new snapshot exists
2. Update `site.xml`
    * Add menu entry for japicmp prior version to new version comparison
    * Comment out comparison to latest snapshot until a new snapshot exists
3. Update `changes.xml`
    * Update release version
    * Set release date to today
4. Update `index.xml`
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

    * Update `pom.xml`
        * Update to SNAPSHOT version
        * Update `japicmp-maven-plugin` configuration with comparison of latest release to new snapshot
    * Update `index.xml` with new SNAPSHOT entry
    * Update `changes.xml` with new SNAPSHOT entry


1. 更新 `pom.xml`
    * 将版本设置为发布版本
    * 更新 `japicmp-maven-plugin` 配置
        * 将先前版本的 reportSet 添加到新版本比较
        * 注释掉 reportSet 与最新快照的比较，直到存在新快照
2. 更新 `site.xml`
    * 将 japicmp 先前版本的菜单项添加到新版本比较
    * 注释掉与最新快照的比较，直到存在新快照
3. 更新 `changes.xml`
    * 更新发布版本
    * 将发布日期设置为今天
4. 更新 `index.xml`
    * 在发布部分添加条目；确保链接正确
5. 提交文件 "Prep release x.y.z"
6. 标记提交
7. 推送提交
8. 构建和部署发布

    ```shell
    mvn clean
    mvn deploy -Psonatype-oss-release
    ```
   
9. 登录 Sonatype OSS 并通过 staging 处理发布（需要权限）
10. 构建和部署站点
    * 建站：
    
        `mvn site`
    * 查看站点结果的问题并根据需要进行更正
    * 部署到 SourceForge；参考方法：部署到 sourceforge.net

        ```shell
        ssh -t sf-username,dbunit@shell.sourceforge.net create
        mvn site:deploy
        ```
      
11. 生成公告邮件
    `mvn changes:announcement-generate -Dchanges.version=theNewVersion`

    使用前根据需要查看和调整文本。

12. 电子邮件用户列表
13. 在 SourceForge 上发布新闻
14. 在 SCM 中准备下一个版本

    * 更新 `pom.xml`
        * 更新到 SNAPSHOT 版本
        * 更新 `japicmp-maven-plugin` 配置，并将最新版本与新快照进行比较
    * 使用新的 SNAPSHOT 条目更新 `index.xml`
    * 使用新的 SNAPSHOT 条目更新 `changes.xml`

---
