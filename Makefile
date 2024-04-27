api-build:
	./gradlew api:bootJar
	docker build -f docker/Dockerfile-api -t base-api .
api-run-local:
	docker run --rm -p 18080:18080 --env-file=docker/local-container.env --name base-api base-api:latest
batch-build:
	./gradlew batch:bootJar
	docker build -f docker/Dockerfile-batch -t base-batch .
batch-run-local:
	docker run --rm --env-file=docker/local-container.env -e BATCH_ID="$(BATCH_ID)" --name base-batch base-batch:latest