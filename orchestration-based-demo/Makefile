.PHONY: install run stop clean

MODULES := gateway orchestrator product order

install:
	@echo "Install common modules..."
	./mvnw install -pl common
	@echo "Packaging..."
	./mvnw package -DskipTests

run:
	@echo "Running all modules..."
	@for module in $(MODULES); do \
		java -jar $$module/target/$$module-1.0-SNAPSHOT.jar & \
	done
	@echo "Keep running"
	tail -f /dev/null

stop:
	@echo "Stopping all modules..."
	@for module in $(MODULES); do \
		pkill -f "java.*$$module" || true; \
	done

clean:
	./mvnw clean