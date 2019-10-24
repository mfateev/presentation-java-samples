/*
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.uber.cadence.samples.money;

import static com.uber.cadence.samples.common.SampleConstants.DOMAIN;

import com.uber.cadence.client.WorkflowClient;
import java.util.Random;
import java.util.UUID;

public class TransferRequester {

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) {
    String reference;
    int amountCents;
    if (args.length == 0) {
      reference = UUID.randomUUID().toString();
      amountCents = new Random().nextInt(5000);
    } else {
      reference = args[0];
      amountCents = Integer.parseInt(args[1]);
    }
    WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);

    // now we can start running instances of our saga - its state will be persisted
    AccountTransferWorkflow transferWorkflow =
        workflowClient.newWorkflowStub(AccountTransferWorkflow.class);
    WorkflowClient.start(
        transferWorkflow::transfer, "account1", "account2", reference, amountCents);
    System.exit(0);
  }
}
