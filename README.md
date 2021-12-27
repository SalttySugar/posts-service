# Posts service

***

## Table of content

* about
* architecture
* how to run
    * docker image
        * env variables
    * docker-compose
    * helm chart
        * values

## helm chart

### Application parameters

| name                              | description                                       | default   |
|-----------------------------------|---------------------------------------------------|-----------|
|kubernetes.enabled                 | Wether enable or not kubernetes integration       | false     |
|kubernetes.config                  | wether to enable or not kubernetes config search  | false     |
|kubernetes.config.name             | Name of the configmap in the cluster              |  " "      |
|kubernetes.config.namespace        | Namespace where configuration is stored           |  " "      |
|kubernetes.config.sources          | key-value pair of configs                         |  " "      |
|kubernetes.config.reload.enabled   | Enables configuration reload on change            |  true     |
|kubernetes.config.reload.mode      | Refresh strategy.                                 |  true     |
|kubernetes.config.reload.mode      | Refresh strategy.                                 |  true     |
|kubernetes.config.reload.mode      | Refresh strategy.                                 |  true     |

### Database Parameters

| name                      | description                                                               | value |
|---------------------------|---------------------------------------------------------------------------|-------|
| mongodb.enabled           | Deploy a MariaDB server to satisfy the applications database requirements | true  |
| mongodb.auth.rootPassword | MongoDB root user password                                                |  " "  |
| mongodb.auth.database     | MongoDB auth database                                                     | admin |
| mongodb.auth.username     | MongoDB auth username                                                     |  " "  |