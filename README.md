# Duplex scan for everybody

Converts pdf files generated by a simplex batch scanner into scans that would have been generated by a much more expensive duplex scanner.

## How it works

First scan the front pages of the document into a pdf file.
Then scan the back pages of the document, appending the scanned pages to the same pdf file.
The pdf now contains pages 1, 3, ..., 4, 2.
Process the pdf with this application. The order of the pages will be 1, 2, 3, ...

## Deployment and usage

### As groovy script

Prerequisites:

* Groovy (at least version 2.5)

#### Build and deploy

Execute the gradle task 'asGroovyScript'.

```
./gradlew asGroovyScript
```

This will create a file named `simplex-2-duplex-{version}.groovy` in `build/distributions`. Copy it to a location of your choice.

#### Usage

```
cat ~/your-simplex-scan | groovy simplex-2-duplex.groovy > your-duplex-scan.pdf 
```

### As AWS Lambda

Not so useful, because you can not upload more than 6 MB of data to an AWS Lamba. But fun.

#### Build and deploy

1. Build the deployment package

```
./gradlew asAwsLambda
```

2. Create an aws lambda function with the deployment package in build/distributions

* Runtime: Java 11 (Corretto)
* Handler: de.kieseltaucher.duplex.foreverybody.app.aws.AWSLambdaHandler::handleRequest

3. Add an API Gateway Trigger

* Type: REST
* Binary Media Types: `*/*`

Make a note of the api endpoint that this step created.

#### Usage

```
curl -X POST your-api-endpoint \
     -H 'content-type: application/pdf' 
     --data-binary '@your-simplex-scan.pdf' \
     > my-duplex-scan.pdf
```

### As standalone http server

A bit nerdy. This distribution can be hosted as web worker, e.g. at Heroku or any other PaaS-Provider.

Requirements: Java 11

#### Build and deploy

1. Build the deployment package

```
./gradlew asStandaloneServer
```

### Usage

Start the server in a shell (Ctrl+C will stop the server):
```
java -jar build/distribution/duplex-scan-for-everybody-{version}.jar
```

This will start an http server listening on port 8080.
In order to change the port to e.g. 5000, use `java java -jar build/distribution/duplex-scan-for-everybody-{version}.jar --port 5000`

To print usage info provide the option `--help` or `-h`.

Convert a pdf:

```
curl -X POST localhost:8080/simplex-2-duplex \
     -H 'content-type: application/pdf' 
     --data-binary '@your-simplex-scan.pdf' \
     > my-duplex-scan.pdf
```

