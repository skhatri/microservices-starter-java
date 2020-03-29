cwd=$(dirname $0)
docker build -t ${DOCKER_USER}/todo-postgres:11.5 -f ${cwd}/Dockerfile_pg ${cwd}
docke push ${DOCKER_USER}/todo-postgres:11.5

