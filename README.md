# Java Cadence Samples for OSS Presentation
This package contains two samples demoed at [Open Source Summit Europe (OSSEU)](https://osseu19.sched.com/event/TLDB/cadence-developer-oriented-workflow-platform-maxim-fateev-uber)

More Cadence info at:

* [Cadence Service](https://github.com/uber/cadence)
* [Cadence Java Client](https://github.com/uber/cadence-java-client)
* [Go Cadence Client](https://github.com/uber-go/cadence-client)

## Overview of the Samples

###Money Transfer Sample

Demonstrates a simple transfer from one account to another. 

###Money Batch Transfer Sample

A single deposit after multiple withdrawals. Demonstrates that workflow is stateful.

## Get the Samples

Run the following commands:

      git clone https://github.com/mfateev/presentation-java-samples.git
      cd presentation-java-samples

## Import into IntelliJ

In the IntelliJ user interface, navigate to **File**->**New**->**Project from Existing Sources**.

Select the cloned directory. In the **Import Project page**, select **Import project from external model**,
choose **Gradle** and then click **Next**->**Finish**.

## Build the Samples

      ./gradlew build

## Run Cadence Server

Run Cadence Server using Docker Compose:

    curl -O https://raw.githubusercontent.com/uber/cadence/master/docker/docker-compose.yml
    docker-compose up

If this does not work, see the instructions for running Cadence Server at https://github.com/uber/cadence/blob/master/README.md.

## Register the Domain

To register the *sample* domain, run the following command once before running any samples:

    ./gradlew -q execute -PmainClass=com.uber.cadence.samples.common.RegisterDomain

## See Cadence UI

The Cadence Server running in a docker container includes a Web UI.

Connect to [http://localhost:8088](http://localhost:8088).

Enter the *sample* domain. You'll see a "No Results" page. After running any sample, change the 
filter in the
top right corner from "Open" to "Closed" to see the list of the completed workflows.

Click on a *RUN ID* of a workflow to see more details about it. Try different view formats to get a different level
of details about the execution history.

## Install Cadence CLI

[Command Line Interface Documentation](https://mfateev.github.io/cadence/docs/08_cli)

## Run the samples

Each sample has specific requirements for running it. The following sections contain information about
how to run each of the samples after you've built them using the preceding instructions.

Don't forget to check unit tests found under src/test/java!

###Money Transfer Sample

Workflow Worker:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneytransfer.AccountTransferWorker
```
Activities Worker:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneytransfer.AccountActivityWorker
```
Initiate Transfer:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneytransfer.TransferRequester
```
###Money Batch Transfer Sample
Workflow Worker:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneybatch.AccountTransferWorker
```
Activities Worker:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneybatch.AccountActivityWorker
```
Initiate Transfer:
```
./gradlew -q execute -PmainClass=com.uber.cadence.samples.moneybatch.TransferRequester
```
