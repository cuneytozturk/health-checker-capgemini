# integration tests
to run the integration tests make sure you have docker running and execute "mvn verify -P integration-tests" to run them in CLI
- this is done because the CI pipeline does not currently support running docker containers for the integration tests