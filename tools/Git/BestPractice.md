# Best Practice

> idea 版本控制菜单快捷键: `alt` + <code>`</code>

1. 使用 `git stash` 暂存当前进度，切换分支处理问题，使用 `git stash pop` 释放暂存的进度；
2. 使用 `git cherry-pick` 有选择的合并提交
    * `git cherry-pick <commit-hash>` 合并一个commit
    * `git cherry-pick <commit-hash-1> <commit-hash-2>` 合并多个 commit
    * `git cherry-pick <commit-hash-s>...<commit-hash-e>` 合并连读的多个 commit
3. 使用 `git add -p` 挑选改动提交
4. 使用 `git grep $regexp $(git rev-list --all)` 从所有的提交中查找代码