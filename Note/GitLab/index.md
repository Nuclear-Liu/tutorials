# GitLab

## Install

环境变量：

```shell
export GITLAB_HOME=/home/node01/volume/gitlab
export GITLAB_RUNNER=/home/node01/volume/gitlab-runner
```

```shell
docker run --detach \
  --hostname gitlab.example.com \
  --publish 8443:443 --publish 8880:80 --publish 8822:22 \
  --name gitlab \
  --restart always \
  --volume $GITLAB_HOME/config:/etc/gitlab \
  --volume $GITLAB_HOME/logs:/var/log/gitlab \
  --volume $GITLAB_HOME/data:/var/opt/gitlab \
  --shm-size 256m \
  gitlab/gitlab-ce:15.0.2-ce.0
```

初始用户名： `root`
初始密码存放位置： `/etc/gitlab/initial_root_password`

```shell
docker run -d --name gitlab-runner --restart always \
  -v $GITLAB_RUNNER/config:/etc/gitlab-runner \
  -v /var/run/docker.sock:/var/run/docker.sock \
  gitlab/gitlab-runner:v15.0.0
```

```shell
docker exec -it gitlab-runner /bin/bash
```

```shell
gitlab-runner register
```

```log
root@79dd8cb87eb2:/# gitlab-runner register
Runtime platform                                    arch=amd64 os=linux pid=52 revision=febb2a09 version=15.0.0
Running in system-mode.                            
                                                   
Enter the GitLab instance URL (for example, https://gitlab.com/):
http://192.168.99.78:8880/
Enter the registration token:
GR1348941iMTWi3EteK8YXSkLgpXW
Enter a description for the runner:
[79dd8cb87eb2]: runner^H^H^H^C
root@79dd8cb87eb2:/# gitlab-runner register
Runtime platform                                    arch=amd64 os=linux pid=65 revision=febb2a09 version=15.0.0
Running in system-mode.                            
                                                   
Enter the GitLab instance URL (for example, https://gitlab.com/):
http://192.168.99.78:8880/
Enter the registration token:
GR1348941iMTWi3EteK8YXSkLgpXW
Enter a description for the runner:
[79dd8cb87eb2]: runner-docker
Enter tags for the runner (comma-separated):
docker
Enter optional maintenance note for the runner:
runner_docker
Registering runner... succeeded                     runner=GR1348941iMTWi3Et
Enter an executor: parallels, shell, virtualbox, docker-ssh+machine, docker+machine, kubernetes, custom, docker, docker-ssh, ssh:
docker
Enter the default Docker image (for example, ruby:2.7):
docker:20.10.7-dind
Runner registered successfully. Feel free to start it, but if it's running already the config should be automatically reloaded!
```
