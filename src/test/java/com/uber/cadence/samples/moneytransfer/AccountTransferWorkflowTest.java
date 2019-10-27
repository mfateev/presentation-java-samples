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

package com.uber.cadence.samples.moneytransfer;

import static com.uber.cadence.samples.moneytransfer.AccountTransferWorker.TASK_LIST;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.testing.TestWorkflowEnvironment;
import com.uber.cadence.worker.Worker;
import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountTransferWorkflowTest {

  private TestWorkflowEnvironment testEnv;
  private Worker worker;
  private WorkflowClient workflowClient;

  @Before
  public void setUp() {
    testEnv = TestWorkflowEnvironment.newInstance();
    worker = testEnv.newWorker(TASK_LIST);
    worker.registerWorkflowImplementationTypes(AccountTransferWorkflowImpl.class);

    workflowClient = testEnv.newWorkflowClient();
  }

  @After
  public void tearDown() {
    testEnv.close();
  }

  @Test
  public void testTransfer() {
    Account activities = mock(Account.class);
    worker.registerActivitiesImplementations(activities);
    testEnv.start();
    WorkflowOptions options =
        new WorkflowOptions.Builder()
            .setTaskList(TASK_LIST)
            .setExecutionStartToCloseTimeout(Duration.ofDays(365))
            .build();
    AccountTransferWorkflow workflow =
        workflowClient.newWorkflowStub(AccountTransferWorkflow.class, options);
    workflow.transfer("account1", "account2", "reference1", 123);
    verify(activities).withdraw(eq("account1"), eq("reference1"), eq(123));
    verify(activities).deposit(eq("account2"), eq("reference1"), eq(123));
  }
}
