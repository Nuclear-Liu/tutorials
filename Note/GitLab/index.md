# GitLab

## Install

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


