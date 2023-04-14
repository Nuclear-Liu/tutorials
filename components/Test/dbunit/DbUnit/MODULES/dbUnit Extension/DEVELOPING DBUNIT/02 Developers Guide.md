## Developers Guide _开发者指南_

Here you find all information needed to contribute good code. 
These are naming conventions, heading comments, javadoc and unit tests.


在这里您可以找到贡献好的代码所需的所有信息。
它们是命名约定、标题注释、javadoc 和单元测试。

---

## Java File Headers _Java 文件头_

For new class files this header is recommended.


对于新的类文件，建议使用此标头。

---

```text
    /*
     *
     * The DbUnit Database Testing Framework
     * Copyright (C)2002-2020, dbunit.sourceforge.net
     *
     * This library is free software; you can redistribute it and/or
     * modify it under the terms of the GNU Lesser General Public
     * License as published by the Free Software Foundation; either
     * version 2.1 of the License, or (at your option) any later version.
     *
     * This library is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
     * Lesser General Public License for more details.
     *
     * You should have received a copy of the GNU Lesser General Public
     * License along with this library; if not, write to the Free Software
     * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
     *
     */
```

For java class definitions this header is recommended.


对于 java 类定义，建议使用此标头。

---

```text
    /**
     * @author <developer> (<sf-user-name> AT users.sourceforge.net)
     * @since <dbunit-version>
     */
```

Here an example:


这里有一个例子

---

```text
    /**
     * @author gommma (gommma AT users.sourceforge.net)
     * @since 2.4.0
     */
```

Besides the standard javadoc tags it is desired to also add the dbunit version in which the method was introduced. 
This lets users and developers keep track of the changes that are made to the old/existing API.


除了标准的 javadoc 标签外，还需要添加引入该方法的 dbunit 版本
这让用户和开发人员可以跟踪对旧存在 API 所做的更改。

---

```text
    /**
     * @param myValue <some comment>
     * @since <dbunit-version>
     */
    public void assertSomething(String myValue) {
    ...
```

## Track Changes with Tickets _使用工单跟踪更改_

If one does not already exist, create a bug or feature ticket for the change.


如果尚不存在，请为更改创建错误或功能票证。

---

## Merge Requests or Patches _合并请求或补丁_

The easier you make it for dbUnit committers to apply changes, the more likely they will apply the changes.


使 dbUnit 提交者应用更改越容易，他们应用更改的可能性就越大。

---

Contribute changes using merge requests from your fork (preferred) or patches attached to tickets.


使用来自您的 fork（首选）或附加到票证的补丁的合并请求来贡献更改。

---

## Tests are Critical _测试至关重要_

Include tests proving the change fixes the issue or the new feature works. 
They especially help with understanding and long term quality, proving future changes do not break the feature.


包括证明更改修复了问题或新功能有效的测试。
它们特别有助于理解和长期质量，证明未来的变化不会破坏功能。

---

## Update Docs _更新文档_

Update relevant and/or create doc pages for the change and include them in the commit with the code changes.


更新相关的或为更改创建文档页面，并将它们包含在代码更改的提交中。

---

## Update `changes.xml` _更新 `changes.xml`_

`changes.xml` is how users easily see a release's change highlights.


`changes.xml` 是用户轻松查看版本更改亮点的方式。

---

* Add an entry for the change to `changes.xml` in the forthcoming release's section. 
    Place new entries after the existing ones.
* Include a terse summary at the end of description attribute.
* See existing entries for examples.


* 在即将发布的版本中为“changes.xml”的更改添加一个条目。
    在现有条目之后放置新条目。
* 在描述属性的末尾包含一个简短的摘要。
* 有关示例，请参阅现有条目。

---

Include this file in the commit with the code changes.


将此文件包含在代码更改的提交中。

---

## Format Code _格式代码_

* Follow the existing files' conventions as best possible.
* If using Eclipse, please import the formatter preference file `java-codestyle-formatter.xml` located in repository root directory.
* When a file has more than a small formatting correction with your changes, commit the formatting corrections in a separate commit with message "Reformat only". 
    Mixing formatting corrections with logic changes creates difficult code reviews and historical change reviews.


* 尽可能遵循现有文件的约定。
* 如果使用 Eclipse，请导入位于存储库根目录中的格式化程序首选项文件 `java-codestyle-formatter.xml` 。
* 当文件有多个小格式更正时，请使用“仅重新格式化”消息在单独的提交中提交格式更正。
    将格式更正与逻辑更改混合在一起会导致代码审查和历史变更审查困难重重。

---

## Commit Messages _提交消息_

* Follow git standard good commit message practices. 
    Good references on this include:
    * https://chris.beams.io/posts/git-commit/#seven-rules
    * https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
    * https://wiki.openstack.org/wiki/GitCommitMessages
* Prefix the commit topic with F (for feature) or B (for bug) and the tracker id, e.g. Fnnn or Bnnn. 
    See the commit history for examples.


* 遵循 git 标准的良好提交消息实践。
  这方面的良好参考包括：
    * https://chris.beams.io/posts/git-commit/#seven-rules
    * https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
    * https://wiki.openstack.org/wiki/GitCommitMessages
* 使用 F（用于功能）或 B（用于错误）和跟踪器 ID 为提交主题添加前缀，例如Fnnn 或 Bnnn。
  有关示例，请参阅提交历史记录。

---

## Keep Current with Master _与 Master 保持同步_

Rebase on master to keep your branch updated. 
Always check if needing a rebase just before creating the merge request or asking for review.


以 master 为基础，让你的分支保持更新。
在创建合并请求或要求审查之前，请始终检查是否需要 rebase。

---

## Run the DbUnit Tests _运行 DbUnit 测试_

Verify the build is clean with your changes and tests by running the dbUnit unit and integration tests against all the supported databases. 
Refer to Integration Tests for setup and running them.


通过对所有受支持的数据库运行dbUnit单元和集成测试，验证构建是否干净，是否包含更改和测试。
有关设置和运行它们的信息，请参阅集成测试。

---

## Discussions and Questions _讨论和问题_

For questions, assistance, or other help, please comment on the tracker item, merge request, or email the dev list.


对于问题、帮助或其他帮助，请评论跟踪器项目、合并请求或通过电子邮件发送开发人员列表。

---
