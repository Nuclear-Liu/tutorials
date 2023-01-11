# Command

> 常用命令

1. 新建虚拟环境: `conda create`
   1. `--name` `-n` : 指定虚拟环境名称 (`conda create --name <envname>`)
   2. `python=x.x` : 指定 Python 版本
2. 删除虚拟环境 `<envname>` : `conda remove`
   1. `--name` : 指定虚拟环境名称 (`conda remove --name <envname> --all`)
   2. `--all` : 所有包（即删除环境）
   3. `<package-name>` `<package-name>-<package-version>` : 指定要操作的包（版本） 不指定 `--name` 则操作当前所在环境
3. 虚拟环境安装包: `conda install`
    1. `--name` `-n` : 指定虚拟环境名称
    2. `<package-name>` `<package-name>-<package-version>` : 指定安装包（版本）(`conda install -n <env-name> <package-name>-<package-version>`)
4. 查看 Conda 信息: `conda info`
    1. `--envs` `-e` : 显示环境信息
5. 列出包信息: `conda list`
    1. `--name` `-n` : 指定虚拟环境名称
6. 切换虚拟环境: `activate <env-name>`
    1. Windows : `activate <env-name>`
    2. Linux / MacOS : `source activate <env-name>`
7. 退出当前虚拟环境: `deactivate`
   1. Windows : `deactivate`
   2. Linux / MacOS : `source deactivate`
8. 包索引: `conda search`
   1. `<package-name>` : 检索 `<package-name>` 信息
9. 更新虚拟环境: `conda update`