.DEFAULT_GOAL := help

help: ## Display help messages
	@grep -E '^[a-zA-Z_/-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

install: ## Docker Compose build
	docker-compose build
run: ## Run Docker Compose
	docker-compose up -d
down: ## Down Docker Compose
	docker-compose down
api-build: ## API Docker build
	./gradlew api:bootJar
	docker build -f docker/Dockerfile-api -t base-api .
api-run-local: ## API Docker run
	docker run --rm -p 18080:18080 --env-file=docker/local-container.env --name base-api base-api:latest
batch-build: ## Batch Docker build
	./gradlew batch:bootJar
	docker build -f docker/Dockerfile-batch -t base-batch .
batch-run-local: ## Batch Docker run
	docker run --rm --env-file=docker/local-container.env -e BATCH_ID="$(BATCH_ID)" --name base-batch base-batch:latest